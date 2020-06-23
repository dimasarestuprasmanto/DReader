package com.dimasarp.dreader;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.text.format.DateFormat;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.firebase.ui.auth.AuthUI;
import com.firebase.ui.database.FirebaseListAdapter;
import com.firebase.ui.database.FirebaseListOptions;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.android.material.snackbar.Snackbar;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.Query;

public class MangaChat extends AppCompatActivity {
    private static int SIGN_IN_REQUEST_CODE = 1;
    private FirebaseListAdapter<ChatMessage> adapter;
    RelativeLayout chat_activity;
    FloatingActionButton fab;

    //fungsi logout
    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        if(item.getItemId() == R.id.signout){
            AuthUI.getInstance().signOut(this).addOnCompleteListener(new OnCompleteListener<Void>() {
                @Override
                public void onComplete(@NonNull Task<Void> task) {
                    Snackbar.make(chat_activity,"succesfully Sign out", Snackbar.LENGTH_SHORT).show();
                    finish();
                }
            });
        }
        return super.onOptionsItemSelected(item);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.main_menu, menu);
        return true;
    }

    //cek login
    @Override
    public void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == SIGN_IN_REQUEST_CODE){
            if(requestCode == RESULT_OK){
                Snackbar.make(chat_activity,"Berhasil Login, Welcome", Snackbar.LENGTH_SHORT).show();
                displayChatMessage();
            }else{
                Snackbar.make(chat_activity,"Gagal Login, Welcome", Snackbar.LENGTH_SHORT).show();
                finish();
            }
        }
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_manga_chat);

        chat_activity = (RelativeLayout) findViewById(R.id.chat_activity);

        fab = (FloatingActionButton) findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                EditText input = (EditText) findViewById(R.id.message);
                FirebaseDatabase.getInstance().getReference().push().setValue(new ChatMessage(input.getText().toString(),
                        FirebaseAuth.getInstance().getCurrentUser().getEmail()));
                input.setText("");
            }
        });

        //cek apakah sudah sing in atau blm
        if(FirebaseAuth.getInstance().getCurrentUser() == null){
            startActivityForResult(AuthUI.getInstance().createSignInIntentBuilder().build(), SIGN_IN_REQUEST_CODE);
        }else{
            Snackbar.make(chat_activity,"Welcome" + FirebaseAuth.getInstance().getCurrentUser().getEmail(), Snackbar.LENGTH_SHORT).show();
            displayChatMessage();
        }

    }

    private void displayChatMessage(){
        ListView listofMessage = (ListView) findViewById(R.id.listview);
        Query query = FirebaseDatabase.getInstance().getReference().child("https://dreader-24af9.firebaseio.com/");
//The error said the constructor expected FirebaseListOptions - here you create them:
        FirebaseListOptions<ChatMessage> options = new FirebaseListOptions.Builder<ChatMessage>()
                .setQuery(query, ChatMessage.class)
                .setLayout(R.layout.list_item)
                .build();
        adapter = new FirebaseListAdapter<ChatMessage>(options){
            @Override
            protected void populateView(@NonNull View v, @NonNull ChatMessage model, int position) {
                //mendapatkan sumber tempat meletakkan isi di list_item
                TextView messageText, messageUser, messageTime;
                messageText = (TextView) v.findViewById(R.id.message_text);
                messageUser = (TextView) v.findViewById(R.id.message_user);
                messageTime = (TextView) v.findViewById(R.id.message_time);

                messageText.setText(model.getMessageText());
                messageUser.setText(model.getMessageUser());
                messageTime.setText(DateFormat.format("dd-MM-yyyy (HH:mm:ss)", model.getMessageTime()));
            }
        };
        listofMessage.setAdapter(adapter);

    }
}
