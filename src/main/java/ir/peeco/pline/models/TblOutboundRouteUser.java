package ir.peeco.pline.models;

import lombok.Data;

import javax.persistence.*;

@Data
@Table
@Entity
public class TblOutboundRouteUser {

  @Id
  @GeneratedValue()
  public long id;

  @OneToOne
  public TblOutboundRoute tblOutboundRoute;
  
  @OneToOne
  public TblSipUser tblSipUser;

  @Column(nullable = false)
  public int duration;
  
  public long enable;


}
