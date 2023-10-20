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
            ArrayList<MethodDeclaration> methods = Klass.GetMethods.getValues(compUnit);
            for(MethodDeclaration method : methods) {
                String name = Mefod.GetName.getValue(method);
                int length = Mefod.GetLength.getValue(method);
                NodeList<Parameter> parameters = Mefod.GetParameters.getValues(method);
                if(length > 20) {
                    System.out.println("[Long Method] " + name);
                }
            }
            System.out.println("======================================");
        }
    }
}
