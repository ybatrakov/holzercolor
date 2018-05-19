package com.example.demo.controller;

import com.example.demo.model.Paint;
import com.example.demo.repository.PaintRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import javax.validation.constraints.*;
import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/api")
public class PaintController {

    @Autowired
    PaintRepository paintRepository;

    @GetMapping("/paints")
    public Iterable<Paint> getAllPaints() {
	return paintRepository.findAll();
    }

    @GetMapping("/paints/{id}")
    Optional<Paint> getPaint(@PathVariable @NotNull @DecimalMin("0") Integer id) {
        return paintRepository.findById(id);
    }
}
