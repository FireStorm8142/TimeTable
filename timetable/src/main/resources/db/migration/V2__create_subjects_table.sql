CREATE TABLE subjects (
                          id UUID PRIMARY KEY,

                          name VARCHAR(100) NOT NULL,
                          code VARCHAR(20),

                          active BOOLEAN NOT NULL DEFAULT TRUE,

                          created_at TIMESTAMPTZ NOT NULL,
                          updated_at TIMESTAMPTZ NOT NULL
);

CREATE UNIQUE INDEX uq_subjects_name_lower
    ON subjects (LOWER(name));

CREATE UNIQUE INDEX uq_subjects_code
    ON subjects (LOWER(code))
    WHERE code IS NOT NULL;
