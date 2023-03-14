package com.Servlet;

import java.io.IOException;
import java.io.PrintWriter;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.Statement;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.DBTool.DBUtil;

/**
 * Servlet implementation class ShowHealth
 */
@WebServlet("/ShowHealth")
public class ShowHealth extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
    /**
     * @see HttpServlet#HttpServlet()
     */
    public ShowHealth() {
        super();
        // TODO Auto-generated constructor stub
    }

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
		response.getWriter().append("Served at: ").append(request.getContextPath());
	}

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		String ID = request.getParameter("ID"); //用于接收前段输入的ID值，此处参数须和input控件的name值一致
        boolean type=false;//用于判断账号和密码是否与数据库中查询结果一致
        response.setContentType("text/html; charset=UTF-8");
        PrintWriter out = response.getWriter();
//        out.print(ID);
//        out.print(PW);
        try
        {
        	Connection con=DBUtil.getConnection();
        	Statement stmt=con.createStatement();
			String sql="select * from sports.healthrecord where username="+ID;
			//out.print(sql);
			System.out.println(sql);
			ResultSet rs=stmt.executeQuery(sql);
			String userid,date,time,place,state,tem;//声明2个变量分别为用户名，密码
            System.out.println("id\t 用户名\t 日期 \t  时间\t 地点\t 状态\t 体温");//其中\t相当于8个空格
            while(rs.next()){//遍历结果集
                userid=rs.getString("username");//获得id
                date=rs.getString("date");//获得id
                time=rs.getString("time");//获得id
                place=rs.getString("place");//获得id
                state=rs.getString("state");//获得id
                tem=rs.getString("tem");//获得id
                System.out.println(userid+"\t"+date+"\t"+time+"\t"+place+"\t"+
                		state+"\t"+tem+"\t");
                out.println(userid+"\t"+date+"\t"+time+"\t"+place+"\t"+
                		state+"\t"+tem+"\t"+"\n");
             }
			System.out.println("获得查询结果集");
        }
        catch(Exception ex)
        {
        	ex.printStackTrace();
        }
        finally
        {
        	DBUtil.Close();
        	//out.print(type);
        	//System.out.println(type);
        	out.flush();
        	out.close();
        }
	}

}
