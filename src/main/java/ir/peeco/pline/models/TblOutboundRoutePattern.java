package ir.peeco.pline.models;

import lombok.Data;

import javax.persistence.*;

@Data
@Table
@Entity(name = "tbl_outbound_route_patterns")
public class TblOutboundRoutePattern {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;

    @OneToOne
    private TblOutboundRoute outboundRoutes;

    @Column(nullable = false)
    private String pattern = "X.";

    @Column(nullable = false)
    private String prefixNum = "9";

    @Column(nullable = false)
    private int dropNumber = 1;

    @Column(nullable = false)
    private int sequential = 0;

    private boolean enable = true;
}
