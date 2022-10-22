package com.melodev484b.unitracker.ui;

import android.app.Application;
import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.melodev484b.unitracker.R;
import com.melodev484b.unitracker.db.Repository;
import com.melodev484b.unitracker.entity.Assessment;

import java.util.List;



public class AssessmentAdapter extends RecyclerView.Adapter<AssessmentAdapter.AssessmentViewHolder> {

    private List<Assessment> mAssessments;
    private final Context context;
    private final LayoutInflater mInflator;

    class AssessmentViewHolder extends RecyclerView.ViewHolder {
        private final TextView assessmentItemView;
        private AssessmentViewHolder(View itemView) {
            super(itemView);
            assessmentItemView = itemView.findViewById(R.id.list_item_title);
            itemView.setOnClickListener(view -> {
                int position = getAdapterPosition();
                final Assessment current = mAssessments.get(position);
                Intent intent = new Intent(context, AssessmentDetail.class);
                intent.putExtra("assessment_id", current.getAssessmentId());
                intent.putExtra("title", current.getTitle());
                intent.putExtra("type", current.getType());
                intent.putExtra("date", current.getDate());
                intent.putExtra("course_id", current.getCourseId());
                context.startActivity(intent);
            });
        }
    }

    public AssessmentAdapter(Context context) {
        mInflator = LayoutInflater.from(context);
        this.context = context;
    }

    @NonNull
    @Override
    public AssessmentAdapter.AssessmentViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View itemView = mInflator.inflate(R.layout.list_item, parent, false);
        return new AssessmentViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(@NonNull AssessmentAdapter.AssessmentViewHolder holder, int position) {
        if (mAssessments != null) {
            Assessment current = mAssessments.get(position);
            String title = current.getTitle();
            holder.assessmentItemView.setText(title);
        }
        else {
            holder.assessmentItemView.setText("No assessment title.");
        }
    }

    public void setAssessments(List<Assessment> assessments) {
        mAssessments = assessments;
        notifyDataSetChanged();
    }

    @Override
    public int getItemCount() {
        if (mAssessments != null) {
            return mAssessments.size();
        }
        return 0;
    }
}
