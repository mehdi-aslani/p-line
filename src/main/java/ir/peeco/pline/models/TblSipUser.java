package ir.peeco.pline.models;

import lombok.Data;

import javax.persistence.*;

@Data
@Table
@Entity
public class TblSipUser {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    public long id;

    @Column(nullable = false, unique = true, length = 20)
    public String uid;

    public String parallel;

    public String acl;

    @Column(unique = true, length = 32)
    public String password;

    public String effectiveCallerIdNumber;

    public String effectiveCallerIdName;

    public String outboundCallerIdNumber;

    public String outboundCallerIdName;

    @OneToOne
    public TblSipProfile tblSipProfile;

    @OneToOne
    public TblSipUserGroup tblSipUserGroup;

    public boolean enable;

}
