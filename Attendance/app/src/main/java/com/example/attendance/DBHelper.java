package com.example.attendance;

import android.content.ContentValues;
import android.content.Context;
import android.content.Intent;
import android.database.Cursor;
import android.database.SQLException;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;
import android.widget.Toast;



import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonArrayRequest;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.google.gson.Gson;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import static android.content.ContentValues.TAG;

public class DBHelper extends SQLiteOpenHelper {
    Context mcontext=null;

    public DBHelper(Context context) {

        super(context, "attendance", null, 2);
        mcontext=context;

    }
    SQLiteDatabase sqLiteDatabase;
    private SubjectAdapter studentAdapter;
    private static final String Table_Course ="course";
    private static final String Table_Student ="student";
    private static final String Table_CourseAssign ="courseassign";
    public static final String Table_Attend ="attendrec";

    //for course table
    private  static final String id="id";
    private static final String Course_id = "Course_id";
    private static final String Course_code = "course_code";
    private static final String Course_title = "title";
    private static final String Course_alias = "alias";
    private static final String design_year = "designyear";
    private static final String credit = "cr";
    private static final String sub_order = "sub_order";
    private static final String dept_id = "dept_id";
    private static final String sem_id="sem_id";
    private static final String int_th = "int_th";
    private static final String int_pr = "int_pr";
    private static final String final_th = "final_th";
    private static final String final_pr = "final_pr";
    private static final String remarks = "remarks";
    private static final String batch_ad = "batch_ad";

    String CREATE_TABLE_COURSE = " Create table "
            + Table_Course + "(" + id + " INTEGER PRIMARY KEY AUTOINCREMENT, " + Course_id + " TEXT , " + Course_code
            + " TEXT(12)," + Course_title + " Text(80)," + Course_alias
            + " TEXT(30)," +  design_year + " TEXT(4)," + credit
            + " REAL," + sub_order + " REAL," + dept_id
            + " REAL,"+ sem_id +" REAL , "+ batch_ad +" INTEGER, " + int_th +" REAL, "+ int_pr + " REAL, " + final_th +" REAL, " + final_pr +" REAL,"+ remarks +" REAL" +")";



    String CREATE_TABLE_CourseAssign = " Create table  " + Table_CourseAssign + "(" + "ID INTEGER PRIMARY KEY AUTOINCREMENT,"
            + "course_assign_id TEXT,"
            + "crn TEXT,"
            + "dept_id INTEGER,"
            + "sem_id INTEGER,"
            + "Course_id TEXT," + "assign INTEGER," + " ut INTEGER, "+ " assessment INTEGER," +"status INTEGER)";

    String CREATE_TABLE_Student= " Create table  " + Table_Student + "(" + "ID INTEGER PRIMARY KEY AUTOINCREMENT,"
            + "student_id INTEGER,"
            + "firstName TEXT,"
            + "middleName TEXT,"
            + "lastName TEXT,"
            + " course_id INTEGER,"
            + "crn TEXT," + "dept_id INTEGER," + " sem_id INTEGER, "+ " batch_bs INTEGER," +"batch_ad INTEGER)";

    String CREATE_TABLE_attend= " Create table  " + Table_Attend + "(" + "ID INTEGER PRIMARY KEY AUTOINCREMENT,"
            + "firstName TEXT,"
            + "middleName TEXT,"
            + "lastName TEXT,"
            + " course_id INTEGER,"
            + "crn TEXT," + "present INTEGER,"+"date TEXT," + " letter INTEGER, "+ " request INTEGER,"+" others INTEGER," +"remarks TEXT," + "updated TEXT)";



    @Override
    public void onCreate(SQLiteDatabase db) {


        db.execSQL(CREATE_TABLE_COURSE);
        db.execSQL(CREATE_TABLE_CourseAssign);
        db.execSQL(CREATE_TABLE_Student);
        db.execSQL(CREATE_TABLE_attend);
    }

    @Override
    public void onUpgrade( SQLiteDatabase db,  int oldVersion,  int newVersion) {

    }






    SQLiteDatabase dba;

    public void drop(){
        dba =this.getWritableDatabase();
        dba.delete(Table_CourseAssign, null ,null);
        dba.delete(Table_Student, null ,null);
        dba.delete(Table_Course, null ,null);
    }
    public void insert(String id,String code,String title,String alias,String dyear,float cr,float sub,float deptid,float semid,float intth,float intpr,float finalth,float finalpr,String rema,int batch_a){
        dba =this.getWritableDatabase();
        ContentValues values = new ContentValues();


        values.put(Course_id,id);
        values.put(Course_code, code);
        values.put(Course_title, title);
        values.put(Course_alias,alias);
        values.put(design_year, dyear);
        values.put(credit, cr);
        values.put(sub_order,sub);
        values.put(dept_id, deptid);
        values.put(sem_id, semid);
        values.put(int_th,intth);
        values.put(int_pr, intpr);
        values.put(final_th, finalth);
        values.put(final_pr,finalpr);
        values.put(remarks, rema);
        values.put(batch_ad,batch_a);

        dba.insert(Table_Course,null,values);

    }


