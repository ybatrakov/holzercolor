package com.example.demo.repository;

import com.example.demo.model.PaletteType;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.jpa.repository.Query;

public interface PaletteTypeRepository extends CrudRepository<PaletteType, Integer> {
    Iterable<PaletteType> findAll();
}
