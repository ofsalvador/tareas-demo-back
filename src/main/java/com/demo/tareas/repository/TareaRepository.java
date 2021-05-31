package com.demo.tareas.repository;

import com.demo.tareas.model.Tarea;
import org.springframework.data.mongodb.repository.MongoRepository;

public interface TareaRepository  extends MongoRepository<Tarea, String> {

}
