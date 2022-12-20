package ir.peeco.pline.models;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;
import lombok.Data;

@Data
@Entity(name = "tbl_sip_parameters")
@Table
public class TblSipParameter {

  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  private Long id;

  @Column(name = "param_group")
  private String group;

  @Column
  private String name;

  @Column(name = "value_type")
  private String type;

  @Column(name = "value_options", length = 1024)
  private String options;

  @Column(name = "default_value")
  private String defaultValue;

  @Column(length = 255)
  private String description;

}
