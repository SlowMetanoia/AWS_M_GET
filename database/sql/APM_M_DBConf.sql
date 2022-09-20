CREATE SCHEMA cqc;

-- смена схемы на courses
SET search_path TO courses;

CREATE TABLE cqc_elem_dict
(
    name varchar(150) not null unique
);
ALTER TABLE cqc_elem_dict
    ADD CONSTRAINT cqc_elem_dict_pk
        PRIMARY KEY (name);

INSERT INTO cqc_elem_dict
VALUES ('Компетенция'),
       ('Индикатор'),
       ('Знание'),
       ('Умение'),
       ('Навык');

CREATE TABLE cqc_element
(
    id        uuid         not null unique,
    parent_id uuid,
    type      varchar(128) not null,
    value     varchar(250) not null
);
ALTER TABLE cqc_element
    ADD CONSTRAINT cqc_element_pk
        PRIMARY KEY (id);
ALTER TABLE cqc_element
    ADD CONSTRAINT parent_id_fk
        FOREIGN KEY (parent_id)
            REFERENCES cqc_element (id)
            ON DELETE CASCADE;
ALTER TABLE cqc_element
    ADD CONSTRAINT type_id_fk
        FOREIGN KEY (type)
            REFERENCES cqc_elem_dict (name)
            ON DELETE CASCADE;

CREATE TABLE cqc_elem_hierarchy
(
    child_type  varchar(150) not null,
    parent_type varchar(150) not null
);
ALTER TABLE cqc_elem_hierarchy
    ADD CONSTRAINT child_parent_pk
        PRIMARY KEY (child_type, parent_type);
ALTER TABLE cqc_elem_hierarchy
    ADD CONSTRAINT child_fk
        FOREIGN KEY (child_type)
            REFERENCES cqc_elem_dict (name)
            ON DELETE CASCADE;
ALTER TABLE cqc_elem_hierarchy
    ADD CONSTRAINT parent_fk
        FOREIGN KEY (parent_type)
            REFERENCES cqc_elem_dict (name)
            ON DELETE CASCADE;
INSERT INTO cqc_elem_hierarchy
VALUES ('Индикатор', 'Компетенция'),
       ('Знание', 'Индикатор'),
       ('Умение', 'Индикатор'),
       ('Навык', 'Индикатор');

CREATE OR REPLACE FUNCTION cqc_elem_insert_trigger() RETURNS trigger AS
$trigger_code$
DECLARE
    parentType varchar(150);
    temp varchar(150);

BEGIN
    IF (NEW.parent_id IS NOT NULL) THEN
        parentType := (SELECT cqc_element.type
                       FROM cqc_element
                       WHERE id = NEW.parent_id);

        temp:= (SELECT cqc_elem_hierarchy.parent_type
                           FROM cqc_elem_hierarchy
                           WHERE child_type = NEW.type);

        IF (temp IS NULL OR parentType <> temp) THEN
            RAISE EXCEPTION 'invalid relationship';
        END IF;
    END IF;
    RETURN NEW;
END ;
$trigger_code$ LANGUAGE plpgsql;

CREATE TRIGGER cqc_elem_insert_trigger
    BEFORE INSERT
    ON cqc_element
    FOR EACH ROW
EXECUTE PROCEDURE cqc_elem_insert_trigger();