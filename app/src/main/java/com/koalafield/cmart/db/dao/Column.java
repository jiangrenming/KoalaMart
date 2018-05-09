package com.koalafield.cmart.db.dao;

import java.lang.annotation.Retention;
import java.lang.annotation.Target;

import static java.lang.annotation.ElementType.FIELD;
import static java.lang.annotation.ElementType.METHOD;
import static java.lang.annotation.RetentionPolicy.RUNTIME;

@Target({ METHOD, FIELD })
@Retention(RUNTIME)
public @interface Column {
	String name() default "";

	boolean primaryKey() default false;
	
	
	int len() default 10;
	
	
	boolean unique() default false;
}
