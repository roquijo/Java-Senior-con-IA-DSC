public class Main {

    public static void main(String[] args) {
        try {
            CuentaBancaria.retirar(4); //ValidacionException
            CuentaBancaria.retirar(4); //EjemploException
            CuentaBancaria.retirar(4);
            CuentaBancaria.retirar(4);
        } catch (ValidacionException e) {
            System.out.println("Ingreso al metodo main");
        } finally {
            System.out.println("Ingreso al metodo main");
        }
    }
}