package io.github.oxnz.Ingrid.expr;

import org.junit.Test;

import static org.junit.Assert.assertEquals;

public class ExprTest {

    private int eval(Expr expr) {
        System.out.println(expr);
        return Calculator.eval(expr);
    }

    @Test
    public void testV() {
        int n = 1;
        assertEquals(1, eval(new V(1)));
    }

    @Test
    public void testUniExpr() {
        int n = 1;
        UniExpr expr = new UniExpr("-", new V(n));
        assertEquals(-1, eval(expr));
        expr = new UniExpr("-", expr);
        assertEquals(1, eval(expr));
        expr = new UniExpr("-", expr);
        assertEquals(-1, eval(expr));
        expr = new UniExpr("+", expr);
        assertEquals(-1, eval(expr));
    }

    @Test
    public void testBinExpr() {
        int a = 1, b = 2;
        BinExpr expr = new BinExpr("+", new V(a), new V(b));
        assertEquals(a+b, eval(expr));
        expr = new BinExpr("-", new V(a), expr);
        assertEquals(-2, eval(expr));
    }
}

