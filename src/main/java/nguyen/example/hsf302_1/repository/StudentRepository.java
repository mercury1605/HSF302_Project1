package nguyen.example.hsf302_1.repository;

import nguyen.example.hsf302_1.entity.Student;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface StudentRepository extends JpaRepository<Student, Integer> {

    Page<Student> findByCreatedBy(String createdBy, Pageable pageable);

    List<Student> findByCreatedBy(String createdBy);

    List<Student> findTop5ByOrderByGpaDesc();

    Optional<Student> findByStudentId(String studentId);

    boolean existsByStudentId(String studentId);

    boolean existsByStudentIdAndIdNot(String studentId, int id);

    Optional<Student> findByIdAndCreatedBy(int id, String createdBy);

    long countByCreatedBy(String createdBy);

    @Query("SELECT s FROM Student s WHERE s.department.id = :departmentId")
    Page<Student> findByDepartmentId(@Param("departmentId") int departmentId, Pageable pageable);

    Page<Student> findByNameContainingIgnoreCase(String name, Pageable pageable);

    Page<Student> findByCreatedByAndNameContainingIgnoreCase(
            String createdBy, String name, Pageable pageable);
}