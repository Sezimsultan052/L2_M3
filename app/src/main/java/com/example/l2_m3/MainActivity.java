package com.example.l2_m3;

import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.annotation.SuppressLint;
import android.app.Application;
import android.app.Fragment;
import android.content.ContentResolver;
import android.database.Cursor;
import android.os.Bundle;
import android.provider.ContactsContract;
import android.view.View;
import android.widget.Button;
import android.widget.ListView;
import android.widget.SimpleCursorAdapter;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.List;
import java.util.ListResourceBundle;

public class MainActivity extends AppCompatActivity {
    RecyclerView rvContacts;
    ContactsAdapter adapter;
    Button btnOpenFragment;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        rvContacts = findViewById(R.id.rv_contacts);
        getAllContacts();
        //initRecyclerView();

        initViews();
    }

    private void initViews() {
        btnOpenFragment = findViewById(R.id.btn_open_fragment);
        btnOpenFragment.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                FragmentManager manager = getSupportFragmentManager();
                FragmentTransaction transaction = manager.beginTransaction();
                transaction.replace(R.id.fragment_container, FirstFtagment.newInstance("ss", "sss"));
                transaction.addToBackStack("FirstFragment");
                transaction.commit();

            }
        });
    }


    private void getAllContacts() {
        List<ContactsModel> contactsList = new ArrayList();
        ContactsModel contactsModel;

        ContentResolver contentResolver = getContentResolver();
        Cursor cursor = contentResolver.query(ContactsContract.Contacts.CONTENT_URI, null, null, null, ContactsContract.CommonDataKinds.Phone.DISPLAY_NAME + " ASC");
        if (cursor.getCount() > 0) {
            while (cursor.moveToNext()) {

                @SuppressLint("Range") int hasPhoneNumber = Integer.parseInt(cursor.getString(cursor.getColumnIndex(ContactsContract.Contacts.HAS_PHONE_NUMBER)));
                if (hasPhoneNumber > 0) {
                    @SuppressLint("Range") String id = cursor.getString(cursor.getColumnIndex(ContactsContract.Contacts._ID));
                    @SuppressLint("Range") String name = cursor.getString(cursor.getColumnIndex(ContactsContract.Contacts.DISPLAY_NAME));

                    contactsModel = new ContactsModel();
                    contactsModel.setName(name);

                    Cursor phoneCursor = contentResolver.query(
                            ContactsContract.CommonDataKinds.Phone.CONTENT_URI,
                            null,
                            ContactsContract.CommonDataKinds.Phone.CONTACT_ID + " = ?",
                            new String[]{id},
                            null);
                    if (phoneCursor.moveToNext()) {
                        @SuppressLint("Range") String phoneNumber = phoneCursor.getString(phoneCursor.getColumnIndex(ContactsContract.CommonDataKinds.Phone.NUMBER));
                        contactsModel.setPhone(phoneNumber);
                    }

                    phoneCursor.close();

                    contactsList.add(contactsModel);
                }
            }

            ContactsAdapter contactAdapter = new ContactsAdapter(getApplicationContext(), contactsList);
            rvContacts.setLayoutManager(new LinearLayoutManager(this));
            rvContacts.setAdapter(contactAdapter);
        }



//    private void initRecyclerView() {
//        //rvContacts = findViewById(R.id.rv_contacts);
//        //rvContacts.setHasFixedSize(true);
//        rvContacts.setLayoutManager(new LinearLayoutManager(this));
//        adapter = new ContactsAdapter(this,getContacts());
//         rvContacts.setAdapter(adapter);
//
////        List<ContactsModel> list = new ArrayList<>();
////        list.add(new ContactsModel("aaaaaa", "5555555"));
////        list.add(new ContactsModel("aaaaaa", "5555555"));
////        list.add(new ContactsModel("aaaaaa", "5555555"));
////        list.add(new ContactsModel("aaaaaa", "5555555"));
////        list.add(new ContactsModel("aaaaaa", "5555555"));
////        list.add(new ContactsModel("aaaaaa", "5555555"));
////        list.add(new ContactsModel("aaaaaa", "5555555"));
//
//
//    }

//    @SuppressLint("Range")
//    private ArrayList<ContactsModel> getContacts() {
//
//        List<ContactsModel> list = new ArrayList<>();
//        Cursor cursor = getContentResolver().query(ContactsContract.CommonDataKinds.Phone.CONTENT_URI, null, null, null, ContactsContract.Contacts.DISPLAY_NAME+ "ASC");
//        cursor.moveToFirst();
//
//        while(cursor.moveToNext()){
//            list.add(new ContactsModel(cursor.getString(cursor.getColumnIndex(ContactsContract.CommonDataKinds.Phone.DISPLAY_NAME)),
//                    cursor.getString(cursor.getColumnIndex(ContactsContract.CommonDataKinds.Phone.NUMBER))));
//
//        }
//
//
//        return (ArrayList<ContactsModel>) list;
//        Cursor cursor = getContentResolver().query(ContactsContract.CommonDataKinds.Phone.CONTENT_URI, null, null, null, null);
//        startManagingCursor(cursor);
//
//        String[] from = {ContactsContract.CommonDataKinds.Phone.DISPLAY_NAME, ContactsContract.CommonDataKinds.Phone.NUMBER,ContactsContract.CommonDataKinds.Phone._ID };
//        int[] to = {android.R.id.text1, android.R.id.text2};
//
//        SimpleCursorAdapter simpleCursorAdapter = new SimpleCursorAdapter(MainActivity.this, android.R.layout.simple_list_item_2, cursor, from, to);
//        lv_contacts.setAdapter(simpleCursorAdapter);
//        lv_contacts.setChoiceMode(ListView.CHOICE_MODE_MULTIPLE);
//        Cursor cursor = getContentResolver().query(ContactsContract.Contacts.CONTENT_URI, null, null, null, ContactsContract.CommonDataKinds.Phone.NUMBER);
//        ArrayList<ContactsModel> list = new ArrayList<>();
//        if(cursor.getCount() >0 ){
//            while(cursor.moveToNext()){
//
//                @SuppressLint("Range") String id = cursor.getString(cursor.getColumnIndex(ContactsContract.Contacts._ID));
//                @SuppressLint("Range") String name = cursor.getString(cursor.getColumnIndex(ContactsContract.Contacts.DISPLAY_NAME));
//                @SuppressLint("Range") String number = cursor.getString(cursor.getColumnIndex(ContactsContract.Contacts.HAS_PHONE_NUMBER));
//
//                if(number.length() > 0) {
//                    Cursor phoneCursor  = getContentResolver().query(ContactsContract.CommonDataKinds.Phone.CONTENT_URI, null , ContactsContract.CommonDataKinds.Phone.CONTACT_ID +
//                            "=?" , new String[]{id}, null);
//
//                    if(phoneCursor.getCount()>0){
//                        while(phoneCursor.moveToNext()){
//
//                            @SuppressLint("Range") String phoneNumber = phoneCursor.getString(phoneCursor.getColumnIndex(ContactsContract.CommonDataKinds.Phone.NUMBER));
//
//                            ContactsModel con = new ContactsModel(name, phoneNumber);
//
//                            list.add(con);
//
//                        }
//                    }
//                    phoneCursor.close();
//                }
//
//            }
//
//
//        }
//        else
//        {
//            Toast.makeText(getApplicationContext(), " no contacts found", Toast.LENGTH_SHORT).show();
//        }
//
//      return  list;
    }
}