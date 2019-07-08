package com.scor.rr.configuration.excel.annotation;

import java.lang.annotation.*;

@Retention(RetentionPolicy.RUNTIME)
@Target(ElementType.FIELD)
@Documented
public @interface RowIndex {
    int value() default -1;
}
