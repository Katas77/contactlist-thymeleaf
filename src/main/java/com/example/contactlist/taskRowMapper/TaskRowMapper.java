package com.example.contactlist.taskRowMapper;

import com.example.contactlist.model.Contact;
import org.springframework.jdbc.core.RowMapper;

import java.sql.ResultSet;
import java.sql.SQLException;

public class TaskRowMapper implements RowMapper<Contact> {
    @Override
    public Contact mapRow(ResultSet rs, int rowNum) throws SQLException {
        return Contact.builder()
                .id(rs.getInt("id"))
                .firstName(rs.getString("firstName"))
                .lastName(rs.getString("lastName"))
                .email(rs.getString("email"))
                .phone(rs.getString("phone")).build();
    }
}

/*
Класс TaskRowMapper реализует интерфейс RowMapper<Contact> и используется для преобразования результатов запроса к базе данных в объекты типа Contact. Давайте рассмотрим его назначение и пример реализации.
        Назначение TaskRowMapper
Основная цель класса TaskRowMapper — преобразовывать результаты выполнения SQL-запросов к объектам Contact. Обычно это используется в случаях,
когда запрос возвращает набор строк, каждая из которых соответствует одному контакту. После выполнения запроса база данных возвращает результат в виде набора строк,
а TaskRowMapper конвертирует эти строки в объекты Contact, которые затем можно использовать в приложении.*/
