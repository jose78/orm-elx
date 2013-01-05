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
package org.elx.orm.annotations;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * This annotation is to be mapped with the primary key in the table.
 * <b>Mandatory</b>
 * 
 * @author Jose Clavero Anderica jose.clavero.anderica@gmail.com
 * 
 */

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
