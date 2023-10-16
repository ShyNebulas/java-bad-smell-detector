package Mefod;

import com.github.javaparser.ast.body.MethodDeclaration;
import com.github.javaparser.ast.visitor.VoidVisitorAdapter;

public class GetName extends VoidVisitorAdapter<StringBuffer> {
    @Override
    public void visit(MethodDeclaration method, StringBuffer  collector) {
        super.visit(method, collector);
        collector.setLength(0);
        collector.append(method.getNameAsString());
    }
}
