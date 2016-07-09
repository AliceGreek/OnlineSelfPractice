/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package manageBean;

import DAO.UserInfoHelper;
import java.util.Collection;
import java.util.Iterator;
import java.util.LinkedList;
import java.util.List;
import javax.enterprise.context.RequestScoped;
import javax.faces.component.UISelectItems;
import javax.inject.Named;
import pck.AdminInfo;
import pck.Batch;
import pck.Java01;
import pck.Performance;
import pck.Student;
import javax.faces.component.html.HtmlSelectOneRadio;
import javax.faces.model.SelectItem;

/**
 *
 * @author admin
 */
@Named("bean")
@RequestScoped
public class StudentManagedBean {

    HtmlSelectOneRadio rdb;

    public HtmlSelectOneRadio getRdb() {

        return rdb;
    }

    public void setRdb(HtmlSelectOneRadio rdb) {
        this.rdb = rdb;
    }
    /**
     * Creates a new instance of StudentManagedBean
     */
    //Get the userName and password from navigation 
    String userName, password;

    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    //Check whether the register number of student and the password match or not
    public String checkStudent() {
        String pass = null;
        List<Student> student = null;
        try {
            UserInfoHelper dao = new UserInfoHelper();
            student = dao.GetStuLoginInfo(userName);
            for (Iterator itr = student.iterator(); itr.hasNext();) {
                Student s = (Student) itr.next();
                pass = s.getPassword();
            }

        } catch (Exception e) {
            System.out.println(e);
        }
        if (this.password.equals(pass)) {
            return "Ok";
        } else {
            return "NotOk";
        }
    }
// get the adminName and password from AdminPage
    String adminName, adminpass;

    public String getAdminName() {
        return adminName;
    }

    public void setAdminName(String adminName) {
        this.adminName = adminName;
    }

    public String getAdminpass() {
        return adminpass;
    }

    public void setAdminpass(String adminpass) {
        this.adminpass = adminpass;
    }

    public StudentManagedBean() {
    }

    public String checkAdmin() {
        String adminpassword = null;
        String returnStr = "notAdmin";
        UserInfoHelper dao = new UserInfoHelper();
        List<AdminInfo> adminInfo = dao.GetAdimLoginInfo(this.adminName);
//        for (Iterator itr = adminInfo.iterator(); itr.hasNext();) {
//        Iterator itr = adminInfo.iterator();
//        while(itr.hasNext()){
//            AdminInfo admin = (AdminInfo)itr.next();
//            adminpassword = admin.getPassword();
//        }
        for (AdminInfo adm : adminInfo) {
            adminpassword = adm.getPassword();
        }
        //this.adminpass = adminpassword;
        //adminpassword = this.adminpass;
        boolean isValid = true;
        for (int i = 0; i < adminpass.length(); i++) {
            if (adminpass.charAt(i) != adminpassword.charAt(i)) {
                isValid = false;
            }
        }
        if (adminpassword != null) {
            if (isValid) {
                returnStr = "yesAdmin";
            } else {
                returnStr = "notAdmin";
            }
        }

        return returnStr;
    }

    /* Alternate method to get the question*/
    public List questionsOfJava() {
        List<Java01> java = new LinkedList<>();
        List AllQuestions = new LinkedList();
        try {
            UserInfoHelper dao = new UserInfoHelper();
            java = dao.GetQue("Java01");
            //Items for the Radio Buttons
//            UISelectItems items = new UISelectItems();
//            List<SelectItem> itemList = new LinkedList<>();
            for (Java01 j : java) {
                //Clear the items in the radio button group
//                items.getChildren().clear();
//                itemList.clear();
//                rdb.getChildren().clear();
//                
                String[] questions = new String[10];
                int i = 65;
                questions[0] = j.getJid() + "";
                questions[1] = j.getQuestion();
                String[] opt = j.getOptions().split("~");
                int o = 2;
                //String opText = "";
                for (String op : opt) {

                    char ch = (char) i;
                    //opText +=ch +"." +op;
                    questions[o] = ch + "." + op;
//                    SelectItem rdbItem = new SelectItem();
//                    rdbItem.setLabel(ch + ". " + op);
//                    rdbItem.setValue(ch);
//                    itemList.add(rdbItem);
                    i++;
                    o++;
                }
//                items.setValue(itemList);
//                rdb.getChildren().add(items);
//                getRdb().setValue(rdb);
                AllQuestions.add(questions);
            }
        } catch (Exception e) {
            System.out.println(e);
        }

        return AllQuestions;
    }
    //get Student performance by register number
    String registerNum;

