package ba.unsa.etf.ts.Therapy.controllers;

import ba.unsa.etf.ts.Therapy.dto.*;
import ba.unsa.etf.ts.Therapy.security.JwtTokenHelper;
import ba.unsa.etf.ts.Therapy.service.RoleService;
import ba.unsa.etf.ts.Therapy.service.UserService;
import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@AllArgsConstructor
@RequestMapping("/api/users")
public class UserController {

    private final PasswordEncoder passwordEncoder;
    private final UserService userService;
    private final RoleService roleService;
    private final JwtTokenHelper tokenHelper;

    // **Login Endpoint**
    @PostMapping("/login")
    public ResponseEntity<LoginResponseDto> login(@RequestBody LoginRequestDto loginRequestDto) {
        UserDto user = userService.getUserByEmail(loginRequestDto.getEmail());
        if (user != null && passwordEncoder.matches(loginRequestDto.getPassword(), user.getPassword())) {
            RoleDto userRole = roleService.getRoleById(user.getRoleId());
            String token = tokenHelper.generateToken(user, userRole);
            return ResponseEntity.ok(new LoginResponseDto(token));
        }
        return ResponseEntity.status(HttpStatus.UNAUTHORIZED).build();
    }

    // **Token Validacija Endpoint**
    @PostMapping("/validate-token")
    public ResponseEntity<Void> validateToken(
            @RequestHeader("Authorization") String authorizationHeader,
            @RequestBody ValidateTokenRequestDto validateTokenRequestDto) {

        String token = authorizationHeader.replace("Bearer ", "");
        boolean isValid = tokenHelper.validateTokenAndItsClaims(token, validateTokenRequestDto.getRoles());

        return isValid ? ResponseEntity.status(HttpStatus.ACCEPTED).build()
                : ResponseEntity.status(HttpStatus.UNAUTHORIZED).build();
    }

    // **Kreiranje Pacijenta**
    @PostMapping("/registerPatient")
    @ResponseStatus(HttpStatus.CREATED)
    public ResponseEntity<UserDto> createPatient(@RequestBody UserDto userDto, @RequestParam int age) {
        userDto.setPassword(passwordEncoder.encode(userDto.getPassword()));
        UserDto createdUserDto = userService.createPatient(userDto);
        return ResponseEntity.status(HttpStatus.CREATED).body(createdUserDto);
    }

    // **Kreiranje Psihologa**
    @PostMapping("/registerPsychologist")
    @ResponseStatus(HttpStatus.CREATED)
    public ResponseEntity<UserDto> createPsychologist(@RequestBody UserDto userDto) {
        userDto.setPassword(passwordEncoder.encode(userDto.getPassword()));
        UserDto createdUserDto = userService.createPsychologist(userDto);
        return ResponseEntity.status(HttpStatus.CREATED).body(createdUserDto);
    }

    // **Dobijanje Korisnika Po ID-u**
    @GetMapping("/{id}")
    public ResponseEntity<UserDto> getUserById(
            @PathVariable String id,
            @RequestHeader("Authorization") String authorizationHeader) {

        String token = authorizationHeader.replace("Bearer ", "");
        String userIdFromToken = tokenHelper.getUsernameFromToken(token);

        if (!id.equals(userIdFromToken)) {
            return ResponseEntity.status(HttpStatus.FORBIDDEN).build();  // Nije dozvoljeno
        }

        UserDto user = userService.getUserById(id);
        return ResponseEntity.ok(user);
    }

    // **Dobijanje Svih Korisnika**
    @GetMapping("/all")
    public ResponseEntity<List<UserDto>> getAllUsers() {
        List<UserDto> users = userService.getAllUsers();
        return ResponseEntity.ok(users);
    }

    // **Dobijanje Korisnika Po Emailu**
    @GetMapping("/user/{email}")
    public ResponseEntity<UserDto> getUserByEmail(
            @PathVariable String email,
            @RequestHeader("Authorization") String authorizationHeader) {

        String token = authorizationHeader.replace("Bearer ", "");
        String userEmailFromToken = tokenHelper.getClaimsFromToken(token).get("email", String.class);

        if (!email.equals(userEmailFromToken)) {
            return ResponseEntity.status(HttpStatus.FORBIDDEN).build();  // Nije dozvoljeno
        }

        UserDto user = userService.getUserByEmail(email);
        return ResponseEntity.ok(user);
    }

    // **Ažuriranje Korisnika Po Emailu**
    @PutMapping("/{email}")
    public ResponseEntity<UserDto> updateUserByEmail(
            @PathVariable String email, @RequestBody UserDto userDto,
            @RequestHeader("Authorization") String authorizationHeader) {

        String token = authorizationHeader.replace("Bearer ", "");
        String userEmailFromToken = tokenHelper.getClaimsFromToken(token).get("email", String.class);

        if (!email.equals(userEmailFromToken)) {
            return ResponseEntity.status(HttpStatus.FORBIDDEN).build();  // Nije dozvoljeno
        }

        UserDto updatedUserDto = userService.updateUser(userDto, email);
        return ResponseEntity.ok(updatedUserDto);
    }

    // **Brisanje Korisnika Po Emailu**
    @DeleteMapping("/delete")
    public ResponseEntity<Void> deleteUserByEmail(
            @RequestBody UserDto userDto,
            @RequestHeader("Authorization") String authorizationHeader) {

        String token = authorizationHeader.replace("Bearer ", "");
        String userEmailFromToken = tokenHelper.getClaimsFromToken(token).get("email", String.class);

        if (!userDto.getEmail().equals(userEmailFromToken)) {
            return ResponseEntity.status(HttpStatus.FORBIDDEN).build();  // Nije dozvoljeno
        }

        userService.deleteUser(userDto);
        return ResponseEntity.noContent().build();
    }

    // **Pretraga Korisnika Po Imeni**
    @GetMapping("/search")
    public ResponseEntity<List<UserDto>> searchUsersByName(
            @RequestParam String name) {

        List<UserDto> users = userService.searchUsersByName(name);
        return ResponseEntity.ok(users);
    }
}
