package org.yde.ydeapp.exposition.application;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;
import org.yde.ydeapp.application.in.GetUserQuery;
import org.yde.ydeapp.application.in.ReferenceUserUseCase;
import org.yde.ydeapp.domain.application.RoleTypeEnum;
import org.yde.ydeapp.domain.application.User;

import javax.validation.Valid;
import javax.validation.constraints.NotNull;
import java.net.URI;
import java.util.List;
import java.util.Set;

@RestController
@RequestMapping("/api/V1")
public class UserResource {

    private static Logger logger = LoggerFactory.getLogger(UserResource.class);

    @Autowired
    ReferenceUserUseCase referenceUserUseCase;

    @Autowired
    GetUserQuery getUserQuery;

    @PostMapping("users")
    public ResponseEntity<Void> createUser(@Valid @RequestBody ReferenceUserUseCase.ReferenceUserCmd referenceUserCmd) {
        User user = referenceUserUseCase.referenceNewUser(referenceUserCmd);
        URI location = ServletUriComponentsBuilder
                .fromCurrentRequest()
                .path("/{userName}")
                .buildAndExpand(user.getUid())
                .toUri();

        return ResponseEntity.created(location).build();

    }

    @GetMapping(value = "users/{uid}", produces = {"application/json"})
    public ResponseEntity<User> getUserByUid(@Valid @NotNull @PathVariable("uid") final String uid) {
        User user = getUserQuery.getUserByUid(uid);
        return new ResponseEntity<>(user, HttpStatus.OK);
    }

    @GetMapping(value = "users", produces = {"application/json"})
    public ResponseEntity<List<User>> getAllUsers() {
        List<User> users = getUserQuery.getAllUsers();
        return new ResponseEntity<>(users, HttpStatus.OK);
    }

    @PutMapping("users/{uid}")
    public ResponseEntity<Void> updateUser(
            @Valid @RequestBody String password, Set<RoleTypeEnum> roles,
            @PathVariable("uid") final String uid) {
        ReferenceUserUseCase.ReferenceUserCmd referenceUserCmd = new ReferenceUserUseCase.ReferenceUserCmd(uid, password, roles);
        User user = referenceUserUseCase.updateExistingUser(referenceUserCmd, uid);
        URI location = ServletUriComponentsBuilder
                .fromCurrentRequest()
                .path("/{noteTitle}")
                .buildAndExpand(user.getUid())
                .toUri();
        return ResponseEntity.created(location).build();
    }

    @DeleteMapping("users/{uid}")
    public ResponseEntity<String> deleteUser(
            @PathVariable("uid") final String uid) {
        referenceUserUseCase.deleteUserByUid(uid);
        return new ResponseEntity<String>(uid, HttpStatus.OK);
    }


}
