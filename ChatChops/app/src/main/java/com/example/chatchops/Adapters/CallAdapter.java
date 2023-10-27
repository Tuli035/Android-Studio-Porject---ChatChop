package com.example.chatchops.Adapters;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.recyclerview.widget.RecyclerView;

import com.example.chatchops.Models.ContactModel;
import com.example.chatchops.R;

import org.jetbrains.annotations.NotNull;

import java.util.ArrayList;

public class CallAdapter extends RecyclerView.Adapter<CallAdapter.ViewHolder> {

    Activity activity;
    ArrayList<ContactModel> arrayList;
    private Context context;
    public CallAdapter(Context context){
        this.context=context;

    }

    public  CallAdapter(Activity activity,ArrayList<ContactModel>arrayList){
        this.activity=activity;
        this.arrayList=arrayList;
        notifyDataSetChanged();
    }




    @NonNull
    @NotNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull @NotNull ViewGroup parent, int viewType) {

        View view= LayoutInflater.from(parent.getContext())
                .inflate(R.layout.item_contact,parent,false);
       // ViewHolder holder=new ViewHolder(view);
        //return holder;

        return new ViewHolder(view);

        //return null;
    }

    @Override
    public void onBindViewHolder(@NonNull @NotNull ViewHolder holder, int position) {

        //initialize contact model

        ContactModel model=arrayList.get(position);

        //set name
        holder.tvName.setText(model.getName());

        //set number
        holder.tvNumber.setText(model.getNumbers());

       holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent=new Intent(Intent.ACTION_DIAL);
                intent.setData(Uri.parse("tel:"+arrayList.get(position).getNumbers()));
               v.getContext().startActivity(intent);
                //Toast.makeText(v.getContext(), "Ok",Toast.LENGTH_SHORT).show();


            }
        });

    }

    @Override
    public int getItemCount() {

        //return arraylist size

        return arrayList.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {


        TextView tvName,tvNumber;
        private RelativeLayout parent;


        public ViewHolder(@NonNull @NotNull View itemView) {
            super(itemView);

            tvName=itemView.findViewById(R.id.tv_name);
            tvNumber=itemView.findViewById(R.id.tv_number);
        }
    }
}
