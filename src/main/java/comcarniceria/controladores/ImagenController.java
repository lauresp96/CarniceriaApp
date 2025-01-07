package comcarniceria.controladores;

import org.springframework.core.io.FileSystemResource;
import org.springframework.core.io.Resource;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;

import java.nio.file.Path;
import java.nio.file.Paths;

@RestController
public class ImagenController {

    @GetMapping("/images/{filename:.+}")
    public Resource getImage(@PathVariable String filename) {
        Path path = Paths.get("C:/Users/laure/Desktop/PROYECTOS MIHAI/Imagenes Carniceria").resolve(filename);
        return new FileSystemResource(path);
    }
}
