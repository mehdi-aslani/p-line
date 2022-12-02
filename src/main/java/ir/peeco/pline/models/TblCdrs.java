package ir.peeco.pline.models;

import lombok.Data;

import javax.persistence.*;

@Data
@Table
@Entity
public class TblCdrs {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    public long id;

    public java.sql.Timestamp calldate;

    public String clid;

    public String src;

    public String dst;

    public String dcontext;

    public String channel;

    public String dstchannel;

    public String lastapp;

    public String lastdata;

    public long duration;

    public long billsec;

    public String disposition;

    public long amaflags;

    public String accountcode;

    public String uniqueid;

    public String peeraccount;

    public String linkedid;

    public long sequence;

    public String callOnTrunk;

    public String realDst;

}
