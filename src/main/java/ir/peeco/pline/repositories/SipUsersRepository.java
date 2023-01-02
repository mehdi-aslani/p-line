package ir.peeco.pline.repositories;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import ir.peeco.pline.models.TblSipUser;

public interface SipUsersRepository extends JpaRepository<TblSipUser, Long> {

  @Query(value = "SELECT t.* FROM #{#entityName} t WHERE t.enable=:enable order by id", nativeQuery = true)
  List<TblSipUser> findAllEnableProfiles(boolean enable);

}
