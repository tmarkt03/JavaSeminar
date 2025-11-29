package com.example.assignment;
class PersonNotFoundException extends RuntimeException {
    PersonNotFoundException(Long id) {
        super("The person can not be found: " + id);
    }
}

