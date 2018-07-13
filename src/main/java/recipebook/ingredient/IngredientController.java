package recipebook.ingredient;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.*;
import recipebook.recipe.Recipe;
import recipebook.recipe.RecipeService;

import javax.validation.Valid;
import java.util.List;
import java.util.Optional;

@CrossOrigin(origins = "http://localhost:4200")
@RestController
@RequestMapping("/ingredients")
public class IngredientController {

    @Autowired
    IngredientService ingredientService;

    @Autowired
    RecipeService recipeService;

    @PreAuthorize("hasAnyRole('ADMIN')")
    @GetMapping
    public List<Ingredient> getIngredients() {
        return ingredientService.findAll();
    }

    @GetMapping("{id}")
    public ResponseEntity<Ingredient> getIngredientById(@PathVariable Long id) {
        Optional<Ingredient> ingredient = ingredientService.findById(id);

        if(ingredient.isPresent()) return ResponseEntity.ok().body(ingredient.get());
        else return ResponseEntity.notFound().build();
    }

    @PostMapping
    public Ingredient createIngredient(@Valid @RequestBody Ingredient ingredient) {
        if(ingredient.getRecipe()!=null){
            Optional<Recipe> recipe = recipeService.findById(ingredient.getRecipe().getId());
            if(recipe.isPresent()) recipeService.save(recipe.get());
        }
        return ingredientService.save(ingredient);
    }

    @DeleteMapping("{id}")
    public ResponseEntity<Ingredient> deleteIngredient(@PathVariable Long id) {
        Optional<Ingredient> ingredient = ingredientService.findById(id);

        if(!ingredient.isPresent()) return ResponseEntity.notFound().build();
        else{
            ingredientService.deleteById(id);
            return ResponseEntity.ok().body(ingredient.get());
        }
    }

    @PutMapping("{id}")
    public ResponseEntity<Ingredient> updateIngredient
            (@PathVariable Long id, @RequestBody Ingredient updatedIngredient) {
        Optional<Ingredient> ingredient = ingredientService.findById(id);
        updatedIngredient.setRecipe(ingredient.get().getRecipe());

        if(!ingredient.isPresent()) return ResponseEntity.notFound().build();
        else return ResponseEntity.ok().body(ingredientService.save(updatedIngredient));
    }
}
