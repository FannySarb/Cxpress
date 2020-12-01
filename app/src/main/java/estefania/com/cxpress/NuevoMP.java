package estefania.com.cxpress;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.Volley;

import org.json.JSONObject;

import io.card.payment.CardIOActivity;
import io.card.payment.CreditCard;

public class NuevoMP extends AppCompatActivity implements Response.Listener<JSONObject>,Response.ErrorListener   {

    private static final int SCAN_RESULT=100;
    private TextView NumeroTarjeta, Fecha, Banco;
    Button btnScaner, btnGuardar;
    RequestQueue request;
    int idComprador;
    JsonObjectRequest jsonObjectRequest;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_nuevo_mp);

        NumeroTarjeta= findViewById(R.id.numeroTarjeta);
        Fecha= findViewById(R.id.Vence_text);
        Banco=findViewById(R.id.banco_text);
        btnScaner=findViewById(R.id.scan);
        btnGuardar=findViewById(R.id.guardarTarjeta);
        request= Volley.newRequestQueue(this);

        recuperarId();


        btnScaner.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                scanearTarjeta();
            }
        });

        btnGuardar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                guardarTarjeta();
            }
        });

    }

    private void recuperarId() {

        SharedPreferences sharedPreferences = getSharedPreferences("Sesion", MODE_PRIVATE);
        idComprador = sharedPreferences.getInt("id", 0);
    }

    private void guardarTarjeta() {
        String url="https://appsmoviles2020.000webhostapp.com/cliente/nuevaTarjeta.php?nombre="+Banco.getText().toString()
                +"&numeroTarjeta="+NumeroTarjeta.getText().toString()+
                "&fechaExpiracion="+Fecha.getText().toString()+"&eliminado=0&idComprador="+idComprador;

        //remplazar espacios y @
        url=url.replace(" ","%20");
        url=url.replace("@","%40");

        //mandamos la url del servicio a volley para que la procese
        jsonObjectRequest=new JsonObjectRequest(Request.Method.GET, url,null,this,this);
        request.add(jsonObjectRequest);
    }


    public void scanearTarjeta()
    {
        Intent intent = new Intent(this, CardIOActivity.class)
                .putExtra(CardIOActivity.EXTRA_REQUIRE_CARDHOLDER_NAME, true)
                .putExtra(CardIOActivity.EXTRA_REQUIRE_CVV, true)
                .putExtra(CardIOActivity.EXTRA_REQUIRE_EXPIRY, true)
                .putExtra(CardIOActivity.EXTRA_REQUIRE_POSTAL_CODE, false);

        startActivityForResult(intent, SCAN_RESULT);

    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if(requestCode==SCAN_RESULT)
        {
            if(data!=null && data.hasExtra(CardIOActivity.EXTRA_SCAN_RESULT))
            {
                CreditCard scanResult=data.getParcelableExtra(CardIOActivity.EXTRA_SCAN_RESULT);
                NumeroTarjeta.setText(scanResult.cardNumber);

                if(scanResult.isExpiryValid())
                {
                    String mes=String.valueOf(scanResult.expiryMonth);
                    String agno=String.valueOf(scanResult.expiryYear);
                    Fecha.setText(mes+"/"+agno);
                    Fecha.setText(mes+"/"+agno);
                    Banco.setText("Santander");
                }
            }
        }
    }

    @Override
    public void onErrorResponse(VolleyError error) {
        Toast.makeText(this, "No ha sido posible guarda la tarjeta", Toast.LENGTH_SHORT).show();
    }

    @Override
    public void onResponse(JSONObject response) {
        Intent i = new Intent(getApplicationContext(), MetodosPagoActivity.class);
        startActivity(i);
    }
}
