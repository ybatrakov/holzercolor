package com.example.demo.controller;

import com.example.demo.model.Base;
import com.example.demo.repository.BaseRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import javax.validation.Valid;
import java.util.List;

@RestController
@RequestMapping("/api")
public class BaseController {

    @Autowired
    BaseRepository baseRepository;

    @GetMapping("/bases")
    public Iterable<Base> getAllBases() {
	return baseRepository.findAll();
    }
}
