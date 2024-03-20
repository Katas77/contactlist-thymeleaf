package com.example.contactlist.listener;


import com.example.contactlist.model.Contact;
import com.example.contactlist.service.ContactService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.boot.context.event.ApplicationStartedEvent;
import org.springframework.context.event.EventListener;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;


@Component
@RequiredArgsConstructor
@Slf4j
public class DatabaseTaskCreator {
    private final ContactService contactService;


    @EventListener(ApplicationStartedEvent.class)
    public void createTaskData() {
        contactService.delAll();
        log.debug("Calling DatabaseTaskCreator->createTaskData...");
        List<Contact> contacts = new ArrayList<>();
        Contact petrov = Contact.builder().id(1).firstName("Andrey").lastName("Petrov").email("petrov1999@mail.ru").phone("+7-918-456-22-44").build();
        contacts.add(petrov);
        Contact ivanov = Contact.builder().id(2).firstName("Ivan").lastName("Ivanov").email("ivanov1894@mail.ru").phone("+7-928-756-22-74").build();
        contacts.add(ivanov);
        contactService.patchInsert(contacts);
    }
}
