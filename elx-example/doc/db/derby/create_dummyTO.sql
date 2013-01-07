

create table Dummy_TABLE(
id_dummy int PRIMARY KEY,
name_dummy varchar(50),	
pais_dummy varchar(50),
trabajo_dummy  varchar(50),
direcion_dummy varchar(50),
edad_dummy int)


-- create the sequence
CREATE SEQUENCE seq_id_dummy AS BIGINT INCREMENT BY 1 START WITH 1 MAXVALUE 1000000000000 ;
