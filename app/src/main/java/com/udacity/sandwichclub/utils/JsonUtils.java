package com.udacity.sandwichclub.utils;

import com.udacity.sandwichclub.model.Sandwich;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

public class JsonUtils {

    public static Sandwich parseSandwichJson(String json) {

        Sandwich sandwich=new Sandwich();
        try {
            JSONObject jsonObject=new JSONObject(json);
            JSONObject name=jsonObject.getJSONObject("name");
            String mainName=name.getString("mainName");
            sandwich.setMainName(mainName);
            JSONArray alsoKnownAs=name.getJSONArray("alsoKnownAs");
            //StringBuilder sb=new StringBuilder();
            List<String> also_know_as_list=new ArrayList<>();
            for(int i=0;i< alsoKnownAs.length();i++)
            {
                String alsoKnownAsString=alsoKnownAs.getString(i);
                also_know_as_list.add(alsoKnownAsString);
            }
            sandwich.setAlsoKnownAs(also_know_as_list);
            String placeOfOrigin=jsonObject.getString("placeOfOrigin");
            sandwich.setPlaceOfOrigin(placeOfOrigin);
            String description=jsonObject.getString("description");
            sandwich.setDescription(description);
            String image=jsonObject.getString("image");
            sandwich.setImage(image);
            JSONArray ingredients=jsonObject.getJSONArray("ingredients");
            List<String> ingredients_list=new ArrayList<>();
            for(int j=0;j<ingredients.length();j++)
            {
                String ingredientsString=ingredients.getString(j);
                ingredients_list.add(ingredientsString);
            }
            sandwich.setIngredients(ingredients_list);
            //sandwich= new Sandwich(mainName,also_know_as_list,placeOfOrigin,description,image,ingredients_list);
        } catch (JSONException e) {
            e.printStackTrace();
        }
        return sandwich;
    }
}
