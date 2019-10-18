package com.example.attendance;


import android.app.FragmentManager;
import android.content.Context;
import android.graphics.Color;
import android.support.v4.app.FragmentActivity;
import android.support.v4.content.ContextCompat;
import android.support.v7.widget.CardView;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.ImageView;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.TextView;
import android.widget.Toast;


import java.io.ObjectInput;
import java.util.ArrayList;

import static android.content.ContentValues.TAG;


public class SubjectAdapter extends RecyclerView.Adapter<SubjectAdapter.ExampleViewHolder> {
    private Context mContext,co;
    private ArrayList<SubjectModel> mExampleList;
    private OnItemClickListener mListner;


    public interface OnItemClickListener{
        void onItemClick(int position);
    }
    public void setOnItemClickListner(OnItemClickListener listner){
        mListner=listner;
    }

    public SubjectAdapter(Context context, ArrayList<SubjectModel> exampleList) {
        mContext = context;
        mExampleList = exampleList;


    }
    public SubjectAdapter (Context c){
        co=c;
    }

    @Override
    public ExampleViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(mContext).inflate(R.layout.subject_recycler, parent, false);
        return new ExampleViewHolder(v);
    }

    @Override
    public void onBindViewHolder(final ExampleViewHolder holder, int position) {

        if(position %2 == 1)
        {
            holder.itemView.setBackgroundColor(Color.parseColor("#b8d1f3"));

        }
        else
        {
            holder.itemView.setBackgroundColor(Color.parseColor("#dae5f4"));

        }
//        if(position % 7 == 0){
//            holder.itemView.setBackgroundColor(Color.parseColor("#dae5f4"));
//        }else if(position % 7 == 1){
//            holder.itemView.setBackgroundColor(Color.parseColor("#dae5f4"));
//        }else if(position % 7 == 2){
//            holder.itemView.setBackgroundColor(Color.parseColor("#dae5f4"));
//        }else if(position % 7 == 3){
//            holder.itemView.setBackgroundColor(Color.parseColor("#dae5f4"));
//        }else if(position % 7 == 4){
//            holder.itemView.setBackgroundColor(Color.parseColor("#dae5f4"));
//        }else if(position % 7 == 5){
//            holder.itemView.setBackgroundColor(Color.parseColor("#dae5f4"));
//        }else if(position % 7 == 6){
//            holder.itemView.setBackgroundColor(Color.parseColor("#dae5f4"));
//        }



        SubjectModel currentItem = mExampleList.get(position);

        final String subjectTitle = currentItem.getTitle();
        final String coursecode=currentItem.getcourse_code();

        final String batch_ad=currentItem.getBatch_ad();
        final String semid=currentItem.getSem_id();
        final String deptid=currentItem.getDept_id();
        holder.mtitle.setText(subjectTitle);
        holder.mcoursecode.setText("Course_code: "+coursecode+" ");

        holder.batch_ad.setText("batch_ad: "+batch_ad+" ");

        holder.dept_id.setText("Dept_id: "+deptid+" ");

        holder.sem_id.setText("Sem_id: "+semid+" ");




    }

    @Override
    public int getItemCount() {
        return mExampleList.size();
    }

    public class ExampleViewHolder extends RecyclerView.ViewHolder {

        public TextView mtitle,mcoursecode,batch_ad,sem_id,dept_id;
        public Button tody,prevous;

        public ExampleViewHolder(View itemView) {
            super(itemView);

            mtitle = itemView.findViewById(R.id.txttitle);
            mcoursecode=itemView.findViewById(R.id.coursecode);
            batch_ad=itemView.findViewById(R.id.batch_ad);
            sem_id=itemView.findViewById(R.id.semid);
            dept_id=itemView.findViewById(R.id.deptid);

            tody=itemView.findViewById(R.id.today);
            prevous=itemView.findViewById(R.id.previous);

            tody.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    if (mListner!=null){
                        int position=getAdapterPosition();
                        if (position!=RecyclerView.NO_POSITION){
                            mListner.onItemClick(position);
                            Toast.makeText(mContext, "Clicked button at position: " + getAdapterPosition(), Toast.LENGTH_SHORT).show();
                        }
                    }
                }
            });
            prevous.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    if (mListner!=null){
                        int position=getAdapterPosition();
                        if (position!=RecyclerView.NO_POSITION){
                            mListner.onItemClick(position);
                            Toast.makeText(mContext, "Clicked button at position: " + getAdapterPosition(), Toast.LENGTH_SHORT).show();

                        }
                    }
                }
            });


        }
    }

}