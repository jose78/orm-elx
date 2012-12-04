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

import java.util.Arrays;

/**
 * 
 * @author Jose Clavero Anderica jose.clavero.anderica@gmail.com
 * 
 */
public class Criteria {

	private boolean flagPagination = false;
	private int pageSize = -1;
	private int pageNum = -1;
	private StringBuilder sbNameTable = null;
	private StringBuilder sbWhere = null
	// , sbColumnsUpdatebles = null
	;
	private Object[] lstParametersWhere = null;

	// private List<Object> lstParametersUpdate = null;

	public Criteria(String where, Object... parameters) {

		sbNameTable = new StringBuilder();
		sbWhere = new StringBuilder(where);

		// sbColumnsUpdatebles = new StringBuilder();
		//
		// lstParametersUpdate = new ArrayList<Object>();
		lstParametersWhere = parameters;

	}

	public Criteria() {

		sbNameTable = new StringBuilder();
		sbWhere = new StringBuilder();
		// sbColumnsUpdatebles = new StringBuilder();
		// lstParametersUpdate = new ArrayList<Object>();

	}

	public Criteria setPagination(int pageSize, int pageNum) {
		this.pageNum = pageNum;
		this.pageSize = pageSize;
		flagPagination = true;
		return this;
	}

	// /**
	// *
	// * @param Where
	// * @param parameters
	// * parameter
	// */
	// public Criteria addColumnsUpdatable(String strColumns, Object...
	// parameters) {
	// sbColumnsUpdatebles.append(strColumns);
	// if (parameters != null) {
	// for (final Object obj : parameters) {
	// lstParametersUpdate.add(obj);
	// }
	// }
	// return this;
	// }
	//
	//
	// public StringBuilder getColumnsUpdatebles() {
	// return sbColumnsUpdatebles;
	// }
	//
	//
	//
	// public List<Object> getLstParametersUpdate() {
	//
	// for (final Object tupla : lstParametersWhere) {
	// lstParametersUpdate.add(tupla);
	// }
	//
	// return lstParametersUpdate;
	// }

	/**
	 * @return the lstParametersWhere
	 */
	public Object[] getParameters() {
		return lstParametersWhere;
	}

	/**
	 * @return the strNameTable
	 */
	public StringBuilder getNameTable() {
		return sbNameTable;
	}

	/**
	 * @return the sbWhere
	 */
	public StringBuilder getWhere() {
		return sbWhere;
	}

	/**
	 * @param strNameTable
	 *            the strNameTable to set
	 */
	public Criteria setNameTable(String strNameTable) {
		sbNameTable.append(strNameTable);
		return this;
	}

	public int getPageNum() {
		return pageNum;
	}

	public int getPageSize() {
		return pageSize;
	}

	public boolean isPagination() {
		return flagPagination;
	}

	/**
	 * Clone the object.
	 */
	@Override
	public Criteria clone() {
		Criteria cloneObj = new Criteria();

		cloneObj.flagPagination = flagPagination;
		cloneObj.pageSize = pageSize;
		cloneObj.pageNum = pageNum;
		cloneObj.sbNameTable.append(sbNameTable.toString());
		cloneObj.sbWhere.append(sbWhere.toString());
		// cloneObj.sbColumnsUpdatebles.append(sbColumnsUpdatebles);

		cloneObj.lstParametersWhere = Arrays.copyOf(lstParametersWhere,
				lstParametersWhere.length);
		// Collections.copy(cloneObj.lstParametersUpdate , lstParametersUpdate);
		return cloneObj;
	}

	@Override
	public String toString() {
		return "Criteria [flagPagination="
				+ flagPagination
				+ ", pageSize="
				+ pageSize
				+ ", pageNum="
				+ pageNum
				+ ", "
				+ (sbNameTable != null ? "sbNameTable=" + sbNameTable + ", "
						: "")
				+ (sbWhere != null ? "sbWhere=" + sbWhere + ", " : "")
				+ (lstParametersWhere != null ? "lstParametersWhere="
						+ Arrays.toString(lstParametersWhere) : "") + "]";
	}

}
