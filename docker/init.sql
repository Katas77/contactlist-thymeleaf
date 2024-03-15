CREATE SCHEMA  contacts_schema;
CREATE TABLE contacts_schema.contacts(
id INT PRIMARY KEY,
firstName VARCHAR(255) NOT NULL,
lastName VARCHAR(255) NOT NULL,
email VARCHAR(255) NOT NULL,
phone VARCHAR(255) NOT NULL);
