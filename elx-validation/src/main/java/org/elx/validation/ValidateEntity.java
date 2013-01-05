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
import java.math.BigDecimal;

import org.elx.validation.annotations.Validation;

/**
 * @author Jose Clavero Anderica jose.clavero.anderica@gmail.com
 * 
 */
public class ValidateEntity {

	private static ValidateEntity validateEntity = null;

	/**
	 * 
	 */
	private ValidateEntity() {

	}

	/**
	 * (Optional) Whether the database column is nullable. <br>
	 * <b>Default value:</b> false
	 */
	private void validateNullable(Validation anntValidation, Field field,
			Object obj) throws ElxValidateException {

		if (getValueField(obj, field) == null && !anntValidation.nullable()) {
			final String cause = "the value is NULL";
			throw new ElxValidateException(field, obj, cause);
		}
	}

	// Format - String
	/**
	 * (Optional) The value of the column will be trimmed in the white spaces
	 * from the beginning and end. (Applies only if a string-valued column is
	 * used.)<br>
	 * <b>Default value:</b> false
	 */
	private void transfortmateToTrim(Validation anntValidation, Field field,
			Object obj) throws ElxValidateException {
		String value = null;
		if (anntValidation.toTrim()
				&& (value = getValueField(obj, field)) != null) {
			setValueField(obj, field, value.trim());
		}
	}

	/**
	 * (Optional) The value of the column is passed to upper case. (Applies only
	 * if a string-valued column is used.)<br>
	 * <b>Default value:</b> false
	 */
	private void transfortmateToUppercase(Validation anntValidation,
			Field field, Object obj) throws ElxValidateException {
		String value = null;
		if (anntValidation.toUppercase()
				&& (value = getValueField(obj, field)) != null) {
			setValueField(obj, field, value.toUpperCase());
		}
	}

	/**
	 * (Optional) The value of the column is passed to lower case. (Applies only
	 * if a string-valued column is used.)<br>
	 * <b>Default value:</b> false
	 */
	private void transfortmateToLowercase(Validation anntValidation,
			Field field, Object obj) throws ElxValidateException {
		String value = null;
		if (anntValidation.toLowercase()
				&& (value = getValueField(obj, field)) != null) {
			setValueField(obj, field, value.toLowerCase());
		}
	}

	// VALUES IN THE DATA.
	/**
	 * (Optional) The value of the column must contain this string. (Applies
	 * only if a string-valued column is used.)<br>
	 * <b>Default value:</b> ""
	 */
	private void validateContains(Validation anntValidation, Field field,
			Object obj) throws ElxValidateException {
		String value = null;
		if (!anntValidation.contains()[0].equals("")
				&& anntValidation.contains().length > 0
				&& (value = getValueField(obj, field)) != null) {
			Boolean isContained = false;
			for (int index = 0; !isContained
					&& index < anntValidation.contains().length; index++) {
				isContained = isContained
						|| (anntValidation.contains()[index] + "").equals(value
								+ "");
			}
			if (!isContained) {
				final String cause = "The value doesn't contain any value valid";
				throw new ElxValidateException(field, obj, cause);
			}
		}
	}

	/**
	 * (Optional) The value of the column must start with this string. (Applies
	 * only if a string-valued column is used.)<br>
	 * <b>Default value:</b> ""
	 */
	private void validateStartWith(Validation anntValidation, Field field,
			Object obj) throws ElxValidateException {
		String value = null;
		if (!anntValidation.startWith()[0].equals("")
				&& anntValidation.startWith().length > 0
				&& (value = getValueField(obj, field)) != null) {
			Boolean isContained = false;
			for (int index = 0; index < anntValidation.startWith().length; index++) {
				isContained = isContained
						|| value.startsWith(anntValidation.startWith()[index]);
			}
			if (!isContained) {
				final String cause = "The value doesn't start with any value valid";
				throw new ElxValidateException(field, obj, cause);
			}
		}
	}

	/**
	 * (Optional) The value of the column must end with this string. (Applies
	 * only if a string-valued column is used.)<br>
	 * <b>Default value:</b> ""
	 */
	private void validateEndWith(Validation anntValidation, Field field,
			Object obj) throws ElxValidateException {
		String value = null;
		if (!anntValidation.endWith()[0].equals("")
				&& anntValidation.endWith().length > 0
				&& (value = getValueField(obj, field)) != null) {
			Boolean isContained = false;
			for (int index = 0; index < anntValidation.endWith().length; index++) {
				isContained = isContained
						|| value.endsWith(anntValidation.endWith()[index]);
			}
			if (!isContained) {
				final String cause = "The value doesn't end with any value valid";
				throw new ElxValidateException(field, obj, cause);
			}
		}
	}

