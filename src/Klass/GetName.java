package Klass;

import com.github.javaparser.ast.CompilationUnit;
import com.github.javaparser.ast.body.ClassOrInterfaceDeclaration;
import com.github.javaparser.ast.visitor.VoidVisitor;
import com.github.javaparser.ast.visitor.VoidVisitorAdapter;
public class GetName {
    private static class GetNameVisitor extends VoidVisitorAdapter<StringBuffer> {
        @Override
        public void visit(ClassOrInterfaceDeclaration klass, StringBuffer collector) {
            super.visit(klass, collector);
            collector.append(klass.getNameAsString());
        }
    }
    public static String getValue(CompilationUnit compUnit) {
        StringBuffer name = new StringBuffer();
        VoidVisitor<StringBuffer> nameVisitor = new GetNameVisitor();
        nameVisitor.visit(compUnit, name);
        return name.toString();
    }
}
