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
package org.elx.orm.utils;

import java.lang.reflect.Field;
import java.lang.reflect.Method;

import org.elx.orm.utils.type.TypeJava;

/**
 * Class of utilities.
 * 
 * @author Jose Clavero Anderica jose.clavero.anderica@gmail.com
 * 
 */
public class Utils {

	private static Utils util = null;

	public static Utils getUtil() {
		if (Utils.util == null) {
			Utils.util = new Utils();
		}
		return Utils.util;
	}

	// /**
	// * Determines if two objects are of the same class and has the same ID.
	// *
	// * @param entityA
	// * @param entityB
	// * @return <li><b>true</b>: the two entities have the same ID. <li>
	// * <b>false</b>:
	// * @throws Exception
	// */
	// public <E1 extends Entity, E2 extends Entity> boolean equals(E1 entityA,
	// E1 entityB) {
	// boolean bEquals = false;
	//
	// bEquals = getKey(entityA).compareTo(getKey(entityB)) == 0;
	//
	// return bEquals;
	// }

	/**
	 * Get the Id Operation.
	 * 
	 * @return
	 */
	public Long getIdOperation() {
		return Thread.currentThread().getId();
	}

	/**
	 * Get the text with Id Operation.
	 * 
	 * @return
	 */
	public String getIdOperationAsString() {
		return "ID Operation:[" + Thread.currentThread().getId() + "]";
	}

	/**
	 * Build a Tupla.
	 * 
	 * @param value
	 * @return
	 */
	public Tupla<TypeJava, Object> getTupla(Object value) {
		TypeJava key = null;

		if (value == null) {
			key = TypeJava.Null;
		} else {
			if (value.getClass().isArray()
					&& "byte[]".equals(value.getClass().getCanonicalName())) {
				key = TypeJava.java_lang_Bytes;
			} else {
				for (final TypeJava type : TypeJava.values()) {
					if (type.toString().equals(
							value.getClass().getName().replace(".", "_"))) {
						key = type;
						break;
					}
				}
			}
		}
		return new Tupla<TypeJava, Object>(key, value);
	}

	/**
	 * Create a new Instance of the parameter class.
	 * 
	 * @param clazz
	 * @return New instance.
	 * @throws ElxGenericException
	 */
	public <T> T createInstance(Class<T> clazz) {
		T instance = null;
		try {
			instance = clazz.newInstance();
		} catch (final InstantiationException e) {
			e.printStackTrace();
		} catch (final IllegalAccessException e) {
			e.printStackTrace();
		}
		return instance;
	}

	/**
	 * Get the filed's value of the object.
	 */
	@SuppressWarnings("unchecked")
	public <T, V> V getValueField(T obj, Field field) {
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
	public void setValueField(Object objResult, Field field, Object value) {
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

	/**
	 * Retrieves the class name from.
	 * 
	 * @param className
	 * @return Class.
	 */
	@SuppressWarnings("unchecked")
	public <S> Class<S> getClass(String className) {
		Class<S> clazz = null;
		try {
			clazz = (Class<S>) Class.forName(className);
		} catch (ClassNotFoundException e) {
			e.printStackTrace();
		}
		return clazz;
	}

	/**
	 * Execute one method of the class.
	 * 
	 * @param obj
	 * @param nameMethod
	 * @return result of the execution.
	 */
	public <T> T executeMethod(Object obj, String nameMethod) {
		T value = null;
		try {
			Method method = obj.getClass().getDeclaredMethod(nameMethod);
			if (method == null) {
				throw new ElxGenericException(
						"Error, Elx not found in this class "
								+ obj.getClass().getName() + " the method "
								+ nameMethod);
			}

			value = (T) method.invoke(obj, null);

		} catch (Exception e) {
			e.printStackTrace();
		}

		return value;
	}
}
