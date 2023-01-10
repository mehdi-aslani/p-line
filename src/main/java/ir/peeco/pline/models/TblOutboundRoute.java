package ir.peeco.pline.models;

import lombok.Data;

import javax.persistence.*;

@Data
@Table
@Entity(name = "tbl_outbound_routes")
public class TblOutboundRoute {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;

    @Column(unique = true)
    private String name = "";

    @Column(nullable = false)
    private boolean privateRoute = false;

    @Column(nullable = false)
    private int sequential = 0;

    @Column(length = 1024)
    private String description = "";

    private boolean enable = true;

}
