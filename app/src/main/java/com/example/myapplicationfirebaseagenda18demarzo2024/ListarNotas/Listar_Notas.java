package com.example.myapplicationfirebaseagenda18demarzo2024.ListarNotas;


import androidx.annotation.NonNull;
import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.app.AlertDialog;
import android.app.Dialog;
import android.content.DialogInterface;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.Toast;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.Query;
import com.google.firebase.database.ValueEventListener;
import com.example.myapplicationfirebaseagenda18demarzo2024.Objetos.Nota;
import com.example.myapplicationfirebaseagenda18demarzo2024.R;
import com.firebase.ui.database.FirebaseRecyclerAdapter;
import com.example.myapplicationfirebaseagenda18demarzo2024.ViewHolder.ViewHolder_Nota;
import com.firebase.ui.database.FirebaseRecyclerOptions;

import org.jetbrains.annotations.NotNull;

public class Listar_Notas extends AppCompatActivity {

    RecyclerView recyclerviewNotas;
    FirebaseDatabase firebaseDatabase;
    DatabaseReference BASE_DE_DATOS;

    LinearLayoutManager linearLayoutManager;

    FirebaseRecyclerAdapter<Nota, ViewHolder_Nota> firebaseRecyclerAdapter;
    FirebaseRecyclerOptions<Nota> options;

    Dialog dialog;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_listar_notas);

        ActionBar actionBar = getSupportActionBar();
        actionBar.setTitle("Mis notas");
        actionBar.setDisplayHomeAsUpEnabled(true);
        actionBar.setDisplayShowHomeEnabled(true);


        recyclerviewNotas = findViewById(R.id.recyclerviewNotas);
        recyclerviewNotas.setHasFixedSize(true);

        firebaseDatabase = FirebaseDatabase.getInstance();
        BASE_DE_DATOS = firebaseDatabase.getReference("Notas_Publicadas");
        dialog = new Dialog(Listar_Notas.this);
        ListarNotasUsuarios();

    }

    private void ListarNotasUsuarios() {
        options = new FirebaseRecyclerOptions.Builder<Nota>().setQuery(BASE_DE_DATOS, Nota.class).build();
        firebaseRecyclerAdapter = new FirebaseRecyclerAdapter<Nota, ViewHolder_Nota>(options) {
            @Override
            protected void onBindViewHolder(@NonNull ViewHolder_Nota viewHolder_nota, int position, @NotNull Nota nota) {
                viewHolder_nota.SetearDatos(
                        getApplicationContext(),
                        nota.getId_nota(),
                        nota.getUid_usuario(),
                        nota.getCorreo_usuario(),
                        nota.getFecha_hora_actual(),
                        nota.getTitulo(),
                        nota.getDescripcion(),
                        nota.getFecha_nota(),
                        nota.getEstado()
                );
            }


            @Override
            public ViewHolder_Nota onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
                View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_nota, parent, false);
                ViewHolder_Nota viewHolder_nota = new ViewHolder_Nota(view);
                viewHolder_nota.setOnClickListener(new ViewHolder_Nota.ClickListener() {
                    @Override
                    public void onItemClick(View view, int position) {
                        Toast.makeText(Listar_Notas.this, "en item Agenda click", Toast.LENGTH_SHORT).show();
                    }


                    @Override
                    public void onItemLongClick(View view, int position) {

                    }
                });
                return viewHolder_nota;
            }
        };

        linearLayoutManager = new LinearLayoutManager(Listar_Notas.this, LinearLayoutManager.VERTICAL, false);
        linearLayoutManager.setReverseLayout(true);
        linearLayoutManager.setStackFromEnd(true);

        recyclerviewNotas.setLayoutManager(linearLayoutManager);
        recyclerviewNotas.setAdapter(firebaseRecyclerAdapter);

    }


    @Override
    protected void onStart() {
        super.onStart();
        if (firebaseRecyclerAdapter != null) {
            firebaseRecyclerAdapter.startListening();
        }
    }

    @Override
    public boolean onSupportNavigateUp() {
        onBackPressed(); //onBackPressedDispatcher.onBackPressed();
        return super.onSupportNavigateUp();
    }
}