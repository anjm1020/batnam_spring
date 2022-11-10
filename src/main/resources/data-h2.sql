insert into air_strip(air_strip_name, air_strip_start_zone, air_strip_end_zone)
values ('활주로1', 'N12', 'S09');
insert into air_strip(air_strip_name, air_strip_start_zone, air_strip_end_zone)
values ('활주로2', 'A03', 'B19');

insert into sector(sector_name, sector_cam_url, sector_x, sector_y, air_strip_id)
values ('A', 'cam_url', '0', '0', 1);
insert into sector(sector_name, sector_cam_url, sector_x, sector_y, air_strip_id)
values ('B', 'cam_url', '0', '0.5', 1);
insert into sector(sector_name, sector_cam_url, sector_x, sector_y, air_strip_id)
values ('C', 'cam_url', '0.5', '0', 1);
insert into sector(sector_name, sector_cam_url, sector_x, sector_y, air_strip_id)
values ('D', 'cam_url', '1', '0', 1);
insert into sector(sector_name, sector_cam_url, sector_x, sector_y, air_strip_id)
values ('E', 'cam_url', '0', '1', 1);
insert into sector(sector_name, sector_cam_url, sector_x, sector_y, air_strip_id)
values ('F', 'cam_url', '1', '1', 1);

insert into log(log_object_name, log_date, log_capture_image_url, log_result_image_url, log_result, sector_id)
values ('BIRD', '2022-11-05T22:32:44.638061', 'url', 'url', 'res', 1);
insert into log(log_object_name, log_date, log_capture_image_url, log_result_image_url, log_result, sector_id)
values ('BIRD', '2022-11-05T22:32:44.638061', 'url', 'url', 'res', 1);
insert into log(log_object_name, log_date, log_capture_image_url, log_result_image_url, log_result, sector_id)
values ('BIRD', '2022-11-04T22:32:44.638061', 'url', 'url', 'res', 1);
insert into log(log_object_name, log_date, log_capture_image_url, log_result_image_url, log_result, sector_id)
values ('BIRD', '2022-11-03T22:32:44.638061', 'url', 'url', 'res', 1);
insert into log(log_object_name, log_date, log_capture_image_url, log_result_image_url, log_result, sector_id)
values ('BIRD', '2022-11-02T22:32:44.638061', 'url', 'url', 'res', 1);
insert into log(log_object_name, log_date, log_capture_image_url, log_result_image_url, log_result, sector_id)
values ('BIRD', '2022-11-01T22:32:44.638061', 'url', 'url', 'res', 1);
insert into log(log_object_name, log_date, log_capture_image_url, log_result_image_url, log_result, sector_id)
values ('BIRD', '2022-10-31T22:32:44.638061', 'url', 'url', 'res', 1);
insert into log(log_object_name, log_date, log_capture_image_url, log_result_image_url, log_result, sector_id)
values ('BIRD', '2022-10-30T22:32:44.638061', 'url', 'url', 'res', 1);

insert into responder (responder_name, responder_type, responder_detail, responder_dest, air_strip_id)
values ("배트맨ADMIN_BE", "ADMIN", "뱉맨", "anjm1020@gmail.com", 1);
insert into responder (responder_name, responder_type, responder_detail, responder_dest, air_strip_id)
values ("배트맨ADMIN_INFRA", "ADMIN", "뱉맨", "ksun4131@gmail.com", 1);

