package com.example.contactlist;

import com.example.contactlist.exception.ContactNotFoundException;
import com.example.contactlist.model.Contact;
import com.example.contactlist.service.impl.ContactServiceImpl;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.jdbc.core.BatchPreparedStatementSetter;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.AdditionalMatchers.aryEq;
import static org.mockito.ArgumentMatchers.*;
import static org.mockito.Mockito.*;

public class ContactServiceImplTest {

    @Mock
    private JdbcTemplate jdbcTemplate;

    @InjectMocks
    private ContactServiceImpl service;

    private static final String SCHEMA_TABLE = "contacts_schema.contacts";

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    void findAll_returnsList() {
        Contact c1 = new Contact(); c1.setId(1); c1.setFirstName("John");
        Contact c2 = new Contact(); c2.setId(2); c2.setFirstName("Jane");
        List<Contact> expected = List.of(c1, c2);

        when(jdbcTemplate.query(anyString(), any(RowMapper.class))).thenReturn(expected);
        List<Contact> result = service.findAll();
        assertEquals(expected, result);
        verify(jdbcTemplate).query(anyString(), any(RowMapper.class));//метод Mockito, который проверяет, был ли вызван определённый метод jdbcTemplate.query(...)) any(RowMapper.class) — любой объект типа RowMapper.anyString() — любая строка
    }

    @Test
    void findById_found_returnsOptional() {
        Contact c = new Contact(); c.setId(1); c.setFirstName("John");
        when(jdbcTemplate.queryForObject(anyString(), any(RowMapper.class), eq(1))).thenReturn(c);
        Optional<Contact> result = service.findById(1);
        assertTrue(result.isPresent());
        assertEquals(c, result.get());
        verify(jdbcTemplate).queryForObject(anyString(), any(RowMapper.class), eq(1));
    }

    @Test
    void findById_notFound_returnsEmptyOptional() {
        when(jdbcTemplate.queryForObject(anyString(), any(RowMapper.class), eq(999)))
                .thenThrow(new EmptyResultDataAccessException(1));
        Optional<Contact> result = service.findById(999);
        assertTrue(result.isEmpty());
        verify(jdbcTemplate).queryForObject(anyString(), any(RowMapper.class), eq(999));
    }

    @Test
    void save_generatesNewId_andCallsUpdate() {
        Contact Alice = new Contact();
        Alice.setFirstName("Alice");
        Alice.setLastName("Smith");
        Alice.setEmail("alice@example.com");
        Alice.setPhone("+7 918 4306237");
        when(jdbcTemplate.queryForObject(contains("MAX(id)"), eq(Integer.class))).thenReturn(10);
        String insertSql = "INSERT INTO " + SCHEMA_TABLE + " (firstName, lastName, email, phone, id) VALUES (?, ?, ?, ?, ?)";
        service.save(Alice);
        assertEquals(10, Alice.getId());
        verify(jdbcTemplate).update(eq(insertSql),
                eq(Alice.getFirstName()),
                eq(Alice.getLastName()),
                eq(Alice.getEmail()),
                eq(Alice.getPhone()),
                eq(Alice.getId())
        );
    }

    @Test
    void updateContact_existing_callsUpdate() {
        Contact c = new Contact(); c.setId(5); c.setFirstName("B");
        when(jdbcTemplate.queryForObject(contains("SELECT 1 FROM"), eq(Integer.class), eq(5))).thenReturn(1);

        String updateSql = "UPDATE " + SCHEMA_TABLE + " SET firstName = ?, lastName = ?, email = ?, phone = ? WHERE id = ?";

        service.updateContact(c);

        verify(jdbcTemplate).update(eq(updateSql),
                eq(c.getFirstName()),
                eq(c.getLastName()),
                eq(c.getEmail()),
                eq(c.getPhone()),
                eq(c.getId())
        );
    }

    @Test
    void updateContact_notExisting_throwsContactNotFoundException() {
        Contact c = new Contact(); c.setId(999);

        when(jdbcTemplate.queryForObject(contains("SELECT 1 FROM"), eq(Integer.class), eq(999)))
                .thenThrow(new EmptyResultDataAccessException(1));
        assertThrows(ContactNotFoundException.class, () -> service.updateContact(c));
        verify(jdbcTemplate, never()).update(contains("SET firstName"), any(), any(), any(), any(), any());
    }

    @Test
    void deleteById_callsUpdate() {
        int id = 7;
        String deleteSql = "DELETE FROM " + SCHEMA_TABLE + " WHERE id = ?";
        service.deleteById(id);
        verify(jdbcTemplate).update(eq(deleteSql), eq(id));
    }

    @Test
    void batchInsert_callsBatchUpdate() {
        Contact a = new Contact(); a.setId(1); a.setFirstName("A");
        Contact b = new Contact(); b.setId(2); b.setFirstName("B");
        List<Contact> contacts = List.of(a, b);
        String insertSql = "INSERT INTO " + SCHEMA_TABLE + " (firstName, lastName, email, phone, id) VALUES (?, ?, ?, ?, ?)";
        service.batchInsert(contacts);
        verify(jdbcTemplate).batchUpdate(eq(insertSql), any(BatchPreparedStatementSetter.class));
    }


    @Test
    void search_emptyOrBlank_returnsFindAll() {
        Contact c = new Contact(); c.setId(1); c.setFirstName("X");
        List<Contact> all = List.of(c);
        when(jdbcTemplate.query(contains("ORDER BY id ASC"), any(RowMapper.class))).thenReturn(all);
        List<Contact> res1 = service.search("");
        List<Contact> res2 = service.search(null);
        List<Contact> res3 = service.search("   ");
        assertEquals(all, res1);
        assertEquals(all, res2);
        assertEquals(all, res3);

        verify(jdbcTemplate, atLeastOnce()).query(contains("ORDER BY id ASC"), any(RowMapper.class));
    }

    @Test
    void search_withQuery_callsJdbcQueryWithPattern() {
        Contact c = new Contact(); c.setId(2); c.setFirstName("Query");
        String q = "jo";
        String pattern = "%" + q.toLowerCase() + "%";

        when(jdbcTemplate.query(anyString(), aryEq(new Object[]{pattern, pattern, pattern, pattern}), any(RowMapper.class)))
                .thenReturn(List.of(c));
        List<Contact> res = service.search(q);
        assertEquals(1, res.size());
        assertEquals(c, res.get(0));
        verify(jdbcTemplate).query(anyString(), aryEq(new Object[]{pattern, pattern, pattern, pattern}), any(RowMapper.class));
    }

    @Test
    void generateNewId_returnsValueFromQuery() {
        when(jdbcTemplate.queryForObject(contains("MAX(id)"), eq(Integer.class))).thenReturn(42);
        int id = service.generateNewId();
        assertEquals(42, id);
    }

    @Test
    void delAll_callsTruncate() {
        String truncateSql = "TRUNCATE TABLE " + SCHEMA_TABLE;
        service.delAll();
        verify(jdbcTemplate).update(eq(truncateSql));
    }
}
