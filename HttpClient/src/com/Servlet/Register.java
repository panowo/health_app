package com.Servlet;

import java.io.IOException;
import java.io.PrintWriter;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.Statement;
import java.sql.PreparedStatement;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.DBTool.DBUtil;

/**
 * Servlet implementation class Register
 */
@WebServlet("/RegisterServlet")
public class Register extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
    /**
     * @see HttpServlet#HttpServlet()
     */
    public Register() {
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
		String ID = request.getParameter("ID"); //���ڽ���ǰ�������IDֵ���˴��������input�ؼ���nameֵһ��
        String PW= request.getParameter("PW");//���ڽ���ǰ�������PWֵ���˴��������input�ؼ���nameֵһ��
        boolean type=false;//�����ж��˺ź������Ƿ������ݿ��в�ѯ���һ��
        response.setContentType("text/html; charset=UTF-8");
        PrintWriter out = response.getWriter();
//        out.print(ID);
//        out.print(PW);
        try
        {
        	Connection con=DBUtil.getConnection();
        	PreparedStatement stmt;
			String sql="insert into user(userid, userpassword) values('"+ID+"', '"+PW+"');";
			//out.print(sql);
			System.out.println(sql);
			stmt = con.prepareStatement(sql);
        	int num = stmt.executeUpdate();//��ӡ�ɾ������������
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
//        	if (type)
//        	   out.print("ע��ɹ�!");
//        	else
//        		out.print("ע��ʧ��!");
        	out.flush();
        	out.close();
        }
        

    }
	

}
