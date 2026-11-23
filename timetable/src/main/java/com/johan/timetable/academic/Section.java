package com.johan.timetable.academic;

import jakarta.persistence.*;
import java.time.Instant;
import java.util.UUID;

@Entity
@Table(
        name = "sections",
        uniqueConstraints = {
                @UniqueConstraint(
                        name = "uq_section_per_year",
                        columnNames = {
                                "academic_year_id",
                                "grade_id",
                                "section_code"
                        }
                )
        }
)
public class Section {

    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    private UUID id;

    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    @JoinColumn(name = "academic_year_id", nullable = false)
    private AcademicYear academicYear;

    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    @JoinColumn(name = "grade_id", nullable = false)
    private Grade grade;

    @Column(name = "section_code", nullable = false, length = 10)
    private String sectionCode;

    @Column(nullable = false)
    private boolean active = true;

    @Column(name = "created_at", nullable = false, updatable = false)
    private Instant createdAt;

    @Column(name = "updated_at", nullable = false)
    private Instant updatedAt;

    protected Section() {}

    public Section(AcademicYear academicYear, Grade grade,
                   String sectionCode) {
        this.academicYear = academicYear;
        this.grade = grade;
        this.sectionCode = sectionCode;
    }

    @PrePersist
    protected void onCreate() {
        Instant now = Instant.now();
        createdAt = now;
        updatedAt = now;
    }

    @PreUpdate
    protected void onUpdate() {
        updatedAt = Instant.now();
    }

    public UUID getId() { return id; }
    public AcademicYear getAcademicYear() { return academicYear; }
    public Grade getGrade() { return grade; }
    public String getSectionCode() { return sectionCode; }
    public boolean isActive() { return active; }
    public Instant getCreatedAt() { return createdAt; }
    public Instant getUpdatedAt() { return updatedAt; }

    public void setSectionCode(String sectionCode) {
        this.sectionCode = sectionCode;
    }

    public void setActive(boolean active) {
        this.active = active;
    }
}
