package com.unidad.gymapp;

import android.app.DatePickerDialog;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.DatePicker;
import android.widget.RadioGroup;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.google.android.material.button.MaterialButton;
import com.google.android.material.textfield.TextInputEditText;

import java.util.Calendar;

public class RegisterActivity extends AppCompatActivity {

    private TextInputEditText etNombre, etCorreo, etTelefono, etFechaNacimiento, etPeso, etAltura, etContrasena, etConfirmarContrasena;
    private RadioGroup rgGenero;
    private Spinner spObjetivo, spEntrenador;
    private TextView tvTelefonoEntrenador;
    private MaterialButton btnRegistrar;

    private String[] telefonosEntrenadores;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);

        // Bind views
        etNombre = findViewById(R.id.etNombre);
        etCorreo = findViewById(R.id.etCorreo);
        etTelefono = findViewById(R.id.etTelefono);
        etFechaNacimiento = findViewById(R.id.etFechaNacimiento);
        etPeso = findViewById(R.id.etPeso);
        etAltura = findViewById(R.id.etAltura);
        etContrasena = findViewById(R.id.etContrasena);
        etConfirmarContrasena = findViewById(R.id.etConfirmarContrasena);

        rgGenero = findViewById(R.id.rgGenero);
        spObjetivo = findViewById(R.id.spObjetivo);
        spEntrenador = findViewById(R.id.spEntrenador);
        tvTelefonoEntrenador = findViewById(R.id.tvTelefonoEntrenador);
        btnRegistrar = findViewById(R.id.btnRegistrar);

        // Cargar adapters (desde arrays en strings.xml)
        ArrayAdapter<CharSequence> adapterObjetivo =
                ArrayAdapter.createFromResource(this, R.array.opciones_objetivo, android.R.layout.simple_spinner_item);
        adapterObjetivo.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spObjetivo.setAdapter(adapterObjetivo);

        ArrayAdapter<CharSequence> adapterEntrenadores =
                ArrayAdapter.createFromResource(this, R.array.entrenadores, android.R.layout.simple_spinner_item);
        adapterEntrenadores.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spEntrenador.setAdapter(adapterEntrenadores);

        // Cargar telefonos
        telefonosEntrenadores = getResources().getStringArray(R.array.telefonos_entrenadores);

        // Listener para actualizar teléfono cuando se elige entrenador
        spEntrenador.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                String telefono = (telefonosEntrenadores != null && telefonosEntrenadores.length > position)
                        ? telefonosEntrenadores[position]
                        : "-";
                tvTelefonoEntrenador.setText("Teléfono entrenador: " + telefono);
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {
                tvTelefonoEntrenador.setText("Teléfono entrenador: -");
            }
        });

        // DatePicker para fecha de nacimiento
        etFechaNacimiento.setOnClickListener(v -> {
            final Calendar c = Calendar.getInstance();
            int year = c.get(Calendar.YEAR);
            int month = c.get(Calendar.MONTH);
            int day = c.get(Calendar.DAY_OF_MONTH);

            DatePickerDialog dpd = new DatePickerDialog(RegisterActivity.this,
                    new DatePickerDialog.OnDateSetListener() {
                        @Override
                        public void onDateSet(DatePicker view, int y, int m, int d) {
                            String fecha = String.format("%02d/%02d/%04d", d, m + 1, y);
                            etFechaNacimiento.setText(fecha);
                        }
                    }, year, month, day);
            dpd.show();
        });

        // Botón registrar
        btnRegistrar.setOnClickListener(v -> {
            String nombre = getText(etNombre);
            String correo = getText(etCorreo);
            String telefono = getText(etTelefono);
            String fechaNacimiento = getText(etFechaNacimiento);
            String peso = getText(etPeso);
            String altura = getText(etAltura);
            String contrasena = getText(etContrasena);
            String confirmar = getText(etConfirmarContrasena);

            // Género
            String genero = "";
            int idGenero = rgGenero.getCheckedRadioButtonId();
            if (idGenero == R.id.rbMasculino) genero = "Masculino";
            else if (idGenero == R.id.rbFemenino) genero = "Femenino";

            String objetivo = spObjetivo.getSelectedItem() != null ? spObjetivo.getSelectedItem().toString() : "";
            String entrenador = spEntrenador.getSelectedItem() != null ? spEntrenador.getSelectedItem().toString() : "";
            String telefonoEntrenador = (telefonosEntrenadores != null && telefonosEntrenadores.length > spEntrenador.getSelectedItemPosition())
                    ? telefonosEntrenadores[spEntrenador.getSelectedItemPosition()]
                    : "";

            // Validaciones simples
            if (nombre.isEmpty() || correo.isEmpty() || contrasena.isEmpty() || fechaNacimiento.isEmpty()) {
                Toast.makeText(this, "Completa los campos obligatorios (nombre, correo, contraseña, fecha)", Toast.LENGTH_SHORT).show();
                return;
            }
            if (!contrasena.equals(confirmar)) {
                Toast.makeText(this, "Las contraseñas no coinciden", Toast.LENGTH_SHORT).show();
                return;
            }

            // Ejemplo: mostrar resumen
            String resumen = "Nombre: " + nombre +
                    "\nCorreo: " + correo +
                    "\nTel: " + telefono +
                    "\nFecha Nac: " + fechaNacimiento +
                    "\nPeso: " + peso + " kg" +
                    "\nAltura: " + altura + " cm" +
                    "\nGénero: " + genero +
                    "\nObjetivo: " + objetivo +
                    "\nEntrenador: " + entrenador +
                    "\nTel Entrenador: " + telefonoEntrenador;

            Toast.makeText(this, resumen, Toast.LENGTH_LONG).show();

            // Aquí puedes crear el objeto Usuario y guardarlo en BD / enviar al backend
        });
    }

    private String getText(TextInputEditText et) {
        return et != null && et.getText() != null ? et.getText().toString().trim() : "";
    }
}