    public void insertCourseAssign(String courseassign_id,String crn,int dept_id,int sem_id, String course_id,int assign,int ut,int assement,int status){
        dba=this.getWritableDatabase();
        ContentValues values = new ContentValues();
        values.put("course_assign_id",courseassign_id);
        values.put("crn",crn);
        values.put("dept_id",dept_id);
        values.put("sem_id",sem_id);
        values.put("Course_id",course_id);
        values.put("assign",assign);
        values.put("ut",ut);
        values.put("assessment",assement);
        values.put("status",status);
        dba.insert(Table_CourseAssign,null,values);


    }
    void insertStudent(int studentid,String first,String middle,String last,int course_id,String crn,int dept,int sem,int batch_bs,int batch_ad){

        dba=this.getWritableDatabase();
        ContentValues values = new ContentValues();
        values.put("student_id",studentid);
        values.put("firstName",first);
        values.put("middleName",middle);
        values.put("lastName",last);
        values.put("course_id",course_id);
        values.put("crn",crn);
        values.put("dept_id",dept);
        values.put("sem_id",sem);
        values.put("batch_bs",batch_bs);
        values.put("batch_ad",batch_ad);
        dba.insert(Table_Student,null,values);
    }

    public ArrayList<SubjectModel> getAllSubject() {
        ArrayList<SubjectModel> courselist = new ArrayList<>();

        // Select All Query
        String selectQuery = "SELECT * FROM course";

        SQLiteDatabase db = this.getWritableDatabase();
        Cursor cursor = db.rawQuery(selectQuery, null);
            int i=0;
        // looping through all rows and adding to list
        if (cursor.moveToFirst()) {
            do {

                String coursd_id=cursor.getString(cursor.getColumnIndex("Course_id"));
               String course_code=cursor.getString(cursor.getColumnIndex("course_code"));
               String title=cursor.getString(cursor.getColumnIndex("title"));
                String batch=cursor.getString(cursor.getColumnIndex("batch_ad"));
                String semid=cursor.getString(cursor.getColumnIndex("sem_id"));
                String dept_id=cursor.getString(cursor.getColumnIndex("dept_id"));
                courselist.add(new SubjectModel(title, course_code, coursd_id,batch,dept_id,semid));
                i++;
            } while (cursor.moveToNext());
        }


        return courselist;
    }


    public ArrayList<StudentModel> getSubStudent(String course_id,String batch_ad,String ca) {
        ArrayList<StudentModel> studentlist = new ArrayList<>();


        Log.d("date",ca);

        String selectQuery = "SELECT s.* FROM student as s JOIN courseassign as c on c.crn=s.crn WHERE c.course_id="+ course_id +" AND s.batch_ad="+ batch_ad +" ORDER BY s.crn";



        SQLiteDatabase db = this.getWritableDatabase();
        SQLiteDatabase db1 = this.getWritableDatabase();
        Cursor cursor = db.rawQuery(selectQuery, null);
        int i=0;
        if (cursor.moveToFirst()) {
            do {

                String crna=cursor.getString(cursor.getColumnIndex("crn"));
                String cr=crna;
                String firstName=cursor.getString(cursor.getColumnIndex("firstName"));
                String middleName=cursor.getString(cursor.getColumnIndex("middleName"));
                String lastName=cursor.getString(cursor.getColumnIndex("lastName"));
                String courseid=cursor.getString(cursor.getColumnIndex("course_id"));

                String queryattendrec="SELECT * FROM attendrec WHERE  date = '"+ ca +"' AND course_id ="+ course_id+ " AND crn ='"+ cr +"'";
                Cursor cursor1 = db1.rawQuery(queryattendrec, null);
                String present="1";
                String letter1="0";
                String request1="0";
                String others="0";
                String otherstxt1="";
                        i++;
                   if (cursor1.moveToFirst()) {

                            present = cursor1.getString(cursor1.getColumnIndex("present"));
                            letter1=cursor1.getString(cursor1.getColumnIndex("letter"));
                            request1=cursor1.getString(cursor1.getColumnIndex("request"));
                            others=cursor1.getString(cursor1.getColumnIndex("others"));
                            otherstxt1=cursor1.getString(cursor1.getColumnIndex("remarks"));



                    }
                    Log.d("present state ",present);
                studentlist.add(new StudentModel(crna, firstName, middleName,lastName,courseid,ca,present,letter1,request1,otherstxt1,others));
            } while (cursor.moveToNext());
        }


        return studentlist;
    }
    void insertAttend(String crna,String firstname,String middleName,String lastname,String courseid,int present,String date,int updated, int letter,int request,int others ,String othersreason){
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues values = new ContentValues();
        values.put("crn",crna);
        values.put("firstName",firstname);
        values.put("middleName",middleName);
        values.put("lastName",lastname);
        values.put("course_id",courseid);
        values.put("present",present);
        values.put("updated",updated);
        values.put("letter",letter);
        values.put("request",request);
        values.put("remarks",othersreason);
        values.put("others",others);




            long a=db.update(Table_Attend, values, "date = ? AND crn = ? AND course_id = ? " , new String[]{date, crna,courseid});

            if (db.update(Table_Attend, values, "date = ? AND crn = ? AND course_id = ? " , new String[]{date, crna,courseid})==0){
                Log.d("sqlite error","query sucessful");

                values.put("date", date);
                db.insert(Table_Attend, null, values);
            }
            else {
                String str = a+"";
                Log.d("sqlite error", str);

            }


//        values.put("date",date);
//        db.insert(Table_Attend,null,values);

    }

