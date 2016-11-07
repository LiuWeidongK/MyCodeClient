package com.example.misaya.imool.DAO;

import java.util.ArrayList;

public class StudentInfo_Macs {
    private String randnumber;
    private String id;
    private ArrayList<String> Macs;

    public StudentInfo_Macs(String randnumber, String id, ArrayList<String> macs) {
        this.randnumber = randnumber;
        this.id = id;
        Macs = macs;
    }

    public String getRandnumber() {
        return randnumber;
    }

    public void setRandnumber(String randnumber) {
        this.randnumber = randnumber;
    }

    public ArrayList<String> getMacs() {
        return Macs;
    }

    public void setMacs(ArrayList<String> macs) {
        Macs = macs;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }
}
