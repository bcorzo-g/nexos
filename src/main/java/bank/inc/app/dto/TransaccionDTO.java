package bank.inc.app.dto;

import lombok.Data;

@Data
public class TransaccionDTO {

    private Integer idtransaccion;
    private String numerotarjeta;
    private Double valor;
    private String estado;
    private String fechaestado;
    
}
