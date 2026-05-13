package cl.duoc.msMantenimiento.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import cl.duoc.msMantenimiento.model.TipoMantenimiento;

@Repository
public interface TipoMantenimientoRepository extends JpaRepository <TipoMantenimiento, Integer>{

}
