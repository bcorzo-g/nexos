package bank.inc.app.util;

import java.util.Date;

public class Utilidades {

    public Long calcularDiasEntre(Date ftransaccion, Date factual) {
        Long diff = factual.getTime() - ftransaccion.getTime();
        Long days = diff / (1000 * 60 * 60 * 24);
        return days;
    }

}
