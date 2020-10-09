insert into user_entity (id, password, roles, uid) values
(-1,   '$2a$10$hKDVYxLefVHV/vtuPhWD3OigtRyOykRLDdUAp80Z1crSoS1lFqaFS', 6, 'Admin') on conflict (id) do nothing;
