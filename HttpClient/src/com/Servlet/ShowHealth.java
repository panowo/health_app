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
		String ID = request.getParameter("ID"); //���ڽ���ǰ�������IDֵ���˴��������input�ؼ���nameֵһ��
        boolean type=false;//�����ж��˺ź������Ƿ������ݿ��в�ѯ���һ��
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
			String userid,date,time,place,state,tem;//����2�������ֱ�Ϊ�û���������
            System.out.println("id\t �û���\t ���� \t  ʱ��\t �ص�\t ״̬\t ����");//����\t�൱��8���ո�
            while(rs.next()){//���������
                userid=rs.getString("username");//���id
                date=rs.getString("date");//���id
                time=rs.getString("time");//���id
                place=rs.getString("place");//���id
                state=rs.getString("state");//���id
                tem=rs.getString("tem");//���id
                System.out.println(userid+"\t"+date+"\t"+time+"\t"+place+"\t"+
                		state+"\t"+tem+"\t");
                out.println(userid+"\t"+date+"\t"+time+"\t"+place+"\t"+
                		state+"\t"+tem+"\t"+"\n");
             }
			System.out.println("��ò�ѯ�����");
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
