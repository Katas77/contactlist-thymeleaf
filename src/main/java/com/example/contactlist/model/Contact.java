package com.example.contactlist.model;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class Contact {
    private int id;
    private String firstName;
    private String lastName;
    private String email;
    private String phone;
}
