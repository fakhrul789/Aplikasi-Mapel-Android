package com.example.islam.jadwal;

import android.app.Dialog;
import android.content.Context;
import android.os.Bundle;
import android.os.Handler;
import android.support.annotation.Nullable;
import android.support.design.widget.FloatingActionButton;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentTransaction;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import java.util.ArrayList;

/**
 * Created by Islam on 23/02/2016.
 */
public class JumatFragment extends Fragment implements View.OnClickListener{
    public static final String hari = "jumat";
    Context context;
    RecyclerView.Adapter adapter;
    DatabaseManager dm;
    SwipeRefreshLayout refreshLayout;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View v = inflater.inflate(R.layout.content_fragment, container, false);
        context = container.getContext();
        dm = new DatabaseManager(context);

        RecyclerView recyclerView = (RecyclerView)v.findViewById(R.id.recyclerView);
        FloatingActionButton fab = (FloatingActionButton)v.findViewById(R.id.fab);
        fab.setOnClickListener(this);

        ArrayList<ArrayList<Object>> jadwal = dm.ambilData(hari);

        LinearLayoutManager llm = new LinearLayoutManager(context);
        recyclerView.setLayoutManager(llm);
        recyclerView.setHasFixedSize(true);

        adapter = new RecyclerViewAdapter(jadwal,hari,context);
        recyclerView.setAdapter(adapter);

        refreshLayout = (SwipeRefreshLayout)v.findViewById(R.id.swipe_layout);
        refreshLayout.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                refreshContent();
            }
        });
        return v;
    }

    @Override
    public void onClick(View v) {
        final Dialog dialog = new Dialog(context);
        dialog.setContentView(R.layout.add_layout);
        final EditText id = (EditText)dialog.findViewById(R.id.id);
        final EditText jam = (EditText)dialog.findViewById(R.id.jam);
        final EditText mapel = (EditText)dialog.findViewById(R.id.mapel);
        final EditText guru = (EditText)dialog.findViewById(R.id.guru);
        Button save = (Button)dialog.findViewById(R.id.simpan);
        Button cancel = (Button)dialog.findViewById(R.id.batal);

        save.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                try {
                    dm.tambahData((int) Long.parseLong(id.getText().toString()), jam.getText().toString(), mapel.getText().toString(), guru.getText().toString(), hari);
                    Toast.makeText(context, "Berhasil Disimpan", Toast.LENGTH_SHORT).show();
                } catch (Exception e) {
                    e.printStackTrace();
                    Toast.makeText(context, "gagal disimpan " + e.toString(), Toast.LENGTH_SHORT).show();
                }
                dialog.dismiss();
            }
        });
        cancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                dialog.dismiss();
            }
        });
        dialog.show();
    }

    private void refreshContent(){
        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {
                FragmentTransaction ft = getFragmentManager().beginTransaction();
                ft.detach(JumatFragment.this).attach(JumatFragment.this).commit();
                refreshLayout.setRefreshing(false);
            }
        }, 1000);
    }
}
