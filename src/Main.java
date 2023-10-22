import Klass.GetChildren;
import com.github.javaparser.StaticJavaParser;
import com.github.javaparser.ast.*;
import com.github.javaparser.ast.body.ClassOrInterfaceDeclaration;
import com.github.javaparser.ast.body.FieldDeclaration;
import com.github.javaparser.ast.body.MethodDeclaration;
import com.github.javaparser.ast.body.Parameter;
import com.github.javaparser.ast.type.ClassOrInterfaceType;

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



            List<ClassOrInterfaceDeclaration> class_children = Klass.GetChildren.getValues(compUnit);



            for(ClassOrInterfaceDeclaration class_child : class_children) {

                String class_name = Klass.GetName.getValue(class_child);
                List<MethodDeclaration> class_methods = Klass.GetMethods.getValues(class_child);
                boolean class_is_data = Klass.IsData.getValue(class_methods);
                // Data Class
                if(class_is_data) {
                    System.out.println("[Data Class] " + class_name);
                }
                // Temporary Fields
                List<FieldDeclaration> class_fields = Klass.GetFields.getValues(compUnit);
                for(FieldDeclaration field : class_fields) {
                    if(Klass.IsTempField.getValue(field, class_methods)) {
                        System.out.println("[Temp Field] " + field);
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
