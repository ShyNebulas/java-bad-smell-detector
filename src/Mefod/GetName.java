package Mefod;

import com.github.javaparser.ast.body.MethodDeclaration;
import com.github.javaparser.ast.visitor.VoidVisitor;
import com.github.javaparser.ast.visitor.VoidVisitorAdapter;
public class GetName {
    private static class GetNameVisitor extends VoidVisitorAdapter<StringBuffer> {
        @Override
        public void visit(MethodDeclaration method, StringBuffer collector) {
            super.visit(method, collector);
            collector.append(method.getNameAsString());
        }
    }
    public static String getValue(MethodDeclaration method) {
        StringBuffer name = new StringBuffer();
        VoidVisitor<StringBuffer> nameVisitor = new GetNameVisitor();
        nameVisitor.visit(method, name);
        return name.toString();
    }
}