	/**
	 * (Optional) The column <b>MAX</b> length. (Applies only if a string-valued
	 * column is used.) <br>
	 * <b>Default value:</b> 255
	 */
	private void validateMaxLength(Validation anntValidation, Field field,
			Object obj) throws ElxValidateException {
		String value = null;
		if ((value = getValueField(obj, field)) != null
				&& value.length() > anntValidation.maxLength()) {
			final String cause = "the length of the value is bigger than maxLength";
			throw new ElxValidateException(field, obj, cause);
		}
	}

	/**
	 * (Optional) The column <b>MIN</b> length. (Applies only if a string-valued
	 * column is used.) <br>
	 * <b>Default value:</b> 0
	 */
	private void validateMinLength(Validation anntValidation, Field field,
			Object obj) throws ElxValidateException {
		String value = null;
		if ((value = getValueField(obj, field)) != null
				&& value.length() < anntValidation.minLength()) {
			final String cause = "the length of the value is less than the minLength";
			throw new ElxValidateException(field, obj, cause);
		}
	}

	/**
	 * (Optional) The precision for a decimal (exact numeric) column. (Applies
	 * only if a decimal column is used.) Value must be set by developer if used
	 * when generating the DDL for the column. <br>
	 * <b>Default value:</b> 0
	 */
	private void validatePrecision(Validation anntValidation, Field field,
			Object obj) throws ElxValidateException {
	}

	/**
	 * (Optional) The scale for a decimal (exact numeric) column. (Applies only
	 * if a decimal column is used.) <br>
	 * <b>Default value:</b> 0
	 */
	private void validateScale(Validation anntValidation, Field field,
			Object obj) throws ElxValidateException {

		BigDecimal bd = new BigDecimal(0.3333d);
		// bd.setScale(newScale)

	}

	/**
	 * Validate the content of the object.
	 * 
	 * @param obj
	 * @throws ElxValidateException
	 */
	public void validate(Object obj) throws ElxValidateException {

		if (obj != null) {
			Field[] fields = obj.getClass().getDeclaredFields();
			for (Field field : fields) {
				Validation anntValidation = field
						.getAnnotation(Validation.class);
				validateField(anntValidation, field, obj);

			}
		}
	}

	/**
	 * Validate only the value of the Field.
	 * 
	 * @param anntValidation
	 * @param field
	 * @param obj
	 * @throws ElxValidateException
	 */
	public void validateField(Validation anntValidation, Field field, Object obj)
			throws ElxValidateException {

		if (anntValidation != null) {
			validateNullable(anntValidation, field, obj);

			// Format - String
			transfortmateToTrim(anntValidation, field, obj);
			transfortmateToUppercase(anntValidation, field, obj);
			transfortmateToLowercase(anntValidation, field, obj);

			// VALUES IN THE DATA.
			validateContains(anntValidation, field, obj);
			validateStartWith(anntValidation, field, obj);
			validateEndWith(anntValidation, field, obj);
			validateMaxLength(anntValidation, field, obj);
			validateMinLength(anntValidation, field, obj);
			validatePrecision(anntValidation, field, obj);
		}
	}

	public static ValidateEntity get() {
		return (validateEntity == null) ? validateEntity = new ValidateEntity()
				: validateEntity;
	}

	/**
	 * Get the filed's value of the object.
	 */
	@SuppressWarnings("unchecked")
	private <T, V> V getValueField(T obj, Field field) {
		field.setAccessible(true);
		V objResult = null;
		try {
			objResult = ((V) field.get(obj));
		} catch (final Exception e) {
			e.printStackTrace();
		}
		return objResult;
	}

	/**
	 * The value will store at the object's attribute.
	 * 
	 * @param objResult
	 * @param field
	 * @param value
	 * @throws ElxGenericException
	 */
	private void setValueField(Object objResult, Field field, Object value) {
		field.setAccessible(true);
		try {
			field.set(objResult, value);
		} catch (final Exception e) {
			System.err.println("objResult: " + objResult);
			System.err.println("field: " + field);
			System.err.println("value:" + value);
			e.printStackTrace();
		}
	}

}
