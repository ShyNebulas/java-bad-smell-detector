package Klass;

import com.github.javaparser.ast.body.ClassOrInterfaceDeclaration;
import com.github.javaparser.ast.visitor.VoidVisitor;
import com.github.javaparser.ast.visitor.VoidVisitorAdapter;
public class GetName {
    private static class GetNameVisitor extends VoidVisitorAdapter<StringBuffer> {
        @Override
        public void visit(ClassOrInterfaceDeclaration klass, StringBuffer collector) {
            super.visit(klass, collector);
            collector.setLength(0);
            collector.append(klass.getNameAsString());
        }
    }
    public static String getValue(ClassOrInterfaceDeclaration klass) {
        StringBuffer name = new StringBuffer();
        VoidVisitor<StringBuffer> nameVisitor = new GetNameVisitor();
        nameVisitor.visit(klass, name);
        return name.toString();
    }
}
