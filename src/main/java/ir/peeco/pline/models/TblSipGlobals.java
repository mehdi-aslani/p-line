package ir.peeco.pline.models;

import lombok.Data;

import javax.persistence.*;
import javax.validation.constraints.Min;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;

@Data
@Table
@Entity(name = "tbl_Sip_globals")
public class TblSipGlobals {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    public long id;

    @Min(value = 0, message = "The 'Contact Expiration Check Interval' value must be greater than or equal to 0")
    @NotNull(message = "'Contact Expiration Check Interval' cannot be null")
    @Column(nullable = false)
    public int contactExpirationCheckInterval = 30;

    @Column(nullable = false)
    public boolean debug = false;

    @NotEmpty(message = "'Default From User' cannot be Empty")
    @Column(nullable = false)
    public String defaultFromUser = "P-Line";

    @NotEmpty(message = "'Default From User' cannot be Empty")
    @Column(nullable = false)
    public String defaultOutboundEndpoint = "default_outbound_endpoint";

    @NotEmpty(message = "'Default Realm' cannot be Empty")
    @Column(nullable = false)
    public String defaultRealm = "p-line";

    @NotNull(message = "'Default Realm' cannot be null")
    @Column(nullable = false)
    public String defaultVoicemailExtension = "";

    @Column(nullable = false)
    public boolean disableMultiDomain = false;

    @NotEmpty(message = "'Endpoint Identifier Order' cannot be Empty")
    @Column(nullable = false)
    public String endpointIdentifierOrder = "ip,username,anonymous";

    @Column(nullable = false)
    public boolean ignoreUriUserOptions = false;

    @Min(value = 0, message = "The 'Keep Alive Interval' value must be greater than or equal to 0")
    @NotNull(message = "'Keep Alive Interval' cannot be null")
    @Column(nullable = false)
    public int keepAliveInterval = 0;

    @Min(value = 0, message = "The 'Max Forwards' value must be greater than or equal to 0")
    @NotNull(message = "'Max Forwards' cannot be null")
    @Column(nullable = false)
    public int maxForwards = 70;

    @Min(value = 0, message = "The 'Max Initial Qualify Time' value must be greater than or equal to 0")
    @NotNull(message = "'Max Initial Qualify Time' cannot be null")
    @Column(nullable = false)
    public int maxInitialQualifyTime = 0;

    @Column(nullable = false)
    public boolean mwiDisableInitialUnsolicited = false;

    @Min(value = 0, message = "The 'Mwi TPS Queue High' value must be greater than or equal to 0")
    @NotNull(message = "'Mwi TPS Queue High' cannot be null")
    @Column(nullable = false)
    public int mwiTpsQueueHigh = 500;

    // @Min(value = 0, message = "The 'Mwi TPS Queue High' value must be greater
    // than or equal to 0")
    @NotNull(message = "'Mwi TPS Queue Low' cannot be null")
    @Column(nullable = false)
    public int mwiTpsQueueLow = -1;

    @NotNull(message = "'Regcontext' cannot be null")
    @Column(nullable = false)
    public String regcontext = "";

    @Min(value = 0, message = "The 'Unidentified Request Count' value must be greater than or equal to 0")
    @NotNull(message = "'Unidentified Request Count' cannot be null")
    @Column(nullable = false)
    public int unidentifiedRequestCount = 5;

    @Min(value = 0, message = "The 'Unidentified Request Period' value must be greater than or equal to 0")
    @NotNull(message = "'Unidentified Request Period' cannot be null")
    @Column(nullable = false)
    public int unidentifiedRequestPeriod = 5;

    @Min(value = 0, message = "The 'Unidentified Request Prune Interval' value must be greater than or equal to 0")
    @NotNull(message = "'Unidentified Request Prune Interval' cannot be null")
    @Column(nullable = false)
    public int unidentifiedRequestPruneInterval = 30;

    @NotEmpty(message = "'User Agent' cannot be Empty")
    @Column(nullable = false)
    public String userAgent = "P-Line Ver 3.0.1";

}
