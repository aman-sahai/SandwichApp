package com.udacity.sandwichclub;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.squareup.picasso.Picasso;
import com.udacity.sandwichclub.model.Sandwich;
import com.udacity.sandwichclub.utils.JsonUtils;

import java.util.List;

public class DetailActivity extends AppCompatActivity {

    public static final String EXTRA_POSITION = "extra_position";
    private static final int DEFAULT_POSITION = -1;
    TextView origin_tv, description_tv, ingredients_tv, also_known_tv;

    String json;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_detail);

        ImageView ingredientsIv = findViewById(R.id.image_iv);
        origin_tv = findViewById(R.id.origin_tv);
        description_tv = findViewById(R.id.description_tv);
        ingredients_tv = findViewById(R.id.ingredients_tv);
        also_known_tv = findViewById(R.id.also_known_tv);

        Intent intent = getIntent();
        if (intent == null) {
            closeOnError();
        }

        int position = intent.getIntExtra(EXTRA_POSITION, DEFAULT_POSITION);
        if (position == DEFAULT_POSITION) {
            // EXTRA_POSITION not found in intent
            closeOnError();
            return;
        }

        String[] sandwiches = getResources().getStringArray(R.array.sandwich_details);
        json = sandwiches[position];
        Sandwich sandwich = JsonUtils.parseSandwichJson(json);
        if (sandwich == null) {
            // Sandwich data unavailable
            closeOnError();
            return;
        }

        populateUI();
        Picasso.with(this)
                .load(sandwich.getImage())
                .into(ingredientsIv);
        if (ingredientsIv.getDrawable() == null) {
            ingredientsIv.setImageResource(R.mipmap.ic_launcher);
        }
        setTitle(sandwich.getMainName());
    }

    private void closeOnError() {
        finish();
        Toast.makeText(this, R.string.detail_error_message, Toast.LENGTH_SHORT).show();
    }

    private void populateUI() {
        Sandwich sandwich = JsonUtils.parseSandwichJson(json);

        List<String> a_k_s = sandwich.getAlsoKnownAs();
        StringBuilder sb = new StringBuilder();
        for (int i = 0; i < a_k_s.size(); i++) {
            sb.append(a_k_s.get(i) + "\n");
        }
        also_known_tv.setText(sb.toString());
        if (also_known_tv.getText().toString().isEmpty()) {
            also_known_tv.setText("Data not available."+"\n");
        }

        List<String> ingredients = sandwich.getIngredients();
        StringBuilder sb2 = new StringBuilder();
        for (int i = 0; i < ingredients.size(); i++) {
            sb2.append(ingredients.get(i) + "\n");
        }
        ingredients_tv.setText(sb2.toString());
        if (ingredients_tv.getText().toString().isEmpty()) {
            ingredients_tv.setText("Data not available."+"\n");
        }

        origin_tv.setText(sandwich.getPlaceOfOrigin());
        if (origin_tv.getText().toString().isEmpty()) {
            origin_tv.setText("Data not available.");
        }

        description_tv.setText(sandwich.getDescription());
        if (description_tv.getText().toString().isEmpty()) {
            description_tv.setText("Data not available.");
        }

    }
}
