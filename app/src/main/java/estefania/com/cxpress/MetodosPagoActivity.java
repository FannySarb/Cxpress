package estefania.com.cxpress;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.os.StrictMode;
import android.view.View;
import android.widget.Button;
import android.widget.ListView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.Volley;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;

public class MetodosPagoActivity extends AppCompatActivity implements Response.Listener<JSONObject>, Response.ErrorListener{
    ArrayList<Integer> idInfoBancaria;
    ArrayList<String> nombre;
    ArrayList<String> numeroTarjeta;
    MetodosListAdapter adapter;

    int idComprador;

    Button btnNuevaTarjeta;

    RequestQueue request;
    JsonObjectRequest jsonObjectRequest;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_metodos_pago);

        StrictMode.ThreadPolicy policy = new StrictMode.ThreadPolicy.Builder().permitAll().build();
        StrictMode.setThreadPolicy(policy);

        recuperarId();

        btnNuevaTarjeta = findViewById(R.id.btnNuevaTrajeta);
        request = Volley.newRequestQueue(this);


        btnNuevaTarjeta.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i = new Intent(MetodosPagoActivity.this, NuevoMP.class);
                startActivity(i);
            }
        });


        cargarDatos();
    }

    void  cargarDatos() {
        String URL = "https://appsmoviles2020.000webhostapp.com/cliente/getInfoBancaria.php?idComprador="+idComprador;

        jsonObjectRequest = new JsonObjectRequest(Request.Method.GET, URL, null, this, this);
        request.add(jsonObjectRequest);
    }

    @Override
    public void onErrorResponse(VolleyError error) {
        Toast.makeText(this, error.toString(), Toast.LENGTH_LONG).show();
    }

    @Override
    public void onResponse(JSONObject response) {
        try {
            JSONArray jsonArray = response.getJSONArray("Tarjetas");
            nombre = new ArrayList<String>();
            numeroTarjeta = new ArrayList<String>();
            idInfoBancaria = new ArrayList<Integer>();

            for (int i = 0; i < jsonArray.length(); i++) {
                JSONObject jsonObject = jsonArray.getJSONObject(i);
                idInfoBancaria.add(jsonObject.getInt("idInfoBancaria"));
                nombre.add(jsonObject.getString("nombre"));
                numeroTarjeta.add(jsonObject.getString("numeroTarjeta"));
            }

            adapter = new MetodosListAdapter(this, idInfoBancaria, nombre, numeroTarjeta);
            ListView listTarjetas = findViewById(R.id.listTarjetas);
            listTarjetas.setAdapter(adapter);
        } catch (JSONException e) {
            e.printStackTrace();
        }
    }

    void recuperarId() {
        SharedPreferences sharedPreferences = getSharedPreferences("Sesion", MODE_PRIVATE);
        idComprador = sharedPreferences.getInt("id", 0);
    }
}
