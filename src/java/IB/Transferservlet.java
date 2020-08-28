/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package IB;

import java.io.IOException;
import java.io.PrintWriter;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import IB.validateservlet;
import java.sql.Connection;
import java.util.Date;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.text.SimpleDateFormat;
import javax.servlet.http.HttpSession;

/**
 *
 * @author Pawan
 */
public class Transferservlet extends HttpServlet {

    /**
     * Processes requests for both HTTP <code>GET</code> and <code>POST</code>
     * methods.
     *
     * @param request servlet request
     * @param response servlet response
     * @throws ServletException if a servlet-specific error occurs
     * @throws IOException if an I/O error occurs
   
     */
                   validateservlet obj=new validateservlet();
    // <editor-fold defaultstate="collapsed" desc="HttpServlet methods. Click on the + sign on the left to edit the code.">
    /**
     * Handles the HTTP <code>GET</code> method.
     *
     * @param request servlet request
     * @param response servlet response
     * @throws ServletException if a servlet-specific error occurs
     * @throws IOException if an I/O error occurs
     */
    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
    }

    /**
     * Handles the HTTP <code>POST</code> method.
     *
     * @param request servlet request
     * @param response servlet response
     * @throws ServletException if a servlet-specific error occurs
     * @throws IOException if an I/O error occur
     */
    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
                ResultSet s1,s2;
                                 HttpSession session = request.getSession();
                String original_uid = (String) session.getAttribute("uname");
                String original_pwd = (String) session.getAttribute("pwd");
                
                response.setContentType("text/html");
                String sender_account="";
                String user=request.getParameter("uname1");
		String acc=request.getParameter("accno1");
                String ifsc=request.getParameter("ifsc1");
		String am=request.getParameter("amt1");
                int amount=Integer.parseInt(am);
                int sender_balance=0;
                int receiver_balance=0;
                String date_of=request.getParameter("date");
                
                PrintWriter writer=response.getWriter();
                writer.println("<h1>"+user+acc+ifsc+am+date_of+original_uid+original_pwd+"</h1>");
                 try {
                                 ResultSet s;
                        String retrieved_user = "";
                        String retrieved_password = "";
                Class.forName("com.mysql.jdbc.Driver");
                Connection con;
                con = DriverManager.getConnection("jdbc:mysql://localhost:8282/student","project","123456");
                String sender_balance_retrieve="select account_no,balance from customer_det where user_id=?";
                PreparedStatement pss=con.prepareStatement(sender_balance_retrieve);
                pss.setString(1,original_uid);
                
             
                String receiver_balance_retrieve="select balance from customer_det where account_no=?";
                PreparedStatement psr=con.prepareStatement(receiver_balance_retrieve);
                psr.setString(1,acc);
                             
                s1=pss.executeQuery();
                            
                s2=psr.executeQuery();
                               
                                
                while(s1.next())
                {
                    sender_account=s1.getString(1);
                    sender_balance=s1.getInt(2);
                }
                s1.close();
 
                while(s2.next())
                {
                    receiver_balance=s2.getInt(1);
                }
                s2.close();
            
                if(sender_balance>amount)
                {
                    if(!acc.equals(sender_account))
                    {String sq = "insert into statement_table values(?,?,?,?,?,?)";
                PreparedStatement ps=con.prepareStatement(sq);
                ps.setString(1,date_of);
                ps.setString(2,sender_account);
                ps.setString(3,acc);
                ps.setInt(4,amount);
                sender_balance=sender_balance-amount;
                receiver_balance=receiver_balance+amount;
                ps.setInt(5,sender_balance);
                ps.setInt(6, receiver_balance);
                ps.executeUpdate();
                String up1="update customer_det set balance=? where account_no=?";
                PreparedStatement ups=con.prepareStatement(up1);
                ups.setInt(1,sender_balance);
                ups.setString(2,sender_account);
                String up2="update customer_det set balance=? where account_no=?";
                PreparedStatement upr=con.prepareStatement(up2);
                upr.setInt(1,receiver_balance);
                upr.setString(2,acc);
                ups.executeUpdate();
                upr.executeUpdate();
                        writer.println("<script type=\"text/javascript\">");
                        writer.println("alert('Transaction Successful');");
                        writer.println("location='welcome.html';");
                        writer.println("</script>");
                    }
                    else
                    {
                        writer.println("<script type=\"text/javascript\">");
                        writer.println("alert('Self transfer not allowed');");
                        writer.println("location='fundtransfer.html';");
                        writer.println("</script>");
                    }
                
                
                
                
                
                }
                else
                {
                                            writer.println("<script type=\"text/javascript\">");
                        writer.println("alert('Insufficient Funds');");
                        writer.println("location='fundtransfer.html';");
                        writer.println("</script>");
                }
                
                 }
                 catch (ClassNotFoundException | SQLException ex) {
                writer.println("<h2>"+ex+"</h2>");
            }
    }

    /**
     * Returns a short description of the servlet.
     *
     * @return a String containing servlet description
     */
    @Override
    public String getServletInfo() {
        return "Short description";
    }// </editor-fold>

}
