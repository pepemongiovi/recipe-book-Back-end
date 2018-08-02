package recipebook.user;

import io.swagger.annotations.Api;
import io.swagger.annotations.SecurityDefinition;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import io.swagger.annotations.ApiOperation;
import recipebook.ingredient.Ingredient;
import recipebook.responses.ResponseConflict;
import recipebook.responses.ResponseCreated;
import recipebook.exceptions.InvalidAttributeException;
import javax.xml.ws.Response;
import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping(value = "/user")
@CrossOrigin(origins = "*", maxAge = 3600)
@Api(value = "User controller", description="Has all the endpoints related to user")
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

    @DeleteMapping("/{id}")
    @ApiOperation(value = "Delete a user by a given id")
    public ResponseEntity delete(@RequestHeader("Authorization") String token, @PathVariable("id") Long id){
        return new ResponseEntity(userService.removeById(id), HttpStatus.OK);
    }

    @GetMapping("{email}")
    @ApiOperation(value = "Get a user by a given email")
    public ResponseEntity<User> getUserByEmail(@PathVariable String email) {
        User user = userService.findByEmail(email);

        if(user!=null) return ResponseEntity.ok().body(user);
        else return ResponseEntity.notFound().build();
    }

    private boolean emailExist(String email) {
        User user = this.userService.findByEmail(email);

        return user != null;
    }
}