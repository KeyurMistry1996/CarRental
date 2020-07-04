package com.example.carrental.admin;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.cardview.widget.CardView;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.example.carrental.R;
import com.firebase.ui.firestore.FirestoreRecyclerAdapter;
import com.firebase.ui.firestore.FirestoreRecyclerOptions;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.Query;
import com.squareup.picasso.Picasso;

public class ManageUser extends AppCompatActivity {
    RecyclerView recyclerView;
    FirebaseFirestore rootRef;
    private FirestoreRecyclerAdapter<UserModel, UserViewHolder> adapter;
    ImageView back;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_manage_user);

       recyclerView  = findViewById(R.id.recycleView);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));



       rootRef = FirebaseFirestore.getInstance();
        Query query = rootRef.collection("user").orderBy("name", Query.Direction.ASCENDING);;

        FirestoreRecyclerOptions<UserModel> options = new FirestoreRecyclerOptions.Builder<UserModel>()
                .setQuery(query, UserModel.class)
                .build();

        adapter = new FirestoreRecyclerAdapter<UserModel, UserViewHolder>(options) {


            @NonNull
            @Override
            public UserViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
                View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_product, parent, false);
                return new UserViewHolder(view);
            }

            @Override
            protected void onBindViewHolder(@NonNull UserViewHolder holder, final int position, @NonNull final UserModel model) {
                holder.setName(model.getName());
                final String documentId = getSnapshots().getSnapshot(position).getId();
                final String name = (String) getSnapshots().getSnapshot(position).get("name");
                String email = (String) getSnapshots().getSnapshot(position).get("email");
                String number = (String) getSnapshots().getSnapshot(position).get("number");
                Picasso.with(getApplicationContext())
                        .load(model.getProfileurl())
                        .placeholder(R.drawable.ic_person_black_24dp)
                        .into(holder.profile);
                final UserInfo userInfo = new UserInfo(documentId,name,email,number);
                holder.cardView.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {

                        Intent intent = new Intent(getApplicationContext(),User_Info.class);
                        intent.putExtra("name",userInfo.getName());
                        intent.putExtra("email",userInfo.getEmail());
                        intent.putExtra("number",userInfo.getNumber());
                        intent.putExtra("id",documentId);
                        intent.putExtra("image",model.getProfileurl());
                        startActivity(intent);

                    }
                });
                holder.imageView.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {

                        new AlertDialog.Builder(ManageUser.this)
                                .setTitle("Delete "+name)
                                .setMessage("Are you sure you want to delete "+name+"?")
                                .setPositiveButton(android.R.string.yes, new DialogInterface.OnClickListener() {
                                    public void onClick(DialogInterface dialog, int which) {
                                        // Continue with delete operation
                                        DocumentReference reference = rootRef.collection("user").document(documentId);
                                        reference.delete();
                                        Toast.makeText(ManageUser.this, "Deleted", Toast.LENGTH_SHORT).show();
                                    }
                                })

                                // A null listener allows the button to dismiss the dialog and take no further action.
                                .setNegativeButton(android.R.string.no, null)
                                .setIcon(R.drawable.alert_icon)
                                .show();




                    }
                });


            }
        };
        recyclerView.setAdapter(adapter);

    }

    @Override
    protected void onStart() {
        super.onStart();
        adapter.startListening();
    }

    @Override
    protected void onStop() {
        super.onStop();

        if (adapter != null) {
            adapter.stopListening();
        }
    }

    public class UserViewHolder extends RecyclerView.ViewHolder {
        private View view;
        ImageView imageView,profile;
        CardView cardView;

        UserViewHolder(View itemView) {
            super(itemView);
            view = itemView;
            imageView = itemView.findViewById(R.id.deletebtn);
            profile = itemView.findViewById(R.id.user_image);
            cardView = itemView.findViewById(R.id.card);

        }



        void setName(String userName) {
            TextView textView = view.findViewById(R.id.text_view);
            textView.setText(userName);

        }
    }
}
