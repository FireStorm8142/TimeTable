package com.johan.timetable.teacher;

import com.johan.timetable.teacher.dto.TeacherRequest;
import com.johan.timetable.teacher.dto.TeacherResponse;

import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.server.ResponseStatusException;

import java.util.List;
import java.util.UUID;

@Service
@Transactional
public class TeacherService {

    private final TeacherRepository repository;

    public TeacherService(TeacherRepository repository) {
        this.repository = repository;
    }

    public TeacherResponse create(TeacherRequest request) {

        String email = normalizeEmail(request.email());

        if (repository.existsByEmailIgnoreCase(email)) {
            throw new ResponseStatusException(
                    HttpStatus.CONFLICT,
                    "Teacher email already exists");
        }

        Teacher teacher = new Teacher(
                request.fullName().trim(),
                email,
                normalizePhone(request.phone())
        );

        return toResponse(repository.save(teacher));
    }

    @Transactional(readOnly = true)
    public List<TeacherResponse> findAll() {
        return repository.findAllByActiveTrueOrderByFullNameAsc()
                .stream()
                .map(this::toResponse)
                .toList();
    }

    @Transactional(readOnly = true)
    public TeacherResponse findById(UUID id) {
        return toResponse(getActiveTeacher(id));
    }

    public TeacherResponse update(UUID id, TeacherRequest request) {

        Teacher teacher = getActiveTeacher(id);
        String email = normalizeEmail(request.email());

        if (repository.existsByEmailIgnoreCaseAndIdNot(
                email, id)) {
            throw new ResponseStatusException(
                    HttpStatus.CONFLICT,
                    "Teacher email already exists");
        }

        teacher.setFullName(request.fullName().trim());
        teacher.setEmail(email);
        teacher.setPhone(normalizePhone(request.phone()));

        return toResponse(repository.save(teacher));
    }

    public void deactivate(UUID id) {
        Teacher teacher = getActiveTeacher(id);
        teacher.setActive(false);
        repository.save(teacher);
    }

    private Teacher getActiveTeacher(UUID id) {
        return repository.findById(id)
                .filter(Teacher::isActive)
                .orElseThrow(() -> new ResponseStatusException(
                        HttpStatus.NOT_FOUND,
                        "Teacher not found"));
    }

    private String normalizeEmail(String email) {
        return email.trim().toLowerCase();
    }

    private String normalizePhone(String phone) {
        if (phone == null || phone.isBlank()) {
            return null;
        }
        return phone.trim();
    }

    private TeacherResponse toResponse(Teacher teacher) {
        return new TeacherResponse(
                teacher.getId(),
                teacher.getFullName(),
                teacher.getEmail(),
                teacher.getPhone(),
                teacher.isActive(),
                teacher.getCreatedAt(),
                teacher.getUpdatedAt()
        );
    }
}
