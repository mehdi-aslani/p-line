package ir.peeco.pline.repositories.outboundRoutes;

import java.util.ArrayList;
import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Predicate;
import javax.persistence.criteria.Root;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Repository;

import ir.peeco.pline.models.TblOutboundRoute;

@Repository
public class OutboundRouteRepositoryInterface {
  private EntityManager em;

  @Autowired
  public void setEm(EntityManager em) {
    this.em = em;
  }

  public List<TblOutboundRoute> findAllByNameAndDesc(String name, String desc, String enable, Pageable pageable) {
    CriteriaBuilder cb = em.getCriteriaBuilder();
    CriteriaQuery<TblOutboundRoute> cq = cb.createQuery(TblOutboundRoute.class);

    Root<TblOutboundRoute> route = cq.from(TblOutboundRoute.class);
    List<Predicate> predicates = new ArrayList<>();

    if (name != null) {
      predicates.add(cb.like(route.get("name"), "%" + name + "%"));
    }

    if (desc != null) {
      predicates.add(cb.like(route.get("title"), "%" + desc + "%"));
    }

    if (desc != null) {
      if (enable.toLowerCase().equals("true") || enable.toLowerCase().equals("1")) {
        predicates.add(cb.equal(route.get("enable"), true));
      } else {
        predicates.add(cb.equal(route.get("enable"), false));
      }
    }

    if (!predicates.isEmpty())
      cq.where(predicates.toArray(new Predicate[0]));

    return em.createQuery(cq).getResultList();
  }

}
