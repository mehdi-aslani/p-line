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
    private long id;

    @Min(value = 0, message = "The 'Contact Expiration Check Interval' value must be greater than or equal to 0")
    @NotNull(message = "'Contact Expiration Check Interval' cannot be null")
    @Column(nullable = false)
    private int contactExpirationCheckInterval = 30;

    @Column(nullable = false)
    private boolean debug = false;

    @NotEmpty(message = "'Default From User' cannot be Empty")
    @Column(nullable = false)
    private String defaultFromUser = "P-Line";

    @NotEmpty(message = "'Default From User' cannot be Empty")
    @Column(nullable = false)
    private String defaultOutboundEndpoint = "default_outbound_endpoint";

    @NotEmpty(message = "'Default Realm' cannot be Empty")
    @Column(nullable = false)
    private String defaultRealm = "p-line";

    @NotNull(message = "'Default Realm' cannot be null")
    @Column(nullable = false)
    private String defaultVoicemailExtension = "";

    @Column(nullable = false)
    private boolean disableMultiDomain = false;

    @NotEmpty(message = "'Endpoint Identifier Order' cannot be Empty")
    @Column(nullable = false)
    private String endpointIdentifierOrder = "ip,username,anonymous";

    @Column(nullable = false)
    private boolean ignoreUriUserOptions = false;

    @Min(value = 0, message = "The 'Keep Alive Interval' value must be greater than or equal to 0")
    @NotNull(message = "'Keep Alive Interval' cannot be null")
    @Column(nullable = false)
    private int keepAliveInterval = 0;

    @Min(value = 0, message = "The 'Max Forwards' value must be greater than or equal to 0")
    @NotNull(message = "'Max Forwards' cannot be null")
    @Column(nullable = false)
    private int maxForwards = 70;

    @Min(value = 0, message = "The 'Max Initial Qualify Time' value must be greater than or equal to 0")
    @NotNull(message = "'Max Initial Qualify Time' cannot be null")
    @Column(nullable = false)
    private int maxInitialQualifyTime = 0;

    @Column(nullable = false)
    private boolean mwiDisableInitialUnsolicited = false;

    @Column(nullable = false)
    private boolean noReferSub = true;

    @Min(value = 0, message = "The 'Mwi TPS Queue High' value must be greater than or equal to 0")
    @NotNull(message = "'Mwi TPS Queue High' cannot be null")
    @Column(nullable = false)
    private int mwiTpsQueueHigh = 500;

    // @Min(value = 0, message = "The 'Mwi TPS Queue High' value must be greater
    // than or equal to 0")
    @NotNull(message = "'Mwi TPS Queue Low' cannot be null")
    @Column(nullable = false)
    private int mwiTpsQueueLow = -1;

    @NotNull(message = "'Regcontext' cannot be null")
    @Column(nullable = false)
    private String regcontext = "";

    @Min(value = 0, message = "The 'Unidentified Request Count' value must be greater than or equal to 0")
    @NotNull(message = "'Unidentified Request Count' cannot be null")
    @Column(nullable = false)
    private int unidentifiedRequestCount = 5;

    @Min(value = 0, message = "The 'Unidentified Request Period' value must be greater than or equal to 0")
    @NotNull(message = "'Unidentified Request Period' cannot be null")
    @Column(nullable = false)
    private int unidentifiedRequestPeriod = 5;

    @Min(value = 0, message = "The 'Unidentified Request Prune Interval' value must be greater than or equal to 0")
    @NotNull(message = "'Unidentified Request Prune Interval' cannot be null")
    @Column(nullable = false)
    private int unidentifiedRequestPruneInterval = 30;

    @NotEmpty(message = "'User Agent' cannot be Empty")
    @Column(nullable = false)
    private String userAgent = "p-line Ver 3.0.1";

    @Column(nullable = false)
    private boolean sendContactStatusOnUpdateRegistration = false;

    @NotEmpty(message = "'Taskprocessor Overload Trigger' cannot be Empty")
    @Column(nullable = false)
    private String taskProcessorOverloadTrigger = "global";

    @Column(nullable = false)
    private boolean useCalleridContact = false;

    // @Min(value = 0, message = "The 'Unidentified request count' value must be
    // greater than or equal to 0")
    // @NotNull(message = "'Unidentified request count' cannot be null")
    // @Column(nullable = false)
    // private int unidentifiedRequestCount = 5;

}
