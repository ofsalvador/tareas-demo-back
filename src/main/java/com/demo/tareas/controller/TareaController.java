package com.demo.tareas.controller;

import com.demo.tareas.model.Tarea;
import com.demo.tareas.service.TareaService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.http.converter.HttpMessageNotReadableException;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.client.HttpClientErrorException;

import javax.validation.Valid;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@CrossOrigin(origins = "*")
@RestController
@RequestMapping("/tarea")
public class TareaController {

    @Autowired
    TareaService tareaService;

    @Operation(summary = "Lista todas las tareas",
            responses = {
                    @ApiResponse(responseCode = "200", description = "Obtienen las tareas correctamente.", content = @Content(mediaType = "application/json")),
                    @ApiResponse(responseCode = "500", description = "Internal Server Error.")
            }
    )
    @GetMapping(produces = {MediaType.APPLICATION_JSON_VALUE})
    public ResponseEntity<List<Tarea>> getAll() {
        try {
            List<Tarea> tareasList = tareaService.getTareasAll();
            return new ResponseEntity(tareasList, HttpStatus.OK);
        }
        catch (Exception e) {
            e.printStackTrace();
            return new ResponseEntity(HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @Operation(summary = "Crea un anueva tarea",
            responses = {
                    @ApiResponse(responseCode = "201", description = "Tarea creada correctamente.", content = @Content(mediaType = "application/json")),
                    @ApiResponse(responseCode = "400", description = "Datos inconsistentes."),
                    @ApiResponse(responseCode = "500", description = "Internal Server Error.")
            }
    )
    @PostMapping(consumes = {MediaType.APPLICATION_JSON_VALUE},
            produces = {MediaType.APPLICATION_JSON_VALUE})
    public ResponseEntity<HttpStatus> create(@Valid @RequestBody Tarea tarea) {
        try {
            Tarea newTarea = tareaService.createTarea(tarea);
            return new ResponseEntity(newTarea, HttpStatus.CREATED);
        }
        catch (Exception e) {
            e.printStackTrace();
            return new ResponseEntity(HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @Operation(summary = "Actualiza una tarea",
            responses = {
                    @ApiResponse(responseCode = "200", description = "Tarea actualizada correctamente.", content = @Content(mediaType = "application/json")),
                    @ApiResponse(responseCode = "400", description = "Datos inconsistentes."),
                    @ApiResponse(responseCode = "404", description = "Tarea no encontrada."),
                    @ApiResponse(responseCode = "500", description = "Internal Server Error.")
            }
    )
    @PutMapping(value = "/{id}", consumes = {MediaType.APPLICATION_JSON_VALUE},
            produces = {MediaType.APPLICATION_JSON_VALUE})
    public ResponseEntity<Tarea> update(@PathVariable("id") String id,@Valid @RequestBody Tarea tarea) {
        try {
            Tarea updTarea = tareaService.updateTarea(tarea, id);
            return new ResponseEntity(updTarea, HttpStatus.OK);
        }
        catch (Exception e) {
            e.printStackTrace();
            if(e instanceof HttpClientErrorException) {
                return new ResponseEntity(((HttpClientErrorException)e).getStatusCode());
            }
            return new ResponseEntity(HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @Operation(summary = "Elimina una tarea",
            responses = {
                    @ApiResponse(responseCode = "204", description = "Tarea eliminada correctamente.", content = @Content(mediaType = "application/json")),
                    @ApiResponse(responseCode = "400", description = "Datos inconsistentes."),
                    @ApiResponse(responseCode = "404", description = "Tarea no encontrada."),
                    @ApiResponse(responseCode = "500", description = "Internal Server Error.")
            }
    )
    @DeleteMapping("/{id}")
    public ResponseEntity<HttpStatus> delete(@PathVariable("id") String id) {
        try {
            tareaService.deleteTarea(id);
            return new ResponseEntity(HttpStatus.NO_CONTENT);
        }
        catch (Exception e) {
            e.printStackTrace();
            if(e instanceof HttpClientErrorException) {
                return new ResponseEntity(((HttpClientErrorException)e).getStatusCode());
            }
            return new ResponseEntity(HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @ResponseStatus(HttpStatus.BAD_REQUEST)
    @ExceptionHandler(MethodArgumentNotValidException.class)
    public Map<String, String> handleValidationBadRequest(
            MethodArgumentNotValidException ex) {
        Map<String, String> errors = new HashMap<>();
        ex.getBindingResult().getAllErrors().forEach((error) -> {
            String fieldName = ((FieldError) error).getField();
            String errorMessage = error.getDefaultMessage();
            errors.put(fieldName, errorMessage);
        });
        return errors;
    }


}