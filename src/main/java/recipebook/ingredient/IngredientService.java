package recipebook.ingredient;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import recipebook.user.User;
import recipebook.user.UserRepository;

import java.util.List;
import java.util.Optional;

@Service
public class IngredientService {

    @Autowired
    IngredientRepository ingredientRepository;

    @Autowired
    UserRepository userRepository;

    @Transactional
    public List<Ingredient> findAll() {
        return ingredientRepository.findAll();
    }

    @Transactional
    public List<Ingredient> getIngredientsByUserEmail(String email){
        User user = userRepository.findByEmail(email);

        if(user!=null){
            return user.getShoppingList();
        }return null;
    }

    @Transactional
    public Optional<Ingredient> findById(Long id) {
        return ingredientRepository.findById(id);
    }

    @Transactional
    public Ingredient save(Ingredient ingredient) {
        return ingredientRepository.save(ingredient);
    }

    @Transactional
    public void deleteById(Long id) {
        ingredientRepository.deleteById(id);
    }
}
