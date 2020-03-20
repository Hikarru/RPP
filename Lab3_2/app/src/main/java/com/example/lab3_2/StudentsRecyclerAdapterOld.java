package com.example.lab3_2;

import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.lab3_2.Entity.StudentOld;

import java.util.ArrayList;

public class StudentsRecyclerAdapterOld extends RecyclerView.Adapter {

    private ArrayList<StudentOld> students;

    public StudentsRecyclerAdapterOld(ArrayList<StudentOld> students){
        super();
        this.students = students;
    }

    @NonNull
    @Override
    public StudentViewHolderOld onCreateViewHolder(@NonNull ViewGroup parent, int i) {
        View itemView = LayoutInflater.from(parent.getContext()).inflate(R.layout.student_recycler_item_old, parent, false);
        return new StudentViewHolderOld(itemView);
    }

    @Override
    public void onBindViewHolder(@NonNull RecyclerView.ViewHolder viewHolder, int i) {
        StudentViewHolderOld studentViewHolderOld = (StudentViewHolderOld) viewHolder;
        studentViewHolderOld.FIO.setText(students.get(i).getFIO());
    }

    @Override
    public int getItemCount() {
        return students.size();
    }
}