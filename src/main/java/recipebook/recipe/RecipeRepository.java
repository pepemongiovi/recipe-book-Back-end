package recipebook.recipe;

import org.springframework.data.repository.CrudRepository;
import org.springframework.web.bind.annotation.CrossOrigin;

import java.util.List;

@CrossOrigin(origins = "http://localhost:4200")
public interface RecipeRepository extends CrudRepository<Recipe, Long> {

    public List<Recipe> findAll();
}
