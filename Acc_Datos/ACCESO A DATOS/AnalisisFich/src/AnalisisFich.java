import java.io.*;

public class AnalisisFich {
    public static void main (String[]args) throws IOException {

        // Primera lectura del archivo
        FileReader leer = new FileReader("seguridad.txt");
        BufferedReader buffer = new BufferedReader(leer);

        String linea;

        while ((linea = buffer.readLine()) != null) {
            System.out.println(linea);
        }
        buffer.close();

        // Escritura en el archivo de 2 nuevas lineas
        BufferedWriter escribir = new BufferedWriter(new FileWriter("seguridad.txt", true));

        escribir.write("[INFO] Nuevo intento de inicio de sesión" + System.lineSeparator());
        escribir.write("[WARNING] Contraseña incorrecta introducida tres veces" + System.lineSeparator());

        escribir.close();

        // Segunda lectura del archivo
        FileReader lectura = new FileReader("seguridad.txt");
        BufferedReader lectors = new BufferedReader(lectura);

        String line;

        while ((line = lectors.readLine()) != null) {
            System.out.println(line);
        }
        lectors.close();

        // Contador de lineas del archivo
        FileReader contar = new FileReader("seguridad.txt");
        BufferedReader lecturas = new BufferedReader(contar);

        String lies;
        int contador = 0;
        int totalCaracteres = 0;
        int totalPalabras = 0;

        while ((lies = lecturas.readLine()) != null) {
            contador ++;

            totalCaracteres += lies.length();

            String[] bloques = lies.trim().split("\\s+");
            totalPalabras += bloques.length;
        }
        System.out.println(
                "\nNúmero de lineas = " + contador +
                "\nNúmero de palabras = " + totalPalabras +
                "\nNúmero de caracteres = " + totalCaracteres
        );

        lecturas.close();


        FileWriter copiar = new FileWriter("seguridad_copia.txt");
        copiar.write(new FileReader("seguridad.txt", true));


    }
}
