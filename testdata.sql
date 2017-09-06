
insert into projection (srid, codespace, name) values (25833, 'http://www.opengis.net/def/crs/EPSG/0/25833', 'EUREF89 UTM sone 33, 2d');
insert into projection (srid, codespace, name) values (32632, 'http://www.opengis.net/def/crs/EPSG/0/32632', 'EPSG:WGS 84 / UTM zone 32N');

-- create admin user. create hash with:
-- echo -n SuperPassord | shasum5.18 -a 512
insert into api_user (username, password_sha512) values ('admin', 'bbb5e8484f4cfbb67ab1b6ec026ebe433091436fe6135955e5e9796f2f6c6f5fad16f528aa20edf0e3dadf217480a94c4fc571cd5c5695cf2393eea714384026');
-- or use pgcrypto
CREATE EXTENSION pgcrypto;
insert into api_user (username, password_sha512) values ('minbruker', encode(digest('mittpassord','sha512'),'hex'));

-- http://kartkatalog.test.geonorge.no/metadata/norges-geologiske-undersokelse/test-sky-nedlasting-api/18777cf4-1f06-4cb0-803d-d6382b76681f
insert into dataset 
    (dataset_id, metadata_uuid, title, supports_area_selection, supports_format_selection, supports_polygon_selection, supports_projection_selection) 
    values (1, '18777cf4-1f06-4cb0-803d-d6382b76681f', 'Grunnvannsborehull', true, true, true, true);
-- http://www.ngu.no/FileArchive/geonorge/Grunnvannsborehull/
insert into dataset_file
    (dataset_id, file_id, url, format_name, srid, area_type, area_code, area_name)
    values (1, '1', 'http://www.ngu.no/FileArchive/geonorge/Grunnvannsborehull/Grunnvannsborehull-fylke_1-32632-sosi.zip', 'SOSI', 32632, 'fylke', '01', 'Østfold');
insert into dataset_file
    (dataset_id, file_id, url, format_name, srid, area_type, area_code, area_name)
    values (1, '2', 'http://www.ngu.no/FileArchive/geonorge/Grunnvannsborehull/Grunnvannsborehull-fylke_2-32632-sosi.zip', 'SOSI', 32632, 'fylke', '02', 'Akershus');
