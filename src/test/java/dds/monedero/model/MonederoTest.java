package dds.monedero.model;

import dds.monedero.exceptions.MaximaCantidadDepositosException;
import dds.monedero.exceptions.MaximoExtraccionDiarioException;
import dds.monedero.exceptions.MontoNegativoException;
import dds.monedero.exceptions.SaldoMenorException;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;


public class MonederoTest {
  private Cuenta cuenta;

  @BeforeEach
  void init() {
    cuenta = new Cuenta();
  }

  @Test
  @DisplayName("Es posible poner $1500 en una cuenta vacía")
  void Poner() {
    cuenta.poner(1500);
    assertEquals(1500, cuenta.getSaldo());

  }

  @Test
  @DisplayName("No es posible poner montos negativos")
  void PonerMontoNegativo() {
    assertThrows(MontoNegativoException.class, () -> cuenta.poner(-1500));
    assertEquals(0, cuenta.getSaldo());

  }

  @Test
  @DisplayName("Es posible realizar múltiples depósitos consecutivos")
  void TresDepositos() {
    cuenta.poner(1500);
    cuenta.poner(456);
    cuenta.poner(1900);
    assertEquals(3856, cuenta.getSaldo());

  }

  @Test
  @DisplayName("No es posible superar la máxima cantidad de depositos diarios")
  void MasDeTresDepositos() {
    Exception exception = assertThrows(MaximaCantidadDepositosException.class, () -> {
      cuenta.poner(1500);
      cuenta.poner(456);
      cuenta.poner(1900);
      cuenta.poner(245);
    });
    assertEquals("Ya excedio los 3 depositos diarios", exception.getMessage());
    assertEquals(3856, cuenta.getSaldo());

  }

  @Test
  @DisplayName("No es posible extraer más que el saldo disponible")
  void ExtraerMasQueElSaldo() {
    cuenta.setSaldo(90);
    Exception exception = assertThrows(SaldoMenorException.class, () -> cuenta.sacar(1001));
    assertEquals("No puede sacar mas de 90.0 $", exception.getMessage());
    assertEquals(90, cuenta.getSaldo());
  }

  @Test
  @DisplayName("No es posible extraer más que el límite diario")
  void ExtraerMasDe1000() {
    cuenta.setSaldo(5000);
    Exception exception = assertThrows(MaximoExtraccionDiarioException.class, () -> cuenta.sacar(1001));
    assertEquals(("No puede extraer mas de $ 1000 diarios"), exception.getMessage());
    assertEquals(5000, cuenta.getSaldo());

  }

  @Test
  @DisplayName("No es posible extraer un monto negativo")
  void ExtraerMontoNegativo() {
    cuenta.setSaldo(500);
    Exception exception = assertThrows(MontoNegativoException.class, () -> cuenta.sacar(-500));
    assertEquals("-500.0: el monto a ingresar debe ser un valor positivo", exception.getMessage());
    assertEquals(500, cuenta.getSaldo());

  }

}