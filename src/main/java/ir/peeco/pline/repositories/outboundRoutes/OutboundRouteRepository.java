package ir.peeco.pline.repositories.outboundRoutes;

import java.util.List;

import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import ir.peeco.pline.models.TblOutboundRoute;

public interface OutboundRouteRepository extends JpaRepository<TblOutboundRoute, Long> {

  @Query(value = "SELECT t.* FROM #{#entityName} t WHERE " +
      "t.name LIKE %:name% AND t.sequential %:sequential% AND (:enable is null or t.enable = :name)", nativeQuery = true)
  List<TblOutboundRoute> findAllBy(@Param("name") String name,
      @Param("sequential") String sequential, @Param("enable") String enable, Pageable pageable);

}
