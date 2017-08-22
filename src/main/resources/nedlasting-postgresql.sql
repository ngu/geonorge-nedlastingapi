-- Script for creating database and tables for use with Geonorge nedlasting-api

-- CREATE DATABASE nedlasting;
-- \connect nedlasting;

-- Main table containing datasets
CREATE TABLE dataset (
	id serial  NOT NULL,
	tittel text NULL,
	metadatauuid text NULL,
	supportsareaselection text NULL,
	supportsformatselection text NULL,
	supportspolygonselection text NULL,
	supportsprojectionselection text NULL,
	fmeklippeurl text NULL,
	mapselectionlayer text NULL,
	accessconstraint text NULL,
	maxarea integer NOT NULL
);

ALTER TABLE dataset ADD PRIMARY KEY (id);
ALTER TABLE dataset ADD CONSTRAINT idx_uuid UNIQUE (metadatauuid);

CREATE TABLE filliste (
	id serial NOT NULL,
	filnavn text NOT NULL,
	url text NOT NULL,
	kategori text NULL,
	underkategori text NULL,
	inndeling text NULL,
	inndelingsverdi text NULL,
	projeksjon text NULL,
	format text NULL,
	dataset integer NULL
);

ALTER TABLE filliste ADD PRIMARY KEY (id);
ALTER TABLE filliste ADD CONSTRAINT fk_dataset FOREIGN KEY (dataset) REFERENCES dataset (id);

CREATE TABLE orderdownload (
	referencenumber integer NOT NULL,
	email text NULL,
	orderdate date  NULL,
	username text NULL,
	uuid  text NOT NULL
);
ALTER TABLE orderdownload ADD PRIMARY KEY (referencenumber);

CREATE TABLE orderitem (
	id serial NOT NULL,
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
ALTER TABLE dataset ALTER COLUMN maxarea SET  DEFAULT ((0)) ;
ALTER TABLE orderdownload ALTER COLUMN  orderdate SET DEFAULT now();
ALTER TABLE orderdownload ALTER COLUMN  uuid SET DEFAULT ('00000000-0000-0000-0000-000000000000');
ALTER TABLE orderitem ALTER COLUMN status SET DEFAULT ((0));
ALTER TABLE orderitem ALTER COLUMN fileid SET DEFAULT ('00000000-0000-0000-0000-000000000000');
ALTER TABLE filliste  WITH CHECK ADD  CONSTRAINT fk_dataset FOREIGN KEY(dataset) REFERENCES dataset (ID) ON DELETE CASCADE
ALTER TABLE orderitem  WITH CHECK ADD  CONSTRAINT fk_order FOREIGN KEY(referencenumber) REFERENCES orderdownload (referencenumber)
