package az.babazade.esocial.repositories;

import az.babazade.esocial.entities.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface UserRepository extends JpaRepository<User,Long> {

    List<User> findAllByActive(Integer active);

    Optional<User> findUserByIdAndActive(Long id, Integer active);

    User findUserByUsernameAndActive(String username,Integer active);
}
