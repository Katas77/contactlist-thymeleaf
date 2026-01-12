package com.example.contactlist.init;

import com.example.contactlist.model.Contact;
import com.example.contactlist.service.ContactService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.boot.context.event.ApplicationStartedEvent;
import org.springframework.context.event.EventListener;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
@Slf4j
public class DatabaseTaskCreator {
    private final ContactService contactService;

    @EventListener(ApplicationStartedEvent.class)
    public void createTaskData() {
        contactService.delAll();
        log.debug("Calling DatabaseTaskCreator->createTaskData...");
        Contact petrov = Contact.builder().id(contactService.generateNewId()).firstName("Andrey").lastName("Petrov").email("petrov1999@mail.ru").phone("+7-918-456-22-44").build();
        contactService.save(petrov);
        Contact ivanov = Contact.builder().id(contactService.generateNewId()).firstName("Ivan").lastName("Ivanov").email("ivanov1894@mail.ru").phone("+7-928-756-22-74").build();
        contactService.save(ivanov);
        Contact andre = Contact.builder().id(contactService.generateNewId()).firstName("Андре").lastName("Павлов").email("ivanov1894@mail.ru").phone("+7-928-756-22-74").build();
        contactService.save(andre);
    }
}

