package com.example.demo.repository;

import com.example.demo.model.Paint;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.jpa.repository.Query;

public interface PaintRepository extends CrudRepository<Paint, Integer> {
    @Query("select p from Paint as p join p.palettes as palettes where palettes.id = ?1")
    public Iterable<Paint> getByPaletteId(Integer paletteId);
}
