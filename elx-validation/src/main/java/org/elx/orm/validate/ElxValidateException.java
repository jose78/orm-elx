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
package org.elx.orm.validate;

import java.lang.reflect.Field;

import org.elx.orm.annotations.Validation;


/**
 * @author Jose Clavero Anderica jose.clavero.anderica@gmail.com
 * 
 */
public class ElxValidateException extends Exception {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;



	/**
	 * @param message
	 * @param cause
	 */
	public ElxValidateException(String message, Throwable cause) {
		super(message, cause);

	}

	/**
	 * @param message
	 */
	public ElxValidateException(String message) {
		super(message);

	}

	/**
	 * @param cause
	 */
	public ElxValidateException(Throwable cause) {
		super(cause);

	}

	public ElxValidateException(Field field, Object obj, String cause) {
		super("Error in "+obj.getClass().getName()+"."+field.getName()+" --> "+cause);
	}
}
