package com.example.misaya.imool.Tool;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class CheckUtil {
    /*
        用户名：非空、长度小于20、只能由字母数字汉字组成 不可有特殊字符
     */
    public Boolean checkUser(String user) {
        if(user.trim().equals(""))
            return false;
        if(user.length()>20)
            return false;
        String regex = "([a-z]|[A-Z]|[0-9]|[\\u4e00-\\u9fa5])+";
        Pattern p = Pattern.compile(regex);
        Matcher m = p.matcher(user);
        return m.matches();
    }

    /*
        密码：非空、长度大于6小于18、只能由字母数字汉字组成 不可有特殊字符
     */
    public Boolean checkPass(String pass) {
        if(pass.trim().equals(""))
            return false;
        if(pass.length()<6||pass.length()>18)
            return false;
        String regex = "([a-z]|[A-Z]|[0-9])+";
        Pattern p = Pattern.compile(regex);
        Matcher m = p.matcher(pass);
        return m.matches();
    }

    /*
        确认密码
     */
    public Boolean checkAgainPass(String pass_1,String pass_2) {
        if(pass_1.equals(pass_2))
            return true;
        return false;
    }

    /*
        学生姓名：非空、长度大于2小于10、只能由汉字组成 不可有特殊字符
     */
    public Boolean checkName(String name) {
        if(name.trim().equals(""))
            return false;
        if(name.length()<2||name.length()>10)
            return false;
        String regex = "([\\u4e00-\\u9fa5])+";
        Pattern p = Pattern.compile(regex);
        Matcher m = p.matcher(name);
        return m.matches();
    }

    /*
        学生班级：非空、长度为6、只能由数字组成 不可有特殊字符
     */
    public Boolean checkClass(String _class) {
        if(_class.trim().equals(""))
            return false;
        if(_class.length()!=6)
            return false;
        String regex = "([0-9])+";
        Pattern p = Pattern.compile(regex);
        Matcher m = p.matcher(_class);
        return m.matches();
    }

    /*
       学生学号：非空、长度为8、只能由数字组成 不可有特殊字符
    */
    public Boolean checkId(String id) {
        if(id.trim().equals(""))
            return false;
        if(id.length()!=8)
            return false;
        String regex = "([0-9])+";
        Pattern p = Pattern.compile(regex);
        Matcher m = p.matcher(id);
        return m.matches();
    }
}
