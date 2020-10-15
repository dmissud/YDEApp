insert into user_entity (id, first_name, last_name, password, roles, uid) values
(-1, 'Admin', 'admin',  '$2a$10$hKDVYxLefVHV/vtuPhWD3OigtRyOykRLDdUAp80Z1crSoS1lFqaFS', 6, '100001') on conflict (id) do nothing;
insert into user_entity (id, first_name, last_name, password, roles, uid) values
(-2, 'Lupin', 'Arsène',  '$2a$10$hKDVYxLefVHV/vtuPhWD3OigtRyOykRLDdUAp80Z1crSoS1lFqaFS', 2, '100002') on conflict (id) do nothing;
insert into user_entity (id, first_name, last_name, password, roles, uid) values
(-3, 'Gadget', 'Inspecteur',  '$2a$10$hKDVYxLefVHV/vtuPhWD3OigtRyOykRLDdUAp80Z1crSoS1lFqaFS', 4, '100003') on conflict (id) do nothing;
insert into user_entity (id, first_name, last_name, password, roles, uid) values
(-4, 'Bond', 'James',  '$2a$10$hKDVYxLefVHV/vtuPhWD3OigtRyOykRLDdUAp80Z1crSoS1lFqaFS', 6, '100004') on conflict (id) do nothing;
insert into user_entity (id, first_name, last_name, password, roles, uid) values
(-5, 'Hercule', 'Poirot',  '$2a$10$hKDVYxLefVHV/vtuPhWD3OigtRyOykRLDdUAp80Z1crSoS1lFqaFS', 2, '100005') on conflict (id) do nothing;
insert into user_entity (id, first_name, last_name, password, roles, uid) values
(-6, 'Holmes', 'Sherlock',  '$2a$10$hKDVYxLefVHV/vtuPhWD3OigtRyOykRLDdUAp80Z1crSoS1lFqaFS', 6, '100006') on conflict (id) do nothing;
insert into user_entity (id, first_name, last_name, password, roles, uid) values
(-7, 'Burma', 'Nestor',  '$2a$10$hKDVYxLefVHV/vtuPhWD3OigtRyOykRLDdUAp80Z1crSoS1lFqaFS', 4, '100007') on conflict (id) do nothing;
insert into user_entity (id, first_name, last_name, password, roles, uid) values
(-8, 'Adamsberg', 'Jean-Baptiste',  '$2a$10$hKDVYxLefVHV/vtuPhWD3OigtRyOykRLDdUAp80Z1crSoS1lFqaFS', 6, '100008') on conflict (id) do nothing;
