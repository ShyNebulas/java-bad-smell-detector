import com.github.javaparser.StaticJavaParser;
import com.github.javaparser.ast.*;
import com.github.javaparser.ast.body.*;

import java.io.IOException;
import java.nio.file.*;
import java.util.List;
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
        StringBuffer length = new StringBuffer();
        VoidVisitor<StringBuffer> lengthVisitor = new Mefod.GetLength();
        lengthVisitor.visit(method, length);
        return Integer.valueOf(length.toString());
    }

    private static void detectMessageChains(MethodDeclaration method) {
            method.walk(MethodCallExpr.class, mc -> {
                if (mc.getScope().isPresent() && mc.getScope().get().isMethodCallExpr()) {
                    System.out.println("Message chain detected in method '" + getMethodName(method) + "': " + mc);
                }
            });
    }

    // Method used for detecting refusedBequest
    private static boolean inheritsMethod(ClassOrInterfaceDeclaration classDeclaration) {
        return classDeclaration.getExtendedTypes().stream().anyMatch(type -> type.getNameAsString().equals(classDeclaration.getExtendedTypes(0).getNameAsString()));
    }
    private static void detectRefusedBequest(ClassOrInterfaceDeclaration classDeclaration) {
        for (MethodDeclaration method : classDeclaration.getMethods()) {
            if (inheritsMethod(classDeclaration)) {
                System.out.println("Refused Bequest detected in class '" + classDeclaration.getNameAsString() + "': Method '" + getMethodName(method) + "' is overridden.");
            }
        }
    }

    public static void main(String[] args) throws IOException {
        List<Path> paths = Directory.getFilePaths(DIRECTORY_PATH);
        for (Path path : paths) {
            System.out.println(path);

            CompilationUnit compUnit = StaticJavaParser.parse(path);

            List<ClassOrInterfaceDeclaration> class_children = compUnit.findAll(ClassOrInterfaceDeclaration.class);
            for(ClassOrInterfaceDeclaration class_child : class_children) {
                String class_name = Klass.GetName.getValue(class_child);
                System.out.println("Class name: " + class_name);
                int class_length = Klass.GetLength.getValue(class_child);
                List<MethodDeclaration> class_methods = Klass.GetMethods.getValues(class_child);
                boolean class_is_data = Klass.IsData.getValue(class_methods);
                // Large Class
                if(class_length > 100) {
                    System.out.println("[Large Class] " + class_name);
                }
                // Data Class
                if(class_is_data) {
                    System.out.println("[Data Class] " + class_name);
                }
                // Temporary Fields
                List<FieldDeclaration> class_fields = Klass.GetFields.getValues(class_child);
                for(FieldDeclaration field : class_fields) {
                    for(VariableDeclarator field_variable : field.getVariables()) {
                        if(Klass.IsTempField.getValue(field_variable, class_methods)) {
                            System.out.println("[Temp Field] " + field_variable.getNameAsString());
                        }
                    }
                }
                // Long Parameter List & Long Method
                for(MethodDeclaration method : class_methods) {
                    String method_name = Mefod.GetName.getValue(method);
                    NodeList<Parameter> method_parameters = Mefod.GetParameters.getValues(method);
                    int method_length = Mefod.GetLength.getValue(method);
                    if(method_parameters.size() > 5) {
                        System.out.println("[Long Parameter List] " + method_name);
                    }
                    if(method_length > 20) {
                        System.out.println("[Long Method] " + method_name);
                    }
                }
            }


            System.out.println("======================================");
        }
    }
}