package uz.pdp.security.user;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;
import java.util.Optional;

public interface UserRepository extends JpaRepository<User, Integer> {

  Optional<User> findByEmail(String email);


//  @Query("select u from _user u where u.role='ADMIN'")
   List<User> findAllByRoleEquals(Role role);

}
