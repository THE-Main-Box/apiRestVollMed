package med.voll.api.infra.repository;

import med.voll.api.domain.dto.user.UserDetailedDataDTO;
import med.voll.api.domain.model.user.User;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Repository;

@Repository
public interface UserRepository extends JpaRepository<User, Long> {

    @Query("SELECT u FROM User u WHERE u.login = :username")
    UserDetails findByLogin(@Param("username") String username);

    boolean existsByLogin(String login);

    @Query("SELECT u FROM User u")
    Page<User> findAllPaged(Pageable page);
}
