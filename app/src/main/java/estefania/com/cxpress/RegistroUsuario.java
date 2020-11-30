package estefania.com.cxpress;

import android.app.ProgressDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.util.Patterns;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.Volley;
import com.google.android.gms.common.api.Status;
import com.google.android.libraries.places.api.Places;
import com.google.android.libraries.places.api.model.Place;
import com.google.android.libraries.places.widget.Autocomplete;
import com.google.android.libraries.places.widget.AutocompleteActivity;
import com.google.android.libraries.places.widget.model.AutocompleteActivityMode;
import com.google.android.material.textfield.TextInputLayout;

import org.json.JSONObject;

import java.util.Arrays;
import java.util.List;
import java.util.regex.Pattern;

import estefania.com.cxpress.login.LoginActivity;


public class
RegistroUsuario extends AppCompatActivity implements Response.Listener<JSONObject>,Response.ErrorListener {
    EditText editRegistroNombre, editRegistroDireccion, editRegistroCorreo, editRegistroPassword, editRegistroConfirmation;
    Button btnRegistrarse;
    TextInputLayout impRegistroNombre, impRegistroDireccion, impRegistroCorreo, impRegistroPassword, impRegistroConfirmation;
    boolean nombre = false, direccion = false, correo = false, password = false, confirmation = false;
    RequestQueue request;
    JsonObjectRequest jsonObjectRequest;
    ProgressDialog progreso;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_registro_usuario);

        editRegistroNombre = findViewById(R.id.editRegistroNombre);
        editRegistroDireccion = findViewById(R.id.editRegistroDireccion);
        editRegistroCorreo = findViewById(R.id.editRegistroCorreo);
        editRegistroPassword = findViewById(R.id.editRegistroPassword);
        editRegistroConfirmation = findViewById(R.id.editRegistroConfirmation);

        btnRegistrarse = findViewById(R.id.btnRegistrarse);

        impRegistroNombre = findViewById(R.id.impRegistroNombre);
        impRegistroDireccion = findViewById(R.id.impRegistroDireccion);
        impRegistroCorreo = findViewById(R.id.impRegistroCorreo);
        impRegistroPassword = findViewById(R.id.impRegistroPassword);
        impRegistroConfirmation = findViewById(R.id.impRegistroConfirmation);

        Places.initialize(getApplicationContext(),"AIzaSyDMOsqnvLh_v094mDp4F_NXQEuEu81AGXY");

        editRegistroDireccion.setFocusable(false);
        editRegistroDireccion.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                List<Place.Field> fieldList = Arrays.asList(Place.Field.ADDRESS, Place.Field.LAT_LNG,Place.Field.NAME);

                Intent intent=new Autocomplete.IntentBuilder(AutocompleteActivityMode.FULLSCREEN, fieldList).build(RegistroUsuario.this);

                startActivityForResult(intent, 100);
            }
        });

        request= Volley.newRequestQueue(this);


        btnRegistrarse.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                btnRegistrarse.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        if(editRegistroNombre.getText().toString().equals("")) {
                            impRegistroNombre.setError("Debe registra un nombre");
                            nombre = false;
                        } else {
                            impRegistroNombre.setError(null);
                            nombre = true;
                        }

                        if(editRegistroDireccion.getText().toString().equals("")) {
                            impRegistroDireccion.setError("Debe registrar una direccion");
                            direccion = false;
                        } else {
                            impRegistroDireccion.setError(null);
                            direccion = true;
                        }

                        if (!Patterns.EMAIL_ADDRESS.matcher(editRegistroCorreo.getText().toString()).matches()) {
                            impRegistroCorreo.setError("Correo inválido");
                            correo = false;
                        } else {
                            impRegistroCorreo.setError(null);
                            correo = true;
                        }

                        Pattern p = Pattern.compile("[0-9][0-9][0-9][0-9][0-9][0-9]");

                        if (!p.matcher(editRegistroPassword.getText().toString()).matches()) {
                            impRegistroPassword.setError("Contraseña inválida");
                            password = false;
                        } else {
                            impRegistroPassword.setError(null);
                            password = true;
                        }
                        if (!p.matcher(editRegistroConfirmation.getText().toString()).matches()) {
                            impRegistroConfirmation.setError("Confirmación inválida");
                            confirmation = false;
                        } else {
                            impRegistroConfirmation.setError(null);
                            confirmation = true;
                        }

                        // para hacer la autenticación provicional
                        if (nombre && direccion && correo && password && confirmation) {
                            String nombre = editRegistroNombre.getText().toString();
                            String direccion = editRegistroDireccion.getText().toString();
                            String correo = editRegistroCorreo.getText().toString();
                            String password = editRegistroPassword.getText().toString();

                            //como faltan validaciones dejé que accediera directamente
                            if (true) {
                               cargarWebservice();
                            } else {
                                Toast.makeText(RegistroUsuario.this, "Datos incorrectos", Toast.LENGTH_SHORT).show();
                            }
                        }
                    }
                });
                }
        });
    }


    private void cargarWebservice() {

        String url="https://appsmoviles2020.000webhostapp.com/cliente/nuevoComprador.php?nombre="+editRegistroNombre.getText().toString()
                +"&correo="+editRegistroCorreo.getText().toString()+
                "&password="+editRegistroPassword.getText().toString()+"&direccion="+editRegistroDireccion.getText().toString();

        //remplazar espacios y @
        url=url.replace(" ","%20");
        url=url.replace("@","%40");

        //mandamos la url del servicio a volley para que la procese
        jsonObjectRequest=new JsonObjectRequest(Request.Method.GET, url,null,this,this);
        request.add(jsonObjectRequest);
    }


    // en caso de que no se haya obtenido despecsa
    @Override
    public void onErrorResponse(VolleyError error) {

        Toast.makeText(this, "No ha sido posible crear una cuenta", Toast.LENGTH_SHORT).show();
    }

    // en caso de que si haya respuesta
    @Override
    public void onResponse(JSONObject response) {

        //borra los campos
        editRegistroDireccion.setText("");
        editRegistroPassword.setText("");
        editRegistroCorreo.setText("");
        editRegistroNombre.setText("");
        editRegistroConfirmation.setText("");


        //mensaje de confirmación

        AlertDialog.Builder dialogo1 = new AlertDialog.Builder(this);
        dialogo1.setTitle("¡Felicidades!");
        dialogo1.setMessage("Ya has creado una cuenta en Cxpress, ahora inicia sesión");
        dialogo1.setPositiveButton("Aceptar", new DialogInterface.OnClickListener() {
            public void onClick(DialogInterface dialogo1, int id) {
                Intent i = new Intent(getApplicationContext(), LoginActivity.class);
                startActivity(i);
            }
        });
        dialogo1.show();
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if (requestCode == 100 && resultCode == RESULT_OK) {
            Place place = Autocomplete.getPlaceFromIntent(data);
            editRegistroDireccion.setText(place.getAddress());

        } else if (resultCode == AutocompleteActivity.RESULT_ERROR)
        {
            Status status=Autocomplete.getStatusFromIntent(data);
            Toast.makeText(getApplicationContext(), status.getStatusMessage(), Toast.LENGTH_SHORT).show();

        }
    }
}
