package nguyen.example.hsf302_1.repository;


import nguyen.example.hsf302_1.entity.UserAccount;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface UserAccountRepository extends JpaRepository<UserAccount, Integer> {

    Optional<UserAccount> findByUsernameAndPassword(String username, String password);

    Optional<UserAccount> findByUsername(String username);

    boolean existsByUsername(String username);
}
