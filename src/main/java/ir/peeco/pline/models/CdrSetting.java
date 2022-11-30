package ir.peeco.pline.models;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;



@Data
@Table
@Entity(name = "Tbl_cdr_settings")
@AllArgsConstructor
@NoArgsConstructor(access = AccessLevel.PUBLIC, force = true)
public class CdrSetting {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;

    @Column(unique = true)
    private String serverName;

    @Column
    private String dbtype;

    @Column
    private String hostname;

    @Column
    private int port;

    @Column
    private String dbname;

    @Column
    private String dbpassword;

    @Column
    private String dbuser;

    @Column
    private String tableName;

    @Column
    private String encoding;

    @Column
    private long enable;

    @Column
    private String description;
}
