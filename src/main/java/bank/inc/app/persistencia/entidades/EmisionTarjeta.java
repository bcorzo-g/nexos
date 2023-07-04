package bank.inc.app.persistencia.entidades;

import java.util.Date;
import javax.persistence.Basic;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;
import javax.persistence.Temporal;
import lombok.Data;

@Entity
@Table(name = "emisiontarjeta")
@Data
public class EmisionTarjeta {


    private static final long serialVersionUID = 1L;
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Basic(optional = false)
    @Column(name = "id")
    private Integer id;
    
    @Column(name = "numerotarjeta")
    private String numeroTarjeta;

    @Column(name = "nombretitular")
    private String nombreTitular;

    @Column(name = "estado")
    private Integer estado;

    @Column(name = "saldo")
    private Double saldo;

    @Column(name = "idproducto")
    private Integer idProducto;

    @Column(name = "fechacreacion")
    @Temporal(javax.persistence.TemporalType.DATE)
    private Date fecha_creacion;

    
    @Column(name = "fechavence")
    @Temporal(javax.persistence.TemporalType.DATE)
    private Date fecha_vence;

}
