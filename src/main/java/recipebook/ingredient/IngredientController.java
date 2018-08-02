package recipebook.ingredient;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.*;
import recipebook.recipe.Recipe;
import recipebook.recipe.RecipeService;
import recipebook.responses.Response;
import recipebook.user.UserService;

import javax.validation.Valid;
import java.util.List;
import java.util.Optional;

@RestController
@CrossOrigin(origins = "*")
@RequestMapping("/ingredients")
@Api(value = "Ingredient controller", description="Has all the endpoints related to ingredients")
public class IngredientController {

    @Autowired
    IngredientService ingredientService;

    @Autowired
    RecipeService recipeService;

    @GetMapping
    @ApiOperation(value = "Get all ingredients")
    public List<Ingredient> getIngredients() {
        return ingredientService.findAll();
    }

    @GetMapping("{id}")
    @ApiOperation(value = "Get a ingredient by a given id")
    public ResponseEntity<Ingredient> getIngredientById(@PathVariable Long id) {
        Optional<Ingredient> ingredient = ingredientService.findById(id);

        if(ingredient.isPresent()) return ResponseEntity.ok().body(ingredient.get());
        else return ResponseEntity.notFound().build();
    }

    @GetMapping("user/{email}")
    @ApiOperation(value = "Get all of a user's ingredients by a given user's email")
    public ResponseEntity<List<Ingredient>> getIngredientsByUserEmail(@PathVariable String email){
        List<Ingredient> ingredients = ingredientService.getIngredientsByUserEmail(email);

        if(ingredients==null) return ResponseEntity.notFound().build();
        else return ResponseEntity.ok().body(ingredients);
    }

    @PostMapping
    @ApiOperation(value = "Create a ingredient")
    public Ingredient createIngredient(@Valid @RequestBody Ingredient ingredient) {
        if(ingredient.getRecipe()!=null){
            Optional<Recipe> recipe = recipeService.findById(ingredient.getRecipe().getId());
            if(recipe.isPresent()) recipeService.save(recipe.get());
        }
        return ingredientService.save(ingredient);
    }

    @DeleteMapping("{id}")
    @ApiOperation(value = "Delete a ingredient by a given id")
    public ResponseEntity<Ingredient> deleteIngredient(@PathVariable Long id) {
        Optional<Ingredient> ingredient = ingredientService.findById(id);

        if(!ingredient.isPresent()) return ResponseEntity.notFound().build();
        else{
            ingredientService.deleteById(id);
            return ResponseEntity.ok().body(ingredient.get());
        }
    }

    @PutMapping("{id}")
    @ApiOperation(value = "Update a ingredient by a given id and a ingredient object")
    public ResponseEntity<Ingredient> updateIngredient
            (@PathVariable Long id, @RequestBody Ingredient updatedIngredient) {
        Optional<Ingredient> ingredient = ingredientService.findById(id);
        updatedIngredient.setRecipe(ingredient.get().getRecipe());

        if(!ingredient.isPresent()) return ResponseEntity.notFound().build();
        else return ResponseEntity.ok().body(ingredientService.save(updatedIngredient));
    }
}
