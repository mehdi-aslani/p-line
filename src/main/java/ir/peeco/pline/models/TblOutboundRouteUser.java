package ir.peeco.pline.models;

import lombok.Data;

import javax.persistence.*;

@Data
@Table
@Entity(name = "tbl_outbound_routeUsers")
public class TblOutboundRouteUser {

  @Id
  @GeneratedValue()
  private long id;

  @OneToOne
  private TblOutboundRoute outboundRoute;

  @OneToOne
  private TblSipUser sipUser;

  @Column(nullable = false)
  private int duration = 0;

  private boolean enable = true;

}
