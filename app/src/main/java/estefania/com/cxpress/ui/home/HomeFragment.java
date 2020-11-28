package estefania.com.cxpress.ui.home;

import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProviders;

import estefania.com.cxpress.AbarrotesActivity;
import estefania.com.cxpress.CarnesActivity;
import estefania.com.cxpress.FrutasyVerdurasActivity;
import estefania.com.cxpress.MariscosActivity;
import estefania.com.cxpress.R;


public class HomeFragment extends Fragment {

    private HomeViewModel homeViewModel;
    ImageButton btnFyv;
    ImageButton btnCarnes;
    ImageButton btnMariscos;
    ImageButton btnAbarrotes;

    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {
        homeViewModel =
                ViewModelProviders.of(this).get(HomeViewModel.class);
        View root = inflater.inflate(R.layout.fragment_home, container, false);
        btnFyv= root.findViewById(R.id.frutyverd);
        btnCarnes= root.findViewById(R.id.carnes);
        btnMariscos= root.findViewById(R.id.mariscos);
        btnAbarrotes= root.findViewById(R.id.abarrotes);

      btnFyv.setOnClickListener(new View.OnClickListener() {
          @Override
          public void onClick(View v) {
              Intent i = new Intent(getContext(), FrutasyVerdurasActivity.class);
              startActivity(i);
          }
      });

        btnCarnes.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i = new Intent(getContext(), CarnesActivity.class);
                startActivity(i);
            }
        });

        btnMariscos.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i = new Intent(getContext(), MariscosActivity.class);
                startActivity(i);
            }
        });

        btnAbarrotes.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i = new Intent(getContext(), AbarrotesActivity.class);
                startActivity(i);
            }
        });
        return root;
    }


}