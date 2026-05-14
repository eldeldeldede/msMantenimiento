package cl.duoc.msMantenimiento.client;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

import cl.duoc.msMantenimiento.dto.EmpleadoDTO;

@FeignClient(name = "msEmpleado", url = "http://localhost:8082")
public interface EmpleadoClient {

    @GetMapping("/api/v1/empleados/dto/{id}")
    EmpleadoDTO obtenerEmpleadoDTO(@PathVariable("id") Integer id);
}
