
CREATE TABLE teachers (
                          id UUID PRIMARY KEY,

                          full_name VARCHAR(150) NOT NULL,
                          email VARCHAR(255),
                          phone VARCHAR(30),

                          active BOOLEAN NOT NULL DEFAULT TRUE,

                          created_at TIMESTAMPTZ NOT NULL,
                          updated_at TIMESTAMPTZ NOT NULL
);

CREATE INDEX idx_teachers_full_name
    ON teachers (full_name);

CREATE INDEX idx_teachers_active
    ON teachers (active);
