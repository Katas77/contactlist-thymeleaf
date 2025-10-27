package com.example.contactlist.controller;

import com.example.contactlist.model.Contact;
import com.example.contactlist.service.ContactService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

@Controller
@Slf4j
@RequiredArgsConstructor
public class ContactController {

    private final ContactService contactService;


    @GetMapping("/")
    public String index(Model model) {
        model.addAttribute("contacts", contactService.findAll());
        return "index"; // → templates/index.html
    }


    @GetMapping("/contacts/create")
    public String showCreateForm(Model model) {
        model.addAttribute("contact", new Contact());
        return "create"; // → templates/create.html
    }

    // Сохранение нового контакта
    @PostMapping("/contacts/create")
    public String createContact(@ModelAttribute Contact contact) {
        contactService.save(contact);
        return "redirect:/";
    }


    @GetMapping("/contacts/edit/{id}")
    public String showEditForm(@PathVariable int id, Model model) {
        Contact contact = contactService.findById(id)
                .orElseThrow(() -> new IllegalArgumentException("Контакт не найден: " + id));
        model.addAttribute("contact", contact); // ← "contact"
        return "edit"; // → templates/edit.html
    }


    @PostMapping("/contacts/edit")
    public String updateContact(@ModelAttribute Contact contact) {
        contactService.updateContact(contact);
        return "redirect:/";
    }


    @GetMapping("/contacts/delete/{id}")
    public String deleteContact(@PathVariable int id) {
        contactService.deleteById(id);
        return "redirect:/";
    }



    }

