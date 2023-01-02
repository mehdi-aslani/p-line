package ir.peeco.pline.repositories;

import ir.peeco.pline.models.PlineUser;
import java.util.Optional;
import org.springframework.data.repository.CrudRepository;

public interface PlineUsersRepository extends CrudRepository<PlineUser, Long> {

  PlineUser findByUsername(String username);

  Optional<PlineUser> findByToken(String token);

}
