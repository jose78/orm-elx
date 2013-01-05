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
 * @author Jose Clavero Anderica jose.clavero.anderica@gmail.com
 * 
 */
public class Select {

	private String query = null;
	private Object[] parameters = null;
	private Integer numPage = null;
	private Integer sizePage = null;

	/**
	 * @param string
	 */
	public Select(String query, Object... objects) {
		this.query = query;
		parameters = objects;
	}

	public Select setPagination(Integer numPage, Integer sizePage) {
		this.setNumPage(numPage);
		this.setSizePage(sizePage);
		return this;
	}

	/**
	 * @return the query
	 */
	public String getQuery() {
		return query;
	}

	/**
	 * @return the parameters
	 */
	public Object[] getParameters() {
		return parameters;
	}

	/**
	 * @return the sizePage
	 */
	public Integer getPageSize() {
		return sizePage;
	}

	/**
	 * @param sizePage
	 *            the sizePage to set
	 */
	private void setSizePage(Integer sizePage) {
		this.sizePage = sizePage;
	}

	/**
	 * @return the numPage
	 */
	public Integer getPageNum() {
		return numPage;
	}

	/**
	 * @param numPage
	 *            the numPage to set
	 */
	private void setNumPage(Integer numPage) {
		this.numPage = numPage;
	}

	/**
	 * @return
	 */
	public String buildQuery() {
		return null;
	}
}
