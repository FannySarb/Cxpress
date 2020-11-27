package estefania.com.cxpress.ui.frutasyverduras;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProviders;

import estefania.com.cxpress.R;

public class FyVFragment extends Fragment {

    private FyVViewModel fyVViewModel;

    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {
        fyVViewModel =
                ViewModelProviders.of(this).get(FyVViewModel.class);
        View root = inflater.inflate(R.layout.fragment_fyv, container, false);


        return root;
    }
}