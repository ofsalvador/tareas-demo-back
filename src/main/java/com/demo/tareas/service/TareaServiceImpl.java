package com.demo.tareas.service;

import com.demo.tareas.model.Tarea;
import com.demo.tareas.repository.TareaRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.client.HttpClientErrorException;

import java.sql.Date;
import java.time.Instant;
import java.util.List;
import java.util.Optional;

@Service
public class TareaServiceImpl implements TareaService {

    @Autowired
    TareaRepository tareaRepository;

    @Override
    public List<Tarea> getTareasAll() {
        return tareaRepository.findAll();
    }

    @Override
    public Tarea createTarea(Tarea tarea) {
        tarea.setFechaCreacion(Date.from(Instant.now()));
        return tareaRepository.save(tarea);
    }

    @Override
    public Tarea updateTarea(Tarea tarea, String id) throws HttpClientErrorException {
        Optional<Tarea> tareaUpd = tareaRepository.findById(id);

        if (tareaUpd.isPresent()) {
            Tarea tareaUpdated = Tarea.builder().id(id).descripcion(tarea.getDescripcion()).vigente(tarea.getVigente()).fechaCreacion(tareaUpd.get().getFechaCreacion()).build();
            return tareaRepository.save(tareaUpdated);
        } else {
            throw new HttpClientErrorException(HttpStatus.NOT_FOUND);
        }

    }

    @Override
    public void deleteTarea(String id)  throws HttpClientErrorException {
        Optional<Tarea> tareaUpd = tareaRepository.findById(id);

        if (tareaUpd.isPresent()) {
            tareaRepository.deleteById(id);
        } else {
            throw new HttpClientErrorException(HttpStatus.NOT_FOUND);
        }

    }
}
