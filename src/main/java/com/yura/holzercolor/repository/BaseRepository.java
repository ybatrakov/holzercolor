package com.yura.holzercolor.repository;

import com.yura.holzercolor.model.Base;
import org.springframework.data.repository.CrudRepository;

import java.util.Optional;

public interface BaseRepository extends CrudRepository<Base, Integer> {
    Optional<Base> findByName(String name);
}
