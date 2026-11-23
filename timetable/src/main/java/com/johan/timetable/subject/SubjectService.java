package com.johan.timetable.subject;

import com.johan.timetable.subject.dto.SubjectRequest;
import com.johan.timetable.subject.dto.SubjectResponse;

import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.server.ResponseStatusException;

import java.util.List;
import java.util.UUID;

@Service
@Transactional
public class SubjectService {

    private final SubjectRepository repository;

    public SubjectService(SubjectRepository repository) {
        this.repository = repository;
    }

    public SubjectResponse create(SubjectRequest request) {

        String name = request.name().trim();

        if (repository.existsByNameIgnoreCase(name)) {
            throw new ResponseStatusException(
                    HttpStatus.CONFLICT,
                    "Subject already exists");
        }

        Subject subject = new Subject(
                name,
                normalizeCode(request.code())
        );

        return toResponse(repository.save(subject));
    }

    @Transactional(readOnly = true)
    public List<SubjectResponse> findAll() {
        return repository.findAllByActiveTrueOrderByNameAsc()
                .stream()
                .map(this::toResponse)
                .toList();
    }

    @Transactional(readOnly = true)
    public SubjectResponse findById(UUID id) {
        return toResponse(getActiveSubject(id));
    }

    public SubjectResponse update(UUID id, SubjectRequest request) {

        Subject subject = getActiveSubject(id);
        String name = request.name().trim();

        if (repository.existsByNameIgnoreCaseAndIdNot(name, id)) {
            throw new ResponseStatusException(
                    HttpStatus.CONFLICT,
                    "Subject name already exists");
        }

        subject.setName(name);
        subject.setCode(normalizeCode(request.code()));

        return toResponse(repository.save(subject));
    }

    public void deactivate(UUID id) {
        Subject subject = getActiveSubject(id);
        subject.setActive(false);
        repository.save(subject);
    }

    private Subject getActiveSubject(UUID id) {
        return repository.findById(id)
                .filter(Subject::isActive)
                .orElseThrow(() -> new ResponseStatusException(
                        HttpStatus.NOT_FOUND,
                        "Subject not found"));
    }

    private String normalizeCode(String code) {
        if (code == null || code.isBlank()) {
            return null;
        }

        return code.trim().toUpperCase();
    }

    private SubjectResponse toResponse(Subject subject) {
        return new SubjectResponse(
                subject.getId(),
                subject.getName(),
                subject.getCode(),
                subject.isActive(),
                subject.getCreatedAt(),
                subject.getUpdatedAt()
        );
    }
}