    public String getRegisterNum() {
        return registerNum;
    }

    public void setRegisterNum(String registerNum) {
        this.registerNum = registerNum;
    }

    public String stuPerformance(String s) {
        List<Performance> perf = null;
        String practice = null;
        String score = null;
        UserInfoHelper dao = new UserInfoHelper();
        perf = dao.GetPerformance(registerNum);
        for (Iterator itr = perf.iterator(); itr.hasNext();) {
            Performance p = (Performance) itr.next();
            practice = p.getPracticeName();
            score = p.getScore().toString();
        }
        if (s.equals("practice")) {
            return practice;
        } else {
            return score;
        }
    }

    public String stuInfo(String p) {
        List<Student> stu = null;
        String batch = null;
        String enName = null;
        String chName = null;
        UserInfoHelper dao = new UserInfoHelper();
        stu = dao.GetStuLoginInfo(this.registerNum);
        for (Iterator itr = stu.iterator(); itr.hasNext();) {
            Student s = (Student) itr.next();
            batch = s.getBatchNum();
            enName = s.getEnName();
            chName = s.getChName();
        }
        if (p.equals("batch")) {
            return batch;
        } else if (p.equals("enName")) {
            return enName;
        } else {
            return chName;
        }
    }
    String item;

    //get the batch number which u select  and all the performance of this batch
    public String getItem() {
        return item;
    }

    public void setItem(String item) {
        this.item = item;
    }

    public List batchPerformance() {
        List performanceByBatch = new LinkedList();
        String[] onePerformance = null;
        List<Student> student = new LinkedList();
        List<String> registerNum = new LinkedList();
        UserInfoHelper dao = new UserInfoHelper();
        student = dao.GetStuInfoByBatch(this.item);
        for (Iterator itr = student.iterator(); itr.hasNext();) {
            onePerformance = new String[5];
            Student t = (Student) itr.next();
            String regis = t.getRegisterNum();
            onePerformance[0] = "Register Number:" + t.getRegisterNum();
            onePerformance[1] = "Chinese Name:" + t.getChName();
            onePerformance[2] = "English Name:" + t.getEnName();
            onePerformance[3] = "Practice name:" + "Java01";
            onePerformance[4] = "Score:" + "90";
//            List<Performance> per = new LinkedList<>();
//            per = dao.GetPerformance(regis);
//            for (Iterator i = per.iterator(); i.hasNext();) {
//                Performance p = (Performance) itr.next();
//                onePerformance[3] = "Practice name:" + p.getPracticeName();
//                onePerformance[4] = "Score:" + p.getScore().toString();
//            }
            performanceByBatch.add(onePerformance);
        }

        return performanceByBatch;
    }

    //add  questions into database
    String addPraName, addQues, addOpt, addAdmin, addKeys;

    public String getAddKeys() {
        return addKeys;
    }

    public void setAddKeys(String addKeys) {
        this.addKeys = addKeys;
    }

    public String getAddPraName() {
        return addPraName;
    }

    public void setAddPraName(String addPraName) {
        this.addPraName = addPraName;
    }

    public String getAddQues() {
        return addQues;
    }

    public void setAddQues(String addQues) {
        this.addQues = addQues;
    }

    public String getAddOpt() {
        return addOpt;
    }

    public void setAddOpt(String addOpt) {
        this.addOpt = addOpt;
    }

    public String getAddAdmin() {
        return addAdmin;
    }

    public void setAddAdmin(String addAdmin) {
        this.addAdmin = addAdmin;
    }

    public String addQuesIntoDatabase() {
        String sign = "";
        UserInfoHelper dao = new UserInfoHelper();
        sign = dao.AddQues(this.addQues, this.addOpt, this.addKeys);
        return sign;
    }
    //get the inout question table and num from modifyQues.xhtml and get the list of that and show them on 
    // another page
    String modifyTable;
    int modifyNum;

    public String getModifyTable() {
        return modifyTable;
    }

    public void setModifyTable(String modifyTable) {
        this.modifyTable = modifyTable;
    }

