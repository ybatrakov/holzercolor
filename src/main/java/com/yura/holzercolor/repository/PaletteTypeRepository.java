package com.yura.holzercolor.repository;

import com.yura.holzercolor.model.PaletteType;
import org.springframework.data.repository.CrudRepository;

public interface PaletteTypeRepository extends CrudRepository<PaletteType, Integer> {
    Iterable<PaletteType> findAll();
}
