package Klass;

import com.github.javaparser.ast.body.ClassOrInterfaceDeclaration;
import com.github.javaparser.ast.body.MethodDeclaration;
import com.github.javaparser.ast.visitor.VoidVisitor;
import com.github.javaparser.ast.visitor.VoidVisitorAdapter;

import java.util.ArrayList;
import java.util.List;
public class GetMethods extends VoidVisitorAdapter<List<MethodDeclaration>> {
    private static class GetMethodsVisitor extends VoidVisitorAdapter<List<MethodDeclaration>> {
        @Override
        public void visit(ClassOrInterfaceDeclaration klass, List<MethodDeclaration> collector) {
            super.visit(klass, collector);
            collector.addAll(klass.getMethods());
        }
    }
    public static List<MethodDeclaration> getValues(ClassOrInterfaceDeclaration klass) {
        List<MethodDeclaration> methods = new ArrayList<>();
        VoidVisitor<List<MethodDeclaration>> methodsVisitor = new GetMethodsVisitor();
        methodsVisitor.visit(klass, methods);
        return methods;
    }
}
