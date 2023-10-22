package Klass;

import com.github.javaparser.ast.body.*;
import com.github.javaparser.ast.visitor.GenericVisitorAdapter;
import com.github.javaparser.ast.visitor.VoidVisitor;
import com.github.javaparser.ast.visitor.VoidVisitorAdapter;

import java.util.concurrent.atomic.AtomicInteger;
public class GetLength {
    private static class GetLengthVisitor extends VoidVisitorAdapter<AtomicInteger> {
        private static class ClassVisitor extends GenericVisitorAdapter<BodyDeclaration, AtomicInteger> {
            @Override
            public CompactConstructorDeclaration visit(CompactConstructorDeclaration body, AtomicInteger collector) {
                //System.out.println("[CompactConstructor]" + statement);
                int method_length = 0;
                if(body.toMethodDeclaration().isPresent()) {
                    method_length = Mefod.GetLength.getValue(body.toMethodDeclaration().get());
                }
                collector.addAndGet(method_length + 1);
                return (CompactConstructorDeclaration) super.visit(body, collector);
            }
            @Override
            public ConstructorDeclaration visit(ConstructorDeclaration body, AtomicInteger collector) {
                //System.out.println("[Constructor]" + statement);
                int method_length = 0;
                if(body.toMethodDeclaration().isPresent()) {
                    method_length = Mefod.GetLength.getValue(body.toMethodDeclaration().get());
                }
                collector.addAndGet(method_length + 1);
                return (ConstructorDeclaration) super.visit(body, collector);
            }
            @Override
            public EnumConstantDeclaration visit(EnumConstantDeclaration body, AtomicInteger collector) {
                //System.out.println("[EnumConstant]" + statement);
                collector.addAndGet(1);
                return (EnumConstantDeclaration) super.visit(body, collector);
            }
            @Override
            public EnumDeclaration visit(EnumDeclaration body, AtomicInteger collector) {
                //System.out.println("[Enum]" + statement);
                collector.addAndGet(1);
                return (EnumDeclaration) super.visit(body, collector);
            }
            @Override
            public FieldDeclaration visit(FieldDeclaration body, AtomicInteger collector) {
                //System.out.println("[Field]" + statement);
                collector.addAndGet(1);
                return (FieldDeclaration) super.visit(body, collector);
            }
            @Override
            public InitializerDeclaration visit(InitializerDeclaration body, AtomicInteger collector) {
                //System.out.println("[Initializer]" + statement);
                collector.addAndGet(1);
                return (InitializerDeclaration) super.visit(body, collector);
            }
            @Override
            public MethodDeclaration visit(MethodDeclaration body, AtomicInteger collector) {
                //System.out.println("[Method]" + statement);
                int method_length = Mefod.GetLength.getValue(body);
                collector.addAndGet(method_length + 1);
                return (MethodDeclaration) super.visit(body, collector);
            }
            @Override
            public RecordDeclaration visit(RecordDeclaration body, AtomicInteger collector) {
                //System.out.println("[Record]" + statement);
                collector.addAndGet(1);
                return (RecordDeclaration) super.visit(body, collector);
            }
        }
        @Override
        public void visit(ClassOrInterfaceDeclaration klass, AtomicInteger collector) {
            klass.accept(new ClassVisitor(), collector);
            super.visit(klass, collector);
        }
    }
    public static int getValue(ClassOrInterfaceDeclaration klass) {
        AtomicInteger length = new AtomicInteger(0);
        VoidVisitor<AtomicInteger> lengthVisitor = new GetLengthVisitor();
        lengthVisitor.visit(klass, length);
        return length.get();
    }
}
