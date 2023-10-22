import com.github.javaparser.StaticJavaParser;
import com.github.javaparser.ast.*;
import com.github.javaparser.ast.body.MethodDeclaration;
import com.github.javaparser.ast.body.Parameter;

import java.io.IOException;
import java.nio.file.*;
import java.util.ArrayList;
import java.util.List;
public class Main {
    private static final Path DIRECTORY_PATH = Paths.get("data/src");
    public static void main(String[] args) throws IOException {
        List<Path> paths = Directory.getFilePaths(DIRECTORY_PATH);
        for (Path path : paths) {
            System.out.println(path);
            CompilationUnit compUnit = StaticJavaParser.parse(path);

            String class_name = Klass.GetName.getValue(compUnit);
            int class_length = Klass.GetLength.getValue(compUnit);
            if(class_length > 20) {
                System.out.println("[Long Class] " + class_name);
            }
            ArrayList<MethodDeclaration> methods = Klass.GetMethods.getValues(compUnit);
            for(MethodDeclaration method : methods) {
                String method_name = Mefod.GetName.getValue(method);
                int method_length = Mefod.GetLength.getValue(method);
                NodeList<Parameter> parameters = Mefod.GetParameters.getValues(method);
                if(method_length > 20) {
                    System.out.println("[Long Method] " + method_name);
                }
            }
            System.out.println("======================================");
        }
    }
}
