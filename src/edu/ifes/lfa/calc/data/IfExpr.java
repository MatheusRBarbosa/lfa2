package edu.ifes.lfa.calc.data;

import java.util.Optional;

/**
 *
 * @author jefferson
 */
public class IfExpr extends ExpecialForm {

    private final Expr cond;
    private final Expr thenExpr;
    private final Optional<Expr> elseExpr;
    
    protected IfExpr(Expr cond, Expr thenExpr, Optional<Expr> elseExpr) {
        this.cond = cond;
        this.thenExpr = thenExpr;
        this.elseExpr = elseExpr;
    }
    
    public IfExpr(Expr cond, Expr thenExpr, Expr elseExpr) {
        this(cond, thenExpr, Optional.of(elseExpr));
    }
    
    public IfExpr(Expr cond, Expr thenExpr) {
        this(cond, thenExpr, Optional.empty());
    }

    @Override
    public Expr eval(Context ctx) {
        Expr c = cond.eval(ctx);
        if (c instanceof Bool) {
            Bool c1 = (Bool) c;
            if (c1.value == true) {
                return thenExpr.eval(ctx);
            }
            else if (elseExpr.isPresent()) {
                Expr e = elseExpr.get();
                return e.eval(ctx);
            }
            else {
                return Expr.NONE;
            }
        }
        else {
            String err = String.format(
                    "If condition is not boolean: %s", c);
            throw new RuntimeException(err);
        }
    }

    @Override
    public String show() {
        if (elseExpr.isPresent()) {
            return String.format("IfExpr(%s, %s, %s)", 
                    cond.show(), 
                    thenExpr.show(), elseExpr.get().show());
        }
        else {
            return String.format("IfExpr(%s, %s)", 
                    cond.show(), 
                    thenExpr.show());
        }
    }
    
}