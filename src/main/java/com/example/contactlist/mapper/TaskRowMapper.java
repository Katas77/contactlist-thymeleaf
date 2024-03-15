package com.example.contactlist.mapper;

import com.example.contactlist.model.Contact;
import org.springframework.jdbc.core.RowMapper;

import java.sql.ResultSet;
import java.sql.SQLException;

public class TaskRowMapper implements RowMapper<Contact> {
    @Override
    public Contact mapRow(ResultSet rs, int rowNum) throws SQLException {
        Contact contact=new Contact();
        contact.setId((int) rs.getInt("id"));
        contact.setFirstName(rs.getString("firstName"));
        contact.setLastName(rs.getString("lastName"));
        contact.setEmail(rs.getString("email"));
        contact.setPhone(rs.getString("phone"));
        return contact;
    }
}
