package ir.peeco.pline.models;

import lombok.Data;

import javax.persistence.*;

@Data
@Table
@Entity(name = "tbl_outbound_route_trunks")
public class TblOutboundRouteTrunk {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;

    @Column(nullable = false)
    private int sequential = 0;

    @OneToOne
    private TblOutboundRoute outboundRoute;

    @OneToOne
    private TblSipTrunk SipTrunk;

    private boolean enable = true;

}
