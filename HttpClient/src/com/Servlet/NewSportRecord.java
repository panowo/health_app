package com.Servlet;

import java.io.IOException;
import java.io.PrintWriter;
import java.sql.Connection;
import java.sql.PreparedStatement;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.DBTool.DBUtil;

/**
 * Servlet implementation class NewSportRecord
 */
@WebServlet("/NewSportRecord")
public class NewSportRecord extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
    /**
     * @see HttpServlet#HttpServlet()
     */
    public NewSportRecord() {
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
		String ID = request.getParameter("ID");
		String DATE = request.getParameter("DATE"); //此处参数须和input控件的name值一致
        String TIME= request.getParameter("TIME");
        String PLACE= request.getParameter("PLACE");
        String s = new String(PLACE.getBytes("ISO8859-1"), "UTF-8");
        String DUR_TIME= request.getParameter("DUR_TIME");
        
        response.setContentType("text/html; charset=UTF-8");
        PrintWriter out = response.getWriter();
        boolean type=false;//用于
        try
        {
        	Connection con=DBUtil.getConnection();
        	PreparedStatement stmt;
        	
			String sql="insert into sportrecord(userid, date,time,place,dur_time) "
					+ "values('"+ID+"','"+DATE+"','"+TIME+"','"+s+"','"+DUR_TIME+"');";
			//out.print(sql);
			System.out.println(sql);
			stmt = con.prepareStatement(sql);
        	int num = stmt.executeUpdate();//添加、删除、更新数据
			if(num>0){
				type=true;
			}

//			ResultSet rs=stmt.executeQuery(sql);
        }
        catch(Exception ex)
        {
        	ex.printStackTrace();
        }
        finally
        {
        	DBUtil.Close();
        	out.print(type);
        	out.flush();
        	out.close();
        }
	}

}
