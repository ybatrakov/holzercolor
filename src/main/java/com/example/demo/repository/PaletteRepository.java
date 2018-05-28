package com.example.demo.repository;

import com.example.demo.model.Palette;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.jpa.repository.Query;

public interface PaletteRepository extends CrudRepository<Palette, Integer> {
    @Query("from Palette as p where p.paletteType.nick = ?1")
    Iterable<Palette> getByTypeNick(String nick);

    /*
    @Query("select p from Palette as p join p.paints as paints where paints.id = ?1")
    public Iterable<Palette> getByPaintId(Integer paintId);
    */
}
