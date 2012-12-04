package org.elx.orm.annotations;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

@Target({ ElementType.FIELD })
@Retention(RetentionPolicy.RUNTIME)
public @interface Reference {

	/**
	 * 
	 */
	Class<?> classResponse();

	/**
	 * Column of the origin Table.
	 */
	String fromColumnName();

	/**
	 * Origin table.
	 */
	String fromTableName();

	/**
	 * Name to identify the Reference
	 */
	String nameReference();

	/**
	 * Name of the attribute
	 */
	String toColumnName();

	boolean isOneToOne();
}
