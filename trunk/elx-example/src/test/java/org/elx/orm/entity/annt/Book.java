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
package org.elx.orm.entity.annt;

import org.elx.orm.annotations.Column;
import org.elx.orm.annotations.Connection;
import org.elx.orm.annotations.Id;
import org.elx.orm.annotations.Table;
import org.elx.orm.annotations.Tables;
import org.elx.orm.utils.Entity;
import org.elx.orm.utils.type.SourceVendor;

/**
 * @author jose
 * 
 */
@org.elx.orm.annotations.Entity(vendorSource = SourceVendor.MySQL)
@Tables(lstTables = {
		@Table(name = "book", schema = "schema_elx_derby", connection = @Connection(nameConnection = "derby_CNX")),
		@Table(name = "book", schema = "", connection = @Connection(nameConnection = "MySQL_CNX")) })
public class Book implements Entity {

	@Id(nameConnection = "MySQL_CNX")
	@Column(name = "bo_id")
	private Long idAddress;

	@Column(name = "bo_tittle")
	private String tittle;

	@Column(name = "bo_mirror1")
	private byte[] mirror;

	/*
	 * (non-Javadoc)
	 * 
	 * @see org.elx.orm.utils.Entity#getIdEntity()
	 */
	public String getIdEntity() {
		// TODO Auto-generated method stub
		return null;
	}

	public Long getIdAddress() {
		return idAddress;
	}

	public void setIdAddress(Long idAddress) {
		this.idAddress = idAddress;
	}

	public String getTittle() {
		return tittle;
	}

	public void setTittle(String tittle) {
		this.tittle = tittle;
	}

	public byte[] getMirror_1() {
		return mirror;
	}

	public void setMirror_1(byte[] mirror) {
		this.mirror = mirror;
	}

}
