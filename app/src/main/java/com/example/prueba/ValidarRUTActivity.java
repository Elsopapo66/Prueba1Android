package com.example.prueba;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import androidx.appcompat.app.AppCompatActivity;

public class ValidarRUTActivity extends AppCompatActivity {

    // Declaramos los componentes que vamos a utilizar en el layout
    private EditText rutInput;  // Campo de texto donde el usuario ingresa el RUT
    private TextView resultadoTextView;  // Donde se mostrará el resultado de la validación (RUT válido o inválido)

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_validar_rut);  // Establecemos el layout de la actividad

        // Enlazamos los elementos de la interfaz gráfica (layout) con las variables declaradas en el código
        rutInput = findViewById(R.id.rutInput);  // Conectamos el EditText del layout para ingresar el RUT
        resultadoTextView = findViewById(R.id.resultadoTextView);  // Conectamos el TextView para mostrar el resultado
        Button validarButton = findViewById(R.id.validarButton);  // Botón que el usuario presiona para validar el RUT

        // Agregamos un evento (listener) que se ejecutará cuando el usuario presione el botón de validar
        validarButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // Obtenemos el RUT ingresado por el usuario en el campo de texto (EditText)
                String rut = rutInput.getText().toString().trim();  // Convertimos el valor ingresado a String y eliminamos espacios

                // Removemos los puntos del RUT en caso de que el usuario los haya ingresado
                rut = rut.replace(".", "");  // Quitamos los puntos si existen

                // Validar la longitud del RUT antes de proceder
                if (!esLongitudValida(rut)) {
                    resultadoTextView.setText("El RUT debe tener entre 8 y 10 caracteres.");
                    return;  // Si la longitud no es válida, detenemos la ejecución aquí
                }

                // Si la longitud es válida, seguimos validando el formato y el dígito verificador
                if (!esFormatoValido(rut)) {
                    // Si el formato no es válido, mostramos un mensaje de error
                    resultadoTextView.setText("Formato de RUT inválido.");
                    return;  // Detenemos la ejecución aquí si el formato es incorrecto
                }

                // Si el formato es correcto, validamos el dígito verificador
                if (esRUTValido(rut)) {
                    // Si el RUT es válido, mostramos el mensaje de éxito
                    resultadoTextView.setText("RUT válido.");
                } else {
                    // Si el dígito verificador no es válido, mostramos un mensaje de error
                    resultadoTextView.setText("RUT inválido.");
                }
            }
        });
    }

    /**
     * Método que verifica si el RUT tiene una longitud válida.
     * Un RUT chileno debe tener entre 8 y 10 caracteres (incluyendo el guion y el dígito verificador).
     *
     * @param rut El RUT ingresado por el usuario.
     * @return true si la longitud es válida (entre 8 y 10 caracteres), false si no lo es.
     */
    private boolean esLongitudValida(String rut) {
        // Verificamos si la longitud del RUT está entre 8 y 10 caracteres
        // Esto incluye la parte numérica del RUT más el guion y el dígito verificador
        return rut.length() >= 8 && rut.length() <= 10;
    }

    /**
     * Método que valida si el formato del RUT es correcto.
     * Este método se asegura de que el RUT esté compuesto por números, un guion, y un dígito verificador (que puede ser un número o la letra 'K').
     *
     * @param rut El RUT ingresado por el usuario.
     * @return true si el formato es válido, false si no lo es.
     */
    private boolean esFormatoValido(String rut) {
        // Primero, verificamos si el RUT contiene un guion ('-') y tiene una longitud válida
        if (!rut.contains("-") || rut.length() < 8 || rut.length() > 10) {
            // Si no tiene guion o la longitud está fuera de los límites, el formato no es válido
            return false;
        }

        // Dividimos el RUT en dos partes: la parte numérica (antes del guion) y el dígito verificador (después del guion)
        String[] partes = rut.split("-");
        if (partes.length != 2) {
            // Si no logramos dividir el RUT en exactamente dos partes, el formato no es válido
            return false;
        }

        // Verificamos que la parte numérica del RUT contenga solo números
        String rutNumerico = partes[0];
        if (!rutNumerico.matches("\\d+")) {
            // Si la parte numérica contiene algo que no sea dígitos, el formato no es válido
            return false;
        }

        // Verificamos que el dígito verificador sea un número o la letra 'K'
        String digitoVerificador = partes[1].toUpperCase();  // Convertimos el dígito a mayúscula por si el usuario ingresa una 'k'
        return digitoVerificador.matches("\\d|K");  // El dígito debe ser un número o la letra 'K'
    }

    /**
     * Método que valida el dígito verificador del RUT.
     * Este método usa una fórmula matemática que verifica si el dígito verificador es correcto según la parte numérica del RUT.
     *
     * @param rut El RUT ingresado por el usuario (en formato "XXXXXXXX-X").
     * @return true si el dígito verificador es correcto, false si no lo es.
     */
    private boolean esRUTValido(String rut) {
        try {
            // Dividimos el RUT en dos partes: el número base y el dígito verificador
            String[] partes = rut.split("-");
            String rutSolo = partes[0];  // La parte numérica del RUT (antes del guion)
            char digitoVerificador = partes[1].toUpperCase().charAt(0);  // El dígito verificador (después del guion)

            int suma = 0;  // Inicializamos la variable que sumará los productos
            int multiplicador = 2;  // El multiplicador empieza en 2 y se irá incrementando cíclicamente

            // Recorremos los dígitos del número base del RUT, desde el último al primero
            for (int i = rutSolo.length() - 1; i >= 0; i--) {
                // Convertimos cada carácter numérico a un número entero y lo multiplicamos por el multiplicador
                suma += Character.getNumericValue(rutSolo.charAt(i)) * multiplicador;
                // El multiplicador se incrementa de 2 a 7, y luego vuelve a 2
                multiplicador = (multiplicador == 7) ? 2 : multiplicador + 1;
            }

            // Calculamos el residuo de la suma total dividido por 11
            int resultado = 11 - (suma % 11);
            char digitoEsperado;

            // Si el resultado es 11, el dígito verificador debe ser '0'
            if (resultado == 11) {
                digitoEsperado = '0';
            } else if (resultado == 10) {
                // Si el resultado es 10, el dígito verificador debe ser 'K'
                digitoEsperado = 'K';
            } else {
                // Para cualquier otro resultado, el dígito esperado es el número correspondiente
                digitoEsperado = (char) (resultado + '0');
            }

            // Comparamos el dígito esperado con el dígito verificador ingresado por el usuario
            return digitoEsperado == digitoVerificador;
        } catch (Exception e) {
            // Si ocurre cualquier error (por ejemplo, el formato no era el adecuado), retornamos falso
            return false;
        }
    }
}
