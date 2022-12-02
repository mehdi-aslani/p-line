package ir.peeco.pline.models;

import lombok.Data;

import javax.persistence.*;

@Data
@Table
@Entity
public class TblOutboundRouteTrunk {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    public long id;

    @Column(nullable = false)
    public int sequential;

    @OneToOne
    public TblOutboundRoute tblOutboundRoute;

    @OneToOne
    public TblSipTrunk tblSipTrunk;

    public boolean enable;


}
