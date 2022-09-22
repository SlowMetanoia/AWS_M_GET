CREATE SCHEMA cqc;

-- смена схемы на courses
SET search_path TO cqc;

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
    temp       varchar(150);

BEGIN
    IF (NEW.parent_id IS NOT NULL) THEN
        parentType := (SELECT cqc_element.type
                       FROM cqc_element
                       WHERE id = NEW.parent_id);

        temp := (SELECT cqc_elem_hierarchy.parent_type
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



CREATE TABLE course
(
    id   UUID         not null unique,
    name varchar(250) not null
);
ALTER TABLE course
    ADD CONSTRAINT course_pk
        PRIMARY KEY (id);

CREATE TABLE course_output_leaf_link
(
    course_id uuid not null,
    leaf_id   uuid not null
);
ALTER TABLE course_output_leaf_link
    ADD CONSTRAINT course_output_leaf_fk
        FOREIGN KEY (course_id)
            REFERENCES course (id)
            ON DELETE CASCADE;
ALTER TABLE course_output_leaf_link
    ADD CONSTRAINT output_leaf_fk
        FOREIGN KEY (leaf_id)
            REFERENCES cqc_element (id)
            ON DELETE CASCADE;

CREATE TABLE course_input_leaf_link
(
    course_id uuid not null,
    leaf_id   uuid not null
);
ALTER TABLE course_input_leaf_link
    ADD CONSTRAINT course_input_leaf_fk
        FOREIGN KEY (course_id)
            REFERENCES course (id)
            ON DELETE CASCADE;
ALTER TABLE course_input_leaf_link
    ADD CONSTRAINT input_leaf_fk
        FOREIGN KEY (leaf_id)
            REFERENCES cqc_element (id)
            ON DELETE CASCADE;

DROP TABLE course_input_leaf_link;
DROP TABLE course_output_leaf_link;

CREATE OR REPLACE FUNCTION course_leaf_insert_trigger() RETURNS trigger AS
$trigger_code$
BEGIN
    IF ((SELECT parent_type
         FROM cqc_elem_hierarchy
         WHERE parent_type =
               (SELECT type FROM cqc_element WHERE id = NEW.leaf_id) LIMIT 1) IS NOT NULL) THEN
        RAISE EXCEPTION 'invalid relationship';
    END IF;
    RETURN NEW;
END;
$trigger_code$ LANGUAGE plpgsql;

CREATE TRIGGER course_input_leaf_insert_trigger
    BEFORE INSERT
    ON course_input_leaf_link
    FOR EACH ROW
EXECUTE PROCEDURE course_leaf_insert_trigger();

CREATE TRIGGER course_output_leaf_insert_trigger
    BEFORE INSERT
    ON course_output_leaf_link
    FOR EACH ROW
EXECUTE PROCEDURE course_leaf_insert_trigger();

INSERT INTO course
VALUES ('78168bf0-ef00-44f3-81de-e0c6b3338ddc', 'Курсик1'),
       ('10321d6e-dcfe-46c8-8674-20aea5fa4c1a', 'Курсик2');

INSERT INTO course_input_leaf_link
VALUES ('78168bf0-ef00-44f3-81de-e0c6b3338ddc', 'a02a0918-f362-4a85-ae4c-f6960851640c');

INSERT INTO course_input_leaf_link
VALUES ('78168bf0-ef00-44f3-81de-e0c6b3338ddc', '04293cea-fa4d-482b-b08d-7fc4691236e7');