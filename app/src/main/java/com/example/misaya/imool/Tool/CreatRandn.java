package com.example.misaya.imool.Tool;

public class CreatRandn {
    private static int NCOUNT = 8;
    public String created(){
        String randnumber = "";
        for(int i=0;i<NCOUNT;i++){
            int n = (int)(Math.random()*10);
            randnumber += n;
        }
        return randnumber;
    }
}
