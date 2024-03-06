package com.example.das_proyecto1;

import android.app.Activity;
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
        Utils.loadLanguage(this);

        Spinner spinner = findViewById(R.id.language_spinner);
        ArrayAdapter<CharSequence> adapter = ArrayAdapter.createFromResource(this,
                R.array.spinner_options_array, android.R.layout.simple_spinner_item);
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinner.setAdapter(adapter);
        Button button = findViewById(R.id.button);
        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // Get the selected item from the spinner
                String selectedValue = spinner.getSelectedItem().toString();
                Utils.changeLanguage(SettingsActivity.this, selectedValue);

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
