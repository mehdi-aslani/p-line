package ir.peeco.pline.models;

import lombok.Data;

import javax.persistence.*;

@Data
@Table
@Entity(name = "tbl_sip_users")
public class TblSipUser {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;

    @Column(nullable = false, unique = true, length = 20)
    private String uid = "";

    private TblSipUser[] parallels = null;

    private String acl = null;

    @Column(unique = true, length = 32)
    private String password = "";

    private String effectiveCallerIdNumber = "";

    private String effectiveCallerIdName = "";

    private String outboundCallerIdNumber = "";

    private String outboundCallerIdName = "";

    @OneToOne
    private TblSipProfile sipProfile = null;

    @OneToOne
    private TblSipUserGroup sipUserGroup = null;

    private boolean enable = true;

}
