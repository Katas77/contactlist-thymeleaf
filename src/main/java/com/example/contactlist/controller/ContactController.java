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
import java.util.concurrent.atomic.AtomicLong;


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


    @GetMapping("/contacts/create")//GETИспользуется для запроса содержимого указанного ресурса.
    public String showCreateForm(Model model) {
        model.addAttribute("contacts", new Contact());
        return "create";
    }


    // http://localhost:8080/task/create
    @PostMapping("/contacts/create")//POST (HTTP)Применяется для передачи пользовательских данных заданному ресурсу.
    public String createTask(@ModelAttribute Contact contact) {
        contactServiceService.save(contact);
        return "redirect:/";
    }


    //http://localhost:8080/task/edit/1
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
        contactServiceService.uptadeContact(contact);
        return "redirect:/";

    }


    @GetMapping("/contacts/delete/{id}")
    public String deleteTask(@PathVariable int id) {
        contactServiceService.deleteById(id);
        return "redirect:/";

    }

}