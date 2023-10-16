package Klass;

import com.github.javaparser.ast.body.ClassOrInterfaceDeclaration;
import com.github.javaparser.ast.body.MethodDeclaration;
import com.github.javaparser.ast.visitor.VoidVisitorAdapter;

import java.util.List;
public class GetMethods extends VoidVisitorAdapter<List<MethodDeclaration>> {
    @Override
    public void visit(ClassOrInterfaceDeclaration klass, List<MethodDeclaration> collector) {
        super.visit(klass, collector);
        collector.clear();
        collector.addAll(klass.getMethods());
    }
}