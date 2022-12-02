package ir.peeco.pline.models;

import lombok.Data;

import javax.persistence.*;
import javax.validation.constraints.Min;
import javax.validation.constraints.NotNull;

@Data
@Table
@Entity(name = "tbl_sip_systems")
public class TblSipSystems {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    public long id;

    @NotNull(message = "'Compact Headers' cannot be null")
    @Column(nullable = false)
    public boolean compactHeaders = false;

    @NotNull(message = "'Disable TCP Switch' cannot be null")
    @Column(nullable = false)
    public boolean disableTcpSwitch = true;

    @Min(value = 0, message = "The 'Thread Pool Auto Increment' value must be greater than or equal to 0")
    @NotNull(message = "'Thread Pool Auto Increment' cannot be null")
    @Column(nullable = false)
    public int threadPoolAutoIncrement = 5;

    @Min(value = 0, message = "The 'Thread Pool Idle Timeout' value must be greater than or equal to 0")
    @NotNull(message = "'Thread Pool Idle Timeout' cannot be null")
    @Column(nullable = false)
    public int threadPoolIdleTimeout = 60;

    @Min(value = 0, message = "The 'Thread Pool Initial Size' value must be greater than or equal to 0")
    @NotNull(message = "'Thread Pool Initial Size' cannot be null")
    @Column(nullable = false)
    public int threadPoolInitialSize = 0;

    @Min(value = 0, message = "The 'Thread Pool Max Size' value must be greater than or equal to 0")
    @NotNull(message = "'Thread Pool Max Size' cannot be null")
    @Column(nullable = false)
    public int threadPoolMaxSize = 50;

    @Min(value = 0, message = "The 'Timer B' value must be greater than or equal to 0")
    @NotNull(message = "'Timer B' cannot be null")
    @Column(nullable = false)
    public long timerB = 32000;

    @Min(value = 0, message = "The 'Timer T1' value must be greater than or equal to 0")
    @NotNull(message = "'Timer T1' cannot be null")
    @Column(nullable = false)
    public int timerT1 = 500;

    @Column(nullable = false)
    public boolean followEarlyMediaFork = true;

    @Column(nullable = false)
    public boolean acceptMultipleSdpAnswers = true;

    @Column(nullable = false)
    public boolean disableRport = true;

}
