package Klass;

import com.github.javaparser.ast.NodeList;
import com.github.javaparser.ast.body.MethodDeclaration;
import com.github.javaparser.ast.body.Parameter;

import java.util.List;
public class IsData {
    public static boolean getValue(List<MethodDeclaration> methods) {
        boolean dataClass = false;
        boolean lock = false;

        for (MethodDeclaration method : methods) {
            String name = Mefod.GetName.getValue(method);
            NodeList<Parameter> parameters = Mefod.GetParameters.getValues(method);

            boolean getter = false;
            if (method.getModifiers().toString().equals("[public ]") && parameters.size() == 0) {
                if ((name.matches("^get[A-Z].*") && !method.getType().toString().equals("void")) ||
                        (name.matches("^is[A-Z].*") && method.getType().toString().equals("boolean"))) {
                    dataClass = true;
                    getter = true;
                }
            }
            if (method.getModifiers().toString().equals("[public ]") && parameters.size() == 1) {
                if (name.matches("^set[A-Z].*") && method.getType().toString().equals("void")) {
                    dataClass = true;
                }
            } else if (!getter) {
                lock = true;
            }
        }
        return dataClass && !lock;
    }
}
