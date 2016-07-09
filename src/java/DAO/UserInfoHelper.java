/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package DAO;

import java.util.Iterator;
import java.util.LinkedList;
import java.util.List;
import org.hibernate.Criteria;
import org.hibernate.Query;
import org.hibernate.SQLQuery;
import org.hibernate.Session;
import org.hibernate.Transaction;
import org.hibernate.criterion.Restrictions;
import pck.AdminInfo;
import pck.Batch;
import pck.HibernateUtil;
import pck.Java01;
import pck.Performance;
import pck.Student;

/**
 *
 * @author admin
 */
public class UserInfoHelper {
//get the information form Student restrianted by register number 

    public List GetStuLoginInfo(String userId) {
        List<Student> student = null;
        try {
            Session session = HibernateUtil.getSessionFactory().getCurrentSession();
            Transaction transaction = session.beginTransaction();
            Criteria c = session.createCriteria(Student.class).add(Restrictions.eq("registerNum", userId));
            student = c.list();
            transaction.commit();
        } catch (Exception e) {
            System.out.println(e);
        }
        return student;
    }
//get the information form AdminInfo restrianted by Admin Name    

    public List GetAdimLoginInfo(String userName) {
        List<AdminInfo> adminInfo = null;
        try {
            Session session = HibernateUtil.getSessionFactory().getCurrentSession();
            Transaction transaction = session.beginTransaction();
            Criteria c = session.createCriteria(AdminInfo.class).add(Restrictions.eq("adminName", userName));
            adminInfo = c.list();
            transaction.commit();
        } catch (Exception e) {
            System.out.println(e);
        }
        return adminInfo;
    }

    //get the information form Performance restrianted by Register number   
    public List GetPerformance(String regisNum) {
        List<Performance> performance = new LinkedList<>();
        try {
            Session session = HibernateUtil.getSessionFactory().getCurrentSession();
            Transaction transaction = session.beginTransaction();
            Criteria c = session.createCriteria(Performance.class).add(Restrictions.eq("registerNum", regisNum));
            performance = c.list();
            transaction.commit();
        } catch (Exception e) {
            System.out.println(e);
        }
        return performance;
    }

    //get the table of  Student table by Batch number
    public List GetStuInfoByBatch(String batch) {
        List<Student> student = new LinkedList();
        Session session = HibernateUtil.getSessionFactory().getCurrentSession();
        Transaction transaction = session.beginTransaction();
        Criteria c = session.createCriteria(Student.class).add(Restrictions.eq("batchNum", batch));
        student = c.list();
        transaction.commit();
        return student;
    }

//get all the questions from the table 
    public List GetQue(String table) {
        List<Java01> Java = new LinkedList<>();
        Session session = HibernateUtil.getSessionFactory().getCurrentSession();
        Transaction transaction = session.beginTransaction();
        Query qry = session.createQuery("from " + table);
        Java = qry.list();
        transaction.commit();
        return Java;
    }

    //add questions into question table
    public String AddQues(String ques, String opt, String keys) {
        Session session = HibernateUtil.getSessionFactory().getCurrentSession();
        Transaction transaction = session.beginTransaction();
        SQLQuery query = session.createSQLQuery("Select max(JId) from Java01");
        int id = (int) query.list().get(0);
        id += 1;
        Java01 javaQues = new Java01();
        javaQues.setJid(id);
        javaQues.setQuestion(ques);
        javaQues.setOptions(opt);
        javaQues.setKeys(keys);
        session.save(javaQues);
        transaction.commit();
        return "added";
    }

    //get the list of questions from exact table for modification
    public List GetQuesForModify(String table, int num) {
        List<Java01> t = new LinkedList<>();
        Session session = HibernateUtil.getSessionFactory().getCurrentSession();
        Transaction tr = session.beginTransaction();
        Criteria c = session.createCriteria(Java01.class).add(Restrictions.eq("jid", num));
        t = c.list();
        tr.commit();
        return t;
    }

    //update the questions into database
    public String UpdateTable(int id, String ques, String opt, String keys) {
        Session session = HibernateUtil.getSessionFactory().getCurrentSession();
        Transaction tr = session.beginTransaction();
        Object obj = session.load(Java01.class, 1);
        Java01 java = (Java01) obj;
        java.setKeys(keys);
        java.setQuestion(ques);
        java.setKeys(keys);
        session.update(java);
        tr.commit();
        return "modified";
    }

    //register 
    public String StuRegister(String regisNum, String chName, String enName, String batch, String pass) {
        Session session = HibernateUtil.getSessionFactory().getCurrentSession();
        Transaction tr = session.beginTransaction();
        Student stu = new Student();
        stu.setRegisterNum(regisNum);
        stu.setChName(chName);
        stu.setEnName(enName);
        stu.setPassword(pass);
        stu.setBatchNum(batch);
        session.save(stu);
        tr.commit();
        return "registerSuccess";

    }

    public List GetCapacityOfBatch(String batch) {
        Session session = HibernateUtil.getSessionFactory().getCurrentSession();
        Transaction tr = session.beginTransaction();
        List<Batch> bt = new LinkedList<>();
        Criteria c = session.createCriteria(Batch.class).add(Restrictions.eq("batchNum", batch));
        bt = c.list();
        return bt;
    }

    //get the keys from database 

    public String[] GetKeys(String table) {
        Session session = HibernateUtil.getSessionFactory().getCurrentSession();
        Transaction tr = session.beginTransaction();
        List<Java01> j = new LinkedList<>();
        Query qry = session.createQuery("from " + table);
        j = qry.list();
        String[] keys = new String[20];
        int i =0;
        for(Iterator itr = j.iterator();itr.hasNext();){
            Java01 java = (Java01)itr.next();
            keys[i] = java.getKeys();
        }
        return keys;
    }
    //save score into database
    public  String SaveScore(String regisNum,String pracName,int score){
        Session session = HibernateUtil.getSessionFactory().getCurrentSession();
        Transaction tr = session.beginTransaction();
        Performance per = new Performance();
        per.setRegisterNum(regisNum);
        per.setPracticeName(pracName);
        per.setScore(score);
        session.save(per);
        tr.commit();
        return "scoreSaved";
}
    //change score
    public String ChangeScore(String regisNum,int score){
        Session session = HibernateUtil.getSessionFactory().getCurrentSession();
        Transaction tr = session.beginTransaction();
//        List<Performance> per = new LinkedList<>();
        Object obj = session.load(Performance.class, regisNum);
//        Criteria c = session.createCriteria(Performance.class).add(Restrictions.eq("registerNum", regisNum));
//        per = c.list();
//        for(Iterator itr = per.iterator();itr.hasNext();){
//            Performance permance = (Performance)itr.next();
//            permance.setScore(score);
//        }
        Performance per = (Performance)obj;
        per.setScore(score);
        session.update(per);
        tr.commit();
        return "scoreChanged";
    }
    }
    
