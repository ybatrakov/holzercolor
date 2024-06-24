package com.yura.holzercolor.repository;

import com.yura.holzercolor.model.PaletteType;
import org.springframework.data.repository.CrudRepository;

import java.util.Collection;

public interface PaletteTypeRepository extends CrudRepository<PaletteType, Integer> {
    Collection<PaletteType> findByFormulaTypeName(String name);
}
