package cl.duoc.msMantenimiento.client;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

import cl.duoc.msMantenimiento.dto.EmpleadoDTO;

public interface EmpleadoClient {

    @GetMapping("/api/v1/empleados/dto/{id}")
    EmpleadoDTO obtenerEmpleadoDTO(@PathVariable("id") Integer id);
}
