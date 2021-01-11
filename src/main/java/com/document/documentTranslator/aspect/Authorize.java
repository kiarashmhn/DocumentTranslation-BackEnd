package com.document.documentTranslator.aspect;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

@Target(value = { ElementType.ANNOTATION_TYPE, ElementType.METHOD })
@Retention(RetentionPolicy.RUNTIME)
public @interface Authorize {

    AAAType type();

    boolean injectUserName() default true;

    enum AAAType {
        USER,
        ADMIN,
        SUPER_ADMIN
    }
}
