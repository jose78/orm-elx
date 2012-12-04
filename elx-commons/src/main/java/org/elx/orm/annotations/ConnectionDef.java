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
 * This annotation must be implemented for all database connections.
 * 
 * @author Jose Clavero Anderica jose.clavero.anderica@gmail.com
 * 
 */
@Target({ ElementType.TYPE })
@Retention(RetentionPolicy.RUNTIME)
public @interface ConnectionDef {

	/**
	 * (Mandatory) Provide the name of the Connection.
	 */
	String nameConnection();

	/**
	 * (Optional) Indicate if you want used this connection to <b>READ</b> data.<br>
	 * <b>Default value:</b> true
	 */
	boolean read();

	/**
	 * (Optional) Indicate if you want used this connection to <b>WRITE</b>
	 * data.<br>
	 * <b>Default value:</b> true
	 */
	boolean write();

}