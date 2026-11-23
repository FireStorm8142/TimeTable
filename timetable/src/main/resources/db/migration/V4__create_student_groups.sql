CREATE TABLE student_groups (
                                id UUID PRIMARY KEY,

                                section_id UUID NOT NULL,

                                name VARCHAR(100) NOT NULL,
                                code VARCHAR(30) NOT NULL,

                                group_type VARCHAR(20) NOT NULL,

                                active BOOLEAN NOT NULL DEFAULT TRUE,

                                created_at TIMESTAMPTZ NOT NULL,
                                updated_at TIMESTAMPTZ NOT NULL,

                                CONSTRAINT fk_student_group_section
                                    FOREIGN KEY (section_id)
                                        REFERENCES sections(id),

                                CONSTRAINT chk_student_group_type
                                    CHECK (group_type IN ('WHOLE_SECTION', 'SUBGROUP')),

                                CONSTRAINT uq_student_group_code
                                    UNIQUE (section_id, code)
);

-- Only one whole-section group per section
CREATE UNIQUE INDEX uq_one_whole_group_per_section
    ON student_groups(section_id)
    WHERE group_type = 'WHOLE_SECTION';

CREATE INDEX idx_student_groups_section
    ON student_groups(section_id);
