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
