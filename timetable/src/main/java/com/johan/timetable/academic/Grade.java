package com.johan.timetable.academic;

import jakarta.persistence.*;
import java.util.UUID;

@Entity
@Table(name = "grades")
public class Grade {

    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    private UUID id;

    @Column(name = "grade_number", nullable = false, unique = true)
    private Short gradeNumber;

    @Column(nullable = false, length = 30)
    private String name;

    protected Grade() {}

    public UUID getId() {
        return id;
    }

    public Short getGradeNumber() {
        return gradeNumber;
    }

    public String getName() {
        return name;
    }
}
