package bank.inc.app;


import bank.inc.app.util.Generador;
import bank.inc.app.util.Utilidades;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class CargasIniciales {
    
    @Bean
    public Generador generador() throws Exception {
        return new Generador();
    }
    
    @Bean
    public Utilidades utilidades() throws Exception {
        return new Utilidades();
    }
    
}
