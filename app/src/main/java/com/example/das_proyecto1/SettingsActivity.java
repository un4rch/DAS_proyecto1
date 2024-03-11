package com.example.das_proyecto1;

import android.app.Activity;
import android.content.Context;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.Spinner;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

public class SettingsActivity extends AppCompatActivity {
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Utils.loadLanguage(SettingsActivity.this);
        Utils.loadTheme(this);
        setContentView(R.layout.activity_settings);

        // +---------+
        // | Toolbar |
        // +---------+
        // Referencia al Toolbar en el layout
        Toolbar toolbar = findViewById(R.id.toolbar);
        // Configurar el Toolbar como la barra de acción de la actividad
        Utils.configToolbar(this, toolbar);

        // +----------+
        // | Language |
        // +----------+
        Spinner langSpinner = findViewById(R.id.language_spinner);
        ArrayAdapter<CharSequence> langAdapter = ArrayAdapter.createFromResource(this,
                R.array.language_spinner_options, android.R.layout.simple_spinner_item);
        langAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        langSpinner.setAdapter(langAdapter);
        Button langButton = findViewById(R.id.language_button);
        langButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // Get the selected item from the spinner
                String selectedValue = langSpinner.getSelectedItem().toString();
                // Guardar el idioma seleccionado en las preferencias
                SharedPreferences sharedPreferences = getSharedPreferences("preferences", Context.MODE_PRIVATE);
                SharedPreferences.Editor editor = sharedPreferences.edit();
                editor.putString("lang", selectedValue);
                editor.apply();
                // Cargar el idioma
                //Utils.loadLanguage(SettingsActivity.this);
                // Reiniciar para cargar idioma
                finish();
                startActivity(getIntent());

                // Do something with the selected value
                //Toast.makeText(getApplicationContext(), "Selected value: " + selectedValue, Toast.LENGTH_SHORT).show();
            }
        });

        // Theme
        Spinner themeSpinner = findViewById(R.id.theme_spinner);
        ArrayAdapter<CharSequence> themeAdapter = ArrayAdapter.createFromResource(this,
                R.array.theme_spinner_options, android.R.layout.simple_spinner_item);
        themeAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        themeSpinner.setAdapter(themeAdapter);
        Button themeButton = findViewById(R.id.theme_button);
        themeButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // Get the selected item from the spinner
                String selectedValue = themeSpinner.getSelectedItem().toString();
                // Guardar el idioma seleccionado en las preferencias
                SharedPreferences sharedPreferences = getSharedPreferences("preferences", Context.MODE_PRIVATE);
                SharedPreferences.Editor editor = sharedPreferences.edit();
                editor.putString("theme", selectedValue);
                editor.apply();
                // Cargar el idioma
                //Utils.loadTheme(SettingsActivity.this);
                // Reiniciar para cargar el tema
                finish();
                startActivity(getIntent());

                // Do something with the selected value
                //Toast.makeText(getApplicationContext(), "Selected value: " + selectedValue, Toast.LENGTH_SHORT).show();
            }
        });
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Load menu.xml into toolbar
        getMenuInflater().inflate(R.menu.menu,menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        return Utils.toolbarItemClick(item, this) || super.onOptionsItemSelected(item);
    }
}
