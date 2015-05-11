# Create and configure your entities #

An **entity** is a simple class that it is used to map with one table in the DB. You only need to define:
  * **Table** (`@Table`) or set of **tables** (`@Tables`) to map.
  * The **id** (`@Id`) with their name of DB connection and the name of sequence to execute in the DB.
  * The **columns** (`@Column`) to map the attribute in the class with the column in the table.
  * The **validation** (`@Validation`) of each column.


```


@Tables(
lstTables = {
		@Table(name = "Company", schema = "schema_elx_derby", connection = @Connection(nameConnection = "derby_CNX")),
		@Table(name = "Company", connection = @Connection(nameConnection = "MySQL_CNX")) 
		}
)
public class TblCompany implements Entity {

	@Id(nameConnection = "MySQL_CNX")
	@Column(name = "comp_id_company")
	private Long idCompany;

	@Column(name = "comp_id_address")
	private Long idAddress;

	@Column(name = "comp_name_company")
	private String name;

	@Validation(startWith = "SECTOR:", toTrim = true, toUppercase = true)
	@Column(name = "comp_sector_Company")
	private String sector;

	@Reference(classResponse = TblAddress.class, fromColumnName = "add_id_address", fromTableName = "Address", isOneToOne = true, nameReference = "REF_COMP_ADD", toColumnName = "comp_id_address")
	private TblAddress address = null;

	@Reference(classResponse = TblEmployee.class, fromColumnName = "emp_id_company", fromTableName = "Employee", isOneToOne = false, nameReference = "REF_COMP_EMP", toColumnName = "comp_id_company")
	private List<TblEmployee> lstEmployees = null;

....

}

```