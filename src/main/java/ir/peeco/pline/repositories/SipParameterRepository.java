package ir.peeco.pline.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import ir.peeco.pline.models.TblSipParameter;

@Repository
public interface SipParameterRepository extends JpaRepository<TblSipParameter, Long> {

}
