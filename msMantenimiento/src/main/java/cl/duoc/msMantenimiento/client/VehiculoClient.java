package cl.duoc.msMantenimiento.client;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

import cl.duoc.msMantenimiento.dto.VehiculoDTO;

@FeignClient(name = "msVehiculo", url = "http://localhost:8086")
public interface VehiculoClient {

    @GetMapping("/api/v1/vehiculos/dto/{id}")
    VehiculoDTO obtenerVehiculoDTO(@PathVariable("id")Integer id);
}
