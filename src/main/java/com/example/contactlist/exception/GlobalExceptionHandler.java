package com.example.contactlist.exception;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.ui.Model;


    @ControllerAdvice
    public class GlobalExceptionHandler {
        @ExceptionHandler(ContactNotFoundException.class)
        public String handleContactNotFound(ContactNotFoundException ex, Model model) {
            model.addAttribute("errorMessage", ex.getMessage());
            return "error/404";
        }

        @ExceptionHandler(IllegalArgumentException.class)
        public ResponseEntity<String> handleNotFound(IllegalArgumentException ex) {
            return ResponseEntity.status(404).body(ex.getMessage());
        }

    }


