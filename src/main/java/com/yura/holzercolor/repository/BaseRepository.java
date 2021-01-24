package com.yura.holzercolor.repository;

import com.yura.holzercolor.model.Base;
import org.springframework.data.repository.CrudRepository;

public interface BaseRepository extends CrudRepository<Base, Integer> {
    Base findByName(String name);
}
