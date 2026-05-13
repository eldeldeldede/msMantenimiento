package cl.duoc.msMantenimiento.client;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

import cl.duoc.msMantenimiento.dto.VehiculoDTO;

@FeignClient(name = "msVehiculo", url = "http://localhost:8086")
public interface VehiculoClient {

    @GetMapping("/dto/{id}")
    VehiculoDTO buscarVehiculoDTO(@PathVariable("id")Integer id);
}
