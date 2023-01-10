package ir.peeco.pline.models;

import lombok.Data;

import javax.persistence.*;

@Data
@Table
@Entity
public class TblInboundRouteAction {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    public long id;

    @OneToOne
    public TblInboundRoute inboundRoute;

    @Column(nullable = false)
    public String action;

    @Column(nullable = false)
    public String value;

    @Column(nullable = false)
    public String options;

}
