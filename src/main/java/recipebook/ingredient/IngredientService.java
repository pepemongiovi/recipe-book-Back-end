package recipebook.ingredient;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import recipebook.ingredient.IngredientRepository;
import recipebook.ingredient.Ingredient;

import java.util.List;
import java.util.Optional;

@Service
public class IngredientService {

    @Autowired
    IngredientRepository ingredientRepository;

    @Transactional
    public List<Ingredient> findAll() {
        return ingredientRepository.findAll();
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
