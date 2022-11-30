package ir.peeco.pline.models;

import lombok.Data;

import javax.persistence.*;

@Data
@Table
@Entity
public class TblOutboundRoutesPattern {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    public long id;

    @OneToOne
    public TblOutboundRoute tblOutboundRoutes;

    @Column(nullable = false)
    public String pattern;

    @Column(nullable = false)
    public String prefixNum;

    @Column(nullable = false)
    public int dropNumber;

    @Column(nullable = false)
    public int sequential;

    public boolean enable;
}
