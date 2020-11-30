package estefania.com.cxpress;

import android.content.Intent;
import android.os.Bundle;

import androidx.fragment.app.FragmentActivity;

import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.BitmapDescriptorFactory;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.Marker;
import com.google.android.gms.maps.model.MarkerOptions;

import estefania.com.cxpress.ui.perfil.PerfilFragment;

public class MapsActivity extends FragmentActivity implements OnMapReadyCallback {

    private GoogleMap mMap;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_maps);
        // Obtain the SupportMapFragment and get notified when the map is ready to be used.
        SupportMapFragment mapFragment = (SupportMapFragment) getSupportFragmentManager()
                .findFragmentById(R.id.map);
        mapFragment.getMapAsync(this);
    }

    @Override
    public void onMapReady(GoogleMap googleMap) {
        mMap = googleMap;

        LatLng mS = new LatLng(19.0926989,-98.125484);
        MarkerOptions m0 = new MarkerOptions();
        m0.position(mS).title("Mercado Hnos. García");
        m0.icon(BitmapDescriptorFactory.fromResource(R.drawable.marker_mercado));
        mMap.addMarker(m0);


        LatLng mZ= new LatLng(19.0701337, -98.1765127);
        MarkerOptions m1 = new MarkerOptions();
        m1.position(mZ).title("Mercado Ignacio Zaragoza");
        m1.icon(BitmapDescriptorFactory.fromResource(R.drawable.marker_mercado));
        mMap.addMarker(m1);
        mMap.moveCamera(CameraUpdateFactory.newLatLng(mZ));

        LatLng cA = new LatLng(19.0868269, -98.1891781);
        MarkerOptions m2 = new MarkerOptions();
        m2.position(cA).title("Central de Abastos");
        m2.icon(BitmapDescriptorFactory.fromResource(R.drawable.marker_mercado));
        mMap.addMarker(m2);
        mMap.moveCamera(CameraUpdateFactory.newLatLng(mZ));

        LatLng yo;
        yo = new LatLng(19.1050356, -98.1556168);
        MarkerOptions m3= new MarkerOptions();
        m3.position(yo).title("Mi ubicación");
        m3.icon(BitmapDescriptorFactory.fromResource(R.drawable.miibucacion));
        mMap.addMarker(m3).showInfoWindow();
        mMap.moveCamera(CameraUpdateFactory.newLatLngZoom(yo, 12.5f));


        mMap.setOnInfoWindowClickListener(new GoogleMap.OnInfoWindowClickListener() {
            @Override
            public void onInfoWindowClick(Marker marker) {
                int idMarker = Integer.parseInt(marker.getId().substring(1));

                if(idMarker==3)
                {
                    Intent intent = new Intent(getApplicationContext(), PerfilFragment.class);
                    startActivity(intent);
                }

                else {

                        Intent intent = new Intent(getApplicationContext(), NegociosActivity.class);
                        intent.putExtra("idMercado", idMarker);
                        startActivity(intent);


                }
            }
        });


    }



}
