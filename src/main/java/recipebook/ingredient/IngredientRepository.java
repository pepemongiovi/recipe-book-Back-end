package recipebook.ingredient;

import org.springframework.data.repository.CrudRepository;
import org.springframework.web.bind.annotation.CrossOrigin;

import java.util.List;

@CrossOrigin(origins = "http://localhost:4200")
public interface IngredientRepository extends CrudRepository<Ingredient, Long> {

    public List<Ingredient> findAll();
}
