package nguyen.example.hsf302_1.config;


import nguyen.example.hsf302_1.entity.Department;
import nguyen.example.hsf302_1.entity.UserAccount;
import nguyen.example.hsf302_1.service.DepartmentService;
import nguyen.example.hsf302_1.service.UserAccountService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;

@Component
public class DataInitializer implements CommandLineRunner {

    @Autowired
    private UserAccountService userAccountService;
    @Autowired
    private DepartmentService departmentService;

    @Override
    public void run(String... args) throws Exception {

        if (!userAccountService.existsByUsername("manager")) {
            UserAccount acc0 = new UserAccount("manager", "manager", 1);
            UserAccount acc1 = new UserAccount("manager1", "manager1", 1);
            UserAccount acc2 = new UserAccount("manager2", "manager2", 1);
            userAccountService.save(acc0);
            userAccountService.save(acc1);
            userAccountService.save(acc2);
        }

        if (!userAccountService.existsByUsername("staff")) {
            UserAccount acc0 = new UserAccount("staff", "staff", 2);
            UserAccount acc1 = new UserAccount("staff1", "staff1", 2);
            UserAccount acc2 = new UserAccount("staff2", "staff2", 2);
            userAccountService.save(acc0);
            userAccountService.save(acc1);
            userAccountService.save(acc2);
        }

        if (!userAccountService.existsByUsername("guest")) {
            UserAccount acc = new UserAccount("guest", "guest", 3);
            userAccountService.save(acc);
        }

        if (departmentService.findAll().isEmpty()) {
            Department dep0 = new Department("Alpha");
            Department dep1 = new Department("Gamma");
            Department dep2 = new Department("Ultra");
            departmentService.save(dep0);
            departmentService.save(dep1);
            departmentService.save(dep2);
        }

    }
}
