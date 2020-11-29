package estefania.com.cxpress.login;

import android.content.Intent;
import android.os.Bundle;
import android.util.Patterns;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.google.android.material.textfield.TextInputLayout;

import org.json.JSONObject;

import java.util.HashMap;
import java.util.Map;

import estefania.com.cxpress.MainActivity;
import estefania.com.cxpress.R;
import estefania.com.cxpress.RegistroUsuario;

public class LoginActivity extends AppCompatActivity implements Response.Listener<JSONObject>, Response.ErrorListener {

    EditText txtUsuario, txtPasswd;
    Button btnIngresar, btnRegistrar;
    TextInputLayout impUsuario, impPasswd;
    boolean user = false;
    boolean pass = false;
    String usuario, passwd;
    RequestQueue request;
    JsonObjectRequest jsonObjectRequest;



    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        txtUsuario = findViewById(R.id.Usuario);
        txtPasswd = findViewById(R.id.Password);
        btnIngresar = findViewById(R.id.Ingresar);
        btnRegistrar = findViewById(R.id.Registro);
        impUsuario = findViewById(R.id.impUsuario);
        impPasswd = findViewById(R.id.impPassword);
        usuario=txtUsuario.getText().toString();
        passwd=txtPasswd.getText().toString();

        btnRegistrar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i = new Intent(getApplicationContext(), RegistroUsuario.class);
                startActivity(i);
            }
        });

        btnIngresar.setOnClickListener(new View.OnClickListener()
        {

            @Override
            public void onClick(View v) {

                //validar con patrón la estructura del usuario (correo) -> valida .matcher
                if(Patterns.EMAIL_ADDRESS.matcher(txtUsuario.getText().toString()).matches()==false)
                {
                    impUsuario.setError("Ingresa un usuario válido");
                    user=false;
                }
                else {

                    impUsuario.setError(null);
                    user = true;
                }

                if(txtPasswd.getText().toString().isEmpty())
                {
                    impPasswd.setError("Ingresa una contraseña");
                    pass=false;
                }
                else {

                    impPasswd.setError(null);
                    pass = true;
                }

                // para hacer la autenticación
                if(user==true && pass==true)
                {
                    Validar("https://appsmoviles2020.000webhostapp.com/validarComprador.php");
                }
            }


        });

    }

    private void Validar(String URL) {
        StringRequest stringRequest= new StringRequest(Request.Method.POST, URL, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                if(!response.isEmpty())
                {
                    Intent intent = new Intent (getApplicationContext(), MainActivity.class);
                    startActivity(intent);
                }
                else
                {
                    Toast.makeText(LoginActivity.this, "Usuario o contraseña incorrectos", Toast.LENGTH_SHORT).show();
                }

            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                Toast.makeText(LoginActivity.this, "Error", Toast.LENGTH_SHORT).show();
            }
        }) {
            @Override
            protected Map<String, String> getParams() throws AuthFailureError
            {
                Map<String, String> Parametros = new HashMap<String, String>();
                Parametros.put("correo", txtUsuario.getText().toString());
                Parametros.put("password", txtPasswd.getText().toString());
                return Parametros;
            }
        };
        RequestQueue requestQueue=Volley.newRequestQueue(this);
        requestQueue.add(stringRequest);



    }


    @Override
    public void onErrorResponse(VolleyError error) {

    }

    @Override
    public void onResponse(JSONObject response) {

    }
}




