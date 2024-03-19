package com.example.contactlist.service;

import com.example.contactlist.model.Contact;

import java.util.List;

public interface ContactService {
    List<Contact> findAll();

    Contact findById(int id);

    void save(Contact contact);

    Contact uptadeContact(Contact contact);

    void deleteById(int id);

    void patchInsert(List<Contact> contacts);

    void delAll();
    int newId();
}
