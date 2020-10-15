insert into user_entity (id, first_name, last_name, password, roles, uid) values
(-1, 'YDE Admin', 'YDE Admin Name',  '$2a$10$hKDVYxLefVHV/vtuPhWD3OigtRyOykRLDdUAp80Z1crSoS1lFqaFS', 6, 'Admin') on conflict (id) do nothing;
