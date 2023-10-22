package Klass;

import com.github.javaparser.ast.body.MethodDeclaration;
import com.github.javaparser.ast.body.VariableDeclarator;

import java.util.List;
public class IsTempField {
    public static boolean getValue(VariableDeclarator variable, List<MethodDeclaration> methods) {
        int count = 0;
        if (variable.getInitializer().isEmpty()) {
            String fieldName = variable.getNameAsString();
            for (MethodDeclaration method : methods) {
                if (method.toString().contains(fieldName)) {
                        count += 1;
                }
            }
        }
        return count == 1;
    }
}
