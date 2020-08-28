/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package IB;
import java.sql.*;
import java.io.*;

import java.io.IOException;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

/**
 *
 * @author Pawan
 */
public class validateservlet extends HttpServlet {
    public String uid;
    protected String pwd;
    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
                response.setContentType("text/html");
                String user=request.getParameter("username");
		String acc=request.getParameter("Account");
                String phone=request.getParameter("Phone");
		String pass=request.getParameter("password");
                String mail=request.getParameter("mail");
		PrintWriter writer=response.getWriter();
                 try {
                                 ResultSet s;
                        String retrieved_user = "";
                        String retrieved_password = "";
                Class.forName("com.mysql.jdbc.Driver");
                Connection con;
                con = DriverManager.getConnection("jdbc:mysql://localhost:8282/student","project","123456");
                String sq = "insert into customer_det values(?,?,?,?,?,?)";
                PreparedStatement ps=con.prepareStatement(sq);
                ps.setString(1, user);
                ps.setString(2, acc);
                ps.setString(3, phone);
                ps.setString(4, mail);
                ps.setString(5, pass);
                ps.setInt(6,1000);
                
                ps.executeUpdate();
                response.sendRedirect("index.html");
                
                }
                

        catch (ClassNotFoundException | SQLException ex) {
                writer.println("<h2>"+ex+"</h2>");
            }
       
    }
    @Override
    public void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException,IOException {
                uid=request.getParameter("user");
		pwd=request.getParameter("passwordl");
		PrintWriter writer=response.getWriter();
                        HttpSession session = request.getSession();
                       session.setAttribute("uname",uid );
                       session.setAttribute("pwd",pwd );
                response.setContentType("text/html");


        
     try {
                                 ResultSet s,det;
                        String retrieved_user = "";
                        String retrieved_password = "";
                Class.forName("com.mysql.jdbc.Driver");
                Connection con;
                con = DriverManager.getConnection("jdbc:mysql://localhost:8282/student","project","123456");
                String sq = "select user_id,password from customer_det where user_id= ? and password=?";
                PreparedStatement ps=con.prepareStatement(sq);
                ps.setString(1, uid);
                ps.setString(2, pwd);
                
                s=ps.executeQuery();
                while(s.next())
                {// Position the cursor                  4 
                    retrieved_user = s.getString(1);        // Retrieve the first column value
                    retrieved_password = s.getString(2);  
                }// Retrieve the first column value
                                  // Print the column values
                if(retrieved_password.equals(pwd) &&  retrieved_user.equals(uid))
                {
                    response.sendRedirect("welcome.html");
                }
                else
                    {
                        writer.println("<script type=\"text/javascript\">");
                        writer.println("alert('User or password incorrect');");
                        writer.println("location='index.html';");
                        writer.println("</script>");
                    }


                }
                

        catch (ClassNotFoundException | SQLException ex) {
                writer.println("<h2>"+ex+"</h2>");
            }
       
        }
    }
