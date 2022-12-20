package ir.peeco.pline.models;

import lombok.Data;

import javax.persistence.*;

@Data
@Table
@Entity(name = "tbl_sip_profile_details")
public class TblSipProfileDetails {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    public long id;

    @OneToOne
    @JoinColumn(name = "sip_profile_id")
    public TblSipProfile sipProfile;

    @Column(name = "sip_type", nullable = false)
    public String type;

    @Column(name = "sip_key", nullable = false)
    public String key;

    @Column(name = "sip_value", nullable = false)
    public String value;

}
