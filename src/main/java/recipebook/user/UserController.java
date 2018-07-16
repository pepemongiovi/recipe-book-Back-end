package recipebook.user;

import io.swagger.annotations.SecurityDefinition;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import io.swagger.annotations.ApiOperation;
import recipebook.responses.ResponseConflict;
import recipebook.responses.ResponseCreated;
import recipebook.exceptions.InvalidAttributeException;
import javax.xml.ws.Response;
import java.util.List;

@CrossOrigin(origins = "*", maxAge = 3600)
@RestController
@RequestMapping(value = "/user")
public class UserController {

    private UserService userService;

    @Autowired
    public UserController(UserService userService) {
        this.userService = userService;
    }

    @PostMapping
    @ApiOperation(value = "Create a new user")
    public ResponseEntity<Response> create(@RequestBody User user) {

        if (user.getName() == null) {
            throw new InvalidAttributeException("Bad Request, Missing parameter 'name'");
        }

        if (user.getPassword() == null) {
            throw new InvalidAttributeException("Bad Request, Missing parameter 'password'!");
        }

        if (user.getEmail() == null) {
            throw new InvalidAttributeException("Bad Request, Missing parameter 'email' or invalid email sent!");
        }

        if (emailExist(user.getEmail())) {
            return new ResponseEntity(new ResponseConflict(
                    String.format("There is an account with that email address: '%s'",
                            user.getEmail())), HttpStatus.CONFLICT);
        } else {
            this.userService.create(user);
            return new ResponseEntity(new ResponseCreated("User account created successfully", user), HttpStatus.CREATED);
        }
    }

    @RequestMapping(value = "/{id}", method = RequestMethod.DELETE)
    @ApiOperation(value = "Delete a user with a given id")
    public ResponseEntity delete(@RequestHeader("Authorization") String token, @PathVariable("id") Long id){
        return new ResponseEntity(userService.removeById(id), HttpStatus.OK);
    }

    @GetMapping
    @ApiOperation(value = "Return all users registered")
    public List<User> getUsers(@RequestHeader(value = "Authorization") String token) {
        return this.userService.getUsers();
    }

    private boolean emailExist(String email) {
        User user = this.userService.findByEmail(email);

        return user != null;
    }
}