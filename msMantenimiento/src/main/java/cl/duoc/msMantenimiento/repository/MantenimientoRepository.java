package cl.duoc.msMantenimiento.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import cl.duoc.msMantenimiento.model.Mantenimiento;

@Repository
public interface MantenimientoRepository extends JpaRepository <Mantenimiento, Integer>{

}
