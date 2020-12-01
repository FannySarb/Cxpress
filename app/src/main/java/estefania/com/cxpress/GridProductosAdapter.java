package estefania.com.cxpress;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import java.io.IOException;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.ArrayList;

public class GridProductosAdapter extends BaseAdapter {
    Context context;
    ArrayList<Integer> idProductos;
    ArrayList<String> nombres;
    ArrayList<String> cantidades;
    ArrayList<String> fotos;
    private static LayoutInflater inflater = null;

    @Override
    public int getCount() {return nombres.size();}

    @Override
    public Object getItem(int position) {return nombres.get(position);}

    @Override
    public long getItemId(int position) { return nombres.indexOf(getItem(position));}

    public  class Holder {
        TextView txtViewItemNombreProducto;
        TextView txtViewItemCantidadProducto;
        ImageView imgItemProducto;
    }

    @Override
    public View getView(final int position, View convertView, ViewGroup parent) {
        GridProductosAdapter.Holder holder = new GridProductosAdapter.Holder();
        View row;

        row = inflater.inflate(R.layout.producto_item, null);
        holder.txtViewItemNombreProducto = row.findViewById(R.id.txtViewItemNombreProducto);
        holder.txtViewItemCantidadProducto = row.findViewById(R.id.txtViewItemCantidadProducto);
        holder.imgItemProducto = row.findViewById(R.id.imgItemProducto);

        holder.txtViewItemNombreProducto.setText(nombres.get(position));
        holder.txtViewItemCantidadProducto.setText(cantidades.get(position));
        if(fotos.get(position)!="null") {
            String urlFoto = "https://appsmoviles2020.000webhostapp.com/imagenes/"+fotos.get(position);
            try {
                URL url = new URL(urlFoto);
                Bitmap bitmap = BitmapFactory.decodeStream(url.openConnection().getInputStream());
                holder.imgItemProducto.setImageBitmap(bitmap);
            } catch (MalformedURLException e) {
                e.printStackTrace();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }


        return row;
    }
}
