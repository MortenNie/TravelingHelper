package dat.user;

import com.fasterxml.jackson.annotation.JsonFormat;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.hibernate.annotations.NaturalId;
import org.mindrot.jbcrypt.BCrypt;

import java.io.Serial;
import java.io.Serializable;

@Entity
@NoArgsConstructor
@Getter
@Table(name = "helper_users")
public class User implements Serializable {


    @Serial
    private static final long serialVersionUID = 1L;

    @Id
    @Setter
    @Basic(optional = false)
    private String username;
    @Setter
    @Basic(optional = false)
    private String password;

    public User(String username, String password) {
        this.username = username;
        this.password = BCrypt.hashpw(password, BCrypt.gensalt());
    }


    public boolean verifyPassword(String pw) {
        return BCrypt.checkpw(pw, password);
    }

    public void setUserPassword(String userPassword) {
        this.password = BCrypt.hashpw(userPassword, BCrypt.gensalt());
    }
}
