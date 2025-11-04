package nguyen.example.hsf302_1.controller;

import jakarta.servlet.http.HttpSession;
import jakarta.validation.Valid;
import lombok.extern.slf4j.Slf4j;
import nguyen.example.hsf302_1.entity.Department;
import nguyen.example.hsf302_1.entity.Student;
import nguyen.example.hsf302_1.entity.UserAccount;
import nguyen.example.hsf302_1.service.DepartmentService;
import nguyen.example.hsf302_1.service.StudentService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Controller
@RequestMapping("/students")
@Slf4j
public class StudentController {

    @Autowired
    private StudentService studentService;

    @Autowired
    private DepartmentService departmentService;

    @GetMapping
    public String showStudentPage(
            @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "5") int size,
            HttpSession session,
            Model model) {

        // Lấy user từ session
        UserAccount loggedInUser = (UserAccount) session.getAttribute("user");
        loadData(model, loggedInUser, page, size, false);
        if (!model.containsAttribute("studentForm")) {
            model.addAttribute("studentForm", new Student());
        }
        return "students";
    }

    @PostMapping("/action")
    public String handleAction(Model model,
                               @Valid @ModelAttribute("studentForm") Student student,
                               BindingResult bindingResult,
                               HttpSession session,
                               @RequestParam("action") String action,
                               @RequestParam(value = "pageNum", defaultValue = "0") int page,
                               @RequestParam(value = "pageSize", defaultValue = "5") int size) {

        UserAccount acc = (UserAccount) session.getAttribute("user");
        boolean editmode = action.equalsIgnoreCase("update");

        //start validation
        boolean hasError = false;
        if (bindingResult.hasErrors()) {
            hasError = true;
        }
        log.info("This is Student object {}", student);
        if (isDuplicateStudentId(action, student)) {
            bindingResult.rejectValue("studentId", "studentId.duplicate", "Student ID must be unique!");
            hasError = true;
        }

        //validate tieng cho update: chi nguoi tao ra record moi co quyen sua record do
        if (action.equalsIgnoreCase("update")) {
            if (!acc.getUsername().equals(student.getCreatedBy())) {
                model.addAttribute("errorMessage", "You are not allowed to edit this record!");
                hasError = true;
            }
        }
        //end validation
        if (hasError) {
            loadData(model, acc, page, size, editmode);
            return "students";
        }
        Student st = null;
        switch (action.toLowerCase()) {
            case "add" -> st = studentService.addStudent(student, acc.getUsername());
            case "update" -> st = studentService.updateStudent(student);
        }
        boolean stt = st != null;
        model.addAttribute(stt ? "successMessage" : "errorMessage",
                action.toUpperCase() + (stt ? " successfully" : " failed"));
        loadData(model, acc, page, size, false);

        //clear form
        model.addAttribute("studentForm", new Student());

        return "students";
    }

    @GetMapping("/edit/{id}")
    public String showEditForm(Model model,
                               HttpSession session,
                               @RequestParam(value = "pageNum", defaultValue = "0") int page,
                               @RequestParam(value = "pageSize", defaultValue = "5") int size,
                               @PathVariable("id") int id) {
        UserAccount acc = (UserAccount) session.getAttribute("user");
        loadData(model, acc, page, size, true);
        Student student = studentService.findById(id);
        model.addAttribute("studentForm", student);
        return "students";
    }


    @GetMapping("/delete/{id}")
    public String handleDelete(Model model,
                               @ModelAttribute("studentForm") Student stu,
                               HttpSession session,
                               @RequestParam(value = "pageNum", defaultValue = "0") int page,
                               @RequestParam(value = "pageSize", defaultValue = "5") int size,
                               @PathVariable("id") int id) {
        UserAccount acc = (UserAccount) session.getAttribute("user");

        Student student = studentService.findById(id);
        //validation
        if (!acc.getUsername().equals(student.getCreatedBy())) {
            model.addAttribute("errorMessage", "You are not allowed to delete this record!");
        } else {
            studentService.deleteById(id);
            model.addAttribute("successMessage", "Delete successfully!");
            loadData(model, acc, page, size, false);
        }
        return "students";
    }


    private boolean isDuplicateStudentId(String action, Student student) {
        switch (action.toLowerCase()) {
            case "add" -> {
                return studentService.existsByStudentId(student.getStudentId());
            }
            case "update" -> {
                return studentService.existsByStudentIdAndIdNot(student.getStudentId(), student.getId());
            }
            default -> {
                return false;
            }
        }
    }

    private void loadData(Model model, UserAccount loggedInUser, int page, int size, boolean editMode) {
        List<Department> departments = departmentService.findAll();
        model.addAttribute("departmentList", departments);
        model.addAttribute("loggedInUser", loggedInUser);
        if (loggedInUser.isManager()) {
            List<Student> topStudents = studentService.findTop5ByGpa();
            model.addAttribute("studentList", topStudents);
            model.addAttribute("isManager", true);
            model.addAttribute("totalStudents", topStudents.size());
        } else if (loggedInUser.isStaff()) {
            Pageable pageable = PageRequest.of(page, size);
            Page<Student> studentPage = studentService.findAll(pageable);
            model.addAttribute("studentList", studentPage.getContent());
            model.addAttribute("currentPage", page);
            model.addAttribute("totalPages", studentPage.getTotalPages());
            model.addAttribute("totalStudents", studentPage.getTotalElements());
            model.addAttribute("pageSize", size);
            model.addAttribute("isManager", false);
            model.addAttribute("editMode", editMode);
        }
    }

}