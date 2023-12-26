package uz.pdp.security.user;

import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import uz.pdp.security.demo.AdminDto;

import java.security.Principal;
import java.util.List;
import java.util.Objects;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class UserService {

    private final PasswordEncoder passwordEncoder;
    private final UserRepository repository;

    public void changePassword(ChangePasswordRequest request, Principal connectedUser) {

        var user = (User) ((UsernamePasswordAuthenticationToken) connectedUser).getPrincipal();

        // check if the current password is correct
        if (!passwordEncoder.matches(request.getCurrentPassword(), user.getPassword())) {
            throw new IllegalStateException("Wrong password");
        }
        // check if the two new passwords are the same
        if (!request.getNewPassword().equals(request.getConfirmationPassword())) {
            throw new IllegalStateException("Password are not the same");
        }

        // update the password
        user.setPassword(passwordEncoder.encode(request.getNewPassword()));

        // save the new password
        repository.save(user);
    }

    public ApiResponse getAll(String role) {

        List<User> adminList = repository.findAllByRoleEquals(Role.valueOf(role));
        return new ApiResponse("All Admins",true,adminList);

    }

    public ApiResponse save(Integer id) {
        if (Objects.isNull(id)) return new ApiResponse("Bad Request",false, HttpStatus.BAD_REQUEST);
        Optional<User> optionalUser = repository.findById(id);
        if (optionalUser.isPresent()) {
            User user = optionalUser.get();
            user.setRole(Role.ADMIN);
            User saved = repository.save(user);
            return new ApiResponse("New Admin Succes", true, saved);
        } else {
            return new ApiResponse("Not Found", false, HttpStatus.NOT_FOUND);
        }
    }

    public ApiResponse update(Integer id, AdminDto adminDto) {
        if (Objects.isNull(id)) return new ApiResponse("Bad Request",false, HttpStatus.BAD_REQUEST);
        Optional<User> optionalUser = repository.findById(id);
        if (optionalUser.isEmpty()) return new ApiResponse("Not Found",false,HttpStatus.NOT_FOUND);
        User user = optionalUser.get();
        user.setFirstname(adminDto.getFirstname());
        user.setLastname(adminDto.getLastname());
        user.setEmail(adminDto.getEmail());
        user.setRole(Role.ADMIN);
        User update = repository.save(user);
        return new ApiResponse("Admin Update",true,update);

    }

    public ApiResponse delete(Integer id) {
        if (Objects.isNull(id)) return new ApiResponse("Bad Request",false, HttpStatus.BAD_REQUEST);
        Optional<User> optionalUser = repository.findById(id);
        if (optionalUser.isEmpty()) return new ApiResponse("Not Found",false,HttpStatus.NOT_FOUND);
        User user = optionalUser.get();
        user.setRole(Role.USER);
        User delete = repository.save(user);
        return new ApiResponse("Admin Delete",true,delete);
    }
}
