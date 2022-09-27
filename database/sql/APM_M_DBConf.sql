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


CREATE OR REPLACE FUNCTION course_leaf_insert_trigger() RETURNS trigger AS
$trigger_code$
BEGIN
    IF ((SELECT parent_type
         FROM cqc_elem_hierarchy
         WHERE parent_type =
               (SELECT type FROM cqc_element WHERE id = NEW.leaf_id)
         LIMIT 1) IS NOT NULL) THEN
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

CREATE OR REPLACE FUNCTION get_course_parts(leafArr uuid[])
    RETURNS TABLE
            (
                id        uuid,
                parent_id uuid,
                type      varchar(128),
                value     varchar(250)
            )
AS
$code$
DECLARE
    temp UUID;
    res  UUID[];

BEGIN
    CREATE TEMP TABLE steps
    (
        stepNumber SERIAL,
        step       UUID[]
    );

    INSERT INTO steps(step)
    VALUES (leafArr);

    res := leafArr;

    WHILE (NOT ((SELECT step FROM steps ORDER BY stepNumber DESC LIMIT 1) = '{}'))
        LOOP
            DECLARE
                tempArr UUID[];

            BEGIN
                FOREACH temp IN ARRAY (SELECT step FROM steps ORDER BY stepNumber DESC LIMIT 1)
                    LOOP
                        tempArr := ARRAY(SELECT cqc_element.parent_id
                                         FROM cqc_element
                                         WHERE cqc_element.parent_id IS NOT NULL
                                           AND cqc_element.id = temp);

                        res := array_cat(res, tempArr);
                        INSERT INTO steps(step) VALUES (tempArr);
                    END LOOP;
            END;
        END LOOP;
    DROP TABLE steps;

    -- Чтобы из массива убрать дубликаты его нужно развернуть в таблицу с одним столбцом
    -- сделать по этой таблице DISTINCT и результат обратно собрать в массив
    RETURN QUERY (SELECT * FROM cqc_element WHERE cqc_element.id IN (SELECT DISTINCT r FROM unnest(res) AS result(r)));
END;
$code$ LANGUAGE plpgsql;

SELECT *
FROM get_course_parts(ARRAY ['7e7a1515-334f-4869-8cf8-02ab53f375f6', 'b0d7e57c-4ae1-4f96-9f69-8170700a4774', 'c7836ea8-eb7c-4eb3-bf35-2f8e43a963a2']::UUID[]);

SELECT *
FROM cqc_element
WHERE parent_id IS NOT NULL
  AND id = 'c7836ea8-eb7c-4eb3-bf35-2f8e43a963a2';

INSERT INTO course
VALUES ('78168bf0-ef00-44f3-81de-e0c6b3338ddc', 'Курсик1'),
       ('10321d6e-dcfe-46c8-8674-20aea5fa4c1a', 'Курсик2');

INSERT INTO course_input_leaf_link
VALUES ('78168bf0-ef00-44f3-81de-e0c6b3338ddc', 'a02a0918-f362-4a85-ae4c-f6960851640c');

INSERT INTO course_input_leaf_link
VALUES ('78168bf0-ef00-44f3-81de-e0c6b3338ddc', '04293cea-fa4d-482b-b08d-7fc4691236e7');

INSERT INTO cqc_element
VALUES (gen_random_uuid(), null, 'Компетенция', 'Компетенция1'),
       (gen_random_uuid(), null, 'Компетенция', 'Компетенция2'),
       (gen_random_uuid(), null, 'Компетенция', 'Компетенция3');

INSERT INTO cqc_element
VALUES (gen_random_uuid(), '5855c853-9e86-4d18-a604-a908bf8476c2', 'Индикатор', 'Индикатор1'),
       (gen_random_uuid(), '5855c853-9e86-4d18-a604-a908bf8476c2', 'Индикатор', 'Индикатор2');

INSERT INTO cqc_element
VALUES (gen_random_uuid(), 'c8db9dc6-a622-402e-b8e7-2ee2013c72f9', 'Знание', 'Знание1'),
       (gen_random_uuid(), 'c8db9dc6-a622-402e-b8e7-2ee2013c72f9', 'Умение', 'Умение1'),
       (gen_random_uuid(), 'c8db9dc6-a622-402e-b8e7-2ee2013c72f9', 'Навык', 'Навык1')

