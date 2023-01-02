package ir.peeco.pline.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import ir.peeco.pline.models.TblSipSystems;

public interface SipSystemsRepository extends JpaRepository<TblSipSystems, Long> {

}
