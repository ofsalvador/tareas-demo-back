package com.demo.tareas.model;

import com.fasterxml.jackson.annotation.JsonProperty;
import io.swagger.v3.oas.annotations.media.Schema;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import lombok.*;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Pattern;
import java.time.Instant;
import java.util.Date;

@Setter
@Getter
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Document(collection = "tareas")
public class Tarea {

    @Id
    @Schema(hidden = true)
    private String id;

    @Schema(description = "Descripción de la tarea.", required = true)
    @NotEmpty(message = "Descripcion es requerida.")
    private String descripcion;

    @Schema(description = "Fecha de creación de la tarea.", required = true, hidden = true)
    private Date fechaCreacion;

    @Schema(description = "Si está o no vigente.", required = true)
    @NotNull(message = "Vigente es requerido.")
    private Boolean vigente;

}
