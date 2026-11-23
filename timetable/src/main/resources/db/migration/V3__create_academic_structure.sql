-- Academic years
CREATE TABLE academic_years (
                                id UUID PRIMARY KEY,
                                name VARCHAR(20) NOT NULL UNIQUE,
                                start_date DATE NOT NULL,
                                end_date DATE NOT NULL,
                                active BOOLEAN NOT NULL DEFAULT TRUE,
                                created_at TIMESTAMPTZ NOT NULL,
                                updated_at TIMESTAMPTZ NOT NULL,

                                CONSTRAINT chk_academic_year_dates
                                    CHECK (end_date > start_date)
);

-- Permanent grade master data
CREATE TABLE grades (
                        id UUID PRIMARY KEY,
                        grade_number SMALLINT NOT NULL UNIQUE,
                        name VARCHAR(30) NOT NULL UNIQUE,

                        CONSTRAINT chk_grade_number
                            CHECK (grade_number BETWEEN 1 AND 12)
);

-- Sections for a particular grade and academic year
CREATE TABLE sections (
                          id UUID PRIMARY KEY,
                          academic_year_id UUID NOT NULL,
                          grade_id UUID NOT NULL,
                          section_code VARCHAR(10) NOT NULL,
                          active BOOLEAN NOT NULL DEFAULT TRUE,
                          created_at TIMESTAMPTZ NOT NULL,
                          updated_at TIMESTAMPTZ NOT NULL,

                          CONSTRAINT fk_section_academic_year
                              FOREIGN KEY (academic_year_id)
                                  REFERENCES academic_years(id),

                          CONSTRAINT fk_section_grade
                              FOREIGN KEY (grade_id)
                                  REFERENCES grades(id),

                          CONSTRAINT uq_section_per_year
                              UNIQUE (academic_year_id, grade_id, section_code)
);

CREATE INDEX idx_sections_academic_year
    ON sections (academic_year_id);

CREATE INDEX idx_sections_grade
    ON sections (grade_id);

-- Insert the permanent Grade I-XII master data
INSERT INTO grades (id, grade_number, name)
VALUES
    (gen_random_uuid(), 1, 'Grade I'),
    (gen_random_uuid(), 2, 'Grade II'),
    (gen_random_uuid(), 3, 'Grade III'),
    (gen_random_uuid(), 4, 'Grade IV'),
    (gen_random_uuid(), 5, 'Grade V'),
    (gen_random_uuid(), 6, 'Grade VI'),
    (gen_random_uuid(), 7, 'Grade VII'),
    (gen_random_uuid(), 8, 'Grade VIII'),
    (gen_random_uuid(), 9, 'Grade IX'),
    (gen_random_uuid(), 10, 'Grade X'),
    (gen_random_uuid(), 11, 'Grade XI'),
    (gen_random_uuid(), 12, 'Grade XII');
