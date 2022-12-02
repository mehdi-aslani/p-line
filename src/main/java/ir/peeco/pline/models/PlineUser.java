package ir.peeco.pline.models;

import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;

@Data
@Entity(name = "tbl_users")
@Table(indexes = {
        @Index(name = "tbl_uers_name_index", columnList = "username", unique = true),
        @Index(name = "tbl_uers_token_index", columnList = "token", unique = true)
})
@AllArgsConstructor
@NoArgsConstructor(access = AccessLevel.PUBLIC, force = true)
public class PlineUser {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(unique = false, nullable = false, length = 32)
    private String fullname;

    @Column(unique = true, nullable = false, length = 32)
    private String username;

    @Column(nullable = false)
    private String password;

    private String token;

    private boolean enable = true;

    private String[] roles;

}
