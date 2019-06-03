package com.example.tutorial.resource;

import com.example.tutorial.model.User;
import com.example.tutorial.service.UserService;
import org.apache.catalina.valves.rewrite.Substitution;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.ws.rs.QueryParam;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

//@RestController
//@RequestMapping(path = "/api/v1/users")
public class UserResource {

    private UserService userService;

    @Autowired
    public UserResource(UserService userService) {
        this.userService = userService;
    }

    @RequestMapping(
            method = RequestMethod.GET,
            produces = MediaType.APPLICATION_JSON_VALUE
    )
    public List<User> fechtUsers(@QueryParam("gender") String gender) {
        System.out.println(gender);
        return userService.getAllUsers(Optional.ofNullable(gender));
    }

    @RequestMapping(
            method = RequestMethod.GET,
            produces = MediaType.APPLICATION_JSON_VALUE,
            path = "{userId}"
    )
    public ResponseEntity<?> fechtUser(@PathVariable("userId") UUID userId){
        Optional<User> userOptional = userService.getUser(userId);
        if (userOptional.isPresent()){
            return ResponseEntity.ok(userOptional.get());
        }
        return ResponseEntity.status(HttpStatus.NOT_FOUND)
                .body(new ErrorMessage("user " + userId + " was not found."));
    }


    @RequestMapping(
            method = RequestMethod.POST,
            consumes = MediaType.APPLICATION_JSON_VALUE,
            produces = MediaType.APPLICATION_JSON_VALUE
    )
    public ResponseEntity<Integer> insertNewUser (@RequestBody User user){
        int result = userService.insertUser(user);
        return getIntegerResponseEntity(result);

    }

    @RequestMapping(
            method = RequestMethod.PUT,
            consumes = MediaType.APPLICATION_JSON_VALUE,
            produces = MediaType.APPLICATION_JSON_VALUE
    )
    public ResponseEntity<Integer> updateUser (@RequestBody User user){
        int result = userService.updateUser(user);
        return getIntegerResponseEntity(result);

    }

    @RequestMapping(
            method = RequestMethod.DELETE,
            produces = MediaType.APPLICATION_JSON_VALUE,
            path = "{userId}"
    )

    public ResponseEntity<Integer> deleteUser(@PathVariable("userId") UUID userId){
        int result = userService.removeUser(userId);
        return getIntegerResponseEntity(result);
    }

    private ResponseEntity<Integer> getIntegerResponseEntity(int result) {
        if (result == 1){
            return ResponseEntity.ok().build();
        }
        return ResponseEntity.badRequest().build();
    }

    static class ErrorMessage {
        String errorMessage;
        public ErrorMessage(String message) {
            this.errorMessage = message;
        }
        public String getErrorMessage() {
            return errorMessage;
        }
        public void setErrorMessage(String errorMessage) {
            this.errorMessage = errorMessage;
        }
    }
}
