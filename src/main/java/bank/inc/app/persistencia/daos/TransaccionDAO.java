package bank.inc.app.persistencia.daos;

import bank.inc.app.persistencia.entidades.Transaccion;
import org.springframework.data.jpa.repository.JpaRepository;

public interface TransaccionDAO extends JpaRepository<Transaccion, Integer> {
    
}
