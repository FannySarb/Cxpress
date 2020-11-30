package estefania.com.cxpress.ui.editarPerfil;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.Bundle;
import android.os.StrictMode;
import android.provider.MediaStore;
import android.util.Base64;
import android.util.Patterns;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProviders;

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.google.android.libraries.places.api.Places;
import com.google.android.libraries.places.api.model.Place;
import com.google.android.libraries.places.widget.Autocomplete;
import com.google.android.libraries.places.widget.model.AutocompleteActivityMode;
import com.google.android.material.textfield.TextInputLayout;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.ByteArrayOutputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import estefania.com.cxpress.MainActivity;
import estefania.com.cxpress.R;

import static android.app.Activity.RESULT_OK;
import static android.content.Context.MODE_PRIVATE;

public class EditarPerfilFragment extends Fragment implements Response.Listener<JSONObject>, Response.ErrorListener{

    private EditarPerfilViewModel editarPerfilViewModel;
    ImageView imgBtnFotoPerfil;
    TextInputLayout impNombrePerfil, impCorreoPerfil, impPasswordPerfil,impDireccionPerfil;
    EditText editNombrePerfil, editCorreoPerfil, editPasswordPerfil,editDireccionPerfil;
    Button  btnGuardarPerfil, btnCancelarPerfil;
    String nombreAnterior, correoAnterior, passwordAnterior, direccionAnterior, fotoAnterior, nuevoNombre, nuevoCorreo, nuevaPassword, nuevaFoto, nuevaDireccion;
    int idComprador;
    Boolean nombre = false, correo = false, password = false;
    //Para las imagenes
    String encodedImage;

