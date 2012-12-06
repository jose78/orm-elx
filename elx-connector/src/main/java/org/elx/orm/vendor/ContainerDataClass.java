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
package org.elx.orm.vendor;

import java.lang.reflect.Field;
import java.util.Map;
import java.util.TreeMap;

import org.elx.orm.annotations.Column;
import org.elx.orm.annotations.Connection;
import org.elx.orm.annotations.Connections;
import org.elx.orm.annotations.Id;
import org.elx.orm.annotations.Table;
import org.elx.orm.annotations.Tables;
import org.elx.orm.utils.ElxGenericException;
import org.elx.orm.utils.Entity;
import org.elx.orm.utils.type.TypeOperation;

public class ContainerDataClass {

	private Map<String, String> mapNameSchema = null;
	private Map<String, String> mapNameTable = null;
	private Map<String, Field> mapNameIdColumns = null;
	private Map<String, Field> mapNameColumnsField = null;
	private Map<String, Field> mapNameIdColumnsField = null;

	/*
	 * <Name_Column , Value_Column>
	 */
	private Map<String, Object> mapValueColumns = null;

	public ContainerDataClass(Class<? extends Entity> clazz,
			TypeOperation typeOperation) {
		mapNameIdColumns = new TreeMap<String, Field>();
		mapNameColumnsField = new TreeMap<String, Field>();
		mapNameIdColumnsField = new TreeMap<String, Field>();

		Map<String, Map<String, String>> map = getInfoSchemaTable(clazz);

		mapNameSchema = map.get("SCHEMA");
		mapNameTable = map.get("TABLE");
		/*
		 * <Name_Column , Value_Column>
		 */
		mapValueColumns = new TreeMap<String, Object>();
		if (clazz.getAnnotation(Table.class) != null
				|| clazz.getAnnotation(Tables.class) != null) {
			readDataFromAnnotation(clazz, typeOperation);
		} else {
			readDataFromClass(clazz, typeOperation);
		}
	}

	private void readDataFromAnnotation(Class<? extends Entity> clazz,
			TypeOperation typeOperation) {

		String column = null;
		for (final Field field : clazz.getDeclaredFields()) {
			final Column anntColumn = field.getAnnotation(Column.class);
			if (anntColumn != null) {
				column = anntColumn.name().trim();
				final Id anntId = field.getAnnotation(Id.class);
				switch (typeOperation) {
				case Delete:
					if (anntId != null) {
						mapNameColumnsField.put(column, field);
					}
					break;
				case Insert:
					if (anntColumn.insertable()) {
						mapNameColumnsField.put(column, field);
					}
					break;
				case Select:
					mapNameColumnsField.put(column, field);
					break;
				case Update:
					if (anntId != null) {
						mapNameIdColumnsField.put(column, field);
						mapNameIdColumns.put(column, field);
					} else if (anntColumn.updatable()) {
						mapNameColumnsField.put(column, field);
					}
					break;
				}

			}
		}
	}

	private void readDataFromClass(Class<? extends Entity> clazz,
			TypeOperation typeOperation) {

		for (final Field field : clazz.getDeclaredFields()) {

			String column = field.getName().trim();
			if (typeOperation == TypeOperation.Insert) {
				mapNameColumnsField = new TreeMap<String, Field>();
			} else {
				Id anntId = field.getAnnotation(Id.class);
				if (anntId != null) {
					mapNameIdColumnsField = new TreeMap<String, Field>();
				} else {
					mapNameIdColumns.put(column, field);
				}
			}
		}
	}

	/**
	 * @return the mapNameColumnsField
	 */
	public Map<String, Field> getMapNameColumnsField() {
		return mapNameColumnsField;
	}

	/**
	 * @return the mapNameIdColumnsField
	 */
	public Map<String, Field> getMapNameIdColumnsField() {
		return mapNameIdColumnsField;
	}

	public String getNameTable(String nameConnection) {
		return mapNameTable.get(nameConnection);
	}

	public Map<String, Field> getNameIdColumns() {
		return mapNameIdColumns;
	}

	/**
	 * @param nameConnection
	 *            TODO
	 * @return the nameSchema
	 */
	public String getNameSchema(String nameConnection) {
		String value = null;
		return ".".equals(value = mapNameSchema.get(nameConnection)) ? " "
				: value;
	}

	/**
	 * This Method retrieves the entity's connections to execute the operations.
	 * 
	 * @param clazz
	 *            Entity that contain the form to access to
	 *            {@link ManagerConncection} and the name of connections to use.
	 * @param enumTypeOperation
	 *            Type of operation (WRITE or READ)
	 * @return List with the connections.
	 * @throws ElxGenericException
	 */
	private Map<String, Map<String, String>> getInfoSchemaTable(Class<?> clazz) {

		// SCHEMA/TABLE, <NAME_CNX , VALUE >
		Map<String, Map<String, String>> result = new TreeMap<String, Map<String, String>>();

		// Name_CONNECTION - NAME_SCHEMA
		Map<String, String> schemaCnxValue = new TreeMap<String, String>();
		result.put("SCHEMA", schemaCnxValue);

		// Name_CONNECTION - NAME_TABLE
		Map<String, String> tableCnxValue = new TreeMap<String, String>();
		result.put("TABLE", tableCnxValue);

		Tables anntTables = clazz.getAnnotation(Tables.class);
		if (anntTables != null) {

			Connection[] lstConnections = new Connection[anntTables.lstTables().length];
			for (int index = 0; index < lstConnections.length; index++) {
				Table anntTable = anntTables.lstTables()[index];
				lstConnections[index] = anntTable.connection();

				tableCnxValue.put(anntTable.connection().nameConnection(),
						anntTable.name());
				schemaCnxValue.put(anntTable.connection().nameConnection(),
						anntTable.schema() + ".");

			}

		} else {
			Table anntTable = clazz.getAnnotation(Table.class);
			if (anntTable != null) {
				tableCnxValue.put(anntTable.connection().nameConnection(),
						anntTable.name());
				schemaCnxValue.put(anntTable.connection().nameConnection(),
						anntTable.schema() + ".");

			} else {
				// Como no tiene anotación TABLE/TABLES nos regimos por el
				// nombre de la tabla.
				Connections anntConnections = clazz
						.getAnnotation(Connections.class);
				if (anntConnections != null) {

					for (Connection connection : anntConnections
							.lstConnections()) {
						tableCnxValue.put(connection.nameConnection(),
								clazz.getSimpleName());
						schemaCnxValue.put(
								connection.nameClassConnectionProvider(), "");
					}

				} else {
					Connection anntConnection = clazz
							.getAnnotation(Connection.class);
					if (anntConnection != null) {
						tableCnxValue.put(anntConnection.nameConnection(),
								clazz.getSimpleName());
						schemaCnxValue.put(
								anntConnection.nameClassConnectionProvider(),
								"");
					} else {
						// throw new
						// ElxGenericException("Connection BAD USED.");
						// Error no hay conexión¡¡¡¡
					}
				}
			}
		}
		return result;
	}
}
