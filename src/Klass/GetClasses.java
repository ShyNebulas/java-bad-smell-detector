package Klass;

import com.github.javaparser.ast.body.ClassOrInterfaceDeclaration;
import com.github.javaparser.ast.visitor.VoidVisitorAdapter;

import java.util.List;
public class GetClasses extends VoidVisitorAdapter<List<Class<?>>> {
    @Override
    public void visit(ClassOrInterfaceDeclaration klass, List<Class<?>> collector) {
        List<Class<?>> children = List.of(klass.getClass().getClasses());
        collector.addAll(children);
        super.visit(klass, collector);
    }
}
