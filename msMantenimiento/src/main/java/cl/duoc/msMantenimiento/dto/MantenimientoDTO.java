package cl.duoc.msMantenimiento.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class MantenimientoDTO {

    private String fecha;
    private String patente;
    private String rut;
    private String tipoMantenimiento;
}
