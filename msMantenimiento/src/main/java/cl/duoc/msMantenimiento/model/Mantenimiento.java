package cl.duoc.msMantenimiento.model;

import java.util.Date;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Entity
@Table(name = "mantenimiento")
@Schema(description = "Entidad que representa un mantenimiento")
public class Mantenimiento {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Schema(description = "Identificador único del mantenimiento", example = "1")
    private Integer id;

    @Column(nullable = false)
    @Schema(description = "Fecha del mantenimiento", example = "2024-06-15")
    private Date fecha_mantenimiento;

    @Column(nullable = false)
    @Schema(description = "Costo del mantenimiento", example = "120000")
    private Integer costo_mantenimiento;
    
    @Column(name = "empleado_id", nullable = false)
    @Schema(description = "Identificador del empleado", example = "2")
    private Integer empleadoId;
    
    @Column(name = "vehiculo_id", nullable = false)
    @Schema(description = "Identificador del vehículo", example = "5")
    private Integer vehiculoId; 

    @ManyToOne
    @JoinColumn(name = "tipo_mantenimiento_id", nullable = false)
    @Schema(description = "Tipo de mantenimiento", example = "Preventivo")
    private TipoMantenimiento tipoMantenimiento;

    @Column(nullable = false)
    @Schema(description = "Descripción del mantenimiento", example = "Cambio de aceite")
    private String descripcion;
}
