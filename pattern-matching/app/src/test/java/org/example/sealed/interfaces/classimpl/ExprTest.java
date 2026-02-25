package org.example.sealed.interfaces.classimpl;

import org.junit.jupiter.api.Test;

class ExprTest {
    @Test void sealedInterfacesWork() {
        // (6 + 7) * -8
        System.out.println(
        new TimesExpr(
            new PlusExpr(new ConstantExpr(6), new ConstantExpr(7)),
            new NegExpr(new ConstantExpr(8))
        ).eval());
    }
}
