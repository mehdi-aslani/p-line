package ir.peeco.pline.models;

import lombok.Data;

import javax.persistence.*;

@Data
@Table
@Entity
public class TblOutboundRoute {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    public long id;

    @Column(unique = true)
    public String name;

    @Column(nullable = false)
    public boolean publicRoute;

    @Column(nullable = false)
    public int sequential;

    @Column(length = 1024)
    public String description;

    public boolean enable;

}
