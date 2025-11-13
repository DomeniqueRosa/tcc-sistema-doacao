package com.sistemadoacao.backend.controller;

import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@CrossOrigin(origins = "http://localhost:4200")
public class TesteController {

    @GetMapping("/api/teste")
    public String teste() {
        return "Conexão OK entre Angular e Spring Boot!";
    }

}
