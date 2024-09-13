package com.example.prueba;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import androidx.appcompat.app.AppCompatActivity;

public class CalcularIMCActivity extends AppCompatActivity {

    // Variables para los elementos de la interfaz gráfica
    private EditText pesoInput;  // Campo de texto para que el usuario ingrese su peso
    private EditText alturaInput;  // Campo de texto para que el usuario ingrese su altura
    private TextView resultadoIMCTextView;  // Texto donde se mostrará el resultado del IMC calculado
    private TextView pesoIdealTextView;  // Texto donde se mostrará el rango de peso ideal

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_calcular_imc);  // Establecemos el layout de la actividad

        // Vinculamos los elementos del layout con las variables del código
        pesoInput = findViewById(R.id.pesoInput);  // Campo de texto donde el usuario ingresa su peso
        alturaInput = findViewById(R.id.alturaInput);  // Campo de texto donde el usuario ingresa su altura
        resultadoIMCTextView = findViewById(R.id.resultadoIMCTextView);  // Donde se mostrará el resultado del IMC
        pesoIdealTextView = findViewById(R.id.pesoIdealTextView);  // Donde se mostrará el rango de peso ideal
        Button calcularButton = findViewById(R.id.calcularButton);  // Botón para iniciar el cálculo del IMC

        // Configuramos una acción para cuando el usuario haga clic en el botón de calcular IMC
        calcularButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // Obtenemos los valores de peso y altura ingresados como texto
                String pesoStr = pesoInput.getText().toString();
                String alturaStr = alturaInput.getText().toString();

                // Verificamos si ambos campos tienen valores ingresados
                if (pesoStr.isEmpty() || alturaStr.isEmpty()) {
                    resultadoIMCTextView.setText("Por favor, ingrese ambos valores.");  // Si alguno está vacío, mostramos un mensaje de error
                    pesoIdealTextView.setText("");  // Limpiamos el campo de peso ideal si no hay datos válidos
                    return;  // Detenemos la ejecución si los campos están vacíos
                }

                try {
                    // Convertimos los valores de texto a números de tipo double para realizar cálculos
                    double peso = Double.parseDouble(pesoStr);  // Convertimos el peso a número
                    double altura = Double.parseDouble(alturaStr);  // Convertimos la altura a número

                    // Verificamos que ambos valores sean mayores que 0 (no tiene sentido calcular con valores negativos o cero)
                    if (peso <= 0 || altura <= 0) {
                        resultadoIMCTextView.setText("Peso y altura deben ser mayores que 0.");
                        pesoIdealTextView.setText("");  // Limpiamos el campo de peso ideal si los valores no son válidos
                        return;
                    }

                    // Llamamos al método que calcula el IMC con los valores ingresados
                    double imc = calcularIMC(peso, altura);

                    // Mostramos el resultado del IMC y la clasificación correspondiente
                    resultadoIMCTextView.setText("IMC: " + String.format("%.2f", imc) + " - " + clasificarIMC(imc));

                    // Calculamos y mostramos el rango de peso ideal para la altura ingresada
                    String pesoIdeal = calcularPesoIdeal(altura);
                    pesoIdealTextView.setText(pesoIdeal);
                } catch (NumberFormatException e) {
                    // Si ocurre un error al convertir los valores a número (por ejemplo, si el usuario ingresa texto en lugar de números), mostramos un mensaje de error
                    resultadoIMCTextView.setText("Por favor, ingrese valores numéricos válidos.");
                    pesoIdealTextView.setText("");  // Limpiamos el campo de peso ideal si hay un error
                }
            }
        });
    }

    /**
     * Método que calcula el Índice de Masa Corporal (IMC) usando la fórmula estándar.
     * El IMC se calcula dividiendo el peso (en kilogramos) por la altura al cuadrado (en metros).
     *
     * @param peso El peso ingresado por el usuario en kilogramos.
     * @param altura La altura ingresada por el usuario en metros.
     * @return El valor del IMC calculado.
     */
    private double calcularIMC(double peso, double altura) {
        // Fórmula del IMC: peso (kg) dividido por la altura (en metros) al cuadrado
        return peso / (altura * altura);
    }

    /**
     * Método que clasifica el IMC de acuerdo a los valores estándar de la OMS.
     * Según el IMC calculado, clasificamos al usuario en bajo peso, peso normal, sobrepeso u obesidad.
     *
     * @param imc El valor del IMC calculado.
     * @return Una cadena de texto que describe la clasificación del IMC.
     */
    private String clasificarIMC(double imc) {
        // Clasificamos el IMC según los rangos definidos por la OMS
        if (imc < 18.5) {
            return "Bajo peso";  // IMC menor a 18.5
        } else if (imc >= 18.5 && imc < 25.0) {
            return "Peso normal";  // IMC entre 18.5 y 24.9
        } else if (imc >= 25.0 && imc < 30.0) {
            return "Sobrepeso";  // IMC entre 25.0 y 29.9
        } else {
            return "Obesidad";  // IMC mayor o igual a 30
        }
    }

    /**
     * Método que calcula el rango de peso ideal basado en la altura ingresada.
     * Calcula el peso ideal mínimo y máximo para que el IMC esté en el rango normal (18.5 a 24.9).
     *
     * @param altura La altura del usuario en metros.
     * @return Una cadena que muestra el rango de peso ideal para la altura ingresada.
     */
    private String calcularPesoIdeal(double altura) {
        // Peso ideal mínimo para un IMC de 18.5
        double pesoIdealMinimo = 18.5 * (altura * altura);

        // Peso ideal máximo para un IMC de 24.9
        double pesoIdealMaximo = 24.9 * (altura * altura);

        // Devolvemos una cadena que muestra el rango de peso ideal en kilogramos
        return String.format("Peso ideal: %.2f kg - %.2f kg", pesoIdealMinimo, pesoIdealMaximo);
    }
}
