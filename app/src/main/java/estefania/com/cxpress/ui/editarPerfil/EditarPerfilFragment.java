package estefania.com.cxpress.ui.editarPerfil;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.Nullable;
import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProviders;

import estefania.com.cxpress.R;
import estefania.com.cxpress.login.LoginActivity;

public class EditarPerfilFragment extends Fragment {

    private EditarPerfilViewModel editarPerfilViewModel;

    ImageView imgFoto;
    Button btnFoto;

    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {
        editarPerfilViewModel = ViewModelProviders.of(this).get(EditarPerfilViewModel.class);
        View root = inflater.inflate(R.layout.fragment_editar_perfil, container, false);

        btnFoto= root.findViewById(R.id.btnFoto);
        imgFoto=root.findViewById(R.id.foto);


        btnFoto.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
               mostrarOpcionesFoto();
            }
        });

        return root;


    }

    private void mostrarOpcionesFoto() {
        final CharSequence[] opciones={"Tomar Foto", "Elegir foto de la galería", "Cancelar"};
        final AlertDialog.Builder opc= new AlertDialog.Builder(getContext());
        opc.setTitle("Elige una opción");
       opc.setItems(opciones, new DialogInterface.OnClickListener() {
           @Override
           public void onClick(DialogInterface dialog, int i) {
               if(opciones[i].equals("Tomar Foto"))
               {
                   Intent intent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);

                       startActivityForResult(intent.createChooser(intent, "Selecciona una opción"), 20);

               }
               else
               {
                   if(opciones[i].equals("Elegir foto de la galería"))
                   {
                        Intent intent=new Intent(Intent.ACTION_PICK,
                                MediaStore.Images.Media.EXTERNAL_CONTENT_URI);
                        intent.setType("image/");
                        startActivityForResult(intent.createChooser(intent, "Selecciona una opción"), 10);
                   }
                   else
                   {
                      dialog.dismiss();
                   }
               }
           }
       });
       opc.show();

    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        switch (requestCode)
        {
            case 10:
                Uri miPath=data.getData();
                imgFoto.setImageURI(miPath);
                break;
            case 20:

                break;
        }
    }
}