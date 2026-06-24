package cl.duoc.msMantenimiento.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import cl.duoc.msMantenimiento.dto.MantenimientoDTO;
import cl.duoc.msMantenimiento.model.Mantenimiento;
import cl.duoc.msMantenimiento.service.MantenimientoService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;

@RestController
@RequestMapping("api/v1/mantenimiento")
@Tag(name = "Mantenimiento", description = "Controlador para gestionar los mantenimientos")
public class MantenimientoController {

    @Autowired
    private MantenimientoService service;

    @GetMapping
    @Operation(summary = "Listar mantenimientos", 
               description = "Obtiene una lista de todos los mantenimientos registrados"
        )
    public ResponseEntity<List<Mantenimiento>>listar(){
        try {
            List<Mantenimiento> mantenimientos = service.listarMantenimientos();
            return ResponseEntity.ok(mantenimientos);
        } catch (Exception e) {
            return ResponseEntity.noContent().build();
        }
    }   

    @PostMapping
    @Operation(summary = "Guardar mantenimiento", 
               description = "Crea un nuevo mantenimiento"
        )
    public ResponseEntity<Mantenimiento> guardarMantenimiento(Mantenimiento mantenimiento){
        try {
            Mantenimiento mantenimientoNuevo = service.guardarMantenimiento(mantenimiento);
            return ResponseEntity.ok(mantenimientoNuevo);
        } catch (Exception e) {
            return ResponseEntity.noContent().build();
        }
    }

    @GetMapping("/id/{id}")
    @Operation(summary = "Buscar mantenimiento por ID", 
               description = "Obtiene un mantenimiento específico por su ID"
        )

    public ResponseEntity<Mantenimiento> buscarPorId(@PathVariable Integer id){
        try {
            Mantenimiento mantenimiento = service.buscarMantenimientoId(id);
            return ResponseEntity.ok(mantenimiento);
        } catch (Exception e) {
            return ResponseEntity.notFound().build();
        }
    }

    @GetMapping("/detalle/{id}")
    @Operation(summary = "Detalle de mantenimiento", 
               description = "Obtiene los detalles de un mantenimiento específico por su ID"
        )
    public ResponseEntity<MantenimientoDTO> detalleMantenimientoDTO(@PathVariable Integer id){
        try {
            MantenimientoDTO mantenimientoDTO = service.obtenerDetalleMantenimientoDTO(id);
            return ResponseEntity.ok(mantenimientoDTO);
        } catch (Exception e) {
            return ResponseEntity.notFound().build();
        }
    }

    @PutMapping("/{id}")
    @Operation(summary = "Actualizar mantenimiento", 
               description = "Actualiza la información de un mantenimiento específico por su ID"
        )
    public ResponseEntity<Mantenimiento> actualizarMantenimiento(@PathVariable Integer id,@RequestBody Mantenimiento mantenimientoActualizado){
        try {
            Mantenimiento mantenimiento = service.actualizarMantenimiento(id, mantenimientoActualizado);
            return ResponseEntity.ok(mantenimiento);
        } catch (Exception e) {
            return ResponseEntity.notFound().build();
        }
    }

    @DeleteMapping("/{id}")
    @Operation(summary = "Eliminar mantenimiento", 
               description = "Elimina un mantenimiento específico por su ID"
        )
    public ResponseEntity<Mantenimiento> eliminarMantenimiento(@PathVariable Integer id){
        try {
            service.eliminarMantenimiento(id);
            return ResponseEntity.noContent().build();
        } catch (Exception e) {
            return ResponseEntity.notFound().build();
        }
    }




}
