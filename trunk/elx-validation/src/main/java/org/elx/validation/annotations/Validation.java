/*Copyright 2012 ELX

  Licensed under the Apache License, Version 2.0 (the "License");
  you may not use this file except in compliance with the License.
  You may obtain a copy of the License at

      http://www.apache.org/licenses/LICENSE-2.0

  Unless required by applicable law or agreed to in writing, software
  distributed under the License is distributed on an "AS IS" BASIS,
  WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
  See the License for the specific language governing permissions and
  limitations under the License.
 */
package org.elx.validation.annotations;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * @author Jose Clavero Anderica jose.clavero.anderica@gmail.com
 * 
 */

@Target({ ElementType.FIELD })
@Retention(RetentionPolicy.RUNTIME)
// @Target(ElementType.ANNOTATION_TYPE)
public @interface Validation {

	/**
	 * (Optional) Whether the database column is nullable. <br>
	 * <b>Default value:</b> false
	 */
	boolean nullable() default false;

	// VALUES IN THE DATA.
	/**
	 * (Optional) The value of the column must contain this string. (Applies
	 * only if a string-valued column is used.)<br>
	 * <b>Default value:</b> ""
	 */
	String[] contains() default "";

	/**
	 * (Optional) The value of the column must start with this string. (Applies
	 * only if a string-valued column is used.)<br>
	 * <b>Default value:</b> ""
	 */
	String[] startWith() default "";

	/**
	 * (Optional) The value of the column must end with this string. (Applies
	 * only if a string-valued column is used.)<br>
	 * <b>Default value:</b> ""
	 */
	String[] endWith() default "";

	// Format - String
	/**
	 * (Optional) The value of the column will be trimmed in the white spaces
	 * from the beginning and end. (Applies only if a string-valued column is
	 * used.)<br>
	 * <b>Default value:</b> false
	 */
	boolean toTrim() default false;

	/**
	 * (Optional) The value of the column is passed to upper case. (Applies only
	 * if a string-valued column is used.)<br>
	 * <b>Default value:</b> false
	 */
	boolean toUppercase() default false;

	/**
	 * (Optional) The value of the column is passed to lower case. (Applies only
	 * if a string-valued column is used.)<br>
	 * <b>Default value:</b> false
	 */
	boolean toLowercase() default false;

	/**
	 * (Optional) The column <b>MAX</b> length. (Applies only if a string-valued
	 * column is used.) <br>
	 * <b>Default value:</b> 255
	 */
	int maxLength() default 255;

	/**
	 * (Optional) The column <b>MIN</b> length. (Applies only if a string-valued
	 * column is used.) <br>
	 * <b>Default value:</b> 0
	 */
	int minLength() default 0;

	/**
	 * (Optional) The precision for a decimal (exact numeric) column. (Applies
	 * only if a decimal column is used.) Value must be set by developer if used
	 * when generating the DDL for the column. <br>
	 * <b>Default value:</b> 0
	 * 
	 * <b>TODO</b>	 
	 */
	int precision() default 0;

	/**
	 * (Optional) The scale for a decimal (exact numeric) column. (Applies only
	 * if a decimal column is used.) <br>
	 * <b>Default value:</b> 0
	 * 
	 * <b>TODO</b>
	 */
	int scale() default 0;

	/**
	 * (Optional) The scale for a decimal (exact numeric) column. (Applies only
	 * if a decimal column is used.) <br>
	 * <b>Default value:</b> 0
	 * 
	 * <b>TODO</b>
	 */
	String description() default "";

}
