package cl.duoc.msMantenimiento.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import cl.duoc.msMantenimiento.client.EmpleadoClient;
import cl.duoc.msMantenimiento.client.VehiculoClient;
import cl.duoc.msMantenimiento.dto.EmpleadoDTO;
import cl.duoc.msMantenimiento.dto.MantenimientoDTO;
import cl.duoc.msMantenimiento.dto.VehiculoDTO;
import cl.duoc.msMantenimiento.model.Mantenimiento;
import cl.duoc.msMantenimiento.repository.MantenimientoRepository;

@Service
public class MantenimientoService {

    @Autowired
    private MantenimientoRepository repo;

    @Autowired
    private VehiculoClient clientVehiculo;

    @Autowired
    private EmpleadoClient clientEmpleado;

    public List<Mantenimiento> listarMantenimientos(){
        return repo.findAll();
    }

    public Mantenimiento buscarMantenimientoId(Integer id){
        return repo.findById(id).orElseThrow(() -> new RuntimeException("El mantenimiento no existe"));
    }

    public Mantenimiento agregarMantenimiento(Mantenimiento mantenimiento){
        return repo.save(mantenimiento);
    }

    public Mantenimiento guardarMantenimiento(Mantenimiento mantenimiento){
        VehiculoDTO vehiculo = clientVehiculo.obtenerVehiculoDTO(mantenimiento.getVehiculoId());
        if(vehiculo == null){
            throw new RuntimeException("El vehículo no existe");
        }

        EmpleadoDTO empleado = clientEmpleado.obtenerEmpleadoDTO(mantenimiento.getEmpleadoId());
        if(empleado == null){
            throw new RuntimeException("El empleado no existe");
        }

        return repo.save(mantenimiento);
    }

    public void eliminarMantenimiento(Integer id){
        if(repo.existsById(id)){
            repo.deleteById(id);
        }else{
            throw new RuntimeException("El mantenimiento no existe");
        }
    }

    public Mantenimiento actualizarMantenimiento(Integer id, Mantenimiento mantenimientoActualizado){
        Mantenimiento mantenimiento = repo.findById(id).orElseThrow(()-> new RuntimeException("El mantenimiento no existe"));
        mantenimiento.setFecha_mantenimiento(mantenimientoActualizado.getFecha_mantenimiento());
        mantenimiento.setCosto_mantenimiento(mantenimientoActualizado.getCosto_mantenimiento());
        mantenimiento.setEmpleadoId(mantenimientoActualizado.getEmpleadoId());
        mantenimiento.setTipoMantenimiento(mantenimientoActualizado.getTipoMantenimiento());
        mantenimiento.setVehiculoId(mantenimientoActualizado.getVehiculoId());

        return repo.save(mantenimiento);
    }

    public MantenimientoDTO obtenerDetalleMantenimientoDTO(Integer id){
        Mantenimiento mantenimiento = repo.findById(id).orElseThrow(() -> new RuntimeException("El mantenimiento no existe"));
        
        VehiculoDTO vehiculo = clientVehiculo.obtenerVehiculoDTO(mantenimiento.getVehiculoId());

        EmpleadoDTO empleado = clientEmpleado.obtenerEmpleadoDTO(mantenimiento.getEmpleadoId()); 
        
        MantenimientoDTO mantenimientoCompleto = new MantenimientoDTO();
        mantenimientoCompleto.setFecha(mantenimiento.getFecha_mantenimiento().toString());
        mantenimientoCompleto.setVehiculo(vehiculo);
        mantenimientoCompleto.setTipoMantenimiento(mantenimiento.getTipoMantenimiento().getNombre());
        mantenimientoCompleto.setEmpleado(empleado);

        return mantenimientoCompleto;
        
    }
}
