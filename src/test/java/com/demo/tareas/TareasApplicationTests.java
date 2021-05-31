package com.demo.tareas;

import com.demo.tareas.model.Tarea;
import com.demo.tareas.service.TareaService;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.time.Instant;
import java.util.Date;
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;

@SpringBootTest
class TareasApplicationTests {

    @Autowired
    TareaService tareaService;

    @Test
    void createTarea() {
        Tarea tarea = Tarea.builder()
                .descripcion("Tarea de prueba")
                .fechaCreacion(Date.from(Instant.now()))
                .vigente(true)
                .build();
        Tarea newTarea = tareaService.createTarea(tarea);
        assertThat(newTarea.getId()).isNotNull();
    }

    @Test
    void getTareas() {
        List<Tarea> newTarea = tareaService.getTareasAll();
        assertThat(newTarea.size()).isGreaterThan(0);
    }

}
