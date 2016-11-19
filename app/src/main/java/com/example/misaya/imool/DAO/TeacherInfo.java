package com.example.misaya.imool.DAO;

public class TeacherInfo{
    private String randNum;
    private String cName;
    private String MAC;
    private String section_1,section_2;

    public TeacherInfo(String randNum, String cName, String MAC,String section_1, String section_2) {
        this.randNum = randNum;
        this.cName = cName;
        this.MAC = MAC;
        this.section_1 = section_1;
        this.section_2 = section_2;
    }

    public String getRandNum() {
        return randNum;
    }

    public void setRandNum(String randNum) {
        this.randNum = randNum;
    }

    public String getcName() {
        return cName;
    }

    public void setcName(String cName) {
        this.cName = cName;
    }

    public String getMAC() {
        return MAC;
    }

    public void setMAC(String MAC) {
        this.MAC = MAC;
    }

    public String getSection_2() {
        return section_2;
    }

    public void setSection_2(String section_2) {
        this.section_2 = section_2;
    }

    public String getSection_1() {
        return section_1;
    }

    public void setSection_1(String section_1) {
        this.section_1 = section_1;
    }
}
