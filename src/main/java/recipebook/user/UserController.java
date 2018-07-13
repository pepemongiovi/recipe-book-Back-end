package recipebook.user;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import javax.validation.Valid;
import java.util.List;
import java.util.Optional;

@CrossOrigin(origins = "http://localhost:4200")
@RequestMapping("/users")
@RestController
public class UserController {

    @Autowired
    UserService userService;

    @GetMapping
    public List<User> getUsers() {
        return userService.findAll();
    }

    @GetMapping("{id}")
    public ResponseEntity<User> getUserById(@PathVariable Long id) {
        Optional<User> user = userService.findById(id);

        if(user.isPresent()) return ResponseEntity.ok().body(user.get());
        else return ResponseEntity.notFound().build();
    }

    @PostMapping
    public User createUser(@Valid @RequestBody User user) {
        return userService.save(user);
    }

    @DeleteMapping("{id}")
    public ResponseEntity<User> deleteUser(@PathVariable Long id) {
        Optional<User> user = userService.findById(id);

        if(user.isPresent()){
            userService.deleteById(id);
            return ResponseEntity.ok().body(user.get());
        }
        else return ResponseEntity.notFound().build();
    }

    @PutMapping("{id}")
    public ResponseEntity<User> updateUser(@PathVariable Long id, @RequestBody User updatedUser) {
        Optional<User> user = userService.findById(id);

        if(!user.isPresent()) return ResponseEntity.notFound().build();
        else return ResponseEntity.ok().body(userService.save(updatedUser));
    }
}
