package com.DBTool;

import java.sql.*;

public class DBUtil {
	private static String url="jdbc:mysql://localhost:3306/sports?serverTimezone=UTC&useUnicode=true&characterEncoding=utf-8&useSSL=false";
	private static String driverClass="com.mysql.cj.jdbc.Driver";
	private static String username="root";
	private static String password="123456";
	private static Connection conn;
	//װ������
	static{
		try{
			Class.forName(driverClass);
		}
		catch(ClassNotFoundException e){
			e.printStackTrace();
		}
	}
	//��ȡ���ݿ�����
	public static Connection getConnection(){
		try{
			conn=DriverManager.getConnection(url,username,password);
		}
		catch(SQLException e){
			e.printStackTrace();
		}
		return conn;
	}
	//�������ݿ�����
	public static void main(String[] args){
		Connection conn=DBUtil.getConnection();
		if(conn!=null){
			System.out.println("���ݿ����ӳɹ�");
		}
		else{
			System.out.println("���ݿ�����ʧ��");
		}
	}
	//�ر����ݿ�����
	public static void Close(){
		if(conn!=null){
			try{
				conn.close();
			}
			catch(SQLException e){
				e.printStackTrace();
			}
		}
	}
}