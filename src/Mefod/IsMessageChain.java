package Mefod;

import com.github.javaparser.ast.body.MethodDeclaration;
import com.github.javaparser.ast.expr.MethodCallExpr;

import java.util.concurrent.atomic.AtomicBoolean;
public class IsMessageChain {
    public static boolean getValue(MethodDeclaration method) {
        AtomicBoolean flag = new AtomicBoolean(false);
        method.walk(MethodCallExpr.class, msgChain -> {
            if(msgChain.getScope().isPresent() && msgChain.getScope().get().isMethodCallExpr()) {
                flag.set(true);
            }
        });
        return flag.get();
    }
}
