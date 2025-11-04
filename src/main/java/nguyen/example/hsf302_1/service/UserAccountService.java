package nguyen.example.hsf302_1.service;

import nguyen.example.hsf302_1.entity.UserAccount;
import nguyen.example.hsf302_1.repository.UserAccountRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class UserAccountService {

    @Autowired
    private UserAccountRepository userAccountRepository;

    public UserAccount login(String username, String password) {
        return userAccountRepository.findByUsernameAndPassword(username, password).orElse(null);
    }

    public UserAccount findByUsername(String username) {
        Optional<UserAccount> userOpt = userAccountRepository.findByUsername(username);
        return userOpt.orElse(null);
    }

    public List<UserAccount> findAll() {
        return userAccountRepository.findAll();
    }

    public UserAccount save(UserAccount userAccount) {
        return userAccountRepository.save(userAccount);
    }

    public boolean existsByUsername(String username) {
        return userAccountRepository.existsByUsername(username);
    }

    public void deleteById(int id) {
        userAccountRepository.deleteById(id);
    }

    public UserAccount findById(int id) {
        Optional<UserAccount> userOpt = userAccountRepository.findById(id);
        return userOpt.orElse(null);
    }
}