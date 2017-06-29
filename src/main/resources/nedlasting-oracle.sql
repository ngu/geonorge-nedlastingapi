-- Script for creating database and tables for use with Geonorge nedlasting-api

-- Main table containing datasets. 
-- Oracle does not support SERIAL such as PostgreSQL or AUTO_INCREMENT as MySQL.
CREATE SEQUENCE dataset_seq;

-- Main table
CREATE TABLE dataset (
	id integer DEFAULT ON NULL dataset_seq.NEXTVAL NOT NULL ENABLE,
	tittel varchar2(255) NULL,
	metadatauuid varchar2(255) NULL,
	supportsareaselection boolean NULL,
	supportsformatselection boolean NULL,
	supportspolynselection boolean NULL,
	supportsprojectionselection boolean NULL,
	fmeklippeurl varchar2(255) NULL,
	mapselectionlayer varchar2(255) NULL,
	accessconstraint varchar2(255) NULL,
	maxarea integer NOT NULL
);


ALTER TABLE dataset ADD PRIMARY KEY (id);
ALTER TABLE dataset ADD CONSTRAINT idx_uuid UNIQUE (metadatauuid);

-- Files connected to a dataset from the main table
CREATE TABLE filliste (
	id varchar2(255)  NULL,				
	filnavn varchar2(255) NOT NULL,
	url varchar2(2000) NOT NULL,
	kategori varchar2(50) NULL,
	underkategori varchar2(100) NULL,
	inndeling varchar2(50) NULL,
	inndelingsverdi varchar2(100) NULL,
	projeksjon varchar2(100) NULL,
	format varchar2(100) NULL,
	dataset integer NULL
);

ALTER TABLE filliste ADD PRIMARY KEY (id);
ALTER TABLE filliste ADD CONSTRAINT fk_dataset FOREIGN KEY (dataset) REFERENCES dataset (id);

-- Store order information
CREATE TABLE orderdownload (
	referencenumber integer NOT NULL,
	email text NULL,
	orderdate date  NULL,
	username text NULL,
	uuid  text NOT NULL
);
ALTER TABLE orderdownload ADD PRIMARY KEY (referencenumber);

-- Order details
CREATE TABLE orderitem (
	id serial ,
	referencenumber int NOT NULL,
	downloadurl text NULL,
	filename text NULL,
	status int NOT NULL,
	message text NULL,
	fileid text NOT NULL,
	format text NULL,
	area text NULL,
	coordinates text NULL,
	coordinatesystem text NULL,
	projection text NULL,
	metadatauuid text NULL,
	metadataname text NULL,
	areaname text NULL,
	projectionname text NULL
);
ALTER TABLE orderitem ADD PRIMARY KEY (id);
ALTER TABLE orderitem ADD CONSTRAINT fk_filliste FOREIGN KEY (fileid) REFERENCES filliste(id);

-- Column defaults
-- FIXME: Convert Postgres syntax to Oracle RDBMS
--ALTER TABLE dataset ALTER COLUMN maxarea SET  DEFAULT ((0)) ;
--ALTER TABLE orderdownload ALTER COLUMN  orderdate SET DEFAULT now();
--ALTER TABLE orderdownload ALTER COLUMN  uuid SET DEFAULT ('00000000-0000-0000-0000-000000000000');
--ALTER TABLE orderitem ALTER COLUMN status SET DEFAULT ((0));
--ALTER TABLE orderitem ALTER COLUMN fileid SET DEFAULT ('00000000-0000-0000-0000-000000000000');
--ALTER TABLE filliste  WITH CHECK ADD  CONSTRAINT fk_dataset FOREIGN KEY(dataset) REFERENCES dataset (ID) ON DELETE CASCADE
--ALTER TABLE orderitem  WITH CHECK ADD  CONSTRAINT fk_order FOREIGN KEY(referencenumber) REFERENCES orderdownload (referencenumber)