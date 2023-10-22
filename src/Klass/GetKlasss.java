package Klass;

import com.github.javaparser.ast.body.ClassOrInterfaceDeclaration;
import com.github.javaparser.ast.visitor.VoidVisitorAdapter;

import java.util.ArrayList;
import java.util.List;
public class GetKlasss extends VoidVisitorAdapter<List<Class<?>>> {
    @Override
    public void visit(ClassOrInterfaceDeclaration klass, List<Class<?>> collector) {
        List<Class<?>> klasses = new ArrayList<>(List.of(klass.getClass().getClasses()));
        collector.addAll(klasses);
        super.visit(klass, collector);
    }
}
