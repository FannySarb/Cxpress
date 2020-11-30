package estefania.com.cxpress.ui.CerrarSesion;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;

import estefania.com.cxpress.login.LoginActivity;

import static android.content.Context.MODE_PRIVATE;

public class CerrarSesionFragment extends Fragment {

    //private ShareViewModel shareViewModel;

    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {
       // shareViewModel =
               // ViewModelProviders.of(this).get(ShareViewModel.class);
        //View root = inflater.inflate(R.layout.fragment_share, container, false);
        //final TextView textView = root.findViewById(R.id.text_share);


        SharedPreferences sharedPreferences = getActivity().getSharedPreferences("Sesion", MODE_PRIVATE);
        sharedPreferences.edit().putInt("id", Integer.valueOf(0)).apply();

        Intent i = new Intent(getContext(), LoginActivity.class);
        startActivity(i);

        //return root;
        return null;
    }
}