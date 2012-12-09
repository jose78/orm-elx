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

import java.sql.SQLException;
import java.util.Collection;

import org.elx.orm.utils.ElxGenericException;
import org.elx.orm.utils.Entity;
import org.elx.orm.validate.ElxValidateException;

/**
 * 
 * @author Jose Clavero Anderica jose.clavero.anderica@gmail.com
 * 
 */
public interface CrudPattern {

	/**
	 * This method delete the entity
	 * 
	 * @param entity
	 *            This object to remove.
	 * @param criteria
	 *            Condition to achieve by the records.
	 * @param flagUseNulls
	 *            <li><b>true</b>: The values of the entity with null's will be
	 *            updated to <b>NULL</b>. <li><b>false</b>: The values of the
	 *            entity with null's will be <b>NOT</b> updated.
	 * @return
	 * @throws ElxGenericException
	 */
	public abstract <T extends Entity> Integer delete(T entity)
			throws SQLException, ElxGenericException;

	/**
	 * Set the entity in database.
	 * 
	 * @param entity
	 * @return
	 * @throws SQLException
	 * @throws ElxGenericException
	 * @throws ElxValidateException
	 */
	public abstract <T extends Entity> Integer insert(T entity)
			throws SQLException, ElxGenericException, ElxValidateException;

	/**
	 * This method updates this entity.
	 * 
	 * @param entity
	 *            This class must be contained the values to update.
	 * @return
	 * @throws ElxGenericException
	 * @throws ElxValidateException
	 */
	public abstract <T extends Entity> Integer update(T entity)
			throws ElxGenericException, SQLException, ElxValidateException;

	/**
	 * @param level
	 *            TODO
	 * @param class1
	 * @param setPagination
	 * @return
	 * @throws ElxGenericException
	 */
	public <T extends Entity, C extends Collection<T>> C find(Class<T> clazz,
			Integer level, Criteria criteria) throws SQLException,
			ElxGenericException;

	public abstract <T, C extends Collection<T>> C read(
			Class<T> clazz, Select select) throws SQLException,
			ElxGenericException;

}