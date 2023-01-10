package ir.peeco.pline.controllers.outboundRoute;

import java.util.Map;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import ir.peeco.pline.models.TblOutboundRoute;
import ir.peeco.pline.repositories.outboundRoutes.OutboundRouteRepository;
import ir.peeco.pline.repositories.outboundRoutes.OutboundRouteRepositoryInterface;

@RestController
@CrossOrigin(value = "*", maxAge = 3600)
@RequestMapping("/outbound-routes")
public class OutboundRoutesController {

  private final OutboundRouteRepository outboundRouteRepository;
  private final OutboundRouteRepositoryInterface routeRepositoryInterface;

  public OutboundRoutesController(OutboundRouteRepository outboundRouteRepository,
      OutboundRouteRepositoryInterface routeRepositoryInterface) {

    this.outboundRouteRepository = outboundRouteRepository;
    this.routeRepositoryInterface = routeRepositoryInterface;
  }

  /**
   * @param params
   * @return
   */
  @GetMapping("/index")
  public ResponseEntity<?> index(@RequestParam(required = false) Map<String, String> params) {

    int page = 0;
    if (params.get("page") != null) {
      page = Integer.parseInt(params.get("page"));
      params.remove("page");
    }

    int size = 10;
    if (params.get("size") != null) {
      size = Integer.parseInt(params.get("size"));
    }

    Sort sort = Sort.unsorted();
    if (params.get("sort") != null) {
      if (params.get("sort").startsWith("-"))
        sort = Sort.by(params.get("sort").substring(1)).descending();
      else
        sort = Sort.by(params.get("sort")).ascending();
    }
    Pageable pageable = PageRequest.of(page, size, sort);

    var table = routeRepositoryInterface.findAllByNameAndDesc(params.get("name"), params.get("description"),
        params.get("enable"), pageable);

    return ResponseEntity.ok(table);
  }

  @GetMapping("/get/{id}")
  public ResponseEntity<?> get(@PathVariable Long id) {
    var item = outboundRouteRepository.findById(id);
    if (item == null || item.isEmpty())
      return ResponseEntity.ok(new TblOutboundRoute());
    return ResponseEntity.ok(item);
  }

}
