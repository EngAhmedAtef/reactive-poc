package com.gizasystems.cssdb.annotation;

import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Import;

import java.lang.annotation.*;

@Retention(value = RetentionPolicy.RUNTIME)
@Target(value = ElementType.TYPE)
@Documented
@Import(value = ImportCssDb.BaseImport.class)
public @interface ImportCssDb {

    @Configuration
    @ComponentScan(basePackages = "com.gizasystems.cssdb")
    class BaseImport {}
}
