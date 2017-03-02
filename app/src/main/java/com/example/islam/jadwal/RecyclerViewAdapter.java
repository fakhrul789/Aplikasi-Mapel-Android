package com.example.islam.jadwal;

import android.animation.AnimatorInflater;
import android.animation.StateListAnimator;
import android.app.Dialog;
import android.content.Context;
import android.os.Build;
import android.support.v7.widget.CardView;
import android.support.v7.widget.RecyclerView;
import android.view.ContextMenu;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;


/**
 * Created by Islam on 25/02/2016.
 */
public class RecyclerViewAdapter extends RecyclerView.Adapter<RecyclerViewAdapter.ViewHolder> {

    public class ViewHolder extends RecyclerView.ViewHolder implements View.OnCreateContextMenuListener{
        TextView jam;
        TextView mapel;
        TextView guru;
        CardView cardView;

        public ViewHolder(View itemView) {
            super(itemView);
            jam = (TextView) itemView.findViewById(R.id.Vjam);
            mapel = (TextView) itemView.findViewById(R.id.Vmapel);
            guru = (TextView) itemView.findViewById(R.id.Vguru);
            cardView = (CardView) itemView.findViewById(R.id.card_view);
            cardView.setOnCreateContextMenuListener(this);
            if(Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP){
                StateListAnimator stateListAnimator = AnimatorInflater.loadStateListAnimator(context,R.anim.on_touch);
                cardView.setStateListAnimator(stateListAnimator);
            }
        }

        @Override
        public void onCreateContextMenu(ContextMenu menu, View v, ContextMenu.ContextMenuInfo menuInfo) {
            menu.add(Menu.NONE,1,0,"Edit").setOnMenuItemClickListener(new MenuItem.OnMenuItemClickListener() {
                @Override
                public boolean onMenuItemClick(MenuItem item) {
                    ArrayList<Object> jaam = jadwal.get(getAdapterPosition());
                    Object data = jaam.get(0);
                    ambilBaris(Long.parseLong(data.toString()));
                    return false;
                }
            });
            menu.add(Menu.NONE, 2, 0, "Hapus").setOnMenuItemClickListener(new MenuItem.OnMenuItemClickListener() {
                @Override
                public boolean onMenuItemClick(MenuItem item) {
                    ArrayList<Object> jaam = jadwal.get(getAdapterPosition());
                    Object data = jaam.get(0);
                    dm.delete(Long.parseLong(data.toString()),hari);
                    Toast.makeText(context,"berhasil",Toast.LENGTH_SHORT).show();
                    return false;
                }
            });
        }
    }

    ArrayList<ArrayList<Object>> jadwal;
    String hari;
    Context context;
    DatabaseManager dm;

    RecyclerViewAdapter(ArrayList<ArrayList<Object>> jadwal, String hari, Context context) {
        this.jadwal = jadwal;
        this.hari = hari;
        this.context = context;
        this.dm = new DatabaseManager(context);
    }

    @Override
    public void onAttachedToRecyclerView(RecyclerView recyclerView) {
        super.onAttachedToRecyclerView(recyclerView);
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(parent.getContext()).inflate(R.layout.fragment_item, parent, false);
        ViewHolder vh = new ViewHolder(v);
        return vh;
    }

    @Override
    public void onBindViewHolder(ViewHolder holder, int position) {
        for (int i = 0; i <= position; i++) {
            ArrayList<Object> data = jadwal.get(i);
            holder.jam.setText(data.get(1).toString());
            holder.mapel.setText(data.get(2).toString());
            holder.guru.setText(data.get(3).toString());
        }
    }

    @Override
    public int getItemCount() {
        return jadwal.size();
    }

    private void ambilBaris(final Long data){
        ArrayList<Object> baris;

        final Dialog dialog = new Dialog(context);
        dialog.setContentView(R.layout.add_layout);

        final EditText id = (EditText)dialog.findViewById(R.id.id);
        id.setEnabled(false);
        final EditText jam = (EditText)dialog.findViewById(R.id.jam);
        final EditText mapel = (EditText)dialog.findViewById(R.id.mapel);
        final EditText guru = (EditText)dialog.findViewById(R.id.guru);

        try{
            baris = dm.ambilBaris(data,hari);
            id.setText(baris.get(0).toString());
            jam.setText(baris.get(1).toString());
            mapel.setText(baris.get(2).toString());
            guru.setText(baris.get(3).toString());
        }catch (Exception e){
            e.printStackTrace();
            Toast.makeText(context,e.toString(),Toast.LENGTH_SHORT).show();
        }

        Button save = (Button)dialog.findViewById(R.id.simpan);
        save.setText("Update");
        save.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dm.update(data, jam.getText().toString(), mapel.getText().toString(), guru.getText().toString(), hari);
                Toast.makeText(context,"Update Berhasil",Toast.LENGTH_SHORT).show();
                dialog.dismiss();
            }
        });
        Button cancel = (Button)dialog.findViewById(R.id.batal);
        cancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dialog.dismiss();
            }
        });
        dialog.show();
    }
}
