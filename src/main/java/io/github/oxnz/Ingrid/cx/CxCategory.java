package io.github.oxnz.Ingrid.cx;

import io.github.oxnz.Ingrid.tx.TxCategory;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

@Target(ElementType.TYPE)
@Retention(RetentionPolicy.RUNTIME)
public @interface CxCategory {
    TxCategory cat();
}
