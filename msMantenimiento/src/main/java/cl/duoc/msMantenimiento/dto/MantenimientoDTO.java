package cl.duoc.msMantenimiento.dto;

import java.util.Date;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class MantenimientoDTO {

    private Integer id;
    private Date fecha;
    private VehiculoDTO vehiculo;
    private EmpleadoDTO empleado;
    private TipoMantenimientoDTO tipoMantenimiento;
}
