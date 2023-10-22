package Klass;

import com.github.javaparser.ast.body.ClassOrInterfaceDeclaration;
import com.github.javaparser.ast.body.FieldDeclaration;
import com.github.javaparser.ast.visitor.VoidVisitorAdapter;

import java.util.List;

public class GetFields extends VoidVisitorAdapter<List<FieldDeclaration>> {
    @Override
    public void visit(ClassOrInterfaceDeclaration klass, List<FieldDeclaration> collector) {
        super.visit(klass, collector);
        collector.addAll(klass.getFields());
    }
}
