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

import java.util.ArrayList;
import java.util.List;

import org.elx.orm.utils.Entity;
import org.elx.orm.utils.type.TypeOperation;

/**
 * 
 * @author Jose Clavero Anderica jose.clavero.anderica@gmail.com
 * 
 */
abstract class AbstractCriteriaBuilder {

	private List<Object> lstParameters = null;
	private StringBuilder sbSql = null;
	private ContainerDataClass dataClass = null;

	protected <T extends Entity> AbstractCriteriaBuilder(Class<T> clazz,
			TypeOperation typeOperation) {
		dataClass = new ContainerDataClass(clazz, typeOperation);
		lstParameters = new ArrayList<Object>();
	}

	public void setSql(StringBuilder sbSql) {
		this.sbSql = sbSql;
	}

	/**
	 * 
	 * @param lstParameters
	 */
	protected void setLstParameters(List<Object> lstParameters) {
		this.lstParameters = lstParameters;
	}

	/**
	 * 
	 * @return the list of Parameters.
	 */
	public List<Object> getListParameters() {
		return this.lstParameters;
	}

	/**
	 * Get the SQL to execute.
	 * 
	 * @return
	 */
	public String getSql() {
		return this.sbSql.toString();
	}

	public ContainerDataClass getDataClass() {
		return dataClass;
	}

}
