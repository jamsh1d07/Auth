package uz.pdp.security.demo;

import io.swagger.v3.oas.annotations.Hidden;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpEntity;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;
import uz.pdp.security.user.ApiResponse;
import uz.pdp.security.user.UserService;

@RequiredArgsConstructor
@RestController
@RequestMapping("/api/v1/admin")
@PreAuthorize("hasRole('ADMIN')")
public class AdminController {

    final UserService userService;

    @GetMapping
    @PreAuthorize("hasAuthority('admin:read')")
    public HttpEntity<?> get() {

        ApiResponse all = userService.getAll("ADMIN");
        return ResponseEntity.ok().body(all);
    }
    @PostMapping("/{id}")
    @PreAuthorize("hasAuthority('admin:create')")
    @Hidden
    public HttpEntity<?> post(@PathVariable(name = "id") Integer id) {
        ApiResponse save = userService.save(id);
        return ResponseEntity.ok().body(save);
    }
    @PutMapping( "/{id}")
    @PreAuthorize("hasAuthority('admin:update')")
    @Hidden
    public HttpEntity<?> put(@PathVariable Integer id, @RequestBody AdminDto adminDto) {
        ApiResponse update = userService.update(id,adminDto);
        return ResponseEntity.ok().body(update);
    }
    @DeleteMapping("/{id}")
    @PreAuthorize("hasAuthority('admin:delete')")
    @Hidden
    public HttpEntity<?> delete(@PathVariable Integer id) {
        ApiResponse delete = userService.delete(id);
        return ResponseEntity.ok().body(delete);
    }

}
