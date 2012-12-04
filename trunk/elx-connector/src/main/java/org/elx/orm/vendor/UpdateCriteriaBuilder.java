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
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Map.Entry;

import org.elx.orm.annotations.Column;
import org.elx.orm.annotations.Validation;
import org.elx.orm.db.Criteria;
import org.elx.orm.utils.Entity;
import org.elx.orm.utils.Utils;
import org.elx.orm.utils.type.TypeOperation;
import org.elx.orm.validate.ElxValidateException;
import org.elx.orm.validate.ValidateEntity;


/**
 * 
 * @author Jose Clavero Anderica jose.clavero.anderica@gmail.com
 * 
 */
class UpdateCriteriaBuilder extends AbstractCriteriaBuilder {

	private List<Object> lstParams = new ArrayList<Object>();
	StringBuilder sbUpdate = new StringBuilder();


	protected <T extends Entity> UpdateCriteriaBuilder(T entity,
			String nameConnection) throws ElxValidateException {
		super(entity.getClass(), TypeOperation.Update);
		sbUpdate.append(Constant.STR_UPDATE)
				.append(getDataClass().getNameSchema(nameConnection))
				.append(getDataClass().getNameTable(nameConnection));
		sbUpdate.append(Constant.STR_SET);
		String separator = "";
		for (Entry<String, Field> entry : getDataClass()
				.getMapNameColumnsField().entrySet()) {
			
			Validation anntValidation= null;
			Column anntColumn = null;
			Object value = null;
			if ((anntColumn = entry.getValue().getAnnotation(Column.class)) != null
					&& anntColumn.updatable()) {
				
				anntValidation = entry.getValue().getAnnotation(Validation.class); 
				
				ValidateEntity.get().validateField(anntValidation, entry.getValue(), entity);
				value = Utils.getUtil().getValueField(entity, entry.getValue());
				sbUpdate.append(separator).append(entry.getKey())
						.append(Constant.STR_EQUALS)
						.append(Constant.STR_PARAMS);
				lstParams.add(value);
				separator = Constant.STR_COMMA;
			}
		}

		separator = Constant.STR_WHERE;
		for (Entry<String, Field> entry : getDataClass().getNameIdColumns()
				.entrySet()) {
			sbUpdate.append(separator).append(entry.getKey())
					.append(Constant.STR_EQUALS).append(Constant.STR_PARAMS);
			lstParams.add(Utils.getUtil().getValueField(entity,
					entry.getValue()));

			// getDataClass().getValueColumns().get(entry);
			separator = Constant.STR_AND;
		}

		// Add the parameter to list.
		getListParameters().addAll(lstParams);

		// Build the query.
		setSql(sbUpdate);
	}
}
