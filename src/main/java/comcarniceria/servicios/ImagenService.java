package comcarniceria.servicios;

import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import org.springframework.beans.factory.annotation.Value;


@Service
public class ImagenService {

    @Value("${path.images}")
    private String pathImages;

    public String guardarImagen(MultipartFile imagen) throws IOException {
        if (imagen.isEmpty()) {
            throw new IllegalArgumentException("La imagen esta vacía");
        }

        String nombreImagen = System.currentTimeMillis() + "_"+ imagen.getOriginalFilename();
        Path rutaImagen = Paths.get(pathImages, nombreImagen);

        Files.copy(imagen.getInputStream(), rutaImagen);
        return "/images/" + nombreImagen;
    }

    public void eliminarImagen(String rutaImagen) {
        try {
            Path path = Paths.get(rutaImagen);
            File file = path.toFile();

            if (file.exists()) {
                boolean deleted = file.delete();
                if (!deleted) {
                    throw new RuntimeException("No se pudo eliminar la imagen: " + rutaImagen);
                }
                System.out.println("Imagen eliminada correctamente: " + rutaImagen);
            } else {
                System.out.println("La imagen no existe en la ruta especificada: " + rutaImagen);
            }
        } catch (Exception e) {
            e.printStackTrace();
            throw new RuntimeException("Error al eliminar imagen: " + rutaImagen, e);
        }
    }
}
