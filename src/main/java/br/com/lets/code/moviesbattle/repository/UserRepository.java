package br.com.lets.code.moviesbattle.repository;

import br.com.lets.code.moviesbattle.model.User;
import org.springframework.data.jpa.repository.JpaRepository;

public interface UserRepository extends JpaRepository<User, String> {

    User findByUsername(String username);

}
