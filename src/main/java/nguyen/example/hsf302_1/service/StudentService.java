package nguyen.example.hsf302_1.service;

import nguyen.example.hsf302_1.entity.Student;
import nguyen.example.hsf302_1.repository.StudentRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

@Service
public class StudentService {

    @Autowired
    private StudentRepository studentRepository;

    public Page<Student> findByCreatedBy(String createdBy, Pageable pageable) {
        return studentRepository.findByCreatedBy(createdBy, pageable);
    }

    public List<Student> findByCreatedBy(String createdBy) {
        return studentRepository.findByCreatedBy(createdBy);
    }

    public List<Student> findTop5ByGpa() {
        return studentRepository.findTop5ByOrderByGpaDesc();
    }

    public Student findById(int id) {
        Optional<Student> studentOpt = studentRepository.findById(id);
        return studentOpt.orElse(null);
    }

    public Student findByStudentId(String studentId) {
        Optional<Student> studentOpt = studentRepository.findByStudentId(studentId);
        return studentOpt.orElse(null);
    }

    public Student addStudent(Student student, String currentUsername) {
        student.setCreatedBy(currentUsername);
        student.setCreatedAt(LocalDate.now());
        student.setUpdatedAt(LocalDate.now());
        return studentRepository.save(student);
    }

    public Student updateStudent(Student student) {
        student.setUpdatedAt(LocalDate.now());
        return studentRepository.save(student);
    }

    public void deleteById(int id) {
        studentRepository.deleteById(id);
    }

    public boolean isOwner(int id, String currentUsername) {
        Optional<Student> studentOpt = studentRepository.findByIdAndCreatedBy(id, currentUsername);
        return studentOpt.isPresent();
    }

    public Student findByIdAndCreatedBy(int id, String createdBy) {
        Optional<Student> studentOpt = studentRepository.findByIdAndCreatedBy(id, createdBy);
        return studentOpt.orElse(null);
    }

    public boolean existsByStudentId(String studentId) {
        return studentRepository.existsByStudentId(studentId);
    }

    public boolean existsByStudentIdAndIdNot(String studentId, int id) {
        return studentRepository.existsByStudentIdAndIdNot(studentId, id);
    }

    public long countByCreatedBy(String createdBy) {
        return studentRepository.countByCreatedBy(createdBy);
    }

    public Page<Student> findAll(Pageable pageable) {
        return studentRepository.findAll(pageable);
    }

    public List<Student> findAll() {
        return studentRepository.findAll();
    }

    public Page<Student> searchByName(String name, Pageable pageable) {
        return studentRepository.findByNameContainingIgnoreCase(name, pageable);
    }

    public Page<Student> searchByCreatedByAndName(String createdBy, String name, Pageable pageable) {
        return studentRepository.findByCreatedByAndNameContainingIgnoreCase(createdBy, name, pageable);
    }

    public Pageable createPageable(int page, int size, String sortBy, String direction) {
        Sort sort = direction.equalsIgnoreCase("ASC")
                ? Sort.by(sortBy).ascending()
                : Sort.by(sortBy).descending();
        return PageRequest.of(page, size, sort);
    }

    public Pageable createPageable(int page, int size) {
        return PageRequest.of(page, size);
    }
}