package com.example.l2_m3;

import static androidx.core.content.ContextCompat.createDeviceProtectedStorageContext;
import static androidx.core.content.ContextCompat.startActivity;

import android.app.SearchManager;
import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.List;

public class ContactsAdapter extends RecyclerView.Adapter<ContactsAdapter.ContactsViewHolder> {

    private LayoutInflater layoutInflater; // проеобраователь xml во вью class
    private List<ContactsModel> list;
    Context context;

    public ContactsAdapter(Context context, List<ContactsModel> list){
        this.list =list;
        this.context = context;
        this.layoutInflater = LayoutInflater.from(context);

    }

    @NonNull
    @Override
    public ContactsViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {

        View view = layoutInflater.inflate(R.layout.item_contact, parent, false);

        return new ContactsViewHolder(view);
        //внешний вид, оболочка для РВ
    }

    @Override
    public void onBindViewHolder(@NonNull ContactsAdapter.ContactsViewHolder holder, int position) {
     //раставление элементов
        holder.txtContactName.setText(list.get(position).getName());
        holder.txtContactNumber.setText(list.get(position).getPhone());

        holder.btnCall.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String phone = holder.txtContactNumber.getText().toString().trim();
                Intent intentCall = new Intent(Intent.ACTION_DIAL, Uri.fromParts("tel", phone, null));
                intentCall.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                context.startActivity(intentCall);
                  //context.getApplicationContext().startActivity(intentCall);
                //((MainActivity)context).startActivity(intentCall);
            }
        });


        holder.btnWA.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
//                Intent webIntent = new Intent(Intent.ACTION_WEB_SEARCH);
//                webIntent.putExtra(SearchManager.QUERY, holder.txtContactNumber.getText().toString().trim());
//                context.startActivity(webIntent);
                String phone = holder.txtContactNumber.getText().toString().trim();
                String url = "https://api.whatsapp.com/send?phone=" + phone;
                Intent intent = new Intent(Intent.ACTION_VIEW);
                intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                intent.setData(Uri.parse(url));
                context.startActivity(intent);
                //v.getContext().startActivity(webIntent);

            }
        });

    }

    @Override
    public int getItemCount() {
        //сколько элементов надо создать
        return list.size();
    }

    public static class ContactsViewHolder extends RecyclerView.ViewHolder{
        TextView txtContactName, txtContactNumber;
        Button btnCall, btnWA;

        public ContactsViewHolder(@NonNull View itemView) {
            // это как каждый элемент РВ
            super(itemView);

            txtContactName = itemView.findViewById(R.id.txt_item_name);
            txtContactNumber = itemView.findViewById(R.id.txt_item_phonenumber);

            btnCall = itemView.findViewById(R.id.btn_item_contact_call);
            btnWA = itemView.findViewById(R.id.btn_item_contact_wa);


        }
    }


}
