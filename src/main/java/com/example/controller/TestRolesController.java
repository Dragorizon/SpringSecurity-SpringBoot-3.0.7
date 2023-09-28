package com.example.controller;

import org.springframework.security.access.annotation.Secured;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class TestRolesController {

    @Secured("ROLE_ADMIN")
    @GetMapping("/accessAdmin")
    public String accessAdmin(){
        return "Hola has accedido con rol admin";
    }

    @Secured("ROLE_USER")
    @GetMapping("/accessUser")
    public String accessUser(){
        return "Hola has accedido con rol User";
    }

    @Secured("ROLE_INVITED")
    @GetMapping("/accessInvited")
    public String accessInvited(){
        return "Hola has accedido con rol Invited";
    }
}
