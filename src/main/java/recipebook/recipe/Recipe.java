package recipebook.recipe;

import com.fasterxml.jackson.annotation.*;
import recipebook.ingredient.Ingredient;

import javax.persistence.*;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import java.io.Serializable;
import java.util.List;

@Entity
@JsonIdentityInfo(generator = ObjectIdGenerators.PropertyGenerator.class, property = "id")
public class Recipe {

    @Id
    @Column(name="recipe_id")
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;

    @NotNull
    @Column(name = "name")
    public String name;

    @NotNull
    @Column(name = "description")
    public String description;

    @Column(name = "image_path")
    public String imgPath;

    @NotNull
    @Column(name = "ingredients")
    @OneToMany(fetch=FetchType.LAZY, cascade = CascadeType.ALL, mappedBy="recipe")
    public List<Ingredient> ingredients;

    public Recipe(String name, String description, String imgPath, List<Ingredient> ingredients) {
        this.name = name;
        this.description = description;
        this.imgPath = imgPath;
        this.ingredients = ingredients;
    }

    public Recipe() {}

    public Long getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getImgPath() {
        return imgPath;
    }

    public void setImgPath(String imgPath) {
        this.imgPath = imgPath;
    }

    public List<Ingredient> getIngredients() {
        return ingredients;
    }

    public void setIngredients(List<Ingredient> ingredients) {
        this.ingredients = ingredients;
    }

    public void setId(Long id) {
        this.id = id;
    }
}
