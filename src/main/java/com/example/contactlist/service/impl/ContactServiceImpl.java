package com.example.contactlist.service.impl;

import com.example.contactlist.exception.ContactNotFoundException;
import com.example.contactlist.model.Contact;
import com.example.contactlist.service.ContactService;
import com.example.contactlist.taskRowMapper.TaskRowMapper;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.BatchPreparedStatementSetter;
import org.springframework.stereotype.Service;

import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
@Slf4j
public class ContactServiceImpl implements ContactService {

    private final JdbcTemplate jdbcTemplate;
    private static final String SCHEMA_TABLE  = "contacts_schema.contacts";

    @Override
    public List<Contact> findAll() {
        String sql = "SELECT * FROM " + SCHEMA_TABLE + " ORDER BY id ASC";
        return jdbcTemplate.query(sql, new TaskRowMapper());
    }

    @Override
    public Optional<Contact> findById(int id) {
        log.info("Calling ContactServiceImpl->findById with ID: {}", id);
        String sql = "SELECT * FROM " + SCHEMA_TABLE  + " WHERE id = ?";
        try {
            Contact contact = jdbcTemplate.queryForObject(sql, new TaskRowMapper(), id);
            return Optional.ofNullable(contact);
        } catch (EmptyResultDataAccessException e) {
            return Optional.empty();
        }
    }

    @Override
    public void save(Contact contact) {
        log.info("Calling ContactServiceImpl->save with Contact: {}", contact);
        contact.setId(generateNewId());
        String sql = "INSERT INTO " + SCHEMA_TABLE  + " (firstName, lastName, email, phone, id) VALUES (?, ?, ?, ?, ?)";
        jdbcTemplate.update(sql,
                contact.getFirstName(),
                contact.getLastName(),
                contact.getEmail(),
                contact.getPhone(),
                contact.getId()
        );
    }

    @Override
    public void updateContact(Contact contact) {
        log.info("Calling ContactServiceImpl->update with Contact: {}", contact);
        if (!existsById(contact.getId())) {
            log.warn("Contact with ID {} not found!", contact.getId());
            throw new ContactNotFoundException("Contact for update not found! ID: " + contact.getId());
        }
        String sql = "UPDATE " + SCHEMA_TABLE  + " SET firstName = ?, lastName = ?, email = ?, phone = ? WHERE id = ?";
        jdbcTemplate.update(sql,
                contact.getFirstName(),
                contact.getLastName(),
                contact.getEmail(),
                contact.getPhone(),
                contact.getId()
        );
    }

    @Override
    public void deleteById(int id) {
        log.info("Calling ContactServiceImpl->delete with ID: {}", id);
        String sql = "DELETE FROM " + SCHEMA_TABLE + " WHERE id = ?";
        jdbcTemplate.update(sql, id);
    }

    @Override
    public void batchInsert(List<Contact> contacts) {
        log.info("Calling batchInsert with {} contacts", contacts.size());
        String sql = "INSERT INTO " + SCHEMA_TABLE  + " (firstName, lastName, email, phone, id) VALUES (?, ?, ?, ?, ?)";
        jdbcTemplate.batchUpdate(sql, new BatchPreparedStatementSetter() {
            @Override
            public void setValues(PreparedStatement ps, int i) throws SQLException {
                Contact contact = contacts.get(i);
                ps.setString(1, contact.getFirstName());
                ps.setString(2, contact.getLastName());
                ps.setString(3, contact.getEmail());
                ps.setString(4, contact.getPhone());
                ps.setLong(5, contact.getId());
            }

            @Override
            public int getBatchSize() {
                return contacts.size();
            }
        });
    }@Override
    public List<Contact> search(String query) {
        if (query == null || query.trim().isEmpty()) {
            return findAll();
        }
        String sql = "SELECT * FROM " + SCHEMA_TABLE + " WHERE LOWER(firstName) LIKE ? OR LOWER(lastName) LIKE ? OR LOWER(email) LIKE ? OR LOWER(phone) LIKE ? ORDER BY id ASC";
        String pattern = "%" + query.trim().toLowerCase() + "%";
        return jdbcTemplate.query(sql, new Object[]{pattern, pattern, pattern, pattern}, new TaskRowMapper());
    }

    public int generateNewId() {
        String sql = "SELECT COALESCE(MAX(id), 0) + 1 FROM " + SCHEMA_TABLE;
        return jdbcTemplate.queryForObject(sql, Integer.class);
    }

    @Override
    public void delAll() {
        String sql = "TRUNCATE TABLE " + SCHEMA_TABLE;
        jdbcTemplate.update(sql);
    }
    private boolean existsById(int id) {
        String sql = "SELECT 1 FROM " + SCHEMA_TABLE + " WHERE id = ?";
        try {
            jdbcTemplate.queryForObject(sql, Integer.class, id);
            return true;
        } catch (EmptyResultDataAccessException e) {
            return false;
        }
    }
}