package nguyen.example.hsf302_1.service;

import nguyen.example.hsf302_1.entity.Department;
import nguyen.example.hsf302_1.repository.DepartmentRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class DepartmentService {

    @Autowired
    private DepartmentRepository departmentRepository;

    public List<Department> findAll() {
        return departmentRepository.findAll();
    }

    public Department findById(int id) {
        Optional<Department> deptOpt = departmentRepository.findById(id);
        return deptOpt.orElse(null);
    }

    public Department findByName(String departmentName) {
        Optional<Department> deptOpt = departmentRepository.findByDepartmentName(departmentName);
        return deptOpt.orElse(null);
    }

    public Department save(Department department) {
        return departmentRepository.save(department);
    }

    public void deleteById(int id) {
        departmentRepository.deleteById(id);
    }

    public boolean existsByName(String departmentName) {
        return departmentRepository.existsByDepartmentName(departmentName);
    }

    public boolean existsByNameAndIdNot(String departmentName, int id) {
        Department dept = findByName(departmentName);
        return dept != null && dept.getId() != id;
    }

    public long count() {
        return departmentRepository.count();
    }
}