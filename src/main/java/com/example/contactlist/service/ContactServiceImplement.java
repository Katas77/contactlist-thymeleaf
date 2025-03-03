package com.example.contactlist.service;

import com.example.contactlist.exception.ContactNotFoundException;
import com.example.contactlist.model.Contact;
import com.example.contactlist.mapper.TaskRowMapper;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.dao.support.DataAccessUtils;
import org.springframework.jdbc.core.ArgumentPreparedStatementSetter;
import org.springframework.jdbc.core.BatchPreparedStatementSetter;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapperResultSetExtractor;
import org.springframework.stereotype.Service;

import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.util.List;
import java.util.Optional;
import java.util.concurrent.atomic.AtomicInteger;

@Service
@RequiredArgsConstructor
@Slf4j
public class ContactServiceImplement implements ContactService {
    private final AtomicInteger currentId = new AtomicInteger(1);
    private final JdbcTemplate jdbcTemplate;

    @Override
    public List<Contact> findAll() {
        String query = "SELECT * FROM contacts_schema.contacts ORDER BY id ASC";
        return jdbcTemplate.query(query, new TaskRowMapper());
    }

    @Override
    public Optional<Contact> findById(int id) {
        log.info("Calling ContactServiceImplement->findById with ID: {}", id);
        String sql = "SELECT * FROM contacts_schema.contacts WHERE id = ?";
        Contact contact = DataAccessUtils.singleResult(
                jdbcTemplate.query(sql, new ArgumentPreparedStatementSetter(new Object[]{id}),
                        new RowMapperResultSetExtractor<>(new TaskRowMapper(), 1)));
        return Optional.ofNullable(contact);
    }

    @Override
    public void save(Contact contact) {
        log.info("Calling ContactServiceImplement->save with Contact: {}", contact);
        contact.setId(newId());
        String sql = "INSERT INTO contacts (firstName, lastName, email, phone, id) VALUES (?, ?, ?, ?, ?)";
        jdbcTemplate.update(sql, contact.getFirstName(), contact.getLastName(),
                contact.getEmail(), contact.getPhone(), contact.getId());
    }

    @Override
    public void updateContact(Contact contact) {
        log.info("Calling ContactServiceImplement->update with Contact: {}", contact);
        Optional<Contact> existingContact = findById(contact.getId());

        if (existingContact.isPresent()) {
            String sql = "UPDATE contacts_schema.contacts SET firstName = ?, lastName = ?, email = ?, phone = ? WHERE id = ?";
            jdbcTemplate.update(sql, contact.getFirstName(), contact.getLastName(),
                    contact.getEmail(), contact.getPhone(), contact.getId());
        } else {
            log.warn("Contact with ID {} not found!", contact.getId());
            throw new ContactNotFoundException("Contact for update not found! ID: " + contact.getId());
        }
    }

    @Override
    public void deleteById(int id) {
        log.info("Calling ContactServiceImplement->delete with ID: {}", id);
        String sql = "DELETE FROM contacts_schema.contacts WHERE id = ?";
        jdbcTemplate.update(sql, id);
    }

    @Override
    public void patchInsert(List<Contact> contacts) {
        log.info("Calling batchInsert");
        String sql = "INSERT INTO contacts (firstName, lastName, email, phone, id) VALUES (?, ?, ?, ?, ?)";
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
    }

    @Override
    public void delAll() {
        String sql = "TRUNCATE TABLE contacts";
        jdbcTemplate.update(sql);
    }

    public int newId() {
        return currentId.getAndIncrement();
    }
}
