import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardCopyOption;

public class Buffer {
    private File archivo;
    private String copia;

    public Buffer(String ruta) throws IOException {
        this.archivo = new File(ruta);

        //Pasar el archivo a un string
        this.copia = Files.readString(archivo.toPath());
    }

     public boolean ArchivoVacio() {
        if (copia.length() == 0) {
            return true;
        }
        else {
            return false;
        }
     }
    public char obtenerCaracter() {
        char primerCaracter;

        // Obtener el primer carácter
        primerCaracter = copia.charAt(0);

        // Eliminar el primer carácter y sobrescribir la copia
        copia = copia.substring(1);

        return primerCaracter;
    }

    public void agregarCaracter (char caracter){
        this.copia = caracter + this.copia;
    }
}
