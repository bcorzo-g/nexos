package bank.inc.app.util;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.Random;

public class Generador {

    public String generarNumeroTarjeta() {
        //0000000000
        String numeroTarjeta = "";
        for (int i = 0; i < 10; i++) {
            numeroTarjeta += new Random().nextInt(10);
        }
        return numeroTarjeta;
    }

    public Date generarFechaVencimiento() {
        try {
            SimpleDateFormat sdf = new SimpleDateFormat("yyyyMMdd");
            Calendar c = Calendar.getInstance();
            c.setTime(new Date());
            c.set(Calendar.DAY_OF_MONTH, 1);
            c.add(Calendar.YEAR, 3);
            return (c.getTime());
        } catch (Exception ex) {
            ex.printStackTrace(System.out);
            return null;
        }
    }

}
