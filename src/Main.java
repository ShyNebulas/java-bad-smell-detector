import com.github.javaparser.StaticJavaParser;
import com.github.javaparser.ast.*;
import com.github.javaparser.ast.body.*;

import java.io.IOException;
import java.nio.file.*;
import java.util.List;
public class Main {
    private static final Path DIRECTORY_PATH = Paths.get("data/src");
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
                boolean class_is_refused_bequest = Klass.IsRefusedBequest.getValue(class_child);
                // Large Class
                if(class_length > 100) {
                    System.out.println("[Large Class] " + class_name);
                }
                // Data Class
                if(class_is_data) {
                    System.out.println("[Data Class] " + class_name);
                }
                // Refused Bequest
                if(class_is_refused_bequest) {
                    System.out.println("[Refused Bequest] " + class_name);
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
                for(MethodDeclaration class_method : class_methods) {
                    String method_name = Mefod.GetName.getValue(class_method);
                    NodeList<Parameter> method_parameters = Mefod.GetParameters.getValues(class_method);
                    int method_length = Mefod.GetLength.getValue(class_method);
                    boolean method_chain = Mefod.IsMessageChain.getValue(class_method);

                    if(method_parameters.size() > 5) {
                        System.out.println("[Long Parameter List] " + method_name);
                    }
                    if(method_length > 20) {
                        System.out.println("[Long Method] " + method_name);
                    }
                    if(method_chain) {
                        System.out.println("[Message Chain] " + method_name);
                    }
                }
            }
            System.out.println("======================================");
        }
    }
}