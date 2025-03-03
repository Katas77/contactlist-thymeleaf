package com.example.contactlist.controller;

import com.example.contactlist.model.Contact;
import com.example.contactlist.service.ContactService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;

import java.util.Optional;

@Controller
@Slf4j
@RequiredArgsConstructor
public class ContactController {

    private final ContactService contactServiceService;

    @GetMapping("/")
    public String index(Model model) {
        model.addAttribute("contacts", contactServiceService.findAll());
        return "index";
    }

    @GetMapping("/contacts/create")
    public String showCreateForm(Model model) {
        model.addAttribute("contacts", new Contact());
        return "create";
    }

    @PostMapping("/contacts/create")
    public String createTask(@ModelAttribute Contact contact) {
        contactServiceService.save(contact);
        return "redirect:/";
    }

    @GetMapping("/contacts/edit/{id}")
    public String showEditForm(@PathVariable int id, Model model) {
        Optional<Contact> contact = contactServiceService.findById(Math.toIntExact(id));
        if (contact.isPresent()) {
            model.addAttribute("contacts", contact.get());
            return "edit";
        } else {
            return "redirect:/";
        }
    }

    @PostMapping("/contacts/edit")
    public String editTask(@ModelAttribute Contact contact) {
        contactServiceService.updateContact(contact);
        return "redirect:/";

    }


    @GetMapping("/contacts/delete/{id}")
    public String deleteTask(@PathVariable int id) {
        contactServiceService.deleteById(id);
        return "redirect:/";
    }
}