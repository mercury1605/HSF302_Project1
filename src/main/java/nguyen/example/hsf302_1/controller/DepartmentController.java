package nguyen.example.hsf302_1.controller;

import jakarta.servlet.http.HttpSession;
import jakarta.validation.Valid;
import nguyen.example.hsf302_1.entity.Department;
import nguyen.example.hsf302_1.entity.UserAccount;
import nguyen.example.hsf302_1.service.DepartmentService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

@Controller
@RequestMapping("/departments")
public class DepartmentController {

    @Autowired
    private DepartmentService departmentService;

    @GetMapping("")
    public String showDepartmentPage(HttpSession session, Model model) {

        UserAccount loggedInUser = (UserAccount) session.getAttribute("user");

        if (loggedInUser == null) {
            return "redirect:/login";
        }

        if (!loggedInUser.isManager()) {
            model.addAttribute("message", "You are not authorized to access this resource!");
            return "error";
        }
        loadData(model, loggedInUser, false);
        return "departments";
    }


    //add & update
    @PostMapping("/action")
    public String handleAction(
            Model model,
            @RequestParam("action") String action,
            @Valid @ModelAttribute("departmentForm") Department department,
            BindingResult bindingResult,
            HttpSession session) {
        UserAccount loggedInUser = (UserAccount) session.getAttribute("user");
        boolean editMode = action.equalsIgnoreCase("update");
        //start validation
        boolean hasError = false;
        if (!loggedInUser.isManager()) {
            model.addAttribute("message", "You are not authorized to access this resource!");
            hasError = true;
        }


        if (bindingResult.hasErrors()) {
            hasError = true;
        }

        if (editMode) {
            if (departmentService.existsByNameAndIdNot(department.getDepartmentName(), department.getId())) {
                bindingResult.rejectValue("departmentName", "departmentName.duplicatename", "This department name existed!");
                hasError = true;
            }
        } else {
            if (departmentService.existsByName(department.getDepartmentName())) {
                bindingResult.rejectValue("departmentName", "departmentName.duplicatename", "This department name existed!");
                hasError = true;
            }
        }


        //end validation
        if (hasError) {
            loadData(model, loggedInUser, editMode);
            return "departments";
        }

        Department dep = departmentService.save(department);
        if (dep != null) {
            model.addAttribute("successMessage", action.toUpperCase() + " successfully!");
        } else {
            model.addAttribute("errorMessage", action.toUpperCase() + " failed:<");
        }
        loadData(model, loggedInUser, false);
        model.addAttribute("departmentForm", new Department());
        return "departments";
    }

    @GetMapping("/edit/{id}")
    public String showEditForm(
            @PathVariable("id") int id,
            HttpSession session,
            Model model) {
        UserAccount acc = (UserAccount) session.getAttribute("user");
        Department department = departmentService.findById(id);
        if (department != null) {
            model.addAttribute("departmentForm", department);
        }
        loadData(model, acc, true);
        return "departments";
    }

    @GetMapping("/delete/{id}")
    public String handleDelete(Model model,
                               HttpSession session,
                               @PathVariable("id") int id) {

        UserAccount acc = (UserAccount) session.getAttribute("user");
        departmentService.deleteById(id);
        model.addAttribute("successMessage", "DELETE successfully!");
        loadData(model, acc, false);
        return "departments";
    }


    private void loadData(Model model, UserAccount loggedInUser, boolean editMode) {
        model.addAttribute("departments", departmentService.findAll());
        model.addAttribute("loggedInUser", loggedInUser);

        if (!model.containsAttribute("departmentForm")) {
            model.addAttribute("departmentForm", new Department());
        }
        model.addAttribute("editMode", editMode);
    }

}