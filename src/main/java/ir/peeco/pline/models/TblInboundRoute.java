package ir.peeco.pline.models;

import lombok.Data;

import javax.persistence.*;

@Data
@Table
@Entity
public class TblInboundRoute {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private long id;

    @Column(unique = true, nullable = false)
    private String name = "";

    private boolean privateRoute = false;

    private int sequential = 0;

    @Column(length = 1024)
    private String description = "";

    private boolean enable = true;
}
