package com.example.myapplication.ui.main;

import android.content.Context;
import android.content.Intent;
import android.database.Cursor;
import android.net.Uri;
import android.provider.ContactsContract;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.myapplication.Contact;
import com.example.myapplication.R;

import java.util.ArrayList;

public class ContactsRecViewAdapter extends RecyclerView.Adapter<ContactsRecViewAdapter.ViewHolder>{
    public static final String EXTRA_TEXT_1 = "com.example.phonebookbasic.EXTRA_TEST_1";
    public static final String EXTRA_TEXT_2 = "com.example.phonebookbasic.EXTRA_TEST_2";
    public static final String EXTRA_TEXT_3 = "com.example.phonebookbasic.EXTRA_TEST_3";

    private ArrayList<Contact> contacts = new ArrayList<>();
    private Context context;
    public ContactsRecViewAdapter(Context context) {
        this.context = context;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.contacts_list_item, parent, false);
        ViewHolder holder = new ViewHolder(view);
        return holder;
    }

//    public void addItem(int position, Contact contact) {
//        contacts.add(position, contact);
//        notifyItemInserted(position);
//        notifyDataSetChanged();
//    }
//
//    public void removeItem(int position) {
//        contacts.remove(position);
//        notifyItemRemoved(position);
//        notifyDataSetChanged();
//    }


    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, final int position) {
        holder.txtName.setText(contacts.get(position).getName());
        holder.txtNumber.setText(contacts.get(position).getNumber());
        holder.parent.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Toast.makeText(context, contacts.get(position).getName() + " is " + contacts.get(position).getNumber(), Toast.LENGTH_SHORT).show();

                String text1 = contacts.get(position).getName();
                String text2 = contacts.get(position).getNumber();

                Intent intent = new Intent(context, HelpFirstActivity.class);
                intent.putExtra(EXTRA_TEXT_1, text1);
                intent.putExtra(EXTRA_TEXT_2, text2);
                intent.putExtra(EXTRA_TEXT_3, position);

                context.startActivity(intent);
            }
        });
    }

    @Override
    public int getItemCount() {
        return contacts.size();
    }

    public void setContacts(ArrayList<Contact> contacts) {
        this.contacts = contacts;
        notifyDataSetChanged();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {

        private TextView txtName;
        private TextView txtNumber;
        //        private EditText editTextName;
//        private EditText editTextNumber;
        private RelativeLayout parent;
//        private ContactsRecViewAdapter adapter;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            txtName = itemView.findViewById(R.id.txtName);
            txtNumber = itemView.findViewById(R.id.txtNumber);
            parent = itemView.findViewById(R.id.parent);


            itemView.findViewById(R.id.deleteButton).setOnClickListener(view -> {
//                Contact deletedContact = contacts.get(getAdapterPosition());

//                Uri uri = Uri.withAppendedPath(ContactsContract.Contacts.CONTENT_URI, contacts.get(getAdapterPosition()).getId());
//                int deleted = context.getContentResolver().delete(uri, null, null);

                Contact deletedContact = contacts.remove(getAdapterPosition());
                notifyItemRemoved(getAdapterPosition());

                Toast.makeText(context, deletedContact.toString(), Toast.LENGTH_SHORT).show();

//                Uri uri = Uri.withAppendedPath(ContactsContract.Contacts.CONTENT_URI, deletedContact.getId());
//                int deleted = context.getContentResolver().delete(uri,null,null);


                boolean b = deleteContact(context, deletedContact.getNumber(), deletedContact.getName());




            });



        }
    }


    public static boolean deleteContact(Context ctx, String phone, String name) {
        Uri contactUri = Uri.withAppendedPath(ContactsContract.PhoneLookup.CONTENT_FILTER_URI, Uri.encode(phone));
        Cursor cur = ctx.getContentResolver().query(contactUri, null, null, null, null);
        try {
            if (cur.moveToFirst()) {
                do {
                    if (cur.getString(cur.getColumnIndex(ContactsContract.PhoneLookup.DISPLAY_NAME)).equalsIgnoreCase(name)) {
                        String lookupKey = cur.getString(cur.getColumnIndex(ContactsContract.Contacts.LOOKUP_KEY));
                        Uri uri = Uri.withAppendedPath(ContactsContract.Contacts.CONTENT_LOOKUP_URI, lookupKey);
                        ctx.getContentResolver().delete(uri, null, null);
                        return true;
                    }

                } while (cur.moveToNext());
            }

        } catch (Exception e) {
            System.out.println(e.getStackTrace());
        } finally {
            cur.close();
        }
        return false;
    }

//    public void deleteContact(Context context, String localContactId)
//    {
//        ContentResolver cr = context.getContentResolver();
//        String rawWhere = ContactsContract.Contacts._ID + " = ? ";
//        String[] whereArgs1 = new String[]{localContactId};
//        Cursor cur = cr.query(ContactsContract.Contacts.CONTENT_URI,
//                null, rawWhere, whereArgs1, null);
//
//        if(cur != null && cur.getCount() > 0) {
//            while (cur.moveToNext()) {
//                try{
//                    String lookupKey = cur.getString(cur.getColumnIndex(ContactsContract.Contacts.LOOKUP_KEY));
//                    Uri uri = Uri.withAppendedPath(ContactsContract.Contacts.CONTENT_LOOKUP_URI, lookupKey);
//                    cr.delete(uri, null, null);
//                }
//                catch(Exception e)
//                {
//                    System.out.println(e.getStackTrace());
//                }
//            }
//        }
//        if(cur != null)
//            cur.close();
//    }



}