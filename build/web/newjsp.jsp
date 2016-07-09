<%-- 
    Document   : newjsp
    Created on : 2016-1-4, 9:30:11
    Author     : admin
--%>

<%@page contentType="text/html" pageEncoding="UTF-8"%>
<%@page import="DAO.UserInfoHelper;" %>
<%@page import="java.util.*;" %>
<%@page import="pck.Student;" %>
<%@page import="java.util.Iterator;" %>
<%@page import="manageBean.StudentManagedBean"%>
<!DOCTYPE html>
<html>
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
        <title>JSP Page</title>
    </head>
    <body>
        njkljklj
        <%
//            out.println("hjkhjk");
            UserInfoHelper s = new UserInfoHelper();
            List<Student> stu = new LinkedList();
            stu = s.GetStuInfoByBatch("J01");
            for(Iterator i = stu.iterator();i.hasNext();){
                Student student = (Student)i.next();
                out.println(student.getRegisterNum());
            }
//            batch = s.batchPerformance();
//            for(Iterator i = batch.iterator();i.hasNext();){
//                String[] list = (String[])i.next();
//               for(String st:list){
//                   String string = st.toString();
//                   out.println(string);
//               }
//            }
        %>
    </body>
</html>
