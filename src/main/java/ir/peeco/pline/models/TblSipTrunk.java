package ir.peeco.pline.models;

import lombok.Data;

import javax.persistence.*;
import javax.validation.constraints.NotEmpty;

@Data
@Table
@Entity(name = "tbl_sip_trunks")
public class TblSipTrunk {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    public Long id;

    @Column(nullable = false, unique = true)
    @NotEmpty(message = "'Contact Expiration Check Interval' cannot be null")
    public String name = "";

    @Column(nullable = false)
    public String cidr = "";

    @Column(nullable = false)
    public String username = "";

    @Column(nullable = false)
    public String password = "";

    @Column(nullable = false)
    public String realm = "";

    @Column(nullable = false)
    public String fromUser = "";

    @Column(nullable = false)
    public String fromDomain = "";

    @Column(nullable = false)
    public String effectiveCallerIdName = "";

    @Column(nullable = false)
    public String effectiveCallerIdNumber = "";

    @Column(nullable = false)
    public String prefixCallerIdNumber = "";

    @Column(nullable = false)
    public String proxy = "";

    @Column(nullable = false)
    public boolean register = false;

    @Column(nullable = false)
    public int maxCalls = 30;

    @OneToOne
    public TblSipProfile tblSipProfile;

    @Column(nullable = false)
    public boolean enable = true;

    @Column(length = 1024)
    public String description = "";

}
