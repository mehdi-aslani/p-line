package ir.peeco.pline.repositories;

import java.util.List;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import ir.peeco.pline.models.TblSipTrunk;

@Repository
public interface SipTrunksRepository extends JpaRepository<TblSipTrunk, Long> {

        // Name Username Realm Proxy
        @Query(value = "SELECT t.* FROM #{#entityName} t WHERE t.name LIKE %:name% AND t.username LIKE %:username% " +
                        "AND t.username LIKE %:username%  AND t.from_user LIKE %:fromUser% " +
                        "AND t.from_domain LIKE %:fromDomain% AND t.proxy LIKE %:proxy%", nativeQuery = true)
        Page<TblSipTrunk> findAllBy(@Param("name") String name,
                        @Param("username") String username,
                        @Param("fromUser") String fromUser,
                        @Param("fromDomain") String fromDomain,
                        @Param("proxy") String proxy,
                        Pageable pageable);

        Long countAllByName(String name);

        TblSipTrunk findByName(String name);

        @Query(value = "SELECT t.* FROM #{#entityName} t WHERE t.enable=:enable order by id", nativeQuery = true)
        List<TblSipTrunk> findAllEnableProfiles(@Param("enable") Boolean enable);

}
