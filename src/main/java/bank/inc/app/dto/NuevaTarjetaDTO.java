package bank.inc.app.dto;

import java.util.Date;
import lombok.Data;

@Data
public class NuevaTarjetaDTO {

   private String numero;
   private String titular;
   private String fechaCreacion;
   private String fechaVencimiento;
   private String estado;
   
}
