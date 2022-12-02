package ir.peeco.pline.models;

import lombok.Data;
import javax.persistence.*;

@Data
@Table
@Entity
public class TblInboundRouteTrunk {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    public long id;

    @OneToOne
    public TblInboundRoute inboundRoute;

    @OneToOne
    public TblSipTrunk tblSipTrunk;

    @Column(nullable = false)
    public int sequential;

    public boolean enable;
}
