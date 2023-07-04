package bank.inc.app.negocio;

import bank.inc.app.dto.AnulacionDTO;
import bank.inc.app.dto.CompraDTO;
import bank.inc.app.dto.NuevaTarjetaDTO;
import bank.inc.app.dto.NumeroEnrrolamientoDTO;
import bank.inc.app.dto.ResultadoTransaccionDTO;
import bank.inc.app.dto.SaldoDTO;
import bank.inc.app.dto.TransaccionDTO;
import bank.inc.app.persistencia.daos.EmisionTarjetaDAO;
import bank.inc.app.persistencia.daos.TransaccionDAO;
import bank.inc.app.persistencia.entidades.EmisionTarjeta;
import bank.inc.app.persistencia.entidades.Transaccion;
import bank.inc.app.util.Generador;
import bank.inc.app.util.Utilidades;
import java.text.SimpleDateFormat;
import java.util.Date;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
public class NegocioTarjetas {

    @Autowired
    private Generador generador;
    @Autowired
    private Utilidades utilidades;

    @Autowired
    private EmisionTarjetaDAO etdao;
    @Autowired
    private TransaccionDAO trdao;

    public NuevaTarjetaDTO generarTarjeta(String productId, String titular) {

        String numeroNuevaTarjeta = productId + generador.generarNumeroTarjeta();
        EmisionTarjeta tarjetaexistente = etdao.consultarPorNumeroTarjeta(numeroNuevaTarjeta);

        while (tarjetaexistente != null) {
            numeroNuevaTarjeta = productId + generador.generarNumeroTarjeta();
            tarjetaexistente = etdao.consultarPorNumeroTarjeta(numeroNuevaTarjeta);
        }

        EmisionTarjeta et = new EmisionTarjeta();
        et.setEstado(0);
        et.setFecha_creacion(new Date());
        et.setFecha_vence(generador.generarFechaVencimiento());
        et.setIdProducto(Integer.parseInt(productId));
        et.setNombreTitular(titular);
        et.setNumeroTarjeta(numeroNuevaTarjeta);
        et.setSaldo(0d);

        et = etdao.save(et);

        NuevaTarjetaDTO tarjeta = new NuevaTarjetaDTO();
        tarjeta.setNumero(et.getNumeroTarjeta());
        tarjeta.setTitular(titular);
        tarjeta.setFechaCreacion(new SimpleDateFormat("dd/MM/yyyy").format(et.getFecha_creacion()));
        tarjeta.setFechaVencimiento(new SimpleDateFormat("MM/yyyy").format(et.getFecha_vence()));
        tarjeta.setEstado("Inactiva");

        return tarjeta;

    }

    public NumeroEnrrolamientoDTO enrrolarTarjeta(NumeroEnrrolamientoDTO numeroTarjeta) {

        EmisionTarjeta tarjeta = etdao.consultarPorNumeroTarjeta(numeroTarjeta.getCardId());
        tarjeta.setEstado(1);
        tarjeta = etdao.save(tarjeta);
        numeroTarjeta.setEstado(tarjeta.getEstado() == 1 ? "Activa" : "Inactiva");
        return numeroTarjeta;

    }

    public NumeroEnrrolamientoDTO bloquearTarjeta(String pnumeroTarjeta) {

        NumeroEnrrolamientoDTO resultado_tarjeta = new NumeroEnrrolamientoDTO();
        EmisionTarjeta tarjeta = etdao.consultarPorNumeroTarjeta(pnumeroTarjeta);
        tarjeta.setEstado(-1);
        tarjeta = etdao.save(tarjeta);
        resultado_tarjeta.setCardId(tarjeta.getNumeroTarjeta());
        resultado_tarjeta.setEstado(tarjeta.getEstado() == -1 ? "Bloqueada" : "Activa");
        return resultado_tarjeta;

    }

