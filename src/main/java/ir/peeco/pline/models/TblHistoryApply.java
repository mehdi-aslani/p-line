package ir.peeco.pline.models;

import lombok.Data;
import javax.persistence.*;

@Data
@Table
@Entity
public class TblHistoryApply {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    public long id;

    @Column(length = 1024)
    public String description;

    public String command;

    public String datetime;

    public int doApply;
}
