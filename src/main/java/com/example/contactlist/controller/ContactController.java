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

    // Главная страница — список контактов
    @GetMapping("/")
    public String index(Model model) {
        model.addAttribute("contacts", contactService.findAll());
        return "index"; // → templates/index.html
    }

    // Форма создания
    @GetMapping("/contacts/create")
    public String showCreateForm(Model model) {
        model.addAttribute("contact", new Contact()); // ← "contact", не "contacts"!
        return "create"; // → templates/create.html
    }

    // Сохранение нового контакта
    @PostMapping("/contacts/create")
    public String createContact(@ModelAttribute Contact contact) {
        contactService.save(contact);
        return "redirect:/";
    }

    // Форма редактирования
    @GetMapping("/contacts/edit/{id}")
    public String showEditForm(@PathVariable Long id, Model model) {
        Contact contact = contactService.findById(Math.toIntExact(id))
                .orElseThrow(() -> new IllegalArgumentException("Контакт не найден: " + id));
        model.addAttribute("contact", contact); // ← "contact"
        return "edit"; // → templates/edit.html
    }

    // Обновление контакта
    @PostMapping("/contacts/edit")
    public String updateContact(@ModelAttribute Contact contact) {
        contactService.save(contact); // или отдельный update — зависит от вашей логики
        return "redirect:/";
    }

    // Удаление
    @GetMapping("/contacts/delete/{id}")
    public String deleteContact(@PathVariable Long id) {
        contactService.deleteById(Math.toIntExact(id));
        return "redirect:/";
    }
}