package ir.peeco.pline.models;

import lombok.Data;

import javax.persistence.*;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.Size;

@Data
@Table
@Entity(name = "tbl_sip_profiles")
public class TblSipProfile {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    public Long id;

    @NotEmpty(message = "'Name' cannot be Empty")
    @Column(unique = true, nullable = false)
    public String name;

    @Column(length = 1024)
    @Size(min = 0, max = 1024, message = "'Description' length must be less 1024 characters")
    public String description;

}