    RequestQueue request;
    JsonObjectRequest jsonObjectRequest;

    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {
        editarPerfilViewModel = ViewModelProviders.of(this).get(EditarPerfilViewModel.class);
        View root = inflater.inflate(R.layout.fragment_editar_perfil, container, false);


        imgBtnFotoPerfil = root.findViewById(R.id.imgBtnFotoPerfil);

        impNombrePerfil = root.findViewById(R.id.impNombrePerfil);
        impCorreoPerfil = root.findViewById(R.id.impCorreoPerfil);
        impPasswordPerfil = root.findViewById(R.id.impPasswordPerfil);
        impDireccionPerfil = root.findViewById(R.id.impDireccionPerfil);

        editNombrePerfil = root.findViewById(R.id.editNombrePerfil);
        editCorreoPerfil = root.findViewById(R.id.editCorreoPerfil);
        editPasswordPerfil = root.findViewById(R.id.editPasswordPerfil);
        editDireccionPerfil = root.findViewById(R.id.editDireccionPerfil);


        btnGuardarPerfil = root.findViewById(R.id.btnGuardarPerfil);
        btnCancelarPerfil = root.findViewById(R.id.btnCancelarPerfil);

        request = Volley.newRequestQueue(getContext());


        StrictMode.ThreadPolicy policy = new StrictMode.ThreadPolicy.Builder().permitAll().build();
        StrictMode.setThreadPolicy(policy);

        recuperarId();
        recuperarPerfil(idComprador);
        Places.initialize(getContext(),"AIzaSyDMOsqnvLh_v094mDp4F_NXQEuEu81AGXY");

        editDireccionPerfil.setFocusable(false);
        editDireccionPerfil.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                List<Place.Field> fieldList = Arrays.asList(Place.Field.ADDRESS, Place.Field.LAT_LNG,Place.Field.NAME);

                Intent intent=new Autocomplete.IntentBuilder(AutocompleteActivityMode.FULLSCREEN, fieldList).build(getActivity());

                startActivityForResult(intent, 100);
            }
        });


        imgBtnFotoPerfil.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mostrarOpcionesFoto();
            }
        });


        btnGuardarPerfil.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                validarCambios();
            }
        });

        btnCancelarPerfil.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                editNombrePerfil.setText(nombreAnterior);
                editCorreoPerfil.setText(correoAnterior);
                editPasswordPerfil.setText(passwordAnterior);
                editDireccionPerfil.setText(direccionAnterior);
                String urlFoto = "https://appsmoviles2020.000webhostapp.com/imagenes/"+fotoAnterior;
                URL url = null;
                try {
                    url = new URL(urlFoto);
                    Bitmap bitmap = BitmapFactory.decodeStream(url.openConnection().getInputStream());
                    imgBtnFotoPerfil.setImageBitmap(bitmap);
                } catch (MalformedURLException e) {
                    e.printStackTrace();
                } catch (IOException e) {
                    e.printStackTrace();
                }
                Intent intent = new Intent(getContext(), MainActivity.class);
                startActivity(intent);

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

    private void mostrarOpcionesFoto() {
        final CharSequence[] opciones={"Tomar Foto", "Elegir foto de la galería", "Cancelar"};
        final AlertDialog.Builder opc= new AlertDialog.Builder(getContext());
        opc.setTitle("Elige una opción");
        opc.setItems(opciones, new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int i) {
                if(opciones[i].equals("Tomar Foto")) {
                    Intent intent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
                    startActivityForResult(intent, 20);
                } else if(opciones[i].equals("Elegir foto de la galería")) {
                    Intent intent = new Intent(Intent.ACTION_PICK, MediaStore.Images.Media.EXTERNAL_CONTENT_URI);
                    intent.setType("image/");
                    startActivityForResult(intent.createChooser(intent, "Selecciona una opción"), 10);
                } else {
                    dialog.dismiss();
                }
            }
        });
        opc.show();
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);


        if(resultCode== RESULT_OK) {
            switch (requestCode) {
                case 10:
                    Uri miPath=data.getData();
                    try {
                        InputStream inputStream = getContext().getContentResolver().openInputStream(miPath);
                        Bitmap bitmap = BitmapFactory.decodeStream(inputStream);
                        imgBtnFotoPerfil.setImageBitmap(bitmap);

                        guardarImagen(bitmap);
                    } catch (FileNotFoundException e) {
                        e.printStackTrace();
                    }
                    break;
                case 20:
                    Bundle bundle = data.getExtras();
                    Bitmap bitmap = (Bitmap) bundle.get("data");
                    imgBtnFotoPerfil.setImageBitmap(bitmap);
                    guardarImagen(bitmap);
                    break;
            }
        } else {

        }
    }

    void validarCambios() {
        //Validación nombre
        if(editNombrePerfil.getText().toString().isEmpty()) {
            impNombrePerfil.setError("Campo de nombre está vacío");
            nombre = false;
        } else {
            impNombrePerfil.setError(null);
            nombre = true;
        }
        //Validación correo
        if (Patterns.EMAIL_ADDRESS.matcher(editCorreoPerfil.getText().toString()).matches() == false) {
            impCorreoPerfil.setError("Ingrese un correo válido");
            correo = false;
        } else {
            impCorreoPerfil.setError(null);
            correo = true;
        }
        if(editCorreoPerfil.getText().toString().isEmpty()) {
            impCorreoPerfil.setError("Campo de correo está vacío");
            correo = false;
        } else {
            impCorreoPerfil.setError(null);
            correo = true;
        }
        //Validación contraseña
        if (editPasswordPerfil.getText().toString().isEmpty()) {
            impPasswordPerfil.setError("Campo de contraseña está vacío");
            password = false;
        } else {
            impPasswordPerfil.setError(null);
            password = true;
        }

        if(nombre && correo && password) {
            nuevoNombre = editNombrePerfil.getText().toString();
            nuevoCorreo = editCorreoPerfil.getText().toString();
            nuevaPassword = editPasswordPerfil.getText().toString();
            nuevaDireccion = editDireccionPerfil.getText().toString();


            guardarCambios(idComprador, nuevoNombre, nuevoCorreo, nuevaPassword, nuevaDireccion);
        }
    }

    void guardarCambios(final int idComprador, final String nombre, final String correo, final String password, final String direccion) {
        String URL = "https://appsmoviles2020.000webhostapp.com/cliente/actualizarComprador.php";

        StringRequest stringRequest = new StringRequest(Request.Method.POST, URL, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                System.out.println(response);
                Toast.makeText(getContext(), "Cambios guardados", Toast.LENGTH_SHORT).show();
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                Toast.makeText(getContext(), error.toString(), Toast.LENGTH_LONG).show();
            }
        }){
            @Override
            protected Map<String, String> getParams() throws AuthFailureError {
                Map<String, String> params = new HashMap<>();
                params.put("idComprador", String.valueOf(idComprador));
                params.put("nombre", nombre);
                params.put("correo", correo);
                params.put("password", password);
                params.put("direccion", direccion);
                params.put("foto", encodedImage);

                return params;
            }
        };
        request.add(stringRequest);
    }

    void recuperarId() {
        SharedPreferences sharedPreferences = getContext().getSharedPreferences("Sesion", MODE_PRIVATE);
        idComprador = sharedPreferences.getInt("id", 0);
    }

    void guardarImagen(Bitmap imagen) {
        ByteArrayOutputStream stream = new ByteArrayOutputStream();
        imagen.compress(Bitmap.CompressFormat.JPEG, 100, stream);
        byte[] imageBytes = stream.toByteArray();
        encodedImage = android.util.Base64.encodeToString(imageBytes, Base64.DEFAULT);
    }


}