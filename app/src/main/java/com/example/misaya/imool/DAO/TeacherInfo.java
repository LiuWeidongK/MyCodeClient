package com.example.misaya.imool.DAO;

public class TeacherInfo{
    private String randNum;
    private String cName;
    private String MAC;

    public TeacherInfo(String randNum, String cName, String MAC) {
        this.randNum = randNum;
        this.cName = cName;
        this.MAC = MAC;
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
}
