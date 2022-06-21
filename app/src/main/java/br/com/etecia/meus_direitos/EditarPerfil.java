package br.com.etecia.meus_direitos;

import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.util.Base64;
import android.view.View;
import android.widget.Button;
import android.widget.CompoundButton;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.google.android.material.chip.Chip;

import java.io.ByteArrayOutputStream;
import java.io.FileNotFoundException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

public class EditarPerfil extends AppCompatActivity {

    ImageView voltar, fotoPerfil;
    TextView editarFoto;
    EditText nomeAdvogado, email, telefone, registroOAB, bibliografia;
    Chip civil, consumidor, trabalhista, penal, empresarial, ambiental, ti, contratual, tributario;
    ArrayList<String> selectedChipData;
    Button alterarPerfil;
    Spinner estado, cidade;

    String url = "";
    Uri seleteduri;
    Bitmap bitmap;
    String encodeImage;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_editar_perfil);

        voltar = findViewById(R.id.imgVoltar);
        fotoPerfil = findViewById(R.id.fotoPerfil);
        nomeAdvogado = findViewById(R.id.nomeAdvogado);
        email = findViewById(R.id.email);
        telefone = findViewById(R.id.telefone);
        cidade = findViewById(R.id.alterarCidade);
        estado = findViewById(R.id.alterarEstado);
        registroOAB = findViewById(R.id.registroOAB);
        bibliografia = findViewById(R.id.bibliografia);

        editarFoto = findViewById(R.id.editarFoto);

        civil = findViewById(R.id.chipCivil);
        consumidor = findViewById(R.id.chipConsumidor);
        trabalhista = findViewById(R.id.chipTrabalhista);
        penal = findViewById(R.id.chipPenal);
        empresarial = findViewById(R.id.chipEmpresarial);
        ambiental = findViewById(R.id.chipAmbiental);
        ti = findViewById(R.id.chipTI);
        contratual = findViewById(R.id.chipContratual);
        tributario = findViewById(R.id.chipTributário);

        alterarPerfil = findViewById(R.id.AlterarPerfil);
        selectedChipData = new ArrayList<>();

        CompoundButton.OnCheckedChangeListener checkedChangeListener = new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {

                if (isChecked){
                    selectedChipData.add(buttonView.getText().toString());
                }
                else{
                    selectedChipData.remove(buttonView.getText().toString());
                }
            }
        };

        civil.setOnCheckedChangeListener(checkedChangeListener);
        consumidor.setOnCheckedChangeListener(checkedChangeListener);
        trabalhista.setOnCheckedChangeListener(checkedChangeListener);
        penal.setOnCheckedChangeListener(checkedChangeListener);
        empresarial.setOnCheckedChangeListener(checkedChangeListener);
        ambiental.setOnCheckedChangeListener(checkedChangeListener);
        ti.setOnCheckedChangeListener(checkedChangeListener);
        contratual.setOnCheckedChangeListener(checkedChangeListener);
        tributario.setOnCheckedChangeListener(checkedChangeListener);

        voltar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(getApplicationContext(), PerfilAdvogado_Adv.class);
                startActivity(intent);
                finish();
            }
        });

        editarFoto.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                

            }
        });

        alterarPerfil.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                StringRequest request = new StringRequest(Request.Method.POST, url, new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {


                        Toast.makeText(EditarPerfil.this, response.toString(), Toast.LENGTH_SHORT).show();
                    }
                }, new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {

                        Toast.makeText(EditarPerfil.this, error.toString(), Toast.LENGTH_SHORT).show();

                    }
                }){

                    @Nullable
                    @Override
                    protected Map<String, String> getParams() throws AuthFailureError {

                        Map<String, String> param = new HashMap<String, String>();

                        param.put("imagem", encodeImage);


                        return param;
                    }
                };

                RequestQueue requestQueue = Volley.newRequestQueue(EditarPerfil.this);
                requestQueue.add(request);

                Intent intent = new Intent(getApplicationContext(), PerfilAdvogado_Adv.class);
                intent.putExtra("areas", selectedChipData.toString());
                setResult(101, intent);
                startActivity(intent);
                finish();
            }
        });
    }

    private  void escolhendoImagem(){
        Intent intent = new Intent(Intent.ACTION_PICK, MediaStore.Images.Media.EXTERNAL_CONTENT_URI);
        intent.setType("image/*");
        startActivityForResult(intent,1);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if (requestCode == 1 || requestCode == RESULT_OK || data != null || data.getData() != null){

            seleteduri = data.getData();

            try {
                InputStream inputStream = getContentResolver().openInputStream(seleteduri);
                bitmap = BitmapFactory.decodeStream(inputStream);
                fotoPerfil.setImageBitmap(bitmap);
                imageStore(bitmap);


            } catch (FileNotFoundException e) {
                e.printStackTrace();
            }

        }
    }

    private void imageStore(Bitmap bitmap) {
        ByteArrayOutputStream stream = new ByteArrayOutputStream();
        bitmap.compress(Bitmap.CompressFormat.JPEG, 100, stream);

        byte [] imagebyte = stream.toByteArray();

        encodeImage = Base64.encodeToString(imagebyte, Base64.DEFAULT);
    }

}