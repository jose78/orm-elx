-- Tablase para la creación del ejemplo.
create table schema_elx.Address(
add_id_address BIGINT PRIMARY KEY ,
add_name_country varchar(50),
add_name_state varchar(50),
add_age_city varchar(50)
);


create table schema_elx.Company(
comp_id_Company BIGINT PRIMARY KEY,
comp_id_addresS BIGINT ,
comp_name_Company varchar(50),	
comp_sector_Company varchar(50)
);

create table schema_elx.Employee(
emp_id_employee BIGINT PRIMARY KEY,
emp_id_address  BIGINT ,
emp_id_company  BIGINT,
emp_name_employee varchar(50),
emp_age_employee int
);


-- create the sequence
CREATE SEQUENCE schema_elx.seq_id_Company AS BIGINT INCREMENT BY 1 START WITH 1 MAXVALUE 1000000000000 ;
-- create the sequence
CREATE SEQUENCE schema_elx.seq_id_Employee AS BIGINT INCREMENT BY 1 START WITH 1 MAXVALUE 1000000000000 ;
-- create the sequence
CREATE SEQUENCE schema_elx.seq_id_Address AS BIGINT INCREMENT BY 1 START WITH 1 MAXVALUE 1000000000000 ;







ALTER TABLE schema_elx.Company add CONSTRAINT  FK_COMPANY_ADDRESS Foreign Key (comp_id_address) REFERENCES  Address(add_id_address);
ALTER TABLE schema_elx.Employee add CONSTRAINT FK_EMPLOYEE_ADDRESS Foreign Key (emp_id_address)  REFERENCES  Address(add_id_address);
ALTER TABLE schema_elx.Employee add CONSTRAINT FK_EMPLOYEE_COMPANY Foreign Key (emp_id_company)  REFERENCES  Company(comp_id_company);



DROP TABLE Company;
DROP TABLE Employee;
DROP table Address;


-- Resultado ok¡¡¡
SELECt c.comp_id_company , a.add_id_addresS AS AC_add_id_addreSS, e.emp_id_employee , ea.add_id_addreSS as ea_add_id_addreSS
FROM schema_elx.Company c 
     LEFT JOIN schema_elx.Address  AS a ON  c.comp_id_addresS = a.add_id_address
     LEFT JOIN schema_elx.Employee AS e ON  c.comp_id_company  = e.emp_id_company 
     LEFT JOIN schema_elx.Address  AS ea ON  e.emp_id_addresS = ea.add_id_addresS
order by       c.comp_id_company , AC_add_id_addreSS, e.emp_id_employee ,  ea_add_id_addreSS
