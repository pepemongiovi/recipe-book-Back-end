package recipebook.recipe;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import javax.xml.ws.Response;
import java.util.List;
import java.util.Optional;

@CrossOrigin(origins = "http://localhost:4200")
@RequestMapping("/recipes")
@RestController
public class RecipeController {

    @Autowired
    RecipeService recipeService;

    @GetMapping
    public List<Recipe> getRecipes() {
        return recipeService.findAll();
    }

    @GetMapping("{id}")
    public ResponseEntity<Recipe> getRecipeById(@PathVariable Long id) {
        Optional<Recipe> recipe = recipeService.findById(id);

        if(recipe.isPresent()) return ResponseEntity.ok().body(recipe.get());
        else return ResponseEntity.notFound().build();
    }

    @PostMapping
    public Recipe createRecipe(@Valid @RequestBody Recipe recipe) {
        return recipeService.save(recipe);
    }

    @DeleteMapping("{id}")
    public ResponseEntity<Recipe> deleteRecipe(@PathVariable Long id) {
        Optional<Recipe> recipe = recipeService.findById(id);

        if(recipe.isPresent()){
            recipeService.deleteById(id);
            return ResponseEntity.ok().body(recipe.get());
        }
        else return ResponseEntity.notFound().build();
    }

    @PutMapping("{id}")
    public ResponseEntity<Recipe> updateRecipe(@PathVariable Long id, @RequestBody Recipe updatedRecipe) {
        Optional<Recipe> recipe = recipeService.findById(id);

        if(!recipe.isPresent()) return ResponseEntity.notFound().build();
        else return ResponseEntity.ok().body(recipeService.save(updatedRecipe));
    }

    @PutMapping("{id}/add-ingredients")
    public ResponseEntity<Recipe> addIngredientsToShoppingList(@PathVariable Long id) {
        Optional<Recipe> recipe = recipeService.findById(id);

        if(!recipe.isPresent()) return ResponseEntity.notFound().build();
        else{
            recipeService.setIngredientsVisible(recipe.get());
            return ResponseEntity.ok().build();
        }
    }
}
