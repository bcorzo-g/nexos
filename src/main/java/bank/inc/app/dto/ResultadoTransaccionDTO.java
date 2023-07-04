package bank.inc.app.dto;

import lombok.Data;

@Data
public class ResultadoTransaccionDTO {
  
    private String cardId;
    private Integer numeroTransaccion;
    private String resultado;
    
}
