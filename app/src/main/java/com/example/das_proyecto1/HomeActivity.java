package com.example.das_proyecto1;

import android.app.Activity;
import android.os.Bundle;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

public class HomeActivity extends AppCompatActivity {
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home);

        // +---------+
        // | Toolbar |
        // +---------+
        // Referencia al Toolbar en el layout
        Toolbar toolbar = findViewById(R.id.toolbar);
        // Configurar el Toolbar como la barra de acción de la actividad
        setSupportActionBar(toolbar);
        // Ocultar el Titulo
        //getSupportActionBar().setDisplayShowTitleEnabled(false);
        getMenuInflater().inflate(R.menu.menu, toolbar.getMenu());
    }
}
