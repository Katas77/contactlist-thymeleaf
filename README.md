<center><font size="3" face="Georgia"> <h3> "Contacts Application"
</h3></font>
</center>

## Overview:
- The Contacts application is a small console-based application designed to manage contact information. 
- It allows users to display, add, delete, and save contact details to a file. 
- The application supports initializing contacts from a file when run with a specific profile.

## Features:
- Display all available contacts
- Add a new contact
- Delete a contact by email
- Save contacts to a file
- Initialize contacts from a file (Optional, based on application profile)


## Prerequisites
- Java 17
- Maven (for building the application)
- Spring Boot 3.2.3

## Setup and Installation
- Clone the repository:
- git clone [repository_url]
- Navigate to the project directory:
- cd contacts-application
- Build the application using Maven:
- mvn clean install 
- Run the application:
- For general use:
- - Work with the database occurs through JdbcTemplate
- - Launch and configure the database via Docker
- - To run using Docker, you need to enter the following commands in the terminal:
- - cd docker   
- - docker-compose up


____

### Usage
- Usage
- After starting the application, follow the on-screen prompts to interact with your contact list. The application supports the following commands:
- Display Contacts: Lists all the contacts in the format "Full Name | Phone Number | Email Address".
- Add Contact: Prompts you to enter a full name, phone number, and email address to add a new contact.
- Delete Contact: Prompts you to enter the email address of the contact you wish to delete.
### Contact Management
Contacts are managed through a simple command-line interface. 
Input errors are handled gracefully, with prompts for correct input.

## Technologies used:

- Java
- Spring
- Docker
- JdbcTemplate
- Thymeleaf


____
✉ Почта для обратной связи:
<a href="">krp77@mail.ru</a>