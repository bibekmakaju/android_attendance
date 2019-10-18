package com.example.attendance;

public class SubjectModel {

    private String title;
    private String course_code;
    private String Course_id;
    private String batch_ad;
    private  String sem_id;
    private String dept_id;


    public SubjectModel(String title1, String coursecode, String courseid, String batchad,String deptid,String semid) {

        title = title1;
        course_code = coursecode;
        Course_id = courseid;
        batch_ad=batchad;
        sem_id=semid;
        dept_id=deptid;


    }

    public String getTitle() {
        return title;
    }


    public String getcourse_code() {
        return course_code;
    }


    public String getCourse_id() {
        return Course_id;
    }

    public String getBatch_ad() {
        return batch_ad;
    }

    public String getDept_id() {
        return dept_id;
    }

    public String getSem_id() {
        return sem_id;
    }
}

