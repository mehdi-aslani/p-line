package ir.peeco.pline.repositories;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import ir.peeco.pline.models.TblSipProfileDetails;

@Repository
public interface SipProfileDetailsRepository extends JpaRepository<TblSipProfileDetails, Long> {

    @Transactional
    @Modifying(clearAutomatically = true)
    @Query(value = "DELETE FROM #{#entityName} t WHERE t.sip_profile_id=:sipProfileId", nativeQuery = true)
    void deleteBySipProfileId(@Param("sipProfileId") Long sipProfileId);

    @Query(value = "SELECT t.* FROM #{#entityName} t WHERE t.sip_profile_id=:id", nativeQuery = true)
    List<TblSipProfileDetails> getBySipProfileId(@Param("id") Long sipProfileId);

    @Query(value = "SELECT t.* FROM #{#entityName} t WHERE t.sip_profile_id=:id AND t.sip_type=:type", nativeQuery = true)
    List<TblSipProfileDetails> getBySipProfileIdAndType(@Param("id") Long sipProfileId, @Param("type") String type);

}
