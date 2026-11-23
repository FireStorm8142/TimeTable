package com.johan.timetable.academic;

import com.johan.timetable.academic.dto.GradeResponse;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Comparator;
import java.util.List;

@Service
@Transactional(readOnly = true)
public class GradeService {

    private final GradeRepository repository;

    public GradeService(GradeRepository repository) {
        this.repository = repository;
    }

    public List<GradeResponse> findAll() {
        return repository.findAll()
                .stream()
                .sorted(Comparator.comparing(Grade::getGradeNumber))
                .map(grade -> new GradeResponse(
                        grade.getId(),
                        grade.getGradeNumber(),
                        grade.getName()
                ))
                .toList();
    }
}
