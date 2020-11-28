package estefania.com.cxpress;

import android.os.Bundle;
import android.widget.Button;
import android.widget.ImageButton;
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

public class MariscosActivity extends AppCompatActivity implements Response.Listener<JSONObject>, Response.ErrorListener{
    ArrayList<Integer> idNegocios;
    ArrayList<String> nombres;
    ArrayList<String> mercados;
    NegociosListAdapter adapter;
    int idCategoria = 3;

    ImageButton imgBtnRegresar;
    Button btnNuevoNegocio;

    RequestQueue request;
    JsonObjectRequest jsonObjectRequest;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_mariscos);



        request = Volley.newRequestQueue(this);


        cargarDatos();
    }

    void  cargarDatos() {
        String URL = "https://appsmoviles2020.000webhostapp.com/cliente/getCategorias.php?idCategoria="+idCategoria;

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
            JSONArray jsonArray = response.getJSONArray("Negocios");
            nombres = new ArrayList<String>();
            mercados = new ArrayList<String>();
            idNegocios = new ArrayList<Integer>();

            for (int i = 0; i < jsonArray.length(); i++) {
                JSONObject jsonObject = jsonArray.getJSONObject(i);
                idNegocios.add(jsonObject.getInt("idPuesto"));
                nombres.add(jsonObject.getString("nombre"));
                mercados.add(jsonObject.getString("mercado"));
            }

            adapter = new NegociosListAdapter(this, idNegocios, nombres, mercados);
            ListView listNegocios = findViewById(R.id.listNegocios);
            listNegocios.setAdapter(adapter);
        } catch (JSONException e) {
            e.printStackTrace();
        }
    }
}
