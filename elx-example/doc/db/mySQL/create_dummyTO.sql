

CREATE TABLE dummy_table
(
  id_dummy bigint PRIMARY KEY ,
  id_dummyMod_1 bigint not null ,
  id_dummyMod_2 bigint not null ,
  name_dummy character varying(50),
  pais_dummy character varying(50),
  trabajo_dummy character varying(50),
  direcion_dummy character varying(50),
  edad_dummy integer,
  CONSTRAINT dummy_table_pkey PRIMARY KEY (id_dummy )
);



CREATE TABLE dummyMod
(
  id_dummy bigint PRIMARY KEY,
  name_dummy character varying(50),
  pais_dummy character varying(50),
  trabajo_dummy character varying(50),
  direcion_dummy character varying(50),
  edad_dummy integer
);

ALTER TABLE dummy_table add CONSTRAINT  FK_dumy_dmymod_1 Foreign Key (id_dummyMod_1) REFERENCES  dummyMod(id_dummy);
ALTER TABLE dummy_table add CONSTRAINT  FK_dumy_dmymod_2 Foreign Key (id_dummyMod_2) REFERENCES  dummyMod(id_dummy);




-- create the sequence
CREATE SEQUENCE seq_id_dummy_mod  INCREMENT BY 1 START WITH 1 MAXVALUE 1000000000000 ;
CREATE SEQUENCE seq_id_dummy  INCREMENT BY 1 START WITH 1 MAXVALUE 1000000000000 ;
