package estefania.com.cxpress.ui.perfil;

import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Bundle;
import android.os.StrictMode;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProviders;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.Volley;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.net.MalformedURLException;
import java.net.URL;

import estefania.com.cxpress.MetodosPagoActivity;
import estefania.com.cxpress.R;

import static android.content.Context.MODE_PRIVATE;

public class PerfilFragment extends Fragment implements Response.ErrorListener, Response.Listener<JSONObject> {

    ImageView imgBtnFotoPerfil;
    ImageButton btnmetodosPago;
    TextView editNombrePerfil, editCorreoPerfil, editDireccionPerfil;
    String fotoAnterior;
    int idComprador;


    RequestQueue request;
    JsonObjectRequest jsonObjectRequest;

    private PerfilViewModel perfilViewModel;

    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {
        perfilViewModel =
                ViewModelProviders.of(this).get(PerfilViewModel.class);
        View root = inflater.inflate(R.layout.fragment_perfil, container, false);

        imgBtnFotoPerfil = root.findViewById(R.id.imgBtnFotoPerfil);
        editNombrePerfil = root.findViewById(R.id.editNombrePerfil);
        editCorreoPerfil = root.findViewById(R.id.editCorreoPerfil);
        editDireccionPerfil = root.findViewById(R.id.editDireccionPerfil);
        btnmetodosPago=root.findViewById(R.id.mPago);

        request = Volley.newRequestQueue(getContext());

        StrictMode.ThreadPolicy policy = new StrictMode.ThreadPolicy.Builder().permitAll().build();
        StrictMode.setThreadPolicy(policy);

        recuperarId();
        recuperarPerfil(idComprador);


        btnmetodosPago.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i = new Intent(getContext(), MetodosPagoActivity.class);
                startActivity(i);
            }
        });

        return root;
    }



    void recuperarPerfil(int idComprador) {
        String URL = "https://appsmoviles2020.000webhostapp.com/cliente/mostrarComprador.php?idComprador="+idComprador;

        jsonObjectRequest = new JsonObjectRequest(Request.Method.GET, URL, null, this, this);
        request.add(jsonObjectRequest);
    }

    @Override
    public void onErrorResponse(VolleyError error) {
        Toast.makeText(getContext(), error.toString(), Toast.LENGTH_LONG).show();
    }

    @Override
    public void onResponse(JSONObject response) {
        try {
            JSONArray jsonArray = response.getJSONArray("Comprador");
            JSONObject jsonObject = jsonArray.getJSONObject(0);

            fotoAnterior = jsonObject.getString("foto");
            String urlFoto = "https://appsmoviles2020.000webhostapp.com/imagenes/"+jsonObject.getString("foto");
            URL url = new URL(urlFoto);
            Bitmap bitmap = BitmapFactory.decodeStream(url.openConnection().getInputStream());
            imgBtnFotoPerfil.setImageBitmap(bitmap);
            editNombrePerfil.setText(jsonObject.getString("nombre"));
            editCorreoPerfil.setText(jsonObject.getString("correo"));
            editDireccionPerfil.setText(jsonObject.getString("direccion"));
        } catch (JSONException e) {
            e.printStackTrace();
        } catch (MalformedURLException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }

    }


    void recuperarId() {
        SharedPreferences sharedPreferences = getContext().getSharedPreferences("Sesion", MODE_PRIVATE);
        idComprador = sharedPreferences.getInt("id", 0);
    }


    }

