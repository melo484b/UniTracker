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
import com.melodev484b.unitracker.entity.Course;

import java.util.List;

public class CourseAdapter extends RecyclerView.Adapter<CourseAdapter.CourseViewHolder> {
    class CourseViewHolder extends RecyclerView.ViewHolder {
        private final TextView courseItemView;
        private CourseViewHolder(View itemView) {
            super(itemView);
            courseItemView = itemView.findViewById(R.id.list_item_title);
            itemView.setOnClickListener(view -> {
                int position = getAdapterPosition();
                final Course current = mCourses.get(position);
                Intent intent = new Intent(context, CourseList.class);
                intent.putExtra("id", current.getCourseId());
                intent.putExtra("title", current.getTitle());
                intent.putExtra("start", current.getStartDate());
                intent.putExtra("end", current.getEndDate());
                intent.putExtra("status", current.getStatus());
                intent.putExtra("instructor", current.getInstructor());
                intent.putExtra("phone", current.getInstructorPhone());
                intent.putExtra("email", current.getInstructorEmail());
                intent.putExtra("note", current.getNote());
                intent.putExtra("term_id", current.getTermId());
                context.startActivity(intent);
            });
        }
    }

    private List<Course> mCourses;
    private final Context context;
    private final LayoutInflater mInflator;

    public CourseAdapter(Context context) {
        mInflator = LayoutInflater.from(context);
        this.context = context;
    }

    @NonNull
    @Override
    public CourseAdapter.CourseViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View itemView = mInflator.inflate(R.layout.list_item, parent, false);
        return new CourseViewHolder(itemView);
    }

    // Put data into the textView
    @Override
    public void onBindViewHolder(@NonNull CourseAdapter.CourseViewHolder holder, int position) {
        if (mCourses != null) {
            Course current = mCourses.get(position);
            String title = current.getTitle();
            holder.courseItemView.setText(title);
        }
        else {
            holder.courseItemView.setText("No course title.");
        }
    }

    public void setCourses(List<Course> courses) {
        mCourses = courses;
        notifyDataSetChanged();
    }

    @Override
    public int getItemCount() {
        if (mCourses != null) {
            return mCourses.size();
        }
        return 0;
    }
}