INSERT INTO cqc_element
VALUES ('ec72a574-8539-414e-81ad-3d59d2db3a2e', null, 'Компетенция', 'Компетеценция1');
INSERT INTO cqc_element
VALUES ('04293cea-fa4d-482b-b08d-7fc4691236e7', 'ec72a574-8539-414e-81ad-3d59d2db3a2e', 'Индикатор', 'Индикатор1');
INSERT INTO cqc_element
VALUES ('a02a0918-f362-4a85-ae4c-f6960851640c', '04293cea-fa4d-482b-b08d-7fc4691236e7', 'Знание', 'Знание1');
INSERT INTO cqc_element
VALUES ('85162fe3-18e0-4f5b-9421-147931d37ff4', '04293cea-fa4d-482b-b08d-7fc4691236e7', 'Умение', 'Умение1');
INSERT INTO cqc_element
VALUES ('3d27d3e3-dae0-48ea-85b9-40a919ac04ee', '04293cea-fa4d-482b-b08d-7fc4691236e7', 'Навык', 'Навык1');

INSERT INTO cqc_element
VALUES (gen_random_uuid(), '04293cea-fa4d-482b-b08d-7fc4691236e7', 'Компетенция', 'Навык1');

INSERT INTO cqc_element
VALUES (gen_random_uuid(), 'ec72a574-8539-414e-81ad-3d59d2db3a2e', 'Знание', 'Знание1');
INSERT INTO cqc_element
VALUES (gen_random_uuid(), 'ec72a574-8539-414e-81ad-3d59d2db3a2e', 'Умение', 'Умение1');
INSERT INTO cqc_element
VALUES (gen_random_uuid(), 'ec72a574-8539-414e-81ad-3d59d2db3a2e', 'Навык', 'Навык1');