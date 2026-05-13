package cl.duoc.msMantenimiento.model;

import java.util.Date;

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
public class Mantenimiento {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @Column(nullable = false)
    private Date fecha_mantenimiento;

    @Column(nullable = false)
    private Integer costo_mantenimiento;
    
    @Column(name = "empleado_id", nullable = false)
    private Integer empleadoId;
    
    @Column(name = "vehiculo_id", nullable = false)
    private Integer vehiculoId; 

    @ManyToOne
    @JoinColumn(name = "tipo_mantenimiento_id", nullable = false)
    private TipoMantenimiento tipoMantenimiento;

    @Column(nullable = false)
    private String descripcion;
}
