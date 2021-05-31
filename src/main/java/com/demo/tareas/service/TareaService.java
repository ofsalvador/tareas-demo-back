package com.demo.tareas.service;

import com.demo.tareas.model.Tarea;
import org.springframework.web.client.HttpClientErrorException;

import java.util.List;

public interface TareaService {

    List<Tarea> getTareasAll();
    Tarea createTarea(Tarea tarea);
    Tarea updateTarea(Tarea tarea, String id) throws HttpClientErrorException;
    void deleteTarea(String id)  throws HttpClientErrorException;

}
