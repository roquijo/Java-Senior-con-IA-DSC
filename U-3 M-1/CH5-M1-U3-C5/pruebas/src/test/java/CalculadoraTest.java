import org.example.Calculadora;
import org.junit.jupiter.api.*;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;

class CalculadoraTest {

    static Calculadora calc;
    static Double num1, num2;

    @BeforeEach
    void setUp() {
        //Se ejecuta antes de cada test
        calc = new Calculadora();
        num1 = 5.0;
        num2 = 3.0;
    }

    @AfterEach
    void tearDown() throws Exception {
        //Se ejecuta despues de cada test
        calc = null;
        System.out.println("Termino el test");
    }

    @BeforeAll
    static void setUpBeforeClass() throws Exception {
        //Se ejecuta una vez antes de ejecutar todos los test


    }

    @AfterAll
    static void tearDownAfterClass() throws Exception {
        //Se ejecuta una vez despues de ejecutar todos los test
    }

    @Test
    void sumar() {


        Double result = calc.sumar(num1, num2);

        assertNotNull(result, "El resultado no es null");
        System.out.println("Paso not null");
        assertEquals(8.0, result, "El resultado es: " + result);
        System.out.println("Paso equals");
    }

    @Test
    void dividir() {




        Double result = calc.dividir(num1, num2);
        assertEquals(num1 / num2, result , "El resultado es: " + result);
    }
}
