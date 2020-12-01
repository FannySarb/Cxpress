package estefania.com.cxpress;

import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AlertDialog;

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

public class MetodosListAdapter extends BaseAdapter {
    Context context;
    ArrayList<Integer> idInfoBancaria;
    ArrayList<String> nombre;
    ArrayList<String> numeroTarjeta;
    private static LayoutInflater inflater = null;

    public MetodosListAdapter(Context context, ArrayList<Integer> idInfoBancaria, ArrayList<String> nombre, ArrayList<String> numeroTarjeta) {
        this.context = context;
        this.idInfoBancaria = idInfoBancaria;
        this.nombre = nombre;
        this.numeroTarjeta = numeroTarjeta;
        inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
    }

    @Override
    public int getCount() {
        return nombre.size();
    }

    @Override
    public Object getItem(int position) {
        return nombre.get(position);
    }

    @Override
    public long getItemId(int position) {
        return nombre.indexOf(getItem(position));
    }

    public class Holder {
        TextView txtViewItemNombreBanco;
        TextView txtViewItemNumeroTarjeta;
        Button btnEliminarItemPago;

    }

    @Override
    public View getView(final int position, View convertView, ViewGroup parent) {
        MetodosListAdapter.Holder holder = new MetodosListAdapter.Holder();
        View row;

        row = inflater.inflate(R.layout.metodopago_item, null);
        holder.txtViewItemNombreBanco = row.findViewById(R.id.txtViewItemNombreBanco);
        holder.txtViewItemNumeroTarjeta = row.findViewById(R.id.txtViewItemNumeroTarjeta);
        holder.btnEliminarItemPago = row.findViewById(R.id.btnEliminarItemPago);

        holder.txtViewItemNombreBanco.setText(nombre.get(position));
        holder.txtViewItemNumeroTarjeta.setText(numeroTarjeta.get(position));

        holder.btnEliminarItemPago.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                AlertDialog.Builder dialog = new AlertDialog.Builder(context);
                dialog.setMessage("¿Seguro que desea eliminar esta tarjeta?").setPositiveButton("Cancelar", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        dialog.dismiss();
                    }
                }).setNegativeButton("Eliminar", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        String URL = "https://appsmoviles2020.000webhostapp.com/cliente/eliminarTarjeta.php";

                        RequestQueue request = Volley.newRequestQueue(context);
                        StringRequest stringRequest = new StringRequest(Request.Method.POST, URL, new Response.Listener<String>() {
                            @Override
                            public void onResponse(String response) {
                                System.out.println(response);
                                Toast.makeText(context, "Método Eliminado", Toast.LENGTH_SHORT).show();
                                idInfoBancaria.remove(position);
                                nombre.remove(position);
                                numeroTarjeta.remove(position);
                                notifyDataSetChanged();
                            }
                        }, new Response.ErrorListener() {
                            @Override
                            public void onErrorResponse(VolleyError error) {
                                Toast.makeText(context, error.toString(), Toast.LENGTH_LONG).show();
                            }
                        }){
                            @Override
                            protected Map<String, String> getParams() throws AuthFailureError {
                                Map<String, String> params = new HashMap<>();
                                params.put("idInfoBancaria", String.valueOf(idInfoBancaria.get(position)));

                                return params;
                            }
                        };
                        request.add(stringRequest);
                    }
                });
                dialog.create();
                dialog.show();
            }
        });


        row.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i = new Intent(context, MetodosPagoActivity.class);
                Bundle bundle = new Bundle();
                bundle.putInt("idInfoBancaria", idInfoBancaria.get(position).intValue());
                i.putExtra("datos", bundle);
                context.startActivity(i);
            }
        });

        return row;
    }
}