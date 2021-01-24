package com.yura.holzercolor.controller;

import com.yura.holzercolor.model.Base;
import com.yura.holzercolor.repository.BaseRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

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
