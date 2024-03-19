package com.example.contactlist.mapper;

import com.example.contactlist.model.Contact;
import org.springframework.jdbc.core.RowMapper;

import java.sql.ResultSet;
import java.sql.SQLException;

public class TaskRowMapper implements RowMapper<Contact> {
    @Override
    public Contact mapRow(ResultSet rs, int rowNum) throws SQLException {
        Contact contact= Contact.builder()
                .id(rs.getInt("id"))
                .firstName(rs.getString("firstName"))
                .lastName(rs.getString("lastName"))
                .email(rs.getString("email"))
                .phone(rs.getString("phone")).build();
        return contact;
    }
}
