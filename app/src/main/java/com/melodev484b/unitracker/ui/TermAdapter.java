package com.melodev484b.unitracker.ui;

import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.melodev484b.unitracker.R;
import com.melodev484b.unitracker.entity.Term;

import java.util.List;

public class TermAdapter extends RecyclerView.Adapter<TermAdapter.TermViewHolder> {

    private List<Term> mTerms;
    private final Context context;
    private final LayoutInflater mInflator;

    class TermViewHolder extends RecyclerView.ViewHolder {
        private final TextView termItemView;
        private TermViewHolder(View itemView) {
            super(itemView);
            termItemView = itemView.findViewById(R.id.list_item_title);
            itemView.setOnClickListener(view -> {
                int position = getAdapterPosition();
                final Term current = mTerms.get(position);
                Intent intent = new Intent(context, TermDetail.class);
                intent.putExtra("term_id", current.getTermId());
                intent.putExtra("title", current.getTitle());
                intent.putExtra("start", current.getStartDate());
                intent.putExtra("end", current.getEndDate());
                context.startActivity(intent);
            });
        }
    }

    public TermAdapter(Context context) {
        mInflator = LayoutInflater.from(context);
        this.context = context;
    }

    @NonNull
    @Override
    public TermAdapter.TermViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View itemView = mInflator.inflate(R.layout.list_item, parent, false);
        return new TermViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(@NonNull TermAdapter.TermViewHolder holder, int position) {
        if (mTerms != null) {
            Term current = mTerms.get(position);
            String title = current.getTitle();
            holder.termItemView.setText(title);
        }
        else {
            holder.termItemView.setText("No term title.");
        }
    }

    public void setTerms(List<Term> terms) {
        mTerms = terms;
        notifyDataSetChanged();
    }

    @Override
    public int getItemCount() {
        if (mTerms != null) {
            return mTerms.size();
        }
        return 0;
    }
}
