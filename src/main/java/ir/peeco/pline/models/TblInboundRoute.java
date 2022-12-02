package ir.peeco.pline.models;

import lombok.Data;

import javax.persistence.*;

@Data
@Table
@Entity
public class TblInboundRoute {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    public long id;

    @Column(unique = true, nullable = false)
    public String name;

    public boolean publicRoute;

    public int sequential;

    @Column(length = 1024)
    public String description;

    public boolean enable;
}
