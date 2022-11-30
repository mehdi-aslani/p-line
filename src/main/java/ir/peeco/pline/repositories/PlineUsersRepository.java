package ir.peeco.pline.repositories;

import ir.peeco.pline.models.PlineUser;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface PlineUsersRepository extends CrudRepository<PlineUser, Long> {

  PlineUser findByUsername(String username);

}
