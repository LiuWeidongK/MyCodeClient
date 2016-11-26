package com.example.misaya.imool.Fragment;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;

import com.example.misaya.imool.Activity.TeacherSolveActivity;
import com.example.misaya.imool.R;

import java.util.ArrayList;

public class TeacherFragment extends Fragment {

    private EditText cName,section_1,section_2;

    View mView;
    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        if (mView == null) {
            mView = inflater.inflate(R.layout.activity_teacher,container,false);
        }

        cName = (EditText) mView.findViewById(R.id.et_teacher_cName);
        section_1 = (EditText) mView.findViewById(R.id.et_teacher_section1);
        section_2 = (EditText) mView.findViewById(R.id.et_teacher_section2);
        Button submit = (Button) mView.findViewById(R.id.btn_teacher_submit);

        submit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                ArrayList<String> list = new ArrayList<>();
                list.add(0,cName.getText().toString());
                list.add(1,section_1.getText().toString());
                list.add(2,section_2.getText().toString());

                Intent intent = new Intent(getActivity(),TeacherSolveActivity.class);
                intent.putStringArrayListExtra("infolist",list);

                startActivity(intent);
            }
        });
        return mView;
    }
}