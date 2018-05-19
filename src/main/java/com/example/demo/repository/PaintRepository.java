package com.example.demo.repository;

import com.example.demo.model.Paint;
import org.springframework.data.repository.CrudRepository;

public interface PaintRepository extends CrudRepository<Paint, Integer> {

}