    public SaldoDTO regargarSaldo(SaldoDTO recarga) {

        EmisionTarjeta info_actual = etdao.consultarPorNumeroTarjeta(recarga.getCardId());
        info_actual.setSaldo(info_actual.getSaldo() + recarga.getBalance());
        info_actual = etdao.save(info_actual);
        recarga.setBalance(info_actual.getSaldo());
        return recarga;

    }

    public SaldoDTO consultarSaldo(String cardId) {
        EmisionTarjeta info_actual = etdao.consultarPorNumeroTarjeta(cardId);
        SaldoDTO info_saldo = new SaldoDTO();
        info_saldo.setCardId(info_actual.getNumeroTarjeta());
        info_saldo.setBalance(info_actual.getSaldo());
        return info_saldo;
    }

    @Transactional
    public ResultadoTransaccionDTO comprar(CompraDTO compra) {
        ResultadoTransaccionDTO resultado = new ResultadoTransaccionDTO();
        resultado.setCardId(compra.getCardId());

        EmisionTarjeta info_tarjeta = etdao.consultarPorNumeroTarjeta(compra.getCardId());
        Date ahora = new Date();

        if ((info_tarjeta.getEstado() == 1) && (info_tarjeta.getSaldo() >= compra.getPrice()) && (info_tarjeta.getFecha_vence().after(ahora))) {

            Transaccion transaccion = new Transaccion();
            transaccion.setEstado(1);
            transaccion.setFechaestado(ahora);
            transaccion.setNumerotarjeta(compra.getCardId());
            transaccion.setValor(compra.getPrice());

            transaccion = trdao.save(transaccion);
            info_tarjeta.setSaldo(info_tarjeta.getSaldo() - compra.getPrice());
            info_tarjeta = etdao.save(info_tarjeta);

            resultado.setNumeroTransaccion(transaccion.getIdtransaccion());
            resultado.setResultado("Transaccion Aprobada");
        } else {
            resultado.setNumeroTransaccion(-1);
            resultado.setResultado("Transaccion Rechazada");
        }
        return resultado;

    }

    public TransaccionDTO consultarTransaccion(Integer transactionId) {
        Transaccion transaccion = trdao.findById(transactionId).get();
        TransaccionDTO resultado = new TransaccionDTO();
        resultado.setEstado(transaccion.getEstado().equals(1) ? "Aprobada" : "Anulada");
        resultado.setFechaestado(new SimpleDateFormat("dd/MM/yyyy hh:mm:ss").format(transaccion.getFechaestado()));
        resultado.setIdtransaccion(transaccion.getIdtransaccion());
        resultado.setNumerotarjeta(transaccion.getNumerotarjeta());
        resultado.setValor(transaccion.getValor());
        return resultado;
    }

    public ResultadoTransaccionDTO anularTransaccion(AnulacionDTO anulacion) {

        ResultadoTransaccionDTO resultado = new ResultadoTransaccionDTO();

        resultado.setCardId(anulacion.getCardId());

        EmisionTarjeta info_tarjeta = etdao.consultarPorNumeroTarjeta(anulacion.getCardId());
        Transaccion info_transaccion = trdao.findById(anulacion.getTransactionId()).get();
        Date ahora = new Date();

        if ((info_transaccion.getNumerotarjeta().equals(anulacion.getCardId())) && (utilidades.calcularDiasEntre(info_transaccion.getFechaestado(), ahora) < 1)) {

            info_transaccion.setEstado(2);
            info_transaccion.setFechaestado(ahora);
            
            info_transaccion = trdao.save(info_transaccion);
            info_tarjeta.setSaldo(info_tarjeta.getSaldo() + info_transaccion.getValor());
            info_tarjeta = etdao.save(info_tarjeta);

            resultado.setNumeroTransaccion(info_transaccion.getIdtransaccion());
            resultado.setResultado("Transaccion Anulada");
        } else {
            resultado.setNumeroTransaccion(-1);
            resultado.setResultado("Anulacion Rechazada");
        }
        return resultado;

    }
}
