package ir.peeco.pline.repositories;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import ir.peeco.pline.models.TblSipTrunk;

@Repository
public interface SipTrunkRepository extends JpaRepository<TblSipTrunk, Long> {

        // Name Username Realm Proxy
        @Query(value = "SELECT t.* FROM #{#entityName} t WHERE t.name LIKE %:name% AND t.username LIKE %:username% " +
                        "AND t.username LIKE %:username%  AND t.realm LIKE %:realm% AND t.proxy LIKE %:proxy%", nativeQuery = true)
        Page<TblSipTrunk> findAllBy(@Param("name") String name,
                        @Param("username") String username,
                        @Param("realm") String realm,
                        @Param("proxy") String proxy,
                        Pageable pageable);

        Long countAllByName(String name);

        TblSipTrunk findByName(String name);
}
