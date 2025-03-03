package com.example.contactlist.service;

import com.example.contactlist.model.Contact;

import java.util.List;
import java.util.Optional;

public interface ContactService {
    List<Contact> findAll();

    Optional<Contact> findById(int id);

    void save(Contact contact);

    void updateContact(Contact contact);

    void deleteById(int id);

    void patchInsert(List<Contact> contacts);

    void delAll();

    int newId();
}
