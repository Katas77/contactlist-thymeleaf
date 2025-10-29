package com.example.contactlist;

import com.example.contactlist.controller.ContactController;
import com.example.contactlist.model.Contact;
import com.example.contactlist.service.ContactService;
import jakarta.servlet.ServletException;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.ArgumentCaptor;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import java.util.List;
import java.util.Optional;
import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@ExtendWith(MockitoExtension.class)
public class ContactControllerTest {

    private MockMvc mockMvc;

    @Mock
    private ContactService contactService;

    @InjectMocks
    private ContactController controller;

    @BeforeEach
    void setUp() {
        mockMvc = MockMvcBuilders.standaloneSetup(controller).build();

    }

    @Test
    void index_returnsIndexAndModel() throws Exception {
        Contact contact = new Contact(); contact.setId(1); contact.setFirstName("John");
        List<Contact> contacts = List.of(contact);

        when(contactService.findAll()).thenReturn(contacts);

        mockMvc.perform(get("/"))
                .andExpect(status().isOk())
                .andExpect(view().name("index"))
                .andExpect(model().attribute("contacts", contacts));

        verify(contactService).findAll();
    }


    @Test
    void createContact_callsSaveAndRedirects() throws Exception {
        mockMvc.perform(post("/contacts/create")
                        .param("firstName", "Alice")
                        .param("lastName", "Smith")
                        .param("email", "alice@example.com")
                        .param("phone", "+123"))
                .andExpect(status().is3xxRedirection())
                .andExpect(redirectedUrl("/"));

        ArgumentCaptor<Contact> captor = ArgumentCaptor.forClass(Contact.class);
        verify(contactService).save(captor.capture());
        Contact saved = captor.getValue();
        assertEquals("Alice", saved.getFirstName());
        assertEquals("Smith", saved.getLastName());
        assertEquals("alice@example.com", saved.getEmail());
        assertEquals("+123", saved.getPhone());
    }

    @Test
    void showEditForm_existing_returnsEditAndModel() throws Exception {
        Contact c = new Contact(); c.setId(5); c.setFirstName("B");
        when(contactService.findById(5)).thenReturn(Optional.of(c));

        mockMvc.perform(get("/contacts/edit/5"))
                .andExpect(status().isOk())
                .andExpect(view().name("edit"))
                .andExpect(model().attribute("contact", c));

        verify(contactService).findById(5);
    }


    @Test
    void updateContact_callsUpdateAndRedirects() throws Exception {
        mockMvc.perform(post("/contacts/edit")
                        .param("id", "10")
                        .param("firstName", "Up")
                        .param("lastName", "Date")
                        .param("email", "up@example.com")
                        .param("phone", "000"))
                .andExpect(status().is3xxRedirection())
                .andExpect(redirectedUrl("/"));

        ArgumentCaptor<Contact> captor = ArgumentCaptor.forClass(Contact.class);
        verify(contactService).updateContact(captor.capture());
        Contact updated = captor.getValue();
        assertEquals(10, updated.getId());
        assertEquals("Up", updated.getFirstName());
    }

    @Test
    void deleteContact_callsDeleteAndRedirects() throws Exception {
        mockMvc.perform(get("/contacts/delete/7"))
                .andExpect(status().is3xxRedirection())
                .andExpect(redirectedUrl("/"));

        verify(contactService).deleteById(7);
    }
    @Test
    void showEditForm_notFound_throwsIllegalArgumentException() throws Exception {
        when(contactService.findById(999)).thenReturn(Optional.empty());
        assertThrows(ServletException.class, () ->  mockMvc.perform(get("/contacts/edit/999")));
        verify(contactService).findById(999);
    }
    @Test
    void listContacts_withQuery_returnsIndexAndModel() throws Exception {
        String q = "jo";
        Contact c = new Contact(); c.setId(2); c.setFirstName("Query");
        List<Contact> list = List.of(c);

        when(contactService.search(q)).thenReturn(list);
        mockMvc.perform(get("/contacts").param("q", q))
                .andExpect(status().isOk())
                .andExpect(view().name("index"))
                .andExpect(model().attribute("contacts", list))
                .andExpect(model().attribute("q", q));
        verify(contactService).search(q);
    }
}
