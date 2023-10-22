package Klass;

import com.github.javaparser.ast.body.ClassOrInterfaceDeclaration;
import com.github.javaparser.ast.visitor.VoidVisitor;
import com.github.javaparser.ast.visitor.VoidVisitorAdapter;

import java.util.ArrayList;
import java.util.List;

public class GetClasses {
    private static class GetClassesVisitor extends VoidVisitorAdapter<List<Class<?>>> {
        @Override
        public void visit(ClassOrInterfaceDeclaration klass, List<Class<?>> collector) {
            List<Class<?>> children = List.of(klass.getClass().getClasses());
            collector.addAll(children);
            super.visit(klass, collector);
        }
    }
    List<Class<?>> getValues(ClassOrInterfaceDeclaration klass) {
        List<Class<?>> classes = new ArrayList<>();
        VoidVisitor<List<Class<?>>> classesVisitor = new GetClassesVisitor();
        classesVisitor.visit(klass, classes);
        return classes;
    }
}
