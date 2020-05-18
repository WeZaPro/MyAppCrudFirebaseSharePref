package example.com.myappcrudfirebase;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.Toast;

import com.google.firebase.FirebaseApp;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.List;

public class MainActivity extends AppCompatActivity {

    EditText txtID, txtName, txtSurname;
    ListView list;
    List<Data> listSee = new ArrayList<Data>();
    DatabaseReference ref;
    Data dat;
    SharedPreferences sharedPref;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        txtID = findViewById(R.id.txtID);
        txtName = findViewById(R.id.txtName);
        txtSurname = findViewById(R.id.txtSurname);
        list = findViewById(R.id.listData);
        FirebaseApp.initializeApp(this);
        FirebaseDatabase instance = FirebaseDatabase.getInstance();
        ref = instance.getReference();
        read();

        list.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                dat = (Data) parent.getItemAtPosition(position);
                txtID.setText(dat.getID());
                txtName.setText(dat.getName());
                txtSurname.setText(dat.getSurname());


            }
        });
    }

    private void read() {
        ref.child("Data").addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                listSee.clear();
                Data d;
                for(DataSnapshot obj : dataSnapshot.getChildren()) {
                    d = obj.getValue(Data.class);
                    listSee.add(d);
                }
                ArrayAdapter<Data> arrayAdapter = new  ArrayAdapter<Data>(MainActivity.this,android.R.layout.simple_list_item_1, listSee);
                list.setAdapter(arrayAdapter);
            }
            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {
            }
        });
    }

    public void save(View v){
        Data data = new Data();
        data.setID(txtID.getText().toString());
        data.setName(txtName.getText().toString());
        data.setSurname(txtSurname.getText().toString());
        ref.child("Data").child(data.getID()).setValue(data);
        Toast.makeText(this,"save data.",Toast.LENGTH_SHORT).show();

        sharedPref = getSharedPreferences(Constance.MY_PREFS, Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = sharedPref.edit();
        editor.putString("TEST TEXT", "123");
        editor.commit();


        // sharePref
        sharedPref = getSharedPreferences(Constance.MY_PREFS, Context.MODE_PRIVATE);
        String tokenValue = sharedPref.getString("TEST TEXT", "123");
        Log.d("token", "get token: " + tokenValue);

        clearAll();
    }

    public void update(View v){
        Data data = new Data();
        data.setID(dat.getID());
        data.setName(txtName.getText().toString());
        data.setSurname(txtSurname.getText().toString());
        ref.child("Data").child(data.getID()).setValue(data);
        Toast.makeText(this,"Actualizado.",Toast.LENGTH_SHORT).show();
        clearAll();
    }

    public void delete(View v){
        ref.child("Data").child(dat.getID()).removeValue();
        Toast.makeText(this,"Eliminado.",Toast.LENGTH_SHORT).show();
        clearAll();
    }

    void clearAll(){
        txtID.setText("");
        txtName.setText("");
        txtSurname.setText("");
    }

    private void sendRegistrationToServer(String ss) {

        SharedPreferences sharedPref = getSharedPreferences(Constance.MY_PREFS, Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = sharedPref.edit();
        editor.putString(Constance.MY_PREFS, "Test");
        editor.commit();

        Log.d("token", "send token: " + "");

    }




}
