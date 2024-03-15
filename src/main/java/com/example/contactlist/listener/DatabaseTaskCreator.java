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
        Contact petrov = new Contact();
        petrov.setId(contactService.findAll().size() + 1);
        petrov.setFirstName("Ivan");
        petrov.setLastName("Petrov");
        petrov.setEmail("petrov1999@mail.ru");
        petrov.setPhone("+7-918-456-22-44");
        contacts.add(petrov);
        Contact ivanov = new Contact();
        ivanov.setId(contactService.findAll().size() + 2);
        ivanov.setFirstName("Ivan");
        ivanov.setLastName("Ivanov");
        ivanov.setEmail("ivanov1894@mail.ru");
        ivanov.setPhone("+7-928-756-22-74");
        contacts.add(ivanov);
        contactService.batsInsert(contacts);
    }

}
