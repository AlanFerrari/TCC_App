package br.com.etecia.meus_direitos;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.cardview.widget.CardView;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;

import br.com.etecia.meus_direitos.objetos.ListAdvogados;

public class RecyclerAdapter extends RecyclerView.Adapter<RecyclerAdapter.ViewHolder>{
    private Context mContext;
    private ArrayList<ListAdvogados> mCartao;
    String[] nomes;
    String[] estados;
    String[] cidades;
    String[] areas;
    String[] fotos;

    public RecyclerAdapter(Context mContext, ArrayList<ListAdvogados> mCartao) {
        this.mContext = mContext;
        this.mCartao = mCartao;
    }

    public RecyclerAdapter(Context mContext, String[] nomes, String[] estados, String[] cidades, String[] areas, String[] fotos) {
        this.mContext = mContext;
        this.nomes = nomes;
        this.estados = estados;
        this.cidades = cidades;
        this.areas = areas;
        this.fotos = fotos;
    }


    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {

        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.modelo_lista_advogado, parent, false);

        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, @SuppressLint("RecyclerView") int position) {
        holder.nomeAdvogado.setText(mCartao.get(position).getNome());
        holder.cidade.setText(mCartao.get(position).getCidade());
        holder.estado.setText(mCartao.get(position).getEstado());
        holder.area_atuacao.setText(mCartao.get(position).getAreaAtuacao());
        holder.imagemPerfil.setImageResource(mCartao.get(position).getFotoPerfil());

        holder.cardView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(mContext, Perfil_Advogados_Cli.class);

                intent.putExtra("id", mCartao.get(position).getId());
                intent.putExtra("nome", mCartao.get(position).getNome());
                intent.putExtra("cidade", mCartao.get(position).getCidade());
                intent.putExtra("estado", mCartao.get(position).getEstado());
                intent.putExtra("areaAtuacao", mCartao.get(position).getAreaAtuacao());
                intent.putExtra("fotoPerfil", mCartao.get(position).getFotoPerfil());

                mContext.startActivity(intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK));
            }
        });
    }

    @Override
    public int getItemCount() {
        return mCartao.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder{

        ImageView imagemPerfil;
        TextView nomeAdvogado;
        TextView cidade;
        TextView estado;
        TextView area_atuacao;
        CardView cardView;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);

            imagemPerfil = itemView.findViewById(R.id.imagem_perfil);
            nomeAdvogado = itemView.findViewById(R.id.nomeAdvogado);
            cidade = itemView.findViewById(R.id.cidade);
            estado = itemView.findViewById(R.id.estado);
            area_atuacao = itemView.findViewById(R.id.nome_area_atuacao);
            cardView = itemView.findViewById(R.id.cardViewAdvogados);
        }
    }
}
