package com.example.attendance;


import android.content.Context;
import android.content.Intent;


import android.content.SharedPreferences;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;
import com.example.attendance.DBHelper;

public class MainActivity extends AppCompatActivity {
    Button cardView;
    EditText username,password;
    RequestQueue requestQueue,requestQueueCourseAssign,requestQueueStudent;
        private String user,pass;
    DBHelper dbHelper;
    public final String rootUrl=Constants.ROOT_URL;
    SharedPreferences sharedPreferences;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);





         dbHelper=new DBHelper(getApplicationContext());

        username=findViewById(R.id.username);
        password=findViewById(R.id.password);




        requestQueue= Volley.newRequestQueue(this);
        requestQueueCourseAssign= Volley.newRequestQueue(this);
        requestQueueStudent=Volley.newRequestQueue(this);

        cardView=findViewById(R.id.loginbtn);
        cardView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                user=username.getText().toString();
                pass=password.getText().toString();


                parseString(user,pass);

            }
        });


    }

    void parseString(String user, String pass){
        dbHelper=new DBHelper(getApplicationContext());
        String url = rootUrl+"attendance_app/user_login.php?user="+user + "&pass="+pass;
        StringRequest stringRequest=new StringRequest(Request.Method.GET, url, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {

                if(response.equals("Username or password incorrect")){
                    Toast.makeText(MainActivity.this,response,Toast.LENGTH_LONG).show();
                }
                else {
                    Intent intent = new Intent(MainActivity.this, NavigationActivity.class);
//                    dbHelper.registerUser();
                    intent.putExtra("lecturer_id",response);
                    String[] values = response.split(",");
                    sharedPreferences = getSharedPreferences("userid", Context.MODE_PRIVATE);
                    SharedPreferences.Editor editor = sharedPreferences.edit();
                    editor.putString("lecturer_id", "loged");
                    editor.putString("fullname", values[0]);
                    editor.apply();

                    dbHelper.drop();
                    parseCourse(values[1]);
                    Toast.makeText(MainActivity.this,response,Toast.LENGTH_SHORT).show();
                    startActivity(intent);
                }
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                Toast.makeText(MainActivity.this,"Connection error :"+error.toString(),Toast.LENGTH_SHORT).show();
            }
        }
        );
        requestQueue.add(stringRequest);

    }


    void parseCourse(String lecturer_id){
        dbHelper=new DBHelper(getApplicationContext());
        String url=rootUrl+"attendance_app/coursetable.php?user="+lecturer_id;
        JsonObjectRequest request=new JsonObjectRequest(Request.Method.GET, url, null,
                new Response.Listener<JSONObject>() {
                    @Override
                    public void onResponse(JSONObject response) {
                        try {
                            JSONArray jsonAray=response.getJSONArray("course");
                            for (int i=0;i<jsonAray.length(); i++){
                                JSONObject student =jsonAray.getJSONObject(i);
                                String id=student.getString("id");
                                String course_code=student.getString("course_code");
                                String title=student.getString("title");
                                String alias=student.getString("alias");
                                String design_year=student.getString("design_year");
                                int batch_ad=student.getInt("batch_ad");
                                float cr=student.getInt("cr");
                                float sub_order=student.getInt("sub_order");
                                float dept_id=student.getInt("dept_id");
                                float sem_id=student.getInt("sem_id");
                                float int_th=student.getInt("int_th");
                                float int_pr=student.getInt("int_pr");
                                float final_th=student.getInt("final_th");
                                float final_pr=student.getInt("final_pr");
                                String remarks=student.getString("remarks");


                                Toast.makeText(MainActivity.this,title,Toast.LENGTH_SHORT).show();
                                Log.d("course",id);
                                dbHelper.insert(id,course_code,title,alias,design_year,cr,sub_order,dept_id,sem_id,int_th,int_pr,final_th,final_pr,remarks,batch_ad);
                                parsecourseassign(id,batch_ad);
                                parseStudent(id,batch_ad);
                            }

                        } catch (JSONException e) {
                            e.printStackTrace();
                        }
                    }
                }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                error.printStackTrace();
            }
        }
        );

        requestQueue.add(request);
    }

    void parsecourseassign(String course_idd,int batch_ad){
        Log.d("cotsedetails",course_idd);
        dbHelper=new DBHelper(getApplicationContext());
        String url=rootUrl+"attendance_app/courseassign.php?id="+course_idd + "&batch="+batch_ad;
        Log.d("error","courseassignmethod");
        JsonObjectRequest rrequest=new JsonObjectRequest(Request.Method.GET, url, null,
                new Response.Listener<JSONObject>() {
                    @Override
                    public void onResponse(JSONObject response) {
                        try {
                            JSONArray jsonAray=response.getJSONArray("courseassign");
                            for (int i=0;i<jsonAray.length(); i++){
                                JSONObject student =jsonAray.getJSONObject(i);
                                String id=student.getString("id");
                                String crn=student.getString("crn");
                                int dept_id=student.getInt("dept_id");
                                int sem_id=student.getInt("sem_id");
                                String course_id=student.getString("course_id");
                                int assign=student.getInt("assign");
                                int ut=student.getInt("ut");
                                int assessment=student.getInt("assessment");
                                int status=student.getInt("status");


                                dbHelper.insertCourseAssign(id,crn,dept_id,sem_id,course_id,assign,ut,assessment,status);

                            }

                        } catch (JSONException e) {
                            e.printStackTrace();
                        }
                    }
                }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                error.printStackTrace();
            }
        }
        );

        requestQueueCourseAssign.add(rrequest);

    }

    void parseStudent(final String student_course_id,int batch_ad){
        Log.d("student method id",student_course_id);
        dbHelper=new DBHelper(getApplicationContext());
        String url=rootUrl+"attendance_app/student.php?user="+student_course_id+ "&batch="+batch_ad;
        JsonObjectRequest studentRequest=new JsonObjectRequest(Request.Method.GET, url, null,
                new Response.Listener<JSONObject>() {
                    @Override
                    public void onResponse(JSONObject response) {
                        try {
                            JSONArray jsonAray=response.getJSONArray("student");
                            for (int i=0;i<jsonAray.length(); i++){
                                JSONObject student =jsonAray.getJSONObject(i);
                                int Student_id=student.getInt("id");
                                String firstName=student.getString("firstName");
                                String middleName=student.getString("middleName");
                                String lastName=student.getString("lastName");
                                int student_courseid=Integer.parseInt(student_course_id);
                                String crn=student.getString("crn");
                                int dept_id=student.getInt("dept_id");
                                int sem_id=student.getInt("sem_id");

                                int batch_bs=student.getInt("batch_bs");
                                int batch_ad=student.getInt("batch_ad");



                                dbHelper.insertStudent(Student_id,firstName,middleName,lastName,student_courseid,crn,dept_id,sem_id,batch_bs,batch_ad);

                            }

                        } catch (JSONException e) {
                            e.printStackTrace();
                        }
                    }
                }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                error.printStackTrace();
            }
        }
        );

        requestQueueStudent.add(studentRequest);

    }
}
