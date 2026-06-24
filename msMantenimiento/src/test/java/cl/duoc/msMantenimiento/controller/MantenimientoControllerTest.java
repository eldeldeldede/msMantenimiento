package cl.duoc.msMantenimiento.controller;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.Mockito.doNothing;
import static org.mockito.Mockito.doThrow;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.delete;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.put;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import java.util.Date;
import java.util.List;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.webmvc.test.autoconfigure.WebMvcTest;
import org.springframework.http.MediaType;
import org.springframework.test.context.bean.override.mockito.MockitoBean;
import org.springframework.test.web.servlet.MockMvc;

import com.fasterxml.jackson.databind.ObjectMapper;

import cl.duoc.msMantenimiento.dto.MantenimientoDTO;
import cl.duoc.msMantenimiento.model.Mantenimiento;
import cl.duoc.msMantenimiento.model.TipoMantenimiento;
import cl.duoc.msMantenimiento.service.MantenimientoService;

@WebMvcTest(MantenimientoController.class)
public class MantenimientoControllerTest {

    @Autowired
    private MockMvc mock;

    @MockitoBean
    private MantenimientoService service;

    private final ObjectMapper objectMapper = new ObjectMapper();

    private Mantenimiento ejemMantenimiento;
    private MantenimientoDTO dtoEjemplo;

    @BeforeEach
    void setUp() {
        TipoMantenimiento tipo = new TipoMantenimiento();
        tipo.setId(1);
        tipo.setNombre("Preventivo");

        ejemMantenimiento = new Mantenimiento();
        ejemMantenimiento.setId(1);
        ejemMantenimiento.setFecha_mantenimiento(new Date());
        ejemMantenimiento.setCosto_mantenimiento(120000);
        ejemMantenimiento.setEmpleadoId(2);
        ejemMantenimiento.setVehiculoId(5);
        ejemMantenimiento.setTipoMantenimiento(tipo);
        ejemMantenimiento.setDescripcion("Cambio de aceite");

        dtoEjemplo = new MantenimientoDTO();
        dtoEjemplo.setFecha("2026-06-23");
        dtoEjemplo.setPatente("BBCC11");
        dtoEjemplo.setTipoMantenimiento("Preventivo");
        dtoEjemplo.setRut("19876543-2");
    }

    // ---------- listarMantenimientos ----------

    @Test
    public void listar_retorna200conLista() throws Exception {
        when(service.listarMantenimientos()).thenReturn(List.of(ejemMantenimiento));

        mock.perform(get("/api/v1/mantenimiento"))
            .andExpect(status().isOk());
    }

    @Test
    public void listar_retornaNoContentSiHayError() throws Exception {
        when(service.listarMantenimientos()).thenThrow(new RuntimeException());

        mock.perform(get("/api/v1/mantenimiento"))
            .andExpect(status().isNoContent());
    }

    // ---------- guardarMantenimiento ----------

    @Test
    public void guardarMantenimiento_retorna200() throws Exception {
        when(service.guardarMantenimiento(any(Mantenimiento.class))).thenReturn(ejemMantenimiento);

        mock.perform(post("/api/v1/mantenimiento")
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(ejemMantenimiento)))
            .andExpect(status().isOk());
    }

    @Test
    public void guardarMantenimiento_retornaNoContentSiHayError() throws Exception {
        when(service.guardarMantenimiento(any(Mantenimiento.class))).thenThrow(new RuntimeException());

        mock.perform(post("/api/v1/mantenimiento")
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(ejemMantenimiento)))
            .andExpect(status().isNoContent());
    }

    // ---------- buscarPorId ----------

    @Test
    public void buscarPorId_retorna200() throws Exception {
        when(service.buscarMantenimientoId(1)).thenReturn(ejemMantenimiento);

        mock.perform(get("/api/v1/mantenimiento/id/1"))
            .andExpect(status().isOk());
    }

    @Test
    public void buscarPorId_retorna404() throws Exception {
        when(service.buscarMantenimientoId(99)).thenThrow(new RuntimeException());

        mock.perform(get("/api/v1/mantenimiento/id/99"))
            .andExpect(status().isNotFound());
    }

    // ---------- detalleMantenimientoDTO ----------

    @Test
    public void detalleMantenimientoDTO_retorna200() throws Exception {
        when(service.obtenerDetalleMantenimientoDTO(1)).thenReturn(dtoEjemplo);

        mock.perform(get("/api/v1/mantenimiento/detalle/1"))
            .andExpect(status().isOk())
            .andExpect(jsonPath("$.patente").value("BBCC11"));
    }

    @Test
    public void detalleMantenimientoDTO_retorna404() throws Exception {
        when(service.obtenerDetalleMantenimientoDTO(99)).thenThrow(new RuntimeException());

        mock.perform(get("/api/v1/mantenimiento/detalle/99"))
            .andExpect(status().isNotFound());
    }

    // ---------- actualizarMantenimiento ----------

    @Test
    public void actualizarMantenimiento_retorna200() throws Exception {
        when(service.actualizarMantenimiento(eq(1), any(Mantenimiento.class))).thenReturn(ejemMantenimiento);

        mock.perform(put("/api/v1/mantenimiento/1")
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(ejemMantenimiento)))
            .andExpect(status().isOk());
    }

    @Test
    public void actualizarMantenimiento_retorna404() throws Exception {
        when(service.actualizarMantenimiento(eq(99), any(Mantenimiento.class))).thenThrow(new RuntimeException());

        mock.perform(put("/api/v1/mantenimiento/99")
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(ejemMantenimiento)))
            .andExpect(status().isNotFound());
    }

    // ---------- eliminarMantenimiento ----------

    @Test
    public void eliminarMantenimiento_retorna200() throws Exception {
        doNothing().when(service).eliminarMantenimiento(1);

        mock.perform(delete("/api/v1/mantenimiento/1"))
            .andExpect(status().isNoContent());
    }

    @Test
    public void eliminarMantenimiento_retorna404() throws Exception {
        doThrow(new RuntimeException()).when(service).eliminarMantenimiento(99);

        mock.perform(delete("/api/v1/mantenimiento/99"))
            .andExpect(status().isNotFound());
    }
}