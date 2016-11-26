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

import com.example.misaya.imool.Activity.StudentSolveActivity;
import com.example.misaya.imool.R;

import java.util.ArrayList;

public class StudentFragment extends Fragment {

    private EditText randnum,id,name;

    View mView;
    @Nullable
    @Override
    public View onCreateView(final LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        if (mView == null) {
            mView = inflater.inflate(R.layout.activity_student,container,false);
        }

        randnum = (EditText) mView.findViewById(R.id.et_student_randnumber);
        id = (EditText) mView.findViewById(R.id.et_student_id);
        name = (EditText) mView.findViewById(R.id.et_student_name);
        Button submit = (Button) mView.findViewById(R.id.btn_student_submit);
        submit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                ArrayList<String> list = new ArrayList<>();
                list.add(0,randnum.getText().toString());
                list.add(1,id.getText().toString());
                list.add(2,name.getText().toString());

                Intent intent = new Intent(getActivity(), StudentSolveActivity.class);
                intent.putStringArrayListExtra("infolist",list);

                startActivity(intent);
            }
        });
        return mView;
    }
}