package com.example.misaya.imool.DAO;

import java.util.ArrayList;

public class ResultInfo {
    private ArrayList<String> ids;
    private ArrayList<String> names;

    public ResultInfo(ArrayList<String> ids, ArrayList<String> names) {
        this.ids = ids;
        this.names = names;
    }

    public ArrayList<String> getIds() {
        return ids;
    }

    public void setIds(ArrayList<String> ids) {
        this.ids = ids;
    }

    public ArrayList<String> getNames() {
        return names;
    }

    public void setNames(ArrayList<String> names) {
        this.names = names;
    }
}
