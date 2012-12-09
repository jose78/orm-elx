package org.elx.orm.entity.annt;/* JA4J - Class org.elx.orm.entity.Employee Licensed to the Apache Software Foundation (ASF) under one or more contributor license agreements.  See the NOTICE file distributed with this work for additional information regarding copyright ownership. The ASF licenses this file to you under the Apache License, Version 2.0 (the "License", validate = @Validation(contains="DATO" )); you may not use this file except in compliance with the License.  You may obtain a copy of the License at http://www.apache.org/licenses/LICENSE-2.0 Unless required by applicable law or agreed to in writing, software distributed under the License is distributed on an "AS IS" BASIS, WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied. See the License for the specific language governing permissions and limitations under the License. */import org.elx.orm.annotations.Column;import org.elx.orm.annotations.Connection;import org.elx.orm.annotations.Id;import org.elx.orm.annotations.Reference;import org.elx.orm.annotations.Table;import org.elx.orm.annotations.Tables;import org.elx.orm.annotations.Validation;import org.elx.orm.utils.Entity;@Tables(lstTables = {		// @Table(name = "Employee", schema = "ADM", connection		// =@Connection(nameConnection = "oracle_CNX"))		// @Table(name = "Address", schema = "schema_postgre", connection =		// @Connection(nameConnection = "postgre_CNX"))		@Table(name = "Employee", schema = "schema_elx_derby", connection = @Connection(nameConnection = "derby_CNX")),		@Table(name = "Employee", connection = @Connection(nameConnection = "MySQL_CNX")) })public class TblEmployee implements Entity {	@Id(nameConnection = "MySQL_CNX")	@Validation(contains = "DATO")	@Column(name = "emp_id_employee")	private Long idEmployee;	@Validation(endWith = "DATO", scale = 5)	@Column(name = "emp_id_address")	private Long idAddress;	@Validation(contains = "DATO")	@Column(name = "emp_id_company")	private Long idCompany;	@Validation(contains = "DATO")	@Column(name = "emp_name_employee")	private String name;	@Validation(contains = "DATO", maxLength = 5)	@Column(name = "emp_age_employee")	private Integer age = -1;	@Reference(classResponse = TblAddress.class, fromColumnName = "add_id_address", fromTableName = "Address", isOneToOne = true, nameReference = "REF_EMP_ADD", toColumnName = "emp_id_address")	private TblAddress address = null;	public TblEmployee() {		// TODO Auto-generated constructor stub	}	/**	 * @return the edad	 */	public Integer getEdad() {		return this.age;	}	/**	 * @return the idCompany	 */	public Long getIdCompany() {		return this.idCompany;	}	/**	 * @return the idEmployee	 */	public Long getIdEmployee() {		return this.idEmployee;	}	/**	 * @return the name	 */	public String getName() {		return this.name;	}	/**	 * @param edad	 *            the edad to set	 */	public void setEdad(Integer edad) {		this.age = edad;	}	/**	 * @param idCompany	 *            the idCompany to set	 */	public void setIdCompany(Long idCompany) {		this.idCompany = idCompany;	}	/**	 * @param idEmployee	 *            the idEmployee to set	 */	public void setIdEmployee(Long idEmployee) {		this.idEmployee = idEmployee;	}	/**	 * @param name	 *            the name to set	 */	public void setName(String name) {		this.name = name;	}	/**	 * @return the idAddress	 */	public Long getIdAddress() {		return idAddress;	}	/**	 * @param idAddress	 *            the idAddress to set	 */	public void setIdAddress(Long idAddress) {		this.idAddress = idAddress;	}	/**	 * @return the age	 */	public Integer getAge() {		return age;	}	/**	 * @param age	 *            the age to set	 */	public void setAge(Integer age) {		this.age = age;	}	/*	 * (non-Javadoc)	 * 	 * @see net.java.elx.utils.Entity#getIdEntity()	 */	public String getIdEntity() {		return String.valueOf(getIdEmployee());	}	@Override	public String toString() {		return "   Employee ["				+ (idEmployee != null ? "idEmployee=" + idEmployee + ", " : "")				+ (idAddress != null ? "idAddress=" + idAddress + ", " : "")				+ (idCompany != null ? "idCompany=" + idCompany + ", " : "")				+ (name != null ? "name=" + name + ", " : "")				+ (age != null ? "age=" + age + ", " : "")				+ ("address=" + address) + "]";	}}