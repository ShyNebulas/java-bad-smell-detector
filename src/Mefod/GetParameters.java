package Mefod;

import com.github.javaparser.ast.NodeList;
import com.github.javaparser.ast.body.MethodDeclaration;
import com.github.javaparser.ast.body.Parameter;
import com.github.javaparser.ast.visitor.VoidVisitorAdapter;

public class GetParameters extends VoidVisitorAdapter<NodeList<Parameter>> {
    @Override
    public void visit(MethodDeclaration method, NodeList<Parameter> collector) {
        super.visit(method, collector);
        collector.clear();
        collector.addAll(method.getParameters());
    }
}
