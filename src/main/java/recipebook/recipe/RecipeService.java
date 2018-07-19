package recipebook.recipe;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import recipebook.ingredient.Ingredient;
import recipebook.ingredient.IngredientRepository;
import recipebook.ingredient.IngredientService;
import recipebook.recipe.RecipeRepository;
import recipebook.recipe.Recipe;
import recipebook.user.User;
import recipebook.user.UserRepository;

import java.util.List;
import java.util.Optional;

@Service
public class RecipeService {

    @Autowired
    RecipeRepository recipeRepository;

    @Autowired
    UserRepository userRepository;

    @Autowired
    IngredientService ingredientService;

    @Transactional
    public void setIngredientsVisibility(Recipe recipe, boolean visibility) {
        for(int i = 0; i<recipe.ingredients.size(); i++){
            Ingredient ingredient = recipe.ingredients.get(i);
            ingredient.setVisibility(visibility);
            ingredientService.save(ingredient);
        }
    }

    @Transactional
    public List<Recipe> getRecipesByUserEmail(String email) {
        User user = userRepository.findByEmail(email);
        if(user!=null){
            return user.getRecipes();
        }
        else return null;
    }

    @Transactional(readOnly = true)
    public List<Recipe> findAll() {
        return recipeRepository.findAll();
    }

    @Transactional(readOnly = true)
    public Optional<Recipe> findById(Long id) {
        return recipeRepository.findById(id);
    }

    @Transactional
    public Recipe createRecipe(Recipe r) {
        Recipe recipe = save(r);

        for (int i =  0; i<recipe.ingredients.size(); i++) {
            Ingredient ingredient = recipe.ingredients.get(i);
            ingredient.setUser(recipe.user);
            ingredient.setRecipe(recipe);
            ingredientService.save(ingredient);
        }
        return recipe;
    }

    @Transactional
    public Recipe save(Recipe recipe) {
        return recipeRepository.save(recipe);
    }

    @Transactional
    public void deleteById(Long id) {
        recipeRepository.deleteById(id);
    }
}
