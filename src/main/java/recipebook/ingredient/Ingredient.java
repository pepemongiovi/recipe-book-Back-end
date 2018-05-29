package recipebook.ingredient;

import com.fasterxml.jackson.annotation.*;
import recipebook.recipe.Recipe;

import javax.persistence.*;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import java.io.Serializable;


@Entity
public class Ingredient {

    @Id
    @Column(name = "ingredient_id")
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;

    @NotNull
    @Column(name = "name")
    public String name;

    @NotNull
    @Column(name = "amount")
    public int amount;

    @NotNull
    @Column(name = "visible")
    public boolean visible;

    @JoinColumn(name="recipe_id")
    @ManyToOne(optional = true, fetch=FetchType.LAZY)
    public Recipe recipe;

    public Ingredient(String name, int amount, boolean visible, Recipe recipe) {
        this.name = name;
        this.amount = amount;
        this.recipe = recipe;
        this.visible = visible;
    }

    public Ingredient() {}

    public Long getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public int getAmount() {
        return amount;
    }

    public void setAmount(int amount) {
        this.amount = amount;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Recipe getRecipe() {
        return recipe;
    }

    public void setRecipe(Recipe recipe) {
        this.recipe = recipe;
    }

    public boolean isVisible() {
        return visible;
    }

    public void setVisibility(boolean visible) {
        this.visible = visible;
    }
}
