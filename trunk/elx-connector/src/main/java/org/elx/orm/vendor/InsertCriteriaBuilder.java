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

import org.elx.orm.annotations.Column;
import org.elx.orm.annotations.Validation;
import org.elx.orm.utils.Entity;
import org.elx.orm.utils.Utils;
import org.elx.orm.utils.type.TypeOperation;
import org.elx.orm.validate.ElxValidateException;
import org.elx.orm.validate.ValidateEntity;


/**
 * This class contain the logic and methods necessary for build the query of
 * Insert.
 * 
 * @author Jose Clavero Anderica jose.clavero.anderica@gmail.com
 * 
 * @param <T>
 *            The type generic must to extend of Entity.
 */
class InsertCriteriaBuilder extends AbstractCriteriaBuilder {

	protected <T extends Entity> InsertCriteriaBuilder(T entity,
			String nameConnection) throws ElxValidateException {
		super(entity.getClass(), TypeOperation.Insert);

		final StringBuilder strColumns = new StringBuilder();
		final StringBuilder strParams = new StringBuilder();

		String separator = "";
		for (Entry<String, Field> entry : getDataClass()
				.getMapNameColumnsField().entrySet()) {

			Column anntColumn = null;
			Validation anntValidation = null;
			if ((anntColumn = entry.getValue().getAnnotation(Column.class)) != null && anntColumn.insertable()) {
				anntValidation = entry.getValue().getAnnotation(Validation.class);
				ValidateEntity.get().validateField(anntValidation, entry.getValue(), entity);
				
				strColumns.append(separator).append(entry.getKey());
				strParams.append(separator).append(Constant.STR_PARAMS);
				getListParameters()
						.add(Utils.getUtil().getValueField(entity,
								entry.getValue()));
				separator = Constant.STR_COMMA;
			}
		}

		String schema =  getDataClass().getNameSchema(nameConnection);

		setSql(new StringBuilder().append(Constant.STR_INSERT_INTO)
				.append(schema)
				.append(getDataClass().getNameTable(nameConnection))
				.append(Constant.STR_OPEN_PARENTHESES).append(strColumns)
				.append(Constant.STR_CLOSE_PARENTHESES)
				.append(Constant.STR_VALUES)
				.append(Constant.STR_OPEN_PARENTHESES).append(strParams)
				.append(Constant.STR_CLOSE_PARENTHESES));
	}
}
