package bank.inc.app.persistencia.daos;

import bank.inc.app.persistencia.entidades.EmisionTarjeta;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

public interface EmisionTarjetaDAO extends JpaRepository<EmisionTarjeta, Integer>{
    
    @Query(value = "select * from emisiontarjeta where numeroTarjeta=:tnumero", nativeQuery = true)
    public EmisionTarjeta consultarPorNumeroTarjeta(String tnumero);
    
}
