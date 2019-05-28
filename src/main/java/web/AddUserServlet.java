package web;

import java.io.IOException;
import java.io.PrintWriter;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import util.DBUtils;


public class AddUserServlet extends HttpServlet{

	public void service (HttpServletRequest request,HttpServletResponse respones) 
			throws ServletException,IOException{
		request.setCharacterEncoding("UTF-8");
		//读取用户信息
		String user = request.getParameter("user");
		String password = request.getParameter("password");
		String email = request.getParameter("email");
		respones.setContentType("text/html;charset=UTF-8");
		PrintWriter out = respones.getWriter();
		//将用户信息插入到数据库
		Connection conn = null;
		PreparedStatement	stat = null;
		try {
			conn = DBUtils.getConn();
			stat = conn.prepareStatement("INSERT INTO t_user VALUES(null,?,?,?)");
			stat.setString(1, user);
			stat.setString(2, password);
			stat.setString(3, email);
			stat.executeUpdate();
			out.println("添加成功！");
		} catch (Exception e) {
			/*
			 * step1.记日志
			 * 将异常的所有信息记录下来，一般会记录到文件夹里面。
			 */
			e.printStackTrace();
			/*
			 * step2.看异常能否恢复，如果不能够恢复（比如，数据库服务暂停，网络中断等等，一般把这样
			 * 的异常称之为系统异常），则提示用户稍后重试：如果能够恢复，则立即恢复
			 */
			out.println("系统繁忙，稍后重试");
		}finally {
			if(conn != null) {
				try {
					conn.close();
				} catch (SQLException e) {
					e.printStackTrace();
				}
			}
		}
		
		/*
		 * 如果没有调用out.close()，则容器会自动关闭调用该方法。
		 * 这里的out由容器创立
		 */
		out.close();
		
	}
	
}
