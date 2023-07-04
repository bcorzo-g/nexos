package bank.inc.app.persistencia.entidades;

import java.util.Date;
import javax.persistence.Basic;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;
import lombok.Data;

@Entity
@Table(name = "transaccion")
@Data
public class Transaccion {

    private static final long serialVersionUID = 1L;
//idTransaccion int PK 
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Basic(optional = false)
    @Column(name = "idtransaccion")
    private Integer idtransaccion;

    @Column(name = "numerotarjeta")
    private String numerotarjeta;

    @Column(name = "valor")
    private Double valor;

    @Column(name = "estado")
    private Integer estado;

    @Column(name = "fechaestado")
    private Date fechaestado;

}
