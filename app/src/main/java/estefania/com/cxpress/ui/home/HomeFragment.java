package estefania.com.cxpress.ui.home;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProviders;

import estefania.com.cxpress.R;


public class HomeFragment extends Fragment {

    private HomeViewModel homeViewModel;
    ImageButton btnFyv;
    ImageButton btnCarnes;
    ImageButton btnMariscos;

    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {
        homeViewModel =
                ViewModelProviders.of(this).get(HomeViewModel.class);
        View root = inflater.inflate(R.layout.fragment_home, container, false);
        btnFyv= root.findViewById(R.id.frutyverd);
        btnCarnes= root.findViewById(R.id.carnes);
        btnMariscos= root.findViewById(R.id.mariscos);
        //btnAbarrotes= root.findViewById(R.id.frutyverd);

      btnFyv.setOnClickListener(new View.OnClickListener() {
          @Override
          public void onClick(View v) {
              Toast.makeText(getContext(), "Negocios que venden frutas y verduras", Toast.LENGTH_SHORT).show();
          }
      });

        btnCarnes.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Toast.makeText(getContext(), "Negocios que venden carnes", Toast.LENGTH_SHORT).show();
            }
        });

        btnMariscos.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Toast.makeText(getContext(), "Negocios que venden mariscos", Toast.LENGTH_SHORT).show();
            }
        });

        /*btnAbarrotes.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Toast.makeText(getContext(), "Negocios que venden abarrotes", Toast.LENGTH_SHORT).show();
            }
        });*/
        return root;
    }


}