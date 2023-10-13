package uz.pdp.security.demo;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import uz.pdp.security.user.Role;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class AdminDto {
    private String firstname;
    private String lastname;
    private String email;
    private String password;
    private Role role;
}
