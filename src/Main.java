import com.github.javaparser.StaticJavaParser;
import com.github.javaparser.ast.CompilationUnit;
import com.github.javaparser.ast.NodeList;
import com.github.javaparser.ast.body.*;
import com.github.javaparser.ast.visitor.VoidVisitor;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.text.MessageFormat;
import java.util.ArrayList;
import java.util.List;
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
    public static List<Class<?>> getKlasss(CompilationUnit compUnit) {
        List<Class<?>> klasss = new ArrayList<>();
        VoidVisitor<List<Class<?>>> KlasssVisitor = new Klass.GetKlasss();
        KlasssVisitor.visit(compUnit, klasss);
        return klasss;
    }

    public static ArrayList<MethodDeclaration> getClassMethods(CompilationUnit compUnit) {
        ArrayList<MethodDeclaration> methods = new ArrayList<>();
        VoidVisitor<List<MethodDeclaration>> methodsVisitor = new Klass.GetMethods();
        methodsVisitor.visit(compUnit, methods);
        return methods;
    }

    public static ArrayList<FieldDeclaration> getClassFields(CompilationUnit compUnit){
        ArrayList<FieldDeclaration> fields = new ArrayList<>();
        VoidVisitor<List<FieldDeclaration>> fieldsVisitor = new Klass.GetFields();
        fieldsVisitor.visit(compUnit, fields);
        return fields;
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
        StringBuffer length = new StringBuffer();
        VoidVisitor<StringBuffer> lengthVisitor = new Mefod.GetLength();
        lengthVisitor.visit(method, length);
        return Integer.valueOf(length.toString());
    }
    public static void main(String[] args) throws IOException {
        List<Path> paths = getDirectoryPaths(DIRECTORY_PATH);
        for (Path path : paths) {
            System.out.println(path);

            boolean dataClass = false;
            boolean lock = false;

            CompilationUnit compUnit = StaticJavaParser.parse(path);

            List<Class<?>> klasss = getKlasss(compUnit);
            ArrayList<MethodDeclaration> methods = getClassMethods(compUnit);
            ArrayList<FieldDeclaration> fields = getClassFields(compUnit);

            for(MethodDeclaration method : methods) {
                String name = getMethodName(method);
                Integer length = getMethodLength(method);
                NodeList<Parameter> parameters = getMethodParameters(method);
                if(parameters.size() > 5) {
                    System.out.println(MessageFormat.format("Method {0} has a long parameter list", name));
                }
                if(length > 20) {
                    System.out.println(MessageFormat.format("Method {0} is a long method", name));
                }
            }
//data class Detector\/
//            for(Class<?> klass : klasss) {
                for (MethodDeclaration method : methods) {
                    boolean getter = false;
                    if (method.getModifiers().toString().equals("[public ]") && getMethodParameters(method).size() == 0) {
                        if ((getMethodName(method).matches("^get[A-Z].*") && !method.getType().toString().equals("void")) ||
                                (getMethodName(method).matches("^is[A-Z].*") && method.getType().toString().equals("boolean"))) {
                            dataClass = true;
                            getter = true;
                        }
                    }
                    if (method.getModifiers().toString().equals("[public ]") && getMethodParameters(method).size() == 1) {
                        if (getMethodName(method).matches("^set[A-Z].*") && method.getType().toString().equals("void")) {
                            dataClass = true;
                        }
                    } else if (!getter) {
                        lock = true;
                    }
                }
                if (dataClass && !lock) {
                    System.out.println("This is a data class.");
                }
//            }
//data class Detector/\
//temporary field Detector\/
            for(FieldDeclaration field : fields) {
                for(VariableDeclarator variable : field.getVariables()) {
                    if (variable.getInitializer().isEmpty()) {
                        String fieldName = variable.getNameAsString();
                        int temp = 0;
                        for (MethodDeclaration method : compUnit.findAll(MethodDeclaration.class)) {
                            if (method.toString().contains(fieldName)) {
                                temp += 1;
                            }
                        }
                        if (temp == 1) {
                            System.out.println(fieldName + " may be a temporary field.");
                        }
                    }
                }
            }
//temporary field Detector/\



            System.out.println("======================================");
        }
    }
}
