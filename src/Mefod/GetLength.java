package Mefod;

import com.github.javaparser.ast.NodeList;
import com.github.javaparser.ast.body.MethodDeclaration;
import com.github.javaparser.ast.stmt.*;
import com.github.javaparser.ast.visitor.VoidVisitorAdapter;
import com.github.javaparser.ast.visitor.GenericVisitorAdapter;

import java.util.concurrent.atomic.AtomicInteger;
public class GetLength extends VoidVisitorAdapter<AtomicInteger> {
    private static class StatementVisitor extends GenericVisitorAdapter<Statement, AtomicInteger> {
        public AssertStmt visit(AssertStmt statement, AtomicInteger count) {
            //System.out.println("[Assert]" + statement);
            count.addAndGet(1);
            return (AssertStmt) super.visit(statement, count);
        }
        public BreakStmt visit(BreakStmt statement, AtomicInteger count) {
            //System.out.println("[Break]" + statement);
            count.addAndGet(1);
            return (BreakStmt) super.visit(statement, count);
        }
        public ContinueStmt visit(ContinueStmt statement, AtomicInteger count) {
            //System.out.println("[Continue]" + statement);
            count.addAndGet(1);
            return (ContinueStmt) super.visit(statement, count);
        }
        public DoStmt visit(DoStmt statement, AtomicInteger count) {
            //System.out.println("[Do]" + statement);
            count.addAndGet(1);
            return (DoStmt) super.visit(statement, count);
        }
        // Call to super() or this()
        public ExplicitConstructorInvocationStmt visit(ExplicitConstructorInvocationStmt statement, AtomicInteger count) {
            //System.out.println("[ExplicitConstructorInvocation]" + statement);
            count.addAndGet(1);
            return (ExplicitConstructorInvocationStmt) super.visit(statement, count);
        }
        public ExpressionStmt visit(ExpressionStmt statement, AtomicInteger count) {
            //System.out.println("[Expression]" + statement);
            count.addAndGet(1);
            return (ExpressionStmt) super.visit(statement, count);
        }
        public ForEachStmt visit(ForEachStmt statement, AtomicInteger count) {
            //System.out.println("[ForEach]" + statement);
            count.addAndGet(1);
            return (ForEachStmt) super.visit(statement, count);
        }
        public ForStmt visit(ForStmt statement, AtomicInteger count) {
            //System.out.println("[For]" + statement);
            count.addAndGet(1);
            return (ForStmt) super.visit(statement, count);
        }
        public IfStmt visit(IfStmt statement, AtomicInteger count) {
            //System.out.println("[If]" + statement);
            if (statement.getElseStmt().isPresent()) {
                count.addAndGet(1);
            }
            count.addAndGet(1);
            return (IfStmt) super.visit(statement, count);
        }
        public LabeledStmt visit(LabeledStmt statement, AtomicInteger count) {
            //System.out.println("[Labeled]" + statement);
            count.addAndGet(1);
            return (LabeledStmt) super.visit(statement, count);
        }
        public LocalClassDeclarationStmt visit(LocalClassDeclarationStmt statement, AtomicInteger count) {
            //System.out.println("[LocalClassDeclaration]" + statement);
            count.addAndGet(1);
            return (LocalClassDeclarationStmt) super.visit(statement, count);
        }
        public LocalRecordDeclarationStmt visit(LocalRecordDeclarationStmt statement, AtomicInteger count) {
            //System.out.println("[LocalRecordDeclaration]" + statement);
            count.addAndGet(1);
            return (LocalRecordDeclarationStmt) super.visit(statement, count);
        }
        public ReturnStmt visit(ReturnStmt statement, AtomicInteger count) {
            //System.out.println("[Return]" + statement);
            count.addAndGet(1);
            return (ReturnStmt) super.visit(statement, count);
        }
        public SwitchStmt visit(SwitchStmt statement, AtomicInteger count) {
            //System.out.println("[Switch]" + statement);
            NodeList<SwitchEntry> entries = statement.getEntries();
            if (!entries.isEmpty()) {
                count.addAndGet(1);
            }
            count.addAndGet(1);
            return (SwitchStmt) super.visit(statement, count);
        }
        public SynchronizedStmt visit(SynchronizedStmt statement, AtomicInteger count) {
            //System.out.println("[Synchronized]" + statement);
            count.addAndGet(1);
            return (SynchronizedStmt) super.visit(statement, count);
        }
        public ThrowStmt visit(ThrowStmt statement, AtomicInteger count) {
            //System.out.println("[Throw]" + statement);
            count.addAndGet(1);
            return (ThrowStmt) super.visit(statement, count);
        }
        public TryStmt visit(TryStmt statement, AtomicInteger count) {
            //System.out.println("[Try]" + statement);
            NodeList<CatchClause> clauses = statement.getCatchClauses();
            if (!clauses.isEmpty()) {
                count.addAndGet(1);
            }
            if (statement.getFinallyBlock().isPresent()) {
                count.addAndGet(1);
            }
            count.addAndGet(1);
            return (TryStmt) super.visit(statement, count);
        }
        public WhileStmt visit(WhileStmt statement, AtomicInteger count) {
            //System.out.println("[While]" + statement);
            count.addAndGet(1);
            return (WhileStmt) super.visit(statement, count);
        }
    }
    @Override
    public void visit(MethodDeclaration method, AtomicInteger collector) {
        method.accept(new GetLength.StatementVisitor(), collector);
        super.visit(method, collector);
    }
}