    void insertFirstAttend(String crna,String firstname,String middleName,String lastname,String courseid,int present,String date,int updated){
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues values = new ContentValues();
        values.put("crn",crna);
        values.put("firstName",firstname);
        values.put("middleName",middleName);
        values.put("lastName",lastname);
        values.put("course_id",courseid);
        values.put("present",present);
        values.put("updated",updated);
        values.put("date", date);



        String selectQuery = "SELECT present FROM attendrec WHERE  date = '"+ date +"' AND course_id ="+ courseid+ " AND crn ='"+ crna +"'";
        Cursor cursor = db.rawQuery(selectQuery, null);

        // looping through all rows and adding to list
        if (cursor.moveToFirst()) {
            do {
            //do nothing

            } while (cursor.moveToNext());
        }
        else {
            db.insert(Table_Attend, null, values);
        }


    }



    void registerUser() {


       List<getResultClass> dataArray;
        dataArray=new ArrayList<getResultClass>();
        String selectQuery = "SELECT * FROM attendrec WHERE updated = 0";

        SQLiteDatabase db = this.getWritableDatabase();
        Cursor cursor = db.rawQuery(selectQuery, null);


        // looping through all rows and adding to list
        if (cursor.moveToFirst()) {
            do {

                String firstName=cursor.getString(cursor.getColumnIndex("firstName"));
                String middleName=cursor.getString(cursor.getColumnIndex("middleName"));
                String lastName=cursor.getString(cursor.getColumnIndex("lastName"));
                String  course_id=cursor.getString(cursor.getColumnIndex("course_id"));
                String crn=cursor.getString(cursor.getColumnIndex("crn"));
                String date=cursor.getString(cursor.getColumnIndex("date"));
                String present=cursor.getString(cursor.getColumnIndex("present"));

                ContentValues values = new ContentValues();

                values.put("updated",1);
                db.update(Table_Attend, values, "date = ? AND crn = ? AND course_id = ? " , new String[]{date, crn,course_id});

                getResultClass dt=new getResultClass(firstName,middleName,lastName,crn,date,present,course_id);

                // add into list array
                dataArray.add(dt);



            }
            while (cursor.moveToNext());
            Toast.makeText(mcontext,"Updating...",Toast.LENGTH_SHORT).show();
            Gson gson=new Gson();

            final String newDataArray=gson.toJson(dataArray);
            Log.d("string",newDataArray);
            RequestQueue queue =  Volley.newRequestQueue(mcontext);
            String rooturl=Constants.ROOT_URL;
        String url = rooturl+"attendance_app/attendrec.php";

        StringRequest stringRequest=new StringRequest(Request.Method.POST, url, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                Log.d("response",response);
                if (response.equals("true")){

                }
                else {

                }



                Toast.makeText(mcontext,"Updated",Toast.LENGTH_SHORT).show();

            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {

            }
        }
        ){
            @Override
            protected Map<String, String> getParams() throws AuthFailureError {
                Map<String,String> param=new HashMap<String, String>();
                param.put("array",newDataArray);

                return param;
            }
        };
            Vconnection.getnInstance(mcontext).addRequestQue(stringRequest);
        }
        else {
            Toast.makeText(mcontext,"no need for update",Toast.LENGTH_SHORT).show();

        }



        }


        public void getAttendRecord(String date){

            String selectQuery = "SELECT * FROM attendrec WHERE date=" +date ;

        }

        public void report(){


        String selectQuery = "SELECT * FROM attendrec WHERE upadted=1";

            SQLiteDatabase db = this.getWritableDatabase();
            Cursor cursor = db.rawQuery(selectQuery, null);

        }


}

