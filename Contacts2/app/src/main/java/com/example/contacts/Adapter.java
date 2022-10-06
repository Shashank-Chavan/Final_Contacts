package com.example.contacts;

import android.annotation.SuppressLint;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.List;

//This is the recyclerView
//it will be used for showint the contact list
public class Adapter extends RecyclerView.Adapter {
    List<Data> dataList;
    private ItemClickListener clickListener;
public Adapter(List<Data> dataList,ItemClickListener clickListener){
    this.dataList = dataList;
    this.clickListener = clickListener;
}

    @NonNull
    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater layoutInflater = LayoutInflater.from(parent.getContext());
        View view = layoutInflater.inflate(R.layout.card,parent,false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull RecyclerView.ViewHolder holder, @SuppressLint("RecyclerView") int position) {
        final Data d =dataList.get(position);
        ViewHolder viewHolder =(ViewHolder) holder;
        viewHolder.name.setText(d.getName());
        viewHolder.number.setText((d.getNumber()));
        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                clickListener.onItemClick(dataList.get(position));
            }
        });
    }

    @Override
    public int getItemCount() {
        return dataList.size();
    }
    class ViewHolder extends RecyclerView.ViewHolder{
        TextView name;
        TextView number;
        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            name = itemView.findViewById(R.id.C_name);
            number = itemView.findViewById(R.id.C_Number);
        }

    }
    //this interface will be used to communicate the recycler view with the contact details fragment
    // as it used the data of the clicked view
    public interface ItemClickListener{
        public void onItemClick(Data data);
    }
}
