import java.util.Random;

public class GeneraNumeros {
    static void main(String[] args) {
//Por defecto generamos 35 numeros:
        int cantidad = 5;

        if (args.length > 0) {
            try {
                cantidad = Integer.parseInt(args[0]);
            } catch (NumberFormatException e){
                System.out.println("El argumento no es un numero " + args[0]);
                System.exit(2);
            }
        }

        // Apartado B)
        // Escribe esos números aleatorios entre 0 y 100 por su salida estándar, uno por línea
        Random aleatorio= new Random();

        for (int i=0; i<cantidad; i++) {
            System.out.println(aleatorio.nextInt(101));
        }
        System.exit(0);
    }
}