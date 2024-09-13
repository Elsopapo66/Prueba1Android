package com.example.prueba;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import androidx.appcompat.app.AppCompatActivity;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);  // Cargamos el archivo XML de layout que define la interfaz de esta actividad

        // Botón para ir a la pantalla de validación de RUT
        Button validarRutButton = findViewById(R.id.validarRutButton);  // Encontramos el botón en el layout por su ID
        validarRutButton.setOnClickListener(new View.OnClickListener() {  // Configuramos una acción que se ejecutará cuando el usuario haga clic en este botón
            @Override
            public void onClick(View v) {
                // Creamos un Intent que iniciará la actividad ValidarRUTActivity
                Intent intent = new Intent(MainActivity.this, ValidarRUTActivity.class);
                startActivity(intent);  // Iniciamos la nueva actividad
            }
        });

        // Botón para ir a la pantalla de cálculo del IMC
        Button calcularIMCButton = findViewById(R.id.calcularIMCButton);  // Encontramos el botón en el layout por su ID
        calcularIMCButton.setOnClickListener(new View.OnClickListener() {  // Configuramos una acción que se ejecutará cuando el usuario haga clic en este botón
            @Override
            public void onClick(View v) {
                // Creamos un Intent que iniciará la actividad CalcularIMCActivity
                Intent intent = new Intent(MainActivity.this, CalcularIMCActivity.class);
                startActivity(intent);  // Iniciamos la nueva actividad
            }
        });
    }
}