    public int getModifyNum() {
        return modifyNum;
    }

    public void setModifyNum(int modifyNum) {
        this.modifyNum = modifyNum;
    }

    public String showModifyQues(String col) {
        String s = null;
        List<Java01> list = new LinkedList<>();
        UserInfoHelper dao = new UserInfoHelper();
        list = dao.GetQuesForModify(this.modifyTable, this.modifyNum);
        for (Iterator itr = list.iterator(); itr.hasNext();) {
            Java01 o = (Java01) itr.next();
            if (col.equals("questions")) {
                s = o.getQuestion();
            } else if (col.equals("options")) {
                s = o.getOptions();
            } else if (col.equals("keys")) {
                s = o.getKeys();
            }
        }
        return s;
    }

    String moQues, moKeys, moOptions;

    public String getMoQues() {
        return moQues;
    }

    public void setMoQues(String moQues) {
        this.moQues = moQues;
    }

    public String getMoKeys() {
        return moKeys;
    }

    public void setMoKeys(String moKeys) {
        this.moKeys = moKeys;
    }

    public String getMoOptions() {
        return moOptions;
    }

    public void setMoOptions(String moOptions) {
        this.moOptions = moOptions;
    }

    public String updateTable() {
        UserInfoHelper dao = new UserInfoHelper();
        String s = "";
        s = dao.UpdateTable(this.modifyNum, this.moQues, this.moOptions, this.addKeys);
        return s;

    }
    //register and save data into database
    String regisNum,regisChName,regisEnName,regisBatch,regisPass;

    public String getRegisNum() {
        return regisNum;
    }

    public void setRegisNum(String regisNum) {
        this.regisNum = regisNum;
    }

    public String getRegisChName() {
        return regisChName;
    }

    public void setRegisChName(String regisChName) {
        this.regisChName = regisChName;
    }

    public String getRegisEnName() {
        return regisEnName;
    }

    public void setRegisEnName(String regisEnName) {
        this.regisEnName = regisEnName;
    }

    public String getRegisBatch() {
        return regisBatch;
    }

    public void setRegisBatch(String regisBatch) {
        this.regisBatch = regisBatch;
    }

    public String getRegisPass() {
        return regisPass;
    }

    public void setRegisPass(String regisPass) {
        this.regisPass = regisPass;
    }
    public String regisStu(){
        UserInfoHelper dao =new UserInfoHelper();
        String s = dao.StuRegister(this.registerNum, this.regisChName,this.regisEnName,this.regisBatch, this.regisPass);
        List<Batch> bt = new LinkedList<>();
        bt = dao.GetCapacityOfBatch(this.regisBatch);
        int capacity = 0;
        for (Batch b : bt) {
            capacity = b.getCapacity();
        }
        if(capacity < 35) return s;
        
        else return "full";
    }
    //check the keys of java
    String key ;
    int showScore;

    public int getShowScore() {
        return showScore;
    }

    public void setShowScore(int showScore) {
        this.showScore = showScore;
    }

    public String getKey() {
        return key;
    }

    public void setKey(String key) {
        this.key = key;
    }
     int score = 40;
    public String checkKeys(){
        UserInfoHelper dao = new UserInfoHelper();
        String[] keys = new String[20];
        keys  = dao.GetKeys("Java01");
        String[] ans = new String[20];
//        int score = 0;
        for(int i = 0 ;i < 20;i++)
        {
            
            if(keys[i].equals(ans[i])){
                score += 10;
            }
        }
        
        String s = dao.SaveScore(this.userName, "Java01", score);
        return s;
    }
    public int showScore(){
        int s = this.score;
    return 40;
    }
    //change score
    int changeScore;
    String changeRegisNum;

    public int getScore() {
        return score;
    }

    public void setScore(int score) {
        this.score = score;
    }

    public int getChangeScore() {
        return changeScore;
    }

    public void setChangeScore(int changeScore) {
        this.changeScore = changeScore;
    }

    public String getChangeRegisNum() {
        return changeRegisNum;
    }

    public void setChangeRegisNum(String changeRegisNum) {
        this.changeRegisNum = changeRegisNum;
    }
    public String changeScoreByAdmin(){
        UserInfoHelper dao  = new UserInfoHelper();
        String s = dao.ChangeScore(this.changeRegisNum, this.changeScore);
        return s;
    }
}
