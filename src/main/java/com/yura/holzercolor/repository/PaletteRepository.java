package com.yura.holzercolor.repository;

import com.yura.holzercolor.model.Palette;
import org.springframework.data.repository.CrudRepository;

import java.util.Collection;
import java.util.Optional;

public interface PaletteRepository extends CrudRepository<Palette, Integer> {
    Collection<Palette> findByPaletteTypeId(int typeId);

    Optional<Palette> findByName(String name);
}
