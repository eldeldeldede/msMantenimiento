package cl.duoc.msMantenimiento.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class MantenimientoDTO {

    private String fecha;
    private VehiculoDTO vehiculo;
    private EmpleadoDTO empleado;
    private String tipoMantenimiento;
}
