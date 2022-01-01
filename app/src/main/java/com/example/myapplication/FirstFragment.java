package com.example.myapplication;

import android.content.ContentResolver;
import android.content.Context;
import android.content.Intent;
import android.database.Cursor;
import android.net.Uri;
import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.provider.ContactsContract;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;

import com.example.myapplication.ui.main.ContactsRecViewAdapter;

import java.util.ArrayList;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link FirstFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class FirstFragment extends Fragment {

    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    public static RecyclerView contactsRecView;
    public static ArrayList<Contact> contacts = new ArrayList<>();
    private EditText editTextName;
    private EditText editTextNumber;

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;

    public FirstFragment() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment FirstFragment.
     */
    // TODO: Rename and change types and number of parameters
    public static FirstFragment newInstance(String param1, String param2) {
        FirstFragment fragment = new FirstFragment();
        Bundle args = new Bundle();
        args.putString(ARG_PARAM1, param1);
        args.putString(ARG_PARAM2, param2);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
//        setContentView(R.layout.activity_main);
//
//        contactsRecView = findViewById(R.id.contactsRecView);
//
////        contacts.add(new Contact("Margot Robbie", "010-1234-0000"));
////        contacts.add(new Contact("Cillian Murphy", "010-1234-1111"));
////        contacts.add(new Contact("Saoirse Ronan", "010-1234-2222"));
////        contacts.add(new Contact("Emma Watson", "010-1234-3333"));
////        contacts.add(new Contact("Cristian Bail", "010-1234-4444"));
//
//        contacts = getContacts(get);
//
//        ContactsRecViewAdapter adapter = new ContactsRecViewAdapter(this);
//        adapter.setContacts(contacts);
//
//        contactsRecView.setAdapter(adapter);
//        contactsRecView.setLayoutManager(new LinearLayoutManager(this));
//
//        editTextName = findViewById(R.id.editTextName);
//        editTextNumber = findViewById(R.id.editTextNumber);
//
//        findViewById(R.id.addButton).setOnClickListener(view -> {
//            String newName = editTextName.getText().toString();
//            String newNumber = editTextNumber.getText().toString();
//            Contact newContact = new Contact(newName, newNumber);
//            contacts.add(newContact);
//            adapter.notifyItemInserted(contacts.size()-1);
//
//            Intent contactIntent = new Intent(ContactsContract.Intents.Insert.ACTION);
//            contactIntent.setType(ContactsContract.RawContacts.CONTENT_TYPE);
//
//            contactIntent
//                    .putExtra(ContactsContract.Intents.Insert.NAME, newName)
//                    .putExtra(ContactsContract.Intents.Insert.PHONE, newNumber);
//
//            startActivity(contactIntent);
//        });
    }


    public ArrayList<Contact> getContacts(Context context){
        ContentResolver resolver = context.getContentResolver();

        Uri phoneUri = ContactsContract.CommonDataKinds.Phone.CONTENT_URI;

        String[] projection = { ContactsContract.CommonDataKinds.Phone.CONTACT_ID // 인덱스 값, 중복될 수 있음 -- 한 사람 번호가 여러개인 경우
                ,  ContactsContract.CommonDataKinds.Phone.DISPLAY_NAME
                ,  ContactsContract.CommonDataKinds.Phone.NUMBER};

        // 4. ContentResolver로 쿼리를 날림 -> resolver 가 provider 에게 쿼리하겠다고 요청
        Cursor cursor = resolver.query(phoneUri, projection, null, null, null);

        // 4. 커서로 리턴된다. 반복문을 돌면서 cursor 에 담긴 데이터를 하나씩 추출
        if(cursor != null){
            while(cursor.moveToNext()){
                // 4.1 이름으로 인덱스를 찾아준다
                int idIndex = cursor.getColumnIndex(projection[0]); // 이름을 넣어주면 그 칼럼을 가져와준다.
                int nameIndex = cursor.getColumnIndex(projection[1]);
                int numberIndex = cursor.getColumnIndex(projection[2]);
                // 4.2 해당 index 를 사용해서 실제 값을 가져온다.
                String id = cursor.getString(idIndex);
                String name = cursor.getString(nameIndex);
                String number = cursor.getString(numberIndex);


                Contact phoneContact = new Contact();
                phoneContact.setName(name);
                phoneContact.setNumber(number);
                phoneContact.setId(id);

                contacts.add(phoneContact);
            }
        }
        cursor.close();
        return contacts;
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View mView = inflater.inflate(R.layout.fragment_first, container, false);

        contactsRecView = mView.findViewById(R.id.contactsRecView);

//        contacts.add(new Contact("Margot Robbie", "010-1234-0000"));
//        contacts.add(new Contact("Cillian Murphy", "010-1234-1111"));
//        contacts.add(new Contact("Saoirse Ronan", "010-1234-2222"));
//        contacts.add(new Contact("Emma Watson", "010-1234-3333"));
//        contacts.add(new Contact("Cristian Bail", "010-1234-4444"));

        contacts = getContacts(getActivity());

        ContactsRecViewAdapter adapter = new ContactsRecViewAdapter(getActivity());
        adapter.setContacts(contacts);

        contactsRecView.setAdapter(adapter);
        contactsRecView.setLayoutManager(new LinearLayoutManager(getActivity()));

        editTextName = mView.findViewById(R.id.editTextName);
        editTextNumber = mView.findViewById(R.id.editTextNumber);

        mView.findViewById(R.id.addButton).setOnClickListener(view -> {
            String newName = editTextName.getText().toString();
            String newNumber = editTextNumber.getText().toString();
            Contact newContact = new Contact(newName, newNumber);
            contacts.add(newContact);
            adapter.notifyItemInserted(contacts.size()-1);

            Intent contactIntent = new Intent(ContactsContract.Intents.Insert.ACTION);
            contactIntent.setType(ContactsContract.RawContacts.CONTENT_TYPE);

            contactIntent
                    .putExtra(ContactsContract.Intents.Insert.NAME, newName)
                    .putExtra(ContactsContract.Intents.Insert.PHONE, newNumber);

            startActivity(contactIntent);
        });

        return mView;
    }
}