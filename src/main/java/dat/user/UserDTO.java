package dat.user;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
public class UserDTO {

    private String username;

    public UserDTO(String username) {
        this.username = username;
    }

    public UserDTO(User user) {
        this.username = user.getUsername();
    }
}
