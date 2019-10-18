package com.example.attendance;

public class getResultClass {

    String firstName,middleName,lastName,crn,date,present,courseid;


    public getResultClass(String firstName1, String middleName1, String lastName1, String crn1, String date1, String present1, String courseid1){
        firstName=firstName1;
        lastName=lastName1;
        middleName=middleName1;
        crn=crn1;
        date=date1;
        present=present1;
        courseid=courseid1;

    }

    public String getFirstName() {
        return firstName;
    }

    public String getMiddleName() {
        return middleName;
    }
    public String getPresent() {
        return present;
    }

    public String getCourseid() {
        return courseid;
    }

    public String getCrn() {
        return crn;
    }

    public String getDate() {
        return date;
    }

    public String getLastName() {
        return lastName;
    }

}
