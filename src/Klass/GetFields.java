package Klass;

import com.github.javaparser.ast.body.ClassOrInterfaceDeclaration;
import com.github.javaparser.ast.body.FieldDeclaration;
import com.github.javaparser.ast.visitor.VoidVisitor;
import com.github.javaparser.ast.visitor.VoidVisitorAdapter;

import java.util.ArrayList;
import java.util.List;
public class GetFields {
    private static class GetFieldsVisitor extends VoidVisitorAdapter<List<FieldDeclaration>> {
        @Override
        public void visit(ClassOrInterfaceDeclaration klass, List<FieldDeclaration> collector) {
            super.visit(klass, collector);
            collector.addAll(klass.getFields());
        }
    }
    public static List<FieldDeclaration> getValues(ClassOrInterfaceDeclaration klass) {
        List<FieldDeclaration> fields = new ArrayList<>();
        VoidVisitor<List<FieldDeclaration>> fieldsVisitor = new GetFieldsVisitor();
        fieldsVisitor.visit(klass, fields);
        return fields;
    }
}
