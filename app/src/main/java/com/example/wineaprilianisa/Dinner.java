package com.example.wineaprilianisa;

import android.content.Context;

import java.util.Random;

public class Dinner {
    public Random randomGen = new Random();
    String[] mealChoices;
    String foodPref;

    public Dinner() {
    }

    public Dinner(Context context, int item) {
        foodPref = getFoodPref(item);

        if(foodPref.equals("vegan")) {
            mealChoices = context.getResources().getStringArray(R.array.vegan_meals);
        } else if (foodPref.equals("vegetarian")) {
            mealChoices = Utility.combine(
                    context.getResources().getStringArray(R.array.vegan_meals),
                    context.getResources().getStringArray(R.array.vegetarian_meals));
        } else if (foodPref.equals("fish")) {
            mealChoices = context.getResources().getStringArray(R.array.meat_meals);
        } else {
            mealChoices = getAllDinners(context);
        }
    }

    public String getChoiceFromArray(String[] choices) {
        return choices[randomGen.nextInt(choices.length)];
    }

    public String getDinnerTonight() {
        String dinner = getChoiceFromArray(mealChoices);
        return dinner;
    }

    public String getFoodPref(int item) {
        String menuPref;
        switch(item) {
            case R.id.vegan_pref:
                menuPref = "vegan";
                break;
            case R.id.vegetarian_pref:
                menuPref = "vegetarian";
                break;
            case R.id.fish_pref:
                menuPref = "fish";
                break;
            case R.id.meat_pref:
                menuPref = "meat";
                break;
            default:
                menuPref = "unrestricted";
        }

        return menuPref;
    }

    public String [] getAllDinners(Context context) {
        mealChoices = Utility.combine(
                context.getResources().getStringArray(R.array.vegan_meals),
                context.getResources().getStringArray(R.array.vegetarian_meals),
                context.getResources().getStringArray(R.array.fish_meals),
                context.getResources().getStringArray(R.array.meat_meals)
        );
        return mealChoices;
    }
}
