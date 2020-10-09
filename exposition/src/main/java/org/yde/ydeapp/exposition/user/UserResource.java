package org.yde.ydeapp.exposition.user;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.annotation.Secured;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;
import org.yde.ydeapp.application.in.user.GetUserQuery;
import org.yde.ydeapp.application.in.user.ReferenceUserUseCase;
import org.yde.ydeapp.application.in.user.ReferenceUserUseCase.ReferenceUserCmd;
import org.yde.ydeapp.domain.user.RoleTypeEnum;
import org.yde.ydeapp.domain.user.User;

import javax.validation.Valid;
import javax.validation.constraints.NotNull;
import java.net.URI;
import java.util.List;

@RestController
@RequestMapping("/api/V1")
public class UserResource {

    private static Logger log = LoggerFactory.getLogger(UserResource.class);

    @Autowired
    ReferenceUserUseCase referenceUserUseCase;

    @Autowired
    GetUserQuery getUserQuery;

    @Autowired
    PasswordEncoder passwordEncoder;

    @PostMapping("users")
    @Secured("ROLE_ADMIN")
    public ResponseEntity<Void> createUser(@Valid @RequestBody ReferenceUserCmd referenceUserCmd) {
        ReferenceUserCmd cyptedReferenceUserCmd = new ReferenceUserCmd(referenceUserCmd.getUid(),
            passwordEncoder.encode(referenceUserCmd.getPassword()),
            referenceUserCmd.getRoles());
        User user = referenceUserUseCase.referenceNewUser(cyptedReferenceUserCmd);

        URI location = ServletUriComponentsBuilder
                .fromCurrentRequest()
                .path("/{userUid}")
                .buildAndExpand(user.getUid())
                .toUri();

        log.error("Creation de {}", user.getUid());

        return ResponseEntity.created(location).build();

    }

    @GetMapping(value = "users/{uid}", produces = {"application/json"})
    @Secured("ROLE_ADMIN")
    public ResponseEntity<User> getUserByUid(@Valid @NotNull @PathVariable("uid") final String uid) {
        User user = getUserQuery.getUserByUid(uid);

        log.error("Get de {}", user.getUid());

        return new ResponseEntity<>(user, HttpStatus.OK);
    }

    @GetMapping(value = "users", produces = {"application/json"})
    @Secured("ROLE_ADMIN")
    public ResponseEntity<List<User>> getAllUsers() {
        List<User> users = getUserQuery.getAllUsers();

        log.error("Get de tois les Users");

        return new ResponseEntity<>(users, HttpStatus.OK);
    }

    @PutMapping("users/{uid}")
    @Secured("ROLE_ADMIN")
    public ResponseEntity<Void> updateUser(
            @Valid @RequestBody String password, List<RoleTypeEnum> roles,
            @PathVariable("uid") final String uid) {
        ReferenceUserUseCase.ReferenceUserCmd referenceUserCmd = new ReferenceUserUseCase.ReferenceUserCmd(uid, password, roles);
        User user = referenceUserUseCase.updateExistingUser(referenceUserCmd, uid);
        URI location = ServletUriComponentsBuilder
                .fromCurrentRequest()
                .path("/{userUid}")
                .buildAndExpand(user.getUid())
                .toUri();

        log.error("Put de {}", user.getUid());

        return ResponseEntity.created(location).build();
    }

    @DeleteMapping("users/{uid}")
    @Secured("ROLE_ADMIN")
    public ResponseEntity<String> deleteUser(
            @PathVariable("uid") final String uid) {
        referenceUserUseCase.deleteUserByUid(uid);
        
        log.error("Delete de {}", uid);

        return new ResponseEntity<>(uid, HttpStatus.OK);
    }

}
