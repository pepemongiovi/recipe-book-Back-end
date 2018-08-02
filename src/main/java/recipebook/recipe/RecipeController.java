package recipebook.recipe;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import javax.xml.ws.Response;
import java.util.List;
import java.util.Optional;

@RestController
@CrossOrigin(origins = "*")
@RequestMapping("/recipes")
@Api(value = "Recipe controller", description="Has all the endpoints related to recipes")
public class RecipeController {

    @Autowired
    RecipeService recipeService;

    @GetMapping
    @ApiOperation(value = "Get all recipes")
    public List<Recipe> getRecipes() {
        return recipeService.findAll();
    }

    @GetMapping("{id}")
    @ApiOperation(value = "Get a recipe by a given id")
    public ResponseEntity<Recipe> getRecipeById(@PathVariable Long id) {
        Optional<Recipe> recipe = recipeService.findById(id);

        if(recipe.isPresent()) return ResponseEntity.ok().body(recipe.get());
        else return ResponseEntity.notFound().build();
    }

    @GetMapping("user/{email}")
    @ApiOperation(value = "Get all recipe's from a user by a given user's email")
    public ResponseEntity<List<Recipe>> getRecipesByUserEmail(@PathVariable String email) {
        List<Recipe> recipes = recipeService.getRecipesByUserEmail(email);
        if(recipes==null) return ResponseEntity.notFound().build();
        else return ResponseEntity.ok().body(recipes);
    }

    @PostMapping
    @ApiOperation(value = "Create a recipe")
    public Recipe createRecipe(@Valid @RequestBody Recipe recipe) {
        return recipeService.createRecipe(recipe);
    }

    @DeleteMapping("{id}")
    @ApiOperation(value = "Delete a recipe by a given id")
    public ResponseEntity<Recipe> deleteRecipe(@PathVariable Long id) {
        Optional<Recipe> recipe = recipeService.findById(id);

        if(recipe.isPresent()){
            recipeService.deleteById(id);
            return ResponseEntity.ok().body(recipe.get());
        }
        else return ResponseEntity.notFound().build();
    }

    @PutMapping("{id}")
    @ApiOperation(value = "Update a recipe by a given id and recipe object")
    public ResponseEntity<Recipe> updateRecipe(@PathVariable Long id, @RequestBody Recipe updatedRecipe) {
        Optional<Recipe> recipe = recipeService.findById(id);

        if(!recipe.isPresent()) return ResponseEntity.notFound().build();
        else return ResponseEntity.ok().body(recipeService.save(updatedRecipe));
    }

    @PutMapping("{id}/add-ingredients")
    @ApiOperation(value = "Add all recipe's ingredients to the shopping list")
    public ResponseEntity<Recipe> addIngredientsToShoppingList(@PathVariable Long id) {
        Optional<Recipe> recipe = recipeService.findById(id);

        if(!recipe.isPresent()) return ResponseEntity.notFound().build();
        else{
            recipeService.setIngredientsVisibility(recipe.get(), true);
            return ResponseEntity.ok().build();
        }
    }

    @PutMapping("{id}/remove-ingredients")
    @ApiOperation(value = "Remove all recipe's ingredients from the shopping list")
    public ResponseEntity<Recipe> removeIngredientsFromShoppingList(@PathVariable Long id) {
        Optional<Recipe> recipe = recipeService.findById(id);

        if(!recipe.isPresent()) return ResponseEntity.notFound().build();
        else{
            recipeService.setIngredientsVisibility(recipe.get(), false);
            return ResponseEntity.ok().build();
        }
    }
}
