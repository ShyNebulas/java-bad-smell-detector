import com.github.javaparser.StaticJavaParser;
import com.github.javaparser.ast.*;
import com.github.javaparser.ast.body.MethodDeclaration;
import com.github.javaparser.ast.body.Parameter;
import com.github.javaparser.ast.visitor.VoidVisitor;

import java.io.IOException;
import java.nio.file.*;
import java.text.MessageFormat;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.stream.Stream;

//TODO: Add line numbers to bad smell errors


public class Main {
    private static final Path DIRECTORY_PATH = Paths.get("data/src");
    public static List<Path> getDirectoryPaths(Path directory) throws IOException {
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
    public static ArrayList<MethodDeclaration> getClassMethods(CompilationUnit compUnit) {
        ArrayList<MethodDeclaration> methods = new ArrayList<>();
        VoidVisitor<List<MethodDeclaration>> methodsVisitor = new Klass.GetMethods();
        methodsVisitor.visit(compUnit, methods);
        return methods;
    }
    public static String getMethodName(MethodDeclaration method) {
        StringBuffer name = new StringBuffer();
        VoidVisitor<StringBuffer> nameVisitor = new Mefod.GetName();
        nameVisitor.visit(method, name);
        return name.toString();
    }
    public static NodeList<Parameter> getMethodParameters(MethodDeclaration method) {
        NodeList<Parameter> parameters = new NodeList<>();
        VoidVisitor<NodeList<Parameter>> parametersVisitor = new Mefod.GetParameters();
        parametersVisitor.visit(method, parameters);
        return parameters;
    }

    public static Integer getMethodLength(MethodDeclaration method) {
        AtomicInteger length = new AtomicInteger(0);
        VoidVisitor<AtomicInteger> lengthVisitor = new Mefod.GetLength();
        lengthVisitor.visit(method, length);
        return length.get();
    }
    public static void main(String[] args) throws IOException {
        List<Path> paths = getDirectoryPaths(DIRECTORY_PATH);
        for (Path path : paths) {
            System.out.println(path);

            CompilationUnit compUnit = StaticJavaParser.parse(path);

            ArrayList<MethodDeclaration> methods = getClassMethods(compUnit);

            for(MethodDeclaration method : methods) {
                System.out.println(method.getNameAsString());
                //String name = getMethodName(method);
                Integer length = getMethodLength(method);
                System.out.println(length);
                //NodeList<Parameter> parameters = getMethodParameters(method);
//                if(parameters.size() > 3) {
//                    System.out.println(MessageFormat.format("Method {0} has a long parameter list", name));
//                }
//                if(length > 10) {
//                    System.out.println(MessageFormat.format("Method {0} has a long method", name));
//                }
            }

            System.out.println("======================================");
        }
    }
}
