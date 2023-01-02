package ir.peeco.pline.repositories;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import ir.peeco.pline.models.TblSipProfile;

public interface SipProfilesRepository extends JpaRepository<TblSipProfile, Long> {

    @Query(value = "SELECT t.* FROM #{#entityName} t WHERE t.name LIKE %:name% AND t.description LIKE %:description%", nativeQuery = true)
    Page<TblSipProfile> findAllBy(@Param("name") String name, @Param("description") String description,
            Pageable pageable);

    boolean existsByName(String name);

    @Query(value = "SELECT t.* FROM #{#entityName} t WHERE t.id=:id", nativeQuery = true)
    TblSipProfile findSipProfileById(@Param("id") Long name);

}
