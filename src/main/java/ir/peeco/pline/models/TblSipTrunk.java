package ir.peeco.pline.models;

import lombok.Data;

import javax.persistence.*;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;

@Data
@Table
@Entity(name = "tbl_sip_trunks")
public class TblSipTrunk {

    public enum SipTrunkMode {
        NoRegister(0),
        Registrable(1),
        register(2);

        public final int mode;

        private SipTrunkMode(int mode) {
            this.mode = mode;
        }
    }

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false, unique = true)
    @NotEmpty(message = "name cannot be Empty")
    private String name = "";

    @Column(nullable = false, length = 1024)
    private String acl = "";

    @Column(nullable = false)
    private String username = "";

    @Column(nullable = false)
    private String password = "";

    @Column(nullable = false)
    private String fromUser = "";

    @Column(nullable = false)
    private String fromDomain = "";

    @Column(nullable = false)
    private String callerIdName = "";

    @Column(nullable = false)
    private String callerIdNumber = "";

    @Column(nullable = false)
    private String proxy = "";

    @Column(nullable = false)
    private SipTrunkMode registerMode = SipTrunkMode.NoRegister;

    @Column(nullable = false)
    private int maxCalls = 30;

    @OneToOne
    @NotNull(message = "SIP Profile cannot be Empty")
    private TblSipProfile sipProfile;

    @Column(nullable = false)
    private boolean enable = true;

    @Column(length = 1024)
    private String description = "";

}
