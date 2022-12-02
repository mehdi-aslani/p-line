package ir.peeco.pline.models;

import lombok.Data;

import javax.persistence.*;

@Data
@Table
@Entity
public class TblInboundRoutesPattern {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    public long id;

    @OneToOne
    public TblInboundRoute inboundRoute;

    @Column(nullable = false)
    public String prefixNum;

    @Column(nullable = false)
    public String prefix;

    @Column(nullable = false)
    public long dropNumber;

    @Column(nullable = false)
    public int sequential;

    public boolean enable;

}
