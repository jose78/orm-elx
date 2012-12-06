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
import java.util.Map.Entry;

import org.elx.orm.utils.ElxGenericException;
import org.elx.orm.utils.Entity;
import org.elx.orm.utils.Utils;
import org.elx.orm.utils.type.TypeOperation;

/**
 * This class contain the logic and methods necessary for build the query of
 * Delete.
 * 
 * @author Jose Clavero Anderica jose.clavero.anderica@gmail.com
 * 
 * @param <T>
 *            The type generic must to extend of Entity.
 */
class DeleteCriteriaBuilder extends AbstractCriteriaBuilder {

	/**
	 * This constructor will generate the query delete from entity ID.
	 * 
	 * @param entity
	 *            Entity to delete in db.
	 * @param nameConnection
	 *            TODO
	 * @throws Exception
	 * @throws ElxGenericException
	 */
	protected <T extends Entity> DeleteCriteriaBuilder(T entity,
			String nameConnection) {
		super(entity.getClass(), TypeOperation.Delete);

		String separator = "";
		final StringBuilder sbWhere = new StringBuilder();

		for (Entry<String, Field> entry : getDataClass().getNameIdColumns()
				.entrySet()) {

			sbWhere.append(separator).append(entry.getKey())
					.append(Constant.STR_EQUALS).append(Constant.STR_PARAMS);

			// Add the parameter to list.
			getListParameters().add(
					Utils.getUtil().getValueField(entity, entry.getValue()));

			separator = Constant.STR_AND;
		}

		// Build the query.
		setSql(new StringBuilder(Constant.STR_DELETE).append(Constant.STR_FROM)
				.append(getDataClass().getNameSchema(nameConnection))
				.append(getDataClass().getNameTable(nameConnection))
				.append(Constant.STR_WHERE).append(sbWhere));

	}
}
