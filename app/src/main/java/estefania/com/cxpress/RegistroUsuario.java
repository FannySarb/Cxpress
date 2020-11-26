package estefania.com.cxpress;

import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.util.Patterns;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.Volley;
import com.google.android.material.textfield.TextInputLayout;

import org.json.JSONObject;

import java.util.regex.Pattern;

import estefania.com.cxpress.login.LoginActivity;


public class RegistroUsuario extends AppCompatActivity implements Response.Listener<JSONObject>,Response.ErrorListener {
    EditText editRegistroNombre, editRegistroDireccion, editRegistroCorreo, editRegistroPassword, editRegistroConfirmation;
    Button btnRegistrarse;
    TextInputLayout impRegistroNombre, impRegistroDireccion, impRegistroCorreo, impRegistroPassword, impRegistroConfirmation;
    boolean nombre = false, direccion = false, correo = false, password = false, confirmation = false;
    RequestQueue request;
    JsonObjectRequest jsonObjectRequest;

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

                        if (Patterns.EMAIL_ADDRESS.matcher(editRegistroCorreo.getText().toString()).matches() == false) {
                            impRegistroCorreo.setError("Correo inválido");
                            correo = false;
                        } else {
                            impRegistroCorreo.setError(null);
                            correo = true;
                        }

                        Pattern p = Pattern.compile("[0-9][0-9][0-9][0-9][0-9][0-9]");

                        if (p.matcher(editRegistroPassword.getText().toString()).matches() == false) {
                            impRegistroPassword.setError("Contraseña inválida");
                            password = false;
                        } else {
                            impRegistroPassword.setError(null);
                            password = true;
                        }
                        if (p.matcher(editRegistroConfirmation.getText().toString()).matches() == false) {
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

        String url="https://appsmoviles2020.000webhostapp.com/nuevoComprador.php?nombre="+editRegistroNombre.getText().toString()+"&correo="+editRegistroCorreo.getText().toString()+
                "&password="+editRegistroPassword.getText().toString()+"&direccion="+editRegistroDireccion.getText().toString();

        //remplazar espacios
        url=url.replace(" ","%20");
        url=url.replace("@","%40");

        //mandamos la url del servicio a volley para que la procese
        jsonObjectRequest=new JsonObjectRequest(Request.Method.GET, url,null,this,this);
        request.add(jsonObjectRequest);
    }

    @Override
    public void onErrorResponse(VolleyError error) {
        Toast.makeText(this, "No ha sido posible crear una cuenta", Toast.LENGTH_SHORT).show();
    }

    @Override
    public void onResponse(JSONObject response) {

        editRegistroDireccion.setText("");
        editRegistroPassword.setText("");
        editRegistroCorreo.setText("");
        editRegistroNombre.setText("");
        editRegistroConfirmation.setText("");

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
}
