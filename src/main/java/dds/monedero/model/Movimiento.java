package dds.monedero.model;

import java.time.LocalDate;

public class Movimiento {
  private LocalDate fecha;
  // Nota: En ningún lenguaje de programación usen jamás doubles (es decir, números con punto flotante) para modelar dinero en el mundo real.
  // En su lugar siempre usen numeros de precision arbitraria o punto fijo, como BigDecimal en Java y similares
  // De todas formas, NO es necesario modificar ésto como parte de este ejercicio. 
  private double monto;
  private boolean esDeposito;

  public Movimiento(final LocalDate fecha, final double monto, final boolean esDeposito) {
    this.fecha = fecha;
    this.monto = monto;
    this.esDeposito = esDeposito;
  }

  public final double getMonto() {
    return monto;
  }

  public final LocalDate getFecha() {
    return fecha;
  }

  public final boolean fueDepositado(final LocalDate fecha) {
    return isDeposito() && esDeLaFecha(fecha);
  }

  public final boolean fueExtraido(final LocalDate fecha) {
    return isExtraccion() && esDeLaFecha(fecha);
  }

  public final boolean esDeLaFecha(final LocalDate fecha) {
    return this.fecha.equals(fecha);
  }

  public final boolean isDeposito() {
    return esDeposito;
  }

  public final boolean isExtraccion() {
    return !esDeposito;
  }

  public final void agregateA(final Cuenta cuenta) {
    cuenta.setSaldo(calcularValor(cuenta));
    cuenta.agregarMovimiento(fecha, monto, esDeposito);
  }

  public final double calcularValor(final Cuenta cuenta) {
    if (esDeposito) {
      return cuenta.getSaldo() + getMonto();
    } else {
      return cuenta.getSaldo() - getMonto();
    }
  }
}
