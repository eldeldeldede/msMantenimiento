package cl.duoc.msMantenimiento.service;

import static org.junit.jupiter.api.Assertions.assertDoesNotThrow;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import java.util.Date;
import java.util.List;
import java.util.Optional;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import cl.duoc.msMantenimiento.client.EmpleadoClient;
import cl.duoc.msMantenimiento.client.VehiculoClient;
import cl.duoc.msMantenimiento.dto.EmpleadoDTO;
import cl.duoc.msMantenimiento.dto.MantenimientoDTO;
import cl.duoc.msMantenimiento.dto.VehiculoDTO;
import cl.duoc.msMantenimiento.model.Mantenimiento;
import cl.duoc.msMantenimiento.model.TipoMantenimiento;
import cl.duoc.msMantenimiento.repository.MantenimientoRepository;

@ExtendWith(MockitoExtension.class)
public class MantenimientoServiceTest {

    @Mock
    private MantenimientoRepository repo;

    @Mock
    private VehiculoClient clientVehiculo;

    @Mock
    private EmpleadoClient clientEmpleado;

    @InjectMocks
    private MantenimientoService service;

    private Mantenimiento ejemMantenimiento;
    private VehiculoDTO ejemVehiculo;
    private EmpleadoDTO ejemEmpleado;

    @BeforeEach
    void setUp() {
        TipoMantenimiento tipo = new TipoMantenimiento();
        tipo.setId(1);
        tipo.setNombre("Correctivo");

        ejemMantenimiento = new Mantenimiento();
        ejemMantenimiento.setId(1);
        ejemMantenimiento.setFecha_mantenimiento(new Date());
        ejemMantenimiento.setCosto_mantenimiento(150000);
        ejemMantenimiento.setEmpleadoId(10);
        ejemMantenimiento.setVehiculoId(20);
        ejemMantenimiento.setTipoMantenimiento(tipo);
        ejemMantenimiento.setDescripcion("Cambio de pastillas de freno");

        ejemVehiculo = new VehiculoDTO();
        ejemVehiculo.setPatente("ABCD12");

        ejemEmpleado = new EmpleadoDTO();
        ejemEmpleado.setRut("12345678-9");
    }

    @Test
    void listarMantenimientos_retornaLista() {
        when(repo.findAll()).thenReturn(List.of(ejemMantenimiento));

        List<Mantenimiento> resultado = service.listarMantenimientos();

        assertEquals(1, resultado.size());
        assertEquals(150000, resultado.get(0).getCosto_mantenimiento());
    }

    @Test
    void buscarMantenimientoId_encontrado() {
        when(repo.findById(1)).thenReturn(Optional.of(ejemMantenimiento));

        Mantenimiento resultado = service.buscarMantenimientoId(1);

        assertNotNull(resultado);
        assertEquals(1, resultado.getId());
    }

    @Test
    void buscarMantenimientoId_noEncontrado() {
        when(repo.findById(1)).thenReturn(Optional.empty());

        RuntimeException exception = assertThrows(RuntimeException.class, () -> {
            service.buscarMantenimientoId(1);
        });

        assertEquals("El mantenimiento no existe", exception.getMessage());
    }

    @Test
    void guardarMantenimiento_exitoso() {
        when(clientVehiculo.obtenerVehiculoDTO(20)).thenReturn(ejemVehiculo);
        when(clientEmpleado.obtenerEmpleadoDTO(10)).thenReturn(ejemEmpleado);
        when(repo.save(ejemMantenimiento)).thenReturn(ejemMantenimiento);

        Mantenimiento resultado = service.guardarMantenimiento(ejemMantenimiento);

        assertNotNull(resultado);
        verify(repo, times(1)).save(ejemMantenimiento);
    }

    @Test
    void guardarMantenimiento_vehiculoNoExiste() {
        when(clientVehiculo.obtenerVehiculoDTO(20)).thenReturn(null);

        RuntimeException exception = assertThrows(RuntimeException.class, () -> {
            service.guardarMantenimiento(ejemMantenimiento);
        });

        assertEquals("El vehículo no existe", exception.getMessage());
        verify(repo, times(0)).save(any(Mantenimiento.class));
    }

    @Test
    void guardarMantenimiento_empleadoNoExiste() {
        when(clientVehiculo.obtenerVehiculoDTO(20)).thenReturn(ejemVehiculo);
        when(clientEmpleado.obtenerEmpleadoDTO(10)).thenReturn(null);

        RuntimeException exception = assertThrows(RuntimeException.class, () -> {
            service.guardarMantenimiento(ejemMantenimiento);
        });

        assertEquals("El empleado no existe", exception.getMessage());
        verify(repo, times(0)).save(any(Mantenimiento.class));
    }

    @Test
    void eliminarMantenimiento_exitoso() {
        when(repo.existsById(1)).thenReturn(true);

        assertDoesNotThrow(() -> service.eliminarMantenimiento(1));
        verify(repo, times(1)).deleteById(1);
    }

    @Test
    void eliminarMantenimiento_noExiste() {
        when(repo.existsById(99)).thenReturn(false);

        RuntimeException exception = assertThrows(RuntimeException.class, () -> {
            service.eliminarMantenimiento(99);
        });

        assertEquals("El mantenimiento no existe", exception.getMessage());
        verify(repo, times(0)).deleteById(99);
    }

    @Test
    void actualizarMantenimiento_exitoso() {
        Mantenimiento datosNuevos = new Mantenimiento();
        datosNuevos.setFecha_mantenimiento(new Date());
        datosNuevos.setCosto_mantenimiento(200000);
        datosNuevos.setEmpleadoId(11);
        datosNuevos.setVehiculoId(21);
        datosNuevos.setTipoMantenimiento(ejemMantenimiento.getTipoMantenimiento());

        when(repo.findById(1)).thenReturn(Optional.of(ejemMantenimiento));
        when(repo.save(ejemMantenimiento)).thenReturn(ejemMantenimiento);

        Mantenimiento resultado = service.actualizarMantenimiento(1, datosNuevos);

        assertEquals(200000, resultado.getCosto_mantenimiento());
        assertEquals(11, resultado.getEmpleadoId());
        verify(repo, times(1)).save(ejemMantenimiento);
    }

    @Test
    void actualizarMantenimiento_noEncontrado() {
        when(repo.findById(99)).thenReturn(Optional.empty());

        RuntimeException exception = assertThrows(RuntimeException.class, () -> {
            service.actualizarMantenimiento(99, ejemMantenimiento);
        });

        assertEquals("El mantenimiento no existe", exception.getMessage());
        verify(repo, times(0)).save(any(Mantenimiento.class));
    }

    @Test
    void obtenerDetalleMantenimientoDTO_exitoso() {
        when(repo.findById(1)).thenReturn(Optional.of(ejemMantenimiento));
        when(clientVehiculo.obtenerVehiculoDTO(20)).thenReturn(ejemVehiculo);
        when(clientEmpleado.obtenerEmpleadoDTO(10)).thenReturn(ejemEmpleado);

        MantenimientoDTO resultado = service.obtenerDetalleMantenimientoDTO(1);

        assertNotNull(resultado);
        assertEquals("ABCD12", resultado.getPatente());
        assertEquals("12345678-9", resultado.getRut());
        assertEquals("Correctivo", resultado.getTipoMantenimiento());
    }
}