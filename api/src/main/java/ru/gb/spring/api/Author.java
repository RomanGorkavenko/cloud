package ru.gb.spring.api;

import lombok.Data;

import java.util.UUID;

@Data
public class Author {

    private UUID id;
    private String firstname;
    private String lastname;
}
