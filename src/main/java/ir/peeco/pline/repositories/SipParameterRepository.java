package ir.peeco.pline.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import ir.peeco.pline.models.TblSipParameter;

public interface SipParameterRepository extends JpaRepository<TblSipParameter, Long> {

}
