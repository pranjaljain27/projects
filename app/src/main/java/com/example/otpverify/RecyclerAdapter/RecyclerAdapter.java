package com.example.otpverify.RecyclerAdapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Filter;
import android.widget.Filterable;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.otpverify.Model.Detail;
import com.example.otpverify.R;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

public class RecyclerAdapter extends RecyclerView.Adapter<RecyclerAdapter.ViewHolder> implements Filterable {
    private Context context;
    private List<Detail> filterDetailList;
    private List<Detail> detailList;

    public RecyclerAdapter(Context context, List<Detail> detailList) {
        this.context = context;
        this.detailList = detailList;
        this.filterDetailList=new ArrayList<>(detailList);
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {

        View view= LayoutInflater.from(context).inflate(R.layout.detail_row,parent,false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {

        Detail detail=detailList.get(position);
        holder.reBname.setText(detail.getBusiName());
        holder.reAdd.setText(detail.getAddr());
        holder.reCat.setText(detail.getCato());
        holder.reMail.setText(detail.getEmails());
    }


    @Override
    public int getItemCount() {
        return detailList.size();
    }

    @Override
    public Filter getFilter() {
        return filter;
    }
    private Filter filter=new Filter() {
        @Override
        protected FilterResults performFiltering(CharSequence charSequence) {

            List<Detail> filteredList=new ArrayList<>();
            if(charSequence.toString().isEmpty())
            {
                filteredList.addAll(filterDetailList);
            }
            else
            {
                for(Detail detail: filterDetailList)
                {
                    if(detail.getAddr().toLowerCase().contains(charSequence.toString().toLowerCase()) ||
                        detail.getBusiName().toLowerCase().contains(charSequence.toString().toLowerCase()) ||
                        detail.getCato().toLowerCase().contains(charSequence.toString().toLowerCase()))
                    {
                        filteredList.add(detail);
                    }
                }
            }
            FilterResults filterResults=new FilterResults();
            filterResults.values=filteredList;
            return filterResults;
        }

        @Override
        protected void publishResults(CharSequence constraint, FilterResults results) {

            detailList.clear();
            detailList.addAll((Collection<? extends Detail>) results.values);
            notifyDataSetChanged();
        }
    };

    public class ViewHolder extends RecyclerView.ViewHolder
    {
        public TextView reBname;
        public TextView reAdd;
        public TextView reMail;
        public TextView reCat;
        public ViewHolder(@NonNull View itemView) {
            super(itemView);

            reBname=itemView.findViewById(R.id.recyBusName);
            reAdd=itemView.findViewById(R.id.recyAdd);
            reMail=itemView.findViewById(R.id.recyMail);
            reCat=itemView.findViewById(R.id.recyCat);
        }
    }
}
