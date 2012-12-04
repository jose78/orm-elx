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
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.TreeMap;


import org.apache.log4j.Logger;
import org.elx.orm.annotations.Id;
import org.elx.orm.annotations.Reference;

/**
 * 
 * @author Jose Clavero Anderica jose.clavero.anderica@gmail.com
 * 
 */
public class CacheClass {

	private static Logger log = Logger.getLogger(CacheClass.class);
	private static Map<String, CacheClass> map = new TreeMap<String, CacheClass>();
	private Field idField = null;
	private List<Field> lstFieldRef = null;

	/**
	 * 
	 */
	private <T> CacheClass(Class<T> clazz) {

		lstFieldRef = new ArrayList<Field>();

		Field[] fields = clazz.getDeclaredFields();
		for (Field field : fields) {
			// log.debug(field.getName());
			if (field.getAnnotation(Reference.class) != null) {
				lstFieldRef.add(field);
			} else if (field.getAnnotation(Id.class) != null) {
				idField = field;
			}
		}
		log.debug("Class " + clazz.getSimpleName() + " has bean cached.");
	}

	/**
	 * This method cached the class in memory.
	 * 
	 * @param clazz
	 * @throws Exception
	 */
	synchronized public static <E> void addClass(Class<E> clazz)
			throws ElxGenericException {
		String key = clazz.getSimpleName();
		CacheClass value = new CacheClass(clazz);
		if (!map.containsKey(key)) {
			map.put(key, value);
		} else {
			log.debug("Error, Isn't posible two classes with the same simple name ["
					+ clazz.getSimpleName() + "].");
			throw new ElxGenericException(
					"Error, Isn't posible two classes with the same simple name ["
							+ clazz.getSimpleName() + "].");
		}
	}

	/**
	 * This method retrieve the cacheClass associate to the param class.
	 * 
	 * @param clazz
	 * @return
	 */
	public static <E extends Entity> CacheClass getCacheClass(Class<E> clazz) {
		return map.get(clazz.getSimpleName());
	}

	/**
	 * @return
	 */
	public Field getIdField() {
		return idField;
	}

	/**
	 * @return
	 */
	public List<Field> getReferences() {
		return this.lstFieldRef;
	}

	//
	//
	//
	//
	//
	//
	//
	//
	//
	//
	//
	// private Integer contField = 0;
	// private static Map<String, CacheClass> mapCacheFromAlias = null;
	// private static Map<String, CacheClass> mapCacheFromClass = null;
	//
	// private Map<String, Field> mapCacheAtrFromAlias = null;
	// private Map<Field, String> mapCacheAtrFromField = null;
	// private Map<String, Field> mapCacheAtrFromNameColumn = null;
	//
	// private String nameClass = null;
	//
	// private Class<?> clazz = null;
	// private Integer contColumns = 0;
	//
	// static {
	// // <Alias_Class , Cache_Class>
	// CacheClass.mapCacheFromAlias = new TreeMap<String, CacheClass>();
	// // <Name_Class , Cache_Class>
	// CacheClass.mapCacheFromClass = new TreeMap<String, CacheClass>();
	// }
	//
	// private <T> void sacheClass(Class<T> clazz) {
	// CacheClass.contClass++;
	// // <ALIAS , FIELD>
	// mapCacheAtrFromAlias = new TreeMap<String, Field>();
	//
	// mapCacheAtrFromNameColumn = new TreeMap<String, Field>();
	//
	// // <FIELD , ALIAS>
	// mapCacheAtrFromField = new TreeMap<Field, String>(
	// new Comparator<Field>() {
	//
	// public int compare(Field o1, Field o2) {
	// return o1.getName().compareTo(o2.getName());
	// }
	//
	// });
	//
	// this.clazz = clazz;
	//
	// aliasClass = "C" + CacheClass.contClass;
	// nameClass = clazz.getSimpleName();
	//
	// for (final Field tmpField : clazz.getDeclaredFields()) {
	// final String alias = "F" + contField;
	//
	// mapCacheAtrFromAlias.put(alias, tmpField);
	// mapCacheAtrFromField.put(tmpField, alias);
	// contColumns++;
	//
	// String nameColumn = null;
	// final Column anntColumn = tmpField.getAnnotation(Column.class);
	// if (anntColumn != null) {
	// nameColumn = anntColumn.name();
	// } else {
	// nameColumn = tmpField.getName();
	// }
	// mapCacheAtrFromNameColumn.put(nameColumn.toUpperCase(), tmpField);
	// contField++;
	// }
	//
	// }
	//
	// /**
	// * Get the Alias name of the class.
	// *
	// * @return
	// */
	// public String getAliasClass() {
	// return aliasClass;
	// }
	//
	// /**
	// * Get the original class.
	// *
	// * @return
	// */
	// public Class<?> getClazz() {
	// return clazz;
	// }
	//
	// /**
	// * Get the name of the class.
	// *
	// * @return
	// */
	// public String getNameClass() {
	// return nameClass;
	// }
	//
	// /**
	// * Get the alias associated to the column.
	// *
	// * @param nameColumn
	// * @return
	// */
	// public String getAliasFromNameColumn(String nameColumn) {
	// return mapCacheAtrFromField.get(mapCacheAtrFromNameColumn
	// .get(nameColumn.toUpperCase()));
	// }
	//
	// /**
	// * Get the Alias of the field in the class.
	// *
	// * @param field
	// * @return
	// */
	// public String getAlias(Field field) {
	// return mapCacheAtrFromField.get(field);
	// }
	//
	// /**
	// * Get the Field associated to Alias Name.
	// *
	// * @param nameAliasField
	// * @return
	// */
	// public Field getField(String nameAliasField) {
	// return mapCacheAtrFromAlias.get(nameAliasField.toUpperCase());
	// }
	//
	// /**
	// * Store and generate in the Cache the information of the class.
	// *
	// * @param clazz
	// */
	// synchronized public static <T> void setClass(Class<T> clazz) {
	// if (CacheClass.mapCacheFromClass.containsKey(clazz.getSimpleName())
	// || CacheClass.mapCacheFromAlias.containsValue(clazz)) {
	// return;
	// }
	//
	// final CacheClass cacheClass = new CacheClass(clazz);
	//
	// CacheClass.contClass++;
	// CacheClass.mapCacheFromAlias
	// .put(cacheClass.getAliasClass(), cacheClass);
	// CacheClass.mapCacheFromClass.put(cacheClass.getNameClass(), cacheClass);
	//
	// CacheClass.log.info("Chached class: " + clazz.getSimpleName());
	//
	// }
	//
	// /**
	// * Get the information of the class from Class.
	// *
	// * @param clazz
	// * @return
	// */
	// public static <T> CacheClass getCacheFromClass(Class<T> clazz) {
	// return CacheClass.mapCacheFromClass.get(clazz.getSimpleName());
	// }
	//
	// /**
	// * Get the information of the class from Alias Name Class.
	// *
	// * @param nameAlias
	// * @return
	// */
	// public static CacheClass getInstanceFromAlias(String nameAlias) {
	// return CacheClass.mapCacheFromAlias.get(nameAlias.toUpperCase());
	// }
	//
	// /*
	// * (non-Javadoc)
	// *
	// * @see java.lang.Object#toString()
	// */
	// @Override
	// public String toString() {
	// return "CacheClass [\nnameClass=" + nameClass + ", \naliasClass="
	// + aliasClass + ", \nclazz=" + clazz + ", \ncontField="
	// + contField + ", \nmapCacheAtrFromAlias="
	// + mapCacheAtrFromAlias + ", \nmapCacheAtrFromField="
	// + mapCacheAtrFromField + "]";
	// }
	//
	// public String getAliasFromColumn(String nameColumn) {
	// // TODO Auto-generated method stub
	// return null;
	// }
	//
	// /**
	// * @return the contColumns
	// */
	// public Integer getContColumns() {
	// return contColumns;
	// }

}
