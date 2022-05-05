package antifraud.controllers;

import antifraud.dto.UserAccessDTO;
import antifraud.dto.UserDTO;
import antifraud.dto.UserRoleDTO;
import antifraud.dto.UserStatusDTO;
import antifraud.services.UserService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;

@RestController
@Slf4j
public class AuthenticationController {

    private final UserService userService;

    @Autowired
    public AuthenticationController(UserService userService) {
        this.userService = userService;
    }

    @PostMapping("/api/auth/user")
    public ResponseEntity<UserDTO> createUser(@RequestBody @Valid UserDTO userDTO) {
        log.info("Incoming user " + userDTO);
        UserDTO savedUser = userService.saveUser(userDTO);
        return new ResponseEntity<>(savedUser, HttpStatus.CREATED);
    }

    @GetMapping("/api/auth/list")
    public ResponseEntity<List<UserDTO>> getAll() {
        return new ResponseEntity<>(userService.getAllUsers(), HttpStatus.OK);
    }

    @DeleteMapping("/api/auth/user/{username}")
    public ResponseEntity<UserStatusDTO> delete(@PathVariable String username) {
       userService.removeUserByUsername(username);
       return new ResponseEntity<>(new UserStatusDTO(username, "Deleted successfully!"), HttpStatus.OK);
    }

    @PutMapping("/api/auth/role")
    public ResponseEntity<UserDTO> update(@RequestBody @Valid UserRoleDTO userRoleDTO) {
        log.info("Incoming: {}", userRoleDTO);
        UserDTO updatedUser = userService.updateRoleByUsername(userRoleDTO);
        return new ResponseEntity<>(updatedUser, HttpStatus.OK);
    }

    @PutMapping("/api/auth/access")
    public ResponseEntity<UserStatusDTO> update(@RequestBody @Valid UserAccessDTO userAccessDTO) {
        log.info("Incoming user: " + userAccessDTO);
        String username = userAccessDTO.getUsername();
        UserStatusDTO userStatusDTO = userService.updateAccessByUsername(userAccessDTO)
                ? new UserStatusDTO.UserLocked(username)
                : new UserStatusDTO.UserUnlocked(username);
        return new ResponseEntity<>(userStatusDTO, HttpStatus.OK);
    }


}
