package com.example.attendance;

public class StudentModel {
    private String crn;
    private String firstname;
    private String middlename;
    private String lastname;
    private String course_id;
    private String date;
    private  String present_status,letter,request,otherstxt,others;


    public StudentModel(String crn1, String firstname1, String middlename1, String lastname1, String courseid,String date1,String present,String letter1,String request1,String othertxt,String others1) {

        crn=crn1;
        firstname=firstname1;
        middlename=middlename1;
        lastname=lastname1;
        course_id=courseid;
        date=date1;
        present_status=present;
        letter=letter1;
        request=request1;
        otherstxt=othertxt;
        others=others1;

    }

    public String getcrn() {
        return crn;
    }


    public String getFirstname() {
        return firstname;
    }


    public String getMiddlename() {
        return middlename;
    }

    public String getLastname() {
        return lastname;
    }

    public String getCourse_id() {
        return course_id;
    }
    public  String getDate(){
        return date;
    }

    public String getPresent_status(){
        return present_status;
    }


    public String getOtherstxt() {
        return otherstxt;
    }

    public String getRequest() {
        return request;
    }

    public String getLetter() {
        return letter;
    }

    public String getOthers() {
        return others;
    }
}
