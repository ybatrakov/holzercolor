package com.example.demo.repository;

import com.example.demo.model.Palette;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.jpa.repository.Query;

public interface PaletteRepository extends CrudRepository<Palette, Integer> {
    @Query("from Palette as p where p.paletteType.nick = ?1")
    Iterable<Palette> getByTypeNick(String nick);

    Palette findByName(String name);
}
