package com.example.chatchops.Activities;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.Manifest;
import android.content.pm.PackageManager;
import android.database.Cursor;
import android.net.Uri;
import android.os.Bundle;
import android.provider.ContactsContract;
import android.widget.Toast;

import com.example.chatchops.Adapters.CallAdapter;
import com.example.chatchops.Models.ContactModel;
import com.example.chatchops.R;
import com.example.chatchops.databinding.ActivityCallBinding;
import com.example.chatchops.databinding.ActivityChatBinding;

import org.jetbrains.annotations.NotNull;

import java.util.ArrayList;

public class CallActivity extends AppCompatActivity {
    //ActivityCallBinding binding;

    RecyclerView recyclerView;
    ArrayList<ContactModel> arrayList=new ArrayList<ContactModel>();
    CallAdapter adapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_call);

        recyclerView=findViewById(R.id.recyclerView);

        //check permission
        checkPermission();

    }

    private void checkPermission() {
        //check condition
        if(ContextCompat.checkSelfPermission(CallActivity.this, Manifest.permission.READ_CONTACTS)
        != PackageManager.PERMISSION_GRANTED){
            //when permission is not granted
            //request permission
            ActivityCompat.requestPermissions(CallActivity.this,new String[]{Manifest.permission.READ_CONTACTS},100);
        }
        else{
            //when permission is granted
            //create method
            getContactList();
        }
    }

    private void getContactList() {
        //initialize uri

        Uri uri= ContactsContract.Contacts.CONTENT_URI;
        //sort by ascending
        String sort=ContactsContract.CommonDataKinds.Phone.DISPLAY_NAME+" ASC";
        //initialize cursor
        Cursor cursor=getContentResolver().query(uri,null,null,null,sort);

        //check condition
        if(cursor.getCount()>0){
            //when count is greater than 0
            //use while loop
            while (cursor.moveToNext()){
                //Cursor move to next
                //get contact id
                String id=cursor.getString(cursor.getColumnIndex(ContactsContract.Contacts._ID));
                //get contact name
                String name=cursor.getString(cursor.getColumnIndex(ContactsContract.Contacts.DISPLAY_NAME));

                //initialize phone uri
                Uri uriPhone=ContactsContract.CommonDataKinds.Phone.CONTENT_URI;
                //initialize selection
                String selection=ContactsContract.CommonDataKinds.Phone.CONTACT_ID+"=?";

                //initialize phone cursor
                Cursor phoneCursor=getContentResolver().query(uriPhone,null,selection,new String[]{id},null);
                //check condition
                if(phoneCursor.moveToNext()){
                    //when phone cursor move to next
                    String number=phoneCursor.getString(phoneCursor.getColumnIndex(ContactsContract.CommonDataKinds.Phone.NUMBER));

                    //initialize contact model
                    ContactModel model=new ContactModel();
                    //set name
                    model.setName(name);
                    //set number
                    model.setNumbers(number);

                    //add model in arraylist
                    arrayList.add(model);
                    //close phone cursor
                    phoneCursor.close();

                }



            }
            //close cursor
            cursor.close();
        }
        //set layout manager
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        //initialize adapter
        adapter=new CallAdapter(this,arrayList);

        //set adapter
        recyclerView.setAdapter(adapter);
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull @NotNull String[] permissions, @NonNull @NotNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        //check condition
        if (requestCode==100&&grantResults.length>0&&grantResults[0]==PackageManager.PERMISSION_GRANTED){
            //when permission is granted
            //call method
            getContactList();
        }
        else {
            //when permission is denied
            //display toast
            Toast.makeText(CallActivity.this,"Permission Denied.",Toast.LENGTH_SHORT).show();

            //call check permission method
            checkPermission();
        }
    }
}