package Mefod;

import com.github.javaparser.ast.NodeList;
import com.github.javaparser.ast.body.MethodDeclaration;
import com.github.javaparser.ast.stmt.*;
import com.github.javaparser.ast.visitor.VoidVisitor;
import com.github.javaparser.ast.visitor.VoidVisitorAdapter;
import com.github.javaparser.ast.visitor.GenericVisitorAdapter;

import java.util.concurrent.atomic.AtomicInteger;
public class GetLength {
    private static class GetLengthVisitor extends VoidVisitorAdapter<AtomicInteger> {
        private static class StatementVisitor extends GenericVisitorAdapter<Statement, AtomicInteger> {
            public AssertStmt visit(AssertStmt statement, AtomicInteger collector) {
                //System.out.println("[Assert]" + statement);
                collector.addAndGet(1);
                return (AssertStmt) super.visit(statement, collector);
            }
            public BreakStmt visit(BreakStmt statement, AtomicInteger collector) {
                //System.out.println("[Break]" + statement);
                collector.addAndGet(1);
                return (BreakStmt) super.visit(statement, collector);
            }
            public ContinueStmt visit(ContinueStmt statement, AtomicInteger collector) {
                //System.out.println("[Continue]" + statement);
                collector.addAndGet(1);
                return (ContinueStmt) super.visit(statement, collector);
            }
            public DoStmt visit(DoStmt statement, AtomicInteger collector) {
                //System.out.println("[Do]" + statement);
                collector.addAndGet(1);
                return (DoStmt) super.visit(statement, collector);
            }
            // Call to super() or this()
            public ExplicitConstructorInvocationStmt visit(ExplicitConstructorInvocationStmt statement, AtomicInteger collector) {
                //System.out.println("[ExplicitConstructorInvocation]" + statement);
                collector.addAndGet(1);
                return (ExplicitConstructorInvocationStmt) super.visit(statement, collector);
            }
            public ExpressionStmt visit(ExpressionStmt statement, AtomicInteger collector) {
                //System.out.println("[Expression]" + statement);
                collector.addAndGet(1);
                return (ExpressionStmt) super.visit(statement, collector);
            }
            public ForEachStmt visit(ForEachStmt statement, AtomicInteger collector) {
                //System.out.println("[ForEach]" + statement);
                collector.addAndGet(1);
                return (ForEachStmt) super.visit(statement, collector);
            }
            public ForStmt visit(ForStmt statement, AtomicInteger collector) {
                //System.out.println("[For]" + statement);
                collector.addAndGet(1);
                return (ForStmt) super.visit(statement, collector);
            }
            public IfStmt visit(IfStmt statement, AtomicInteger collector) {
                //System.out.println("[If]" + statement);
                if (statement.getElseStmt().isPresent()) {
                    collector.addAndGet(1);
                }
                collector.addAndGet(1);
                return (IfStmt) super.visit(statement, collector);
            }
            public LabeledStmt visit(LabeledStmt statement, AtomicInteger collector) {
                //System.out.println("[Labeled]" + statement);
                collector.addAndGet(1);
                return (LabeledStmt) super.visit(statement, collector);
            }
            public LocalClassDeclarationStmt visit(LocalClassDeclarationStmt statement, AtomicInteger collector) {
                //System.out.println("[LocalClassDeclaration]" + statement);
                collector.addAndGet(1);
                return (LocalClassDeclarationStmt) super.visit(statement, collector);
            }
            public LocalRecordDeclarationStmt visit(LocalRecordDeclarationStmt statement, AtomicInteger collector) {
                //System.out.println("[LocalRecordDeclaration]" + statement);
                collector.addAndGet(1);
                return (LocalRecordDeclarationStmt) super.visit(statement, collector);
            }
            public ReturnStmt visit(ReturnStmt statement, AtomicInteger collector) {
                //System.out.println("[Return]" + statement);
                collector.addAndGet(1);
                return (ReturnStmt) super.visit(statement, collector);
            }
            public SwitchStmt visit(SwitchStmt statement, AtomicInteger collector) {
                //System.out.println("[Switch]" + statement);
                NodeList<SwitchEntry> entries = statement.getEntries();
                if (!entries.isEmpty()) {
                    collector.addAndGet(1);
                }
                collector.addAndGet(1);
                return (SwitchStmt) super.visit(statement, collector);
            }
            public SynchronizedStmt visit(SynchronizedStmt statement, AtomicInteger collector) {
                //System.out.println("[Synchronized]" + statement);
                collector.addAndGet(1);
                return (SynchronizedStmt) super.visit(statement, collector);
            }
            public ThrowStmt visit(ThrowStmt statement, AtomicInteger collector) {
                //System.out.println("[Throw]" + statement);
                collector.addAndGet(1);
                return (ThrowStmt) super.visit(statement, collector);
            }
            public TryStmt visit(TryStmt statement, AtomicInteger collector) {
                //System.out.println("[Try]" + statement);
                NodeList<CatchClause> clauses = statement.getCatchClauses();
                if (!clauses.isEmpty()) {
                    collector.addAndGet(1);
                }
                if (statement.getFinallyBlock().isPresent()) {
                    collector.addAndGet(1);
                }
                collector.addAndGet(1);
                return (TryStmt) super.visit(statement, collector);
            }
            public WhileStmt visit(WhileStmt statement, AtomicInteger collector) {
                //System.out.println("[While]" + statement);
                collector.addAndGet(1);
                return (WhileStmt) super.visit(statement, collector);
            }
        }
        @Override
        public void visit(MethodDeclaration method, AtomicInteger collector) {
            method.accept(new StatementVisitor(), collector);
            super.visit(method, collector);
        }
    }
    public static int getValue(MethodDeclaration method) {
        AtomicInteger length = new AtomicInteger(0);
        VoidVisitor<AtomicInteger> lengthVisitor = new GetLengthVisitor();
        lengthVisitor.visit(method, length);
        return length.get();
    }
}