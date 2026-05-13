package cl.duoc.msMantenimiento.config;

import org.springframework.boot.CommandLineRunner;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import cl.duoc.msMantenimiento.model.Mantenimiento;
import cl.duoc.msMantenimiento.model.TipoMantenimiento;
import cl.duoc.msMantenimiento.repository.MantenimientoRepository;
import cl.duoc.msMantenimiento.repository.TipoMantenimientoRepository;

@Configuration
public class DataLoader {

    @Bean
    CommandLineRunner initData(MantenimientoRepository mantenimientoRepo,
                               TipoMantenimientoRepository tipoRepo
    ) {
        return args -> {
            
            if(mantenimientoRepo.count() > 0){
                System.out.println("Datos de mantenimiento ya cargados");
            }else{
                TipoMantenimiento tipo1 = new TipoMantenimiento(null, "Cambio de aceite");
                TipoMantenimiento tipo2 = new TipoMantenimiento(null, "Revisión de frenos");
                TipoMantenimiento tipo3 = new TipoMantenimiento(null, "Alineación y balanceo");
                tipoRepo.save(tipo1);
                tipoRepo.save(tipo2);
                tipoRepo.save(tipo3);  


                Mantenimiento mantenimiento1 = new Mantenimiento(null, new java.util.Date(), 50000, 1, 1, tipo1, "Cambio de aceite y filtro");
                Mantenimiento mantenimiento2 = new Mantenimiento(null, new java.util.Date(), 75000, 2, 2, tipo2, "Revisión y cambio de pastillas de freno");
                Mantenimiento mantenimiento3 = new Mantenimiento(null, new java.util.Date(), 60000, 3, 3, tipo3, "Alineación y balanceo de neumáticos");
                mantenimientoRepo.save(mantenimiento1); 
                mantenimientoRepo.save(mantenimiento2);
                mantenimientoRepo.save(mantenimiento3);
            }
        };
    }
}
