package recipebook.recipe;

import com.fasterxml.jackson.annotation.*;
import recipebook.ingredient.Ingredient;
import recipebook.user.User;

import javax.persistence.*;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import java.io.Serializable;
import java.util.List;

@Entity
@JsonIdentityInfo(generator = ObjectIdGenerators.PropertyGenerator.class, property = "id")
@JsonIgnoreProperties({"hibernateLazyInitializer", "handler"})
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

    @NotNull
    @Column(name = "ingredients")
    @OneToMany(fetch=FetchType.LAZY, cascade = CascadeType.ALL, mappedBy="recipe")
    public List<Ingredient> ingredients;

    @JoinColumn(name="id")
    @ManyToOne(fetch=FetchType.LAZY)
    public User user;

    public Recipe(String name, String description, List<Ingredient> ingredients, User user) {
        this.name = name;
        this.description = description;
        this.ingredients = ingredients;
        this.user = user;
    }

    public Recipe() {}

    public User getUser() {
        return user;
    }

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
