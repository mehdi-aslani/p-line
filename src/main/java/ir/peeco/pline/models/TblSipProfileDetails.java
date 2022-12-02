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

    @Column(name = "_type", nullable = false)
    public String type;

    @Column(name = "_key", nullable = false)
    public String key;

    @Column(name = "_value", nullable = false)
    public String value;

}
