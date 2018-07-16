package recipebook.user;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;
import recipebook.util.Crud;

import java.util.List;

@Service
public class UserService implements Crud<User> {

    private UserRepository userRepository;
    private BCryptPasswordEncoder bCryptPasswordEncoder;

    @Autowired
    public UserService(UserRepository userRepository, BCryptPasswordEncoder bCryptPasswordEncoder) {
        this.userRepository = userRepository;
        this.bCryptPasswordEncoder = bCryptPasswordEncoder;
    }

    @Override
    public User create(Object t) {
        if(t instanceof User) {
            User user = new User();
            user.setEmail(((User) t).getEmail());
            user.setName(((User) t).getName());
            user.setPassword(bCryptPasswordEncoder.encode(((User) t).getPassword()));

            return userRepository.save(user);
        }
        return null;
    }

    public List<User> getUsers() {
        return userRepository.findAll();
    }

    @Override
    public User getById(Long id) {
        return null;
    }


    @Override
    public boolean removeById(Long id) {
        if(userRepository.findById(id) != null){
            userRepository.deleteById(id);
            return true;
        }

        return false;
    }

    @Override
    public void removeAll() {
        this.userRepository.deleteAll();
    }

    public User findByEmail(String email) {
        return userRepository.findByEmail(email);
    }

    public boolean removeByEmail(String email){
        User user = findByEmail(email);
        if(user != null){
            userRepository.delete(user);
            return true;
        }
        return false;
    }

    @Override
    public User update(User t1, User t2) {
        // TODO Auto-generated method stub
        return null;
    }
}

