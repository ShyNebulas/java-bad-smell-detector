import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.List;
import java.util.stream.Stream;
public class Directory {
    public static List<Path> getFilePaths(Path directory) throws IOException {
        try(Stream<Path> paths = Files.walk(directory)) {
            return paths.filter(file -> {
                try {
                    return Files.isRegularFile(file) && !Files.isHidden(file);
                } catch (IOException error) {
                    throw new RuntimeException(error);
                }
            }).sorted().toList();
        }
    }
}
