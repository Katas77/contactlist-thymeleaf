package com.example.contactlist.model;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@Builder
@AllArgsConstructor
public class Contact {
    private int id;
    private String firstName;
    private String lastName;
    private String email;
    private String phone;

    public Contact() {
    }
}
