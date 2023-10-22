package Klass;

import com.github.javaparser.ast.body.ClassOrInterfaceDeclaration;
public class IsRefusedBequest {
    public static boolean getValue(ClassOrInterfaceDeclaration klass) {
        return klass.getExtendedTypes().stream().anyMatch(type -> type.getNameAsString().equals(klass.getExtendedTypes(0).getNameAsString()));
    }
}
