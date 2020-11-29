package estefania.com.cxpress.login;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.util.Patterns;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.google.android.material.textfield.TextInputLayout;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

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

        verificarSesion();
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

                if(usuario.isEmpty())
                {
                    impUsuario.setError("Ingresa una usuario");
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
                if(user && pass)
                {
                    Validar(usuario,passwd);
                }
            }


        });

    }

    private void Validar(final String correo, final String password) {

        String URL = "https://appsmoviles2020.000webhostapp.com/cliente/validarComprador.php?correo="+correo+"&password="+password;
        URL = URL.replaceAll("@", "%40");

        jsonObjectRequest = new JsonObjectRequest(Request.Method.GET, URL, null, this, this);
        request.add(jsonObjectRequest);
    }


    @Override
    public void onErrorResponse(VolleyError error) {
        error.printStackTrace();
        Toast.makeText(this, error.toString(), Toast.LENGTH_SHORT).show();
    }

    @Override
    public void onResponse(JSONObject response) {
        try {
            JSONArray jsonArray = response.getJSONArray("Comprador");
            JSONObject jsonObject = jsonArray.getJSONObject(0);
            if(jsonObject.getString("idComprador").equals("0")) {
                Toast.makeText(this, "Usuario o contraseña incorrectos", Toast.LENGTH_LONG).show();
            } else {
                generarSesion(jsonObject.getString("idComprador"));
            }
        } catch (JSONException e) {
            e.printStackTrace();
        }
    }

    //con esta función se guarda el id del usuario en caso de que el login sea correcto
    void generarSesion(String idComprador) {
        SharedPreferences sharedPreferences = getSharedPreferences("Sesion", MODE_PRIVATE);
        sharedPreferences.edit().putInt("id", Integer.valueOf(idComprador)).apply();

        Intent intent = new Intent(LoginActivity.this, MainActivity.class);
        startActivity(intent);
        finish();
    }
    //con esta funcion se verifica si hay una sesion iniciada
    void verificarSesion() {
        SharedPreferences sharedPreferences = getSharedPreferences("Sesion", MODE_PRIVATE);
        int id = sharedPreferences.getInt("id", 0);

        if(id!=0) {
            Intent intent = new Intent(LoginActivity.this, MainActivity.class);
            startActivity(intent);
            finish();
        }
    }
}




