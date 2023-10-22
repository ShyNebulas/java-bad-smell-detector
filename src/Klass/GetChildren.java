package Klass;

import com.github.javaparser.StaticJavaParser;
import com.github.javaparser.ast.CompilationUnit;
import com.github.javaparser.ast.Node;
import com.github.javaparser.ast.body.ClassOrInterfaceDeclaration;
import com.github.javaparser.ast.visitor.GenericVisitorAdapter;
import com.github.javaparser.ast.visitor.VoidVisitor;
import com.github.javaparser.ast.visitor.VoidVisitorAdapter;

import java.util.ArrayList;
import java.util.List;
public class GetChildren {
    //static Node parent;
    private static class GetChildrenVisitor extends VoidVisitorAdapter<List<ClassOrInterfaceDeclaration>> {
        private static class ClassVisitor extends GenericVisitorAdapter<ClassOrInterfaceDeclaration, List<ClassOrInterfaceDeclaration>> {
            @Override
            public ClassOrInterfaceDeclaration visit(ClassOrInterfaceDeclaration klass, List<ClassOrInterfaceDeclaration> collector) {
                collector.add(klass);
                return super.visit(klass, collector);
            }
        }
        @Override
        public void visit(ClassOrInterfaceDeclaration klass, List<ClassOrInterfaceDeclaration> collector) {
            klass.accept(new ClassVisitor(), collector);
            super.visit(klass, collector);
        }
    }
    public static List<ClassOrInterfaceDeclaration> getValues(CompilationUnit klass) {
        List<ClassOrInterfaceDeclaration> classes = new ArrayList<>();
        VoidVisitor<List<ClassOrInterfaceDeclaration>> classesVisitor = new GetChildrenVisitor();
        classesVisitor.visit(klass, classes);
        classes.remove(0);
        return classes;
    }
}
