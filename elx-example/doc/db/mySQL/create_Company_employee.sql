
create schema schema_elx_mysql;

create table schema_elx_mysql.Address(
add_id_address BIGINT PRIMARY KEY AUTO_INCREMENT,
add_name_country varchar(50),
add_name_state varchar(50),
add_age_city varchar(50)
);

create table schema_elx_mysql.Company(
comp_id_Company BIGINT PRIMARY KEY AUTO_INCREMENT,
comp_id_addresS BIGINT ,
comp_name_Company varchar(50),	
comp_sector_Company varchar(50)
);


create table schema_elx_mysql.Employee(
emp_id_employee BIGINT PRIMARY KEY AUTO_INCREMENT,
emp_id_address  BIGINT ,
emp_id_company  BIGINT,
emp_name_employee varchar(50),
emp_age_employee int
);

ALTER TABLE schema_elx_mysql.Company add CONSTRAINT  FK_COMPANY_ADDRESS Foreign Key (comp_id_address) REFERENCES  schema_elx_mysql.Address(add_id_address);
ALTER TABLE schema_elx_mysql.Employee add CONSTRAINT FK_EMPLOYEE_ADDRESS Foreign Key (emp_id_address)  REFERENCES  schema_elx_mysql.Address(add_id_address);
ALTER TABLE schema_elx_mysql.Employee add CONSTRAINT FK_EMPLOYEE_COMPANY Foreign Key (emp_id_company)  REFERENCES  schema_elx_mysql.Company(comp_id_company);



DROP TABLE schema_elx_mysql.Company;
DROP TABLE schema_elx_mysql.Employee;
DROP table schema_elx_mysql.Address;


-- Resultado ok¡¡¡
SELECt c.comp_id_company , a.add_id_addresS AS AC_add_id_addreSS, e.emp_id_employee , ea.add_id_addreSS as ea_add_id_addreSS
FROM schema_elx.Company c 
     LEFT JOIN schema_elx.Address  AS a ON  c.comp_id_addresS = a.add_id_address
     LEFT JOIN schema_elx.Employee AS e ON  c.comp_id_company  = e.emp_id_company 
     LEFT JOIN schema_elx.Address  AS ea ON  e.emp_id_addresS = ea.add_id_addresS
order by       c.comp_id_company , AC_add_id_addreSS, e.emp_id_employee ,  ea_add_id_addreSS
