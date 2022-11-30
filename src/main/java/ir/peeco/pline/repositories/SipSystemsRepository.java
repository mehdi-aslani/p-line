package ir.peeco.pline.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import ir.peeco.pline.models.TblSipSystems;

@Repository
public interface SipSystemsRepository extends JpaRepository<TblSipSystems, Long> {

}
