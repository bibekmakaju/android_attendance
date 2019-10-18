package com.example.attendance;

import android.app.Activity;
import android.content.Context;
import android.graphics.Color;
import android.support.annotation.NonNull;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;

import static android.view.View.GONE;

public class StudentAdapter extends RecyclerView.Adapter<StudentAdapter.ExampleViewHolder> {
    private Context mContextStudent;
    private ArrayList<StudentModel> mStudentList;
    private SubjectAdapter.OnItemClickListener mListner;
    ArrayList<String> arrayList = new ArrayList<String>();






    public StudentAdapter(Context context, ArrayList<StudentModel> exampleList) {
        mContextStudent = context;
        mStudentList = exampleList;

    }

    @Override
    public StudentAdapter.ExampleViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {


        View v = LayoutInflater.from(mContextStudent).inflate(R.layout.student_recycler, parent, false);
        return new StudentAdapter.ExampleViewHolder(v);
    }

    @Override
    public void onBindViewHolder(final StudentAdapter.ExampleViewHolder holder, final int position) {


        if(position %2 == 1)
        {
            holder.itemView.setBackgroundColor(Color.parseColor("#DCDCDC"));

        }
        else
        {
            holder.itemView.setBackgroundColor(Color.parseColor("#C0C0C0"));

        }

        final String [] crntodb =new String[50];
        String [] firstnametodb;
        String [] middenametodb;
        String [] lastnametodb;
        String [] course_idtodb;
        String [] present_todb;
        String [] reuest_todb;
        String [] letter_todb;
        String [] othertxttodp;

        StudentModel currentItem = mStudentList.get(position);
        final String crn = currentItem.getcrn();
        final String firstname=currentItem.getFirstname();
        final String middlename=currentItem.getMiddlename();
        final String lastname=currentItem.getLastname();
        final  String roll_no=crn.substring(crn.length()-2);
        final String course_id=currentItem.getCourse_id();
        final String present_id=currentItem.getPresent_status();
        final String letter1=currentItem.getLetter();
        final String request1=currentItem.getRequest();
        final  String otherstxt1=currentItem.getOtherstxt();
            final String date=currentItem.getDate();
        final String others = currentItem.getOthers();
        holder.roll.setText(roll_no+ ". ");
        String fullname=firstname+" "+middlename+" "+lastname;
        holder.name.setText(fullname);
//
//        if (fullname.equals("Student not available")){
//            Log.d("no student",fullname);
//            holder.cbSelect.setEnabled(false);
//            holder.cbSelect.setVisibility(GONE);
//
//        }


        if (present_id.equals("0")){
            holder.cbSelect.setChecked(false);
            holder.rg1.setVisibility(View.VISIBLE);
        }
        
        if (letter1.equals("1")){
            holder.letter.setChecked(true);
            holder.others.setChecked(false);
            holder.request.setChecked(false);
        }
        else if(request1.equals("1")){
            holder.request.setChecked(true);
            holder.letter.setChecked(false);
            holder.others.setChecked(false);
        }
        else if(others.equals("1")){
            holder.others.setChecked(true);
            holder.otherstxt.setText(otherstxt1);
        }


       final DBHelper dbHelper=new DBHelper(mContextStudent);
//        dbHelper.insertAttend(crn,firstname,middlename,lastname,course_id,1,date,0,0,0,0,"");

        holder.cbSelect.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener()
        {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked)
            {
                if ( isChecked )
                {
                    dbHelper.insertAttend(crn,firstname,middlename,lastname,course_id,1,date,0,0,0,0,"");
                    holder.rg1.setVisibility(GONE);

                }

                else {
                    dbHelper.insertAttend(crn,firstname,middlename,lastname,course_id,0,date,0,1,0,0,"");
                    Toast.makeText(buttonView.getContext(),"Absent ->"+roll_no,Toast.LENGTH_SHORT).show();
                    holder.rg1.setVisibility(View.VISIBLE);



                }


            }
        });


        holder.rg1.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(RadioGroup group, int checkedId) {

                if (checkedId == R.id.radioletter) {
                    Toast.makeText(mContextStudent,"radio_letter",Toast.LENGTH_SHORT).show();
                    dbHelper.insertAttend(crn,firstname,middlename,lastname,course_id,0,date,0,1,0,0,"");
                    holder.otherstxt.setBackgroundResource(R.drawable.comment_disable_round);
                    holder.otherstxt.setEnabled(false);
                } else  if (checkedId == R.id.radiorequest) {
                    dbHelper.insertAttend(crn,firstname,middlename,lastname,course_id,0,date,0,0,1,0,"");
                    holder.otherstxt.setEnabled(false);
                    holder.otherstxt.setBackgroundResource(R.drawable.comment_disable_round);
                } else  if (checkedId == R.id.others) {
                    dbHelper.insertAttend(crn,firstname,middlename,lastname,course_id,0,date,0,0,0,1,"No reason");
                    holder.otherstxt.setBackgroundResource(R.drawable.comment_blue_round);
                    holder.otherstxt.setEnabled(true);
                }

            }
        });


        holder.otherstxt.addTextChangedListener(new TextWatcher() {

            public void afterTextChanged(Editable s) {}

            public void beforeTextChanged(CharSequence s, int start, int count, int after) {}

            public void onTextChanged(CharSequence s, int start, int before, int count) {
                dbHelper.insertAttend(crn,firstname,middlename,lastname,course_id,0,date,0,0,0,1,s.toString());
            }
        });




    }

    @Override
    public int getItemCount() {
        return mStudentList.size();
    }


    public class ExampleViewHolder extends RecyclerView.ViewHolder {

        public TextView roll,name;
        public CheckBox cbSelect;
        public RadioGroup rg1;
        public RadioButton letter,request,others;
        EditText otherstxt;



        public ExampleViewHolder(View itemView) {
            super(itemView);

            roll=itemView.findViewById(R.id.roll);
            name=itemView.findViewById(R.id.student_name);
            cbSelect = itemView.findViewById(R.id.checkbox);
            rg1 = (itemView).findViewById(R.id.radioSex);
            letter=(itemView).findViewById(R.id.radioletter);
            request=(itemView).findViewById(R.id.radiorequest);
            others=(itemView).findViewById(R.id.others);
            otherstxt=(itemView).findViewById(R.id.otherstxt);




        }
    }



    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public int getItemViewType(int position) {

        return position;
    }
}