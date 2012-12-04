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
package org.elx.orm.db;

/**
 * @author Jose Clavero Anderica
 *         jose.clavero.anderica@gmail.com
 *
 */
import java.lang.reflect.Field;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.TreeMap;
import java.util.Map.Entry;

import org.elx.orm.annotations.Column;
import org.elx.orm.annotations.Id;
import org.elx.orm.annotations.Reference;
import org.elx.orm.annotations.Validation;
import org.elx.orm.utils.CacheClass;
import org.elx.orm.utils.ElxGenericException;
import org.elx.orm.utils.Entity;
import org.elx.orm.utils.Utils;
import org.elx.orm.utils.type.TypeOperation;
import org.elx.orm.vendor.ContainerDataClass;
import org.elx.orm.vendor.Vendor;


public class Reader<E extends Entity> {

	private QueryBuilder query = null;
	private Reader<?>[] readers = null;
	private Class<E> mainClazz = null;
	private Integer mainLevel = null;
	private Cont cont = null;
	private Vendor vendor = null;
	private ContainerDataClass containerDataClass = null;
	private String nameConnection = null;
	private Field field = null;

	public Reader(Vendor vendor, Class<E> clazz, Integer level,
			String nameConnection) throws ElxGenericException {
		this.field = null;
		this.mainClazz = clazz;
		this.mainLevel = level;
		this.cont = new Cont();
		this.vendor = vendor;
		this.nameConnection = nameConnection;
		this.containerDataClass = new ContainerDataClass(clazz,
				TypeOperation.Select);

		generateSelect();

	}

	private Reader(Vendor vendor, Field field, Class<E> clazz,
			Integer internalLevel, Cont cont, String nameColumnTo,
			String aliasTableFrom, String nameColumnFrom, String nameTableFrom,
			String nameConnection, Boolean isOneToOne)
			throws ElxGenericException {
		this.field = field;
		this.mainClazz = clazz;
		this.mainLevel = internalLevel;
		this.cont = cont.increment();
		this.vendor = vendor;
		this.nameConnection = nameConnection;
		this.containerDataClass = new ContainerDataClass(clazz,
				TypeOperation.Select);

		generateSelect(nameColumnTo, nameColumnFrom, nameTableFrom,
				aliasTableFrom, isOneToOne);
	}

	/**
	 * @param nameColumnTo
	 * @param nameColumnFrom
	 * @param nameTableFrom
	 * @throws ElxGenericException
	 */
	@SuppressWarnings("unchecked")
	private void generateSelect(String nameColumnTo, String nameColumnFrom,
			String nameTableFrom, String aliasTableFrom, Boolean isOneToOne)
			throws ElxGenericException {
		lstColums = new ArrayList<String>();
		CacheClass cache = CacheClass.getCacheClass(mainClazz);

		String nameTable = containerDataClass.getNameTable(nameConnection);
		String nameSchema = containerDataClass.getNameSchema(nameConnection);

		query = new QueryBuilder(vendor, nameTable, nameSchema, cont.getValue(),
				nameColumnTo, aliasTableFrom, nameColumnFrom, nameTableFrom,
				isOneToOne);

		for (Entry<String, Field> entry : containerDataClass
				.getMapNameColumnsField().entrySet()) {
			if (entry.getValue().getAnnotation(Id.class) != null) {
				query.addColumnId(entry.getKey(), entry.getValue()
						.getAnnotation(Validation.class));
			} else {
			
				Validation anntValidation = entry.getValue().getAnnotation(
						Validation.class);				
				query.addColumn(entry.getKey(), anntValidation);
			}
		}

		if (mainLevel > 0) {
			if (cache.getReferences().size() > 0) {
				readers = new Reader[cache.getReferences().size()];
				Integer internalLevel = mainLevel - 1;
				for (int index = 0; index < readers.length; index++) {

					Field field = cache.getReferences().get(index);
					Reference anntReference = field
							.getAnnotation(Reference.class);

					// System.out.println("-> " +
					// anntReference.nameReference());

					readers[index] = new Reader(vendor, cache.getReferences()
							.get(index), anntReference.classResponse(),
							internalLevel, cont, anntReference.toColumnName(),
							query.getAliasTable(),
							anntReference.fromColumnName(),
							anntReference.fromTableName(), nameConnection,
							!anntReference.isOneToOne());

					// System.out.println("<- " +
					// anntReference.nameReference());
				}
			}
		}
	}

	List<String> lstColums = null;

	@SuppressWarnings("unchecked")
	private void generateSelect() throws ElxGenericException {

		lstColums = new ArrayList<String>();
		CacheClass cache = CacheClass.getCacheClass(mainClazz);

		String nameTable = containerDataClass.getNameTable(nameConnection);
		String nameSchema = containerDataClass.getNameSchema(nameConnection);

		query = new QueryBuilder(vendor, nameTable, nameSchema, cont.getValue());

		for (Entry<String, Field> entry : containerDataClass
				.getMapNameColumnsField().entrySet()) {

			if (entry.getValue().getAnnotation(Id.class) != null) {
				query.addColumnId(entry.getKey(), entry.getValue()
						.getAnnotation(Validation.class));
			} else {
				
				Validation anntValidation = entry.getValue().getAnnotation(
						Validation.class);				
				query.addColumn(entry.getKey(), anntValidation);
			}
		}

		if (mainLevel > 0) {
			if (cache.getReferences().size() > 0) {
				readers = new Reader[cache.getReferences().size()];
				Integer internalLevel = mainLevel - 1;
				for (int index = 0; index < readers.length; index++) {

					Field field = cache.getReferences().get(index);
					Reference anntReference = field
							.getAnnotation(Reference.class);

					// System.out.println(">- " +
					// anntReference.nameReference());

					readers[index] = new Reader(vendor, cache.getReferences()
							.get(index), anntReference.classResponse(),
							internalLevel, cont, anntReference.toColumnName(),
							query.getAliasTable(),
							anntReference.fromColumnName(),
							anntReference.fromTableName(), nameConnection,
							anntReference.isOneToOne());

					// System.out.println("-< " +
					// anntReference.nameReference());
				}
			}
		}

	}

	public StringBuilder getQuery() {
		buildQuery();
		return query.buildQuery();
	}

	private void buildQuery() {
		if (readers != null) {
			for (Reader<?> reader : readers) {
				reader.buildQuery();
				query.addSelect(reader.query);
			}
		}
	}

	private Boolean flagInsetr = false;
	private E cache = null;

	/**
	 * This method cleans the cache data
	 */
	private void cleanCache() {
		cache = null;
		if (readers != null) {
			for (Reader<?> reader : readers) {
				reader.cleanCache();
			}
		}
	}

	/**
	 * @param resultSet
	 * @return
	 * @throws SQLException
	 * @throws ElxGenericException
	 */
	public List<E> buildResult(ResultSet resultSet) throws SQLException,
			ElxGenericException {

		// ResultSetMetaData mD = resultSet.getMetaData();
		// Map<String, String> metaData = new TreeMap<String, String>();
		// for (int index = 1; index <= mD.getColumnCount(); index++) {
		// // System.out.println("LABEL:[" + mD.getColumnLabel(index) + "]  ;"
		// + " NAME:[" + mD.getColumnName(index) + "]  ; tableName:["
		// + mD.getTableName(index) + "]");
		// metaData.put(mD.getColumnLabel(index), mD.getColumnLabel(index)
		// + "*" + mD.getColumnName(index) + "*" + index);
		// }

		int cont = 0;
		List<E> lstResult = new ArrayList<E>();
		Integer index = null;
		while (resultSet.next()) {
			cont++;
			E entity = generateResult(resultSet);
			if ((index = lstResult.indexOf(entity)) >= 0) {
				Entity tmp = lstResult.get(index);
				tmp.getIdEntity();
			} else {
				lstResult.add(entity);
			}
		}

		return lstResult;
	}

	/**
	 * Retrieve the data of the ResultSet in the Class.
	 * 
	 * @param resultSet
	 * @return Object with datas
	 * @throws SQLException
	 */
	private E reloadaClass(ResultSet resultSet) throws SQLException {
		E entityTmp = Utils.getUtil().createInstance(mainClazz);
		for (Entry<String, String> entry : query.getColumns().entrySet()) {
			Field field = containerDataClass.getMapNameColumnsField().get(
					entry.getKey());
			field.setAccessible(true);
			Object value = resultSet.getObject(entry.getValue());
			Utils.getUtil().setValueField(entityTmp, field, value);
		}
		return entityTmp;
	}

	private <K extends Entity> E generateResult(ResultSet resultSet)
			throws SQLException, ElxGenericException {

		E entityTmp = reloadaClass(resultSet);
		if (cache == null) {
			cache = entityTmp;
			flagInsetr = true;
		} else if (entityTmp.getIdEntity().equals(cache.getIdEntity())) {
			entityTmp = cache;
			flagInsetr = false;
		} else {
			cleanCache();
			flagInsetr = true;
			cache = entityTmp;
		}

		for (int index = 0; readers != null && index < readers.length; index++) {
			Entity tmp = readers[index].generateResult(resultSet);
			if (readers[index].flagInsetr) {

				Reference anntReference = readers[index].field
						.getAnnotation(Reference.class);
				if (anntReference.isOneToOne()) {
					Entity entityOneToOne = Utils.getUtil().getValueField(
							cache, readers[index].field);
					if (entityOneToOne == null || tmp.equals(entityOneToOne)) {
						Utils.getUtil().setValueField(cache,
								readers[index].field, tmp);
					} else {
						throw new ElxGenericException("Error, this reference:["
								+ anntReference.nameReference()
								+ "] don't have a relation One To One");
					}
				} else {
					List<K> lst = Utils.getUtil().getValueField(cache,
							readers[index].field);

					if (lst == null) {
						lst = new ArrayList<K>();
						Utils.getUtil().setValueField(cache,
								readers[index].field, lst);
					}

					Integer indexOf = -1;
					if ((indexOf = lst.indexOf(entityTmp)) >= 0) {
						Entity entityOneToMany = lst.get(indexOf);
					} else {
						lst.add((K) tmp);
					}
				}
			}
		}

		return entityTmp;
	}

}

class Cont {

	private Long contValue = null;

	/**
 * 
 */
	protected Cont() {
		this.contValue = new Long("0");
	}

	public Cont increment() {
		contValue++;
		return this;
	}

	public Long getValue() {
		return contValue;
	}

}
class QueryBuilder {

	final private static String STR_SPACE = "\n      "; 
	private static Integer contCoreColumns = 0;
	
	private List<String> lstColumnsWhere = null;
	private List<String> lstColumnsOrderBy = null;
	private Map<String, String> lstColumsRS = null;
	private List<String> lstTables = null;
	private List<String> lstOuterJoin = null;
	private List<String> lstColums = null;
	private Long contColumn = null;
	private String newLine = STR_SPACE;
	private String nameTable, nameSchema;
	private String aliasTable = "";
	private String aliasColumn = null;
	private String valueRandom = null;
	private Vendor vendor = null;

	protected QueryBuilder(Vendor vendor, String nameTable, String nameSchema,
			Long contTable) {

		lstColumsRS = new TreeMap<String, String>();
		lstColums = new ArrayList<String>();
		lstOuterJoin = new ArrayList<String>();
		lstTables = new ArrayList<String>();
		lstColumnsOrderBy = new ArrayList<String>();
		lstColumnsWhere = new ArrayList<String>();
		contColumn = new Long(0L);
		// valueRandom= (System.currentTimeMillis()+"");
		valueRandom = "105032";
		valueRandom = valueRandom.substring(valueRandom.length() - 2);

		this.vendor = vendor;
		this.nameSchema = nameSchema;
		this.nameTable = nameTable;
		aliasTable = "entity";
//		aliasTable = "at_" + valueRandom + "_" + contTable;
		aliasColumn = "ac_" + valueRandom + "_" + contTable;
		lstTables.add(STR_SPACE + nameSchema + nameTable + " "
				+ aliasTable);

		contCoreColumns++;
	}

	/**
	 * @param nameColumnTo
	 * @param nameColumnFrom
	 * @param nameTableFrom
	 * @param isOneToOne
	 *            TODO
	 * @param vendor2
	 * @param nameTable2
	 * @param nameSchema2
	 * @param value
	 */
	protected QueryBuilder(Vendor vendor, String nameTable, String nameSchema,
			Long contTable, String nameColumnTo, String aliasTableTo,
			String nameColumnFrom, String nameTableFrom, Boolean isOneToOne) {
		// Yo trabajaré con el TO.

		lstColumsRS = new TreeMap<String, String>();
		lstColums = new ArrayList<String>();
		lstOuterJoin = new ArrayList<String>();
		lstTables = new ArrayList<String>();
		lstColumnsOrderBy = new ArrayList<String>();
		lstColumnsWhere = new ArrayList<String>();
		contColumn = new Long(0L);

		// valueRandom= (System.currentTimeMillis()+"");
		valueRandom = "105032";
		valueRandom = valueRandom.substring(valueRandom.length() - 2);

		this.vendor = vendor;
		this.nameSchema = nameSchema;
		this.nameTable = nameTable;

		aliasTable = "at_" + valueRandom + "_" + contTable;
		aliasColumn = "ac_" + valueRandom + "_" + contTable;

		String outerJoin = vendor.generateOuterJoin(nameTable, nameSchema,
				nameColumnTo, aliasTableTo, aliasTable, nameColumnFrom);

		lstOuterJoin.add(STR_SPACE + outerJoin);

		lstTables.add(STR_SPACE + nameSchema + nameTable + " "
				+ aliasTable);
		if (!isOneToOne) {
			contCoreColumns++;
		}
	}

	/**
	 * @return the aliasTable
	 */
	public String getAliasTable() {
		return aliasTable;
	}

	/**
	 * @param select
	 */
	public void addSelect(QueryBuilder select) {
		this.lstColumnsOrderBy.addAll(select.lstColumnsOrderBy);
		this.lstColums.addAll(select.lstColums);
		this.lstOuterJoin.addAll(select.lstOuterJoin);
		this.lstTables.addAll(select.lstTables);
		this.lstColumnsWhere.addAll(select.lstColumnsWhere);
	}

	/**
	 * @return
	 */
	public StringBuilder buildQuery() {
		return vendor.generateQuery(nameSchema, nameTable, aliasTable,
				lstTables, lstColums, lstOuterJoin, lstColumnsOrderBy,
				lstColumnsWhere);
	}

	/**
	 * @param key
	 * @param anntValidation
	 * @param column
	 *            TODO
	 */
	public void addColumn(String key, Validation anntValidation) {

	
		String column = aliasTable + "." + key;

		if (anntValidation != null) {
			if (anntValidation.toTrim()) {
				column = vendor.toTrim(column);
			}

			if (anntValidation.toLowercase()) {
				column = vendor.toLowerCase(column);
			} else if (anntValidation.toUppercase()) {
				column = vendor.toUpperCase(column);
			}
		}

		contColumn++;
//		if (anntValidation != null && anntValidation.formatDate().length() > 0) {
//			// lstColums.add(aliasTable +"."+key +" AS "+aliasColumn);
//		} else {
			lstColums.add(newLine + column + " AS " + aliasColumn + "_"
					+ contColumn);

			lstColumsRS.put(key, aliasColumn + "_" + contColumn);
			newLine = "";
//		}
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see java.lang.Object#toString()
	 */
	@Override
	public String toString() {
		final int maxLen = 10;
		return "Query ["
				+ (lstColums != null ? "lstColums="
						+ lstColums.subList(0,
								Math.min(lstColums.size(), maxLen)) + ", " : "")
				+ (contColumn != null ? "contColumn=" + contColumn + ", " : "")
				+ (nameTable != null ? "nameTable=" + nameTable + ", " : "")
				+ (nameSchema != null ? "nameSchema=" + nameSchema + ", " : "")
				+ (aliasTable != null ? "aliasTable=" + aliasTable + ", " : "")
				+ (aliasColumn != null ? "aliasColumn=" + aliasColumn + ", "
						: "")
				+ (valueRandom != null ? "valueRandom=" + valueRandom + ", "
						: "") + (vendor != null ? "vendor=" + vendor : "")
				+ "]";
	}

	/**
	 * @return List with name of columns.
	 */
	public Map<String, String> getColumns() {
		return lstColumsRS;
	}

	public void addColumnId(String key, Validation validation) {
		contColumn++;
//		if (validation != null && validation.formatDate().length() > 0) {
//			// lstColums.add(aliasTable +"."+key +" AS "+aliasColumn);
//		} else {
			lstColums.add(newLine + aliasTable + "." + key + " AS "
					+ aliasColumn + "_" + contColumn);
			lstColumsRS.put(key, aliasColumn + "_" + contColumn);
//		}
		lstColumnsOrderBy.add(aliasColumn + "_" + contColumn);
		lstColumnsWhere.add("( CORE." + aliasColumn + "_" + contColumn
				+ " IS NULL OR " + "CORE." + aliasColumn + "_" + contColumn
				+ " = " + aliasTable + "." + key + " )");

	}
}

