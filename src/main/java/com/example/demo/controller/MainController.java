package com.example.demo.controller;

import java.util.Collection;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;

@Controller
public class MainController {
    private final static Logger log = LoggerFactory.getLogger(MainController.class);

    // Redirect logic: if user has 'REGULAR' role, it should be redirected to index.html, even if it also has 'FACADE' role
    // Redirect to facade.html only if there's only 'FACADE' role available.
    @GetMapping("/")
    public String main() {
        boolean facade = false;

        Collection<? extends GrantedAuthority> authorities = SecurityContextHolder.getContext().getAuthentication().getAuthorities();
        for (GrantedAuthority a : authorities) {
            if("ROLE_REGULAR".equals(a.getAuthority())) {
                return "redirect:/index.html";
            }
            if("ROLE_FACADE".equals(a.getAuthority())) {
                facade = true;
            }
        }
        if(facade) {
            return "redirect:/facade.html";
        }
        // TODO No appropriate roles found it's better to render error
        return "redirect:/index.html";
    }
}
