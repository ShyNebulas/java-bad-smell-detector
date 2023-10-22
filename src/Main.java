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
