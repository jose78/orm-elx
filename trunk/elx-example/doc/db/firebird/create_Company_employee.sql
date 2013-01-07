-- Create tables.
create table Address(
add_id_address bigint PRIMARY KEY,
add_name_country varchar(50),
add_name_state varchar(50),
add_age_city varchar(50)
);
create table Company(
comp_id_Company BIGINT PRIMARY KEY ,
comp_id_addresS BIGINT ,
comp_name_Company varchar(50),	
comp_sector_Company varchar(50)
);
create table Employee(
emp_id_employee BIGINT PRIMARY KEY ,
emp_id_address  BIGINT ,
emp_id_company  BIGINT,
emp_name_employee varchar(50),
emp_age_employee int
);

-- Alter tables.
ALTER TABLE Company add CONSTRAINT  FK_COMPANY_ADDRESS Foreign Key (comp_id_address) REFERENCES  Address(add_id_address);
ALTER TABLE Employee add CONSTRAINT FK_EMPLOYEE_ADDRESS Foreign Key (emp_id_address)  REFERENCES  Address(add_id_address);
ALTER TABLE Employee add CONSTRAINT FK_EMPLOYEE_COMPANY Foreign Key (emp_id_company)  REFERENCES  Company(comp_id_company);


-- Create the sequences.
CREATE SEQUENCE seq_id_Company;
CREATE SEQUENCE seq_id_Employee;
CREATE SEQUENCE seq_id_Address;

-- Delete talbes.
DROP TABLE Company;
DROP TABLE Employee;
DROP table Address;


