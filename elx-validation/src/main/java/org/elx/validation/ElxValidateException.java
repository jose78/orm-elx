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
package org.elx.validation;

import java.lang.reflect.Field;
import java.util.ArrayList;
import java.util.List;

import org.elx.validation.utils.ValidateCommand;

/**
 * @author Jose Clavero Anderica jose.clavero.anderica@gmail.com
 * 
 */
public class ElxValidateException extends Exception {

	private static List<ValidateCommand> lstCommand = new ArrayList<ValidateCommand>();

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
		executeCmd();
	}

	/**
	 * @param message
	 */
	public ElxValidateException(String message) {
		super(message);
		executeCmd();
	}

	/**
	 * @param cause
	 */
	public ElxValidateException(Throwable cause) {
		super(cause);
		executeCmd();
	}

	public ElxValidateException(Field field, Object obj, String cause) {
		super("Error in " + obj.getClass().getName() + "." + field.getName()
				+ " --> " + cause);
		executeCmd();
	}

	private void executeCmd() {
		for (ValidateCommand cmd : lstCommand) {
			cmd.execute(this);
		}
	}

	/**
	 * 
	 * @param command
	 */
	public static void addCommand(ValidateCommand command) {
		ElxValidateException.lstCommand.add(command);
	}
}
