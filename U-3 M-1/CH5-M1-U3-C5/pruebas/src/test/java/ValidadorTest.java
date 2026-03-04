
import org.example.Validador;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;

class ValidadorTest {

    private Validador validador;

    @BeforeEach
    void setUp() {
        // Se ejecuta antes de cada prueba
        validador = new Validador();
    }

    // ========== Pruebas para validarEmail ==========

    @Test
    void testValidarEmail_Correcto() {
        // ARRANGE
        String email = "usuario@example.com";

        // ACT
        boolean resultado = validador.validarEmail(email);

        // ASSERT
        assertTrue(resultado, "El email válido debe retornar true");
    }

    @Test
    void testValidarEmail_SinArroba() {
        // ARRANGE
        String email = "usuarioexample.com";

        // ACT
        boolean resultado = validador.validarEmail(email);

        // ASSERT
        assertFalse(resultado, "Email sin @ debe retornar false");
    }

    @Test
    void testValidarEmail_Null() {
        // ARRANGE
        String email = null;

        // ACT
        boolean resultado = validador.validarEmail(email);

        // ASSERT
        assertFalse(resultado, "Email null debe retornar false");
    }

    @Test
    void testValidarEmail_Vacio() {
        // ARRANGE
        String email = "";

        // ACT
        boolean resultado = validador.validarEmail(email);

        // ASSERT
        assertFalse(resultado, "Email vacío debe retornar false");
    }

    // ========== Pruebas para validarPassword ==========

    @Test
    void testValidarPassword_Correcto() {
        // ARRANGE
        String password = "Password123";

        // ACT
        boolean resultado = validador.validarPassword(password);

        // ASSERT
        assertTrue(resultado, "Password válido debe retornar true");
    }

    @Test
    void testValidarPassword_MuyCorto() {
        // ARRANGE
        String password = "Pass1";  // Solo 5 caracteres

        // ACT
        boolean resultado = validador.validarPassword(password);

        // ASSERT
        assertFalse(resultado, "Password muy corto debe retornar false");
    }

    @Test
    void testValidarPassword_SinMayuscula() {
        // ARRANGE
        String password = "password123";  // Sin mayúscula

        // ACT
        boolean resultado = validador.validarPassword(password);

        // ASSERT
        assertFalse(resultado, "Password sin mayúscula debe retornar false");
    }

    @Test
    void testValidarPassword_SinNumero() {
        // ARRANGE
        String password = "Password";  // Sin número

        // ACT
        boolean resultado = validador.validarPassword(password);

        // ASSERT
        assertFalse(resultado, "Password sin número debe retornar false");
    }

    // ========== Pruebas para validarRango ==========

    @Test
    void testValidarRango_DentroDelRango() {
        // ARRANGE
        int numero = 5;
        int min = 1;
        int max = 10;

        // ACT
        boolean resultado = validador.validarRango(numero, min, max);

        // ASSERT
        assertTrue(resultado, "Número dentro del rango debe retornar true");
    }

    @Test
    void testValidarRango_MenorAlMinimo() {
        // ARRANGE
        int numero = 0;
        int min = 1;
        int max = 10;

        // ACT
        boolean resultado = validador.validarRango(numero, min, max);

        // ASSERT
        assertFalse(resultado, "Número menor al mínimo debe retornar false");
    }

    @Test
    void testValidarRango_MayorAlMaximo() {
        // ARRANGE
        int numero = 15;
        int min = 1;
        int max = 10;

        // ACT
        boolean resultado = validador.validarRango(numero, min, max);

        // ASSERT
        assertFalse(resultado, "Número mayor al máximo debe retornar false");
    }

    @Test
    void testValidarRango_EnElLimite() {
        // ARRANGE
        int numero = 10;
        int min = 1;
        int max = 10;

        // ACT
        boolean resultado = validador.validarRango(numero, min, max);

        // ASSERT
        assertTrue(resultado, "Número en el límite debe retornar true");
    }
}