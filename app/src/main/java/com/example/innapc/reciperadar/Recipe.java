package com.example.innapc.reciperadar;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

public class Recipe {

    //public String category;
    public String nameOf;
    public  String  ingredients;
    public String how;
    private DatabaseReference recipesDatabase; // connector between the app and the database

/**
    public Recipe(){
        recipesDatabase= FirebaseDatabase.getInstance().getReference().child("Recipes");
        category= String.valueOf(recipesDatabase.child(category.toString()));
        nameOf= String.valueOf(recipesDatabase.child(category.toString()).child(nameOf.toString()));
        ingredients= String.valueOf(recipesDatabase.child(category.toString()).child(nameOf.toString()).child(ingredients.toString()));
        how= String.valueOf(recipesDatabase.child(category.toString()).child(nameOf.toString()).child(how.toString()));

    }

    public Recipe( DatabaseReference a){
        category= String.valueOf(a.child(category.toString()));
        nameOf= String.valueOf(recipesDatabase.child(category.toString()).child(nameOf.toString()));
        ingredients= String.valueOf(recipesDatabase.child(category.toString()).child(nameOf.toString()).child(ingredients.toString()));
        how= String.valueOf(recipesDatabase.child(category.toString()).child(nameOf.toString()).child(how.toString()));

    }
 */
public Recipe(String nameOf, String ingredients, String how){
    this.nameOf=nameOf;
    this.ingredients=ingredients;
    this.how=how;
}

public String toString(){
        return ( nameOf+" Ingrediants: "+ingredients+ " What to do: "+how);
}

}
