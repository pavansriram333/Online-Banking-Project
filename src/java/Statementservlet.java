/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

import java.io.IOException;
import java.io.PrintWriter;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

/**
 *
 * @author Pawan
 */
public class Statementservlet extends HttpServlet {



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
         HttpSession session = request.getSession();
         String acc_no="";
                String original_uid = (String) session.getAttribute("uname");
                response.setContentType("text/html;charset=UTF-8");
                        ResultSet det,accset;
                        
                try (PrintWriter writer = response.getWriter()) {
                            Class.forName("com.mysql.jdbc.Driver");
                            
                            
                Connection con;
                con = DriverManager.getConnection("jdbc:mysql://localhost:8282/student","project","123456");

                String acc_ret="select account_no from customer_det where user_id=?";
                PreparedStatement ap=con.prepareStatement(acc_ret);
                ap.setString(1,original_uid);
                accset=ap.executeQuery();
                while(accset.next())
                {
                acc_no=accset.getString(1);
                }
                writer.println(acc_no);

          
                ap=con.prepareStatement("select date_of_trans,amount,from_balance from statement_table where from_acc=? union select date_of_trans,amount,to_balance from statement_table where benificiary_ac=? order by date_of_trans desc;");
                ap.setString(1,acc_no);
                ap.setString(2,acc_no);
                writer.println("|||");
                det=ap.executeQuery();
                writer.println("|||");
                writer.println("<%@ page language=\"java\" contentType=\"text/html; charset=ISO-8859-1\" pageEncoding=\"ISO-8859-1\"%><!DOCTYPE html><html><head><link href=\"https://fonts.googleapis.com/css2?family=Lato:wght@400;900&display=swap\" rel=\"stylesheet\"><meta charset=\"utf-8\"><meta name=\"viewport\" content=\"width=device-width, initial-scale=1\"><link rel=\"stylesheet\" href=\"https://maxcdn.bootstrapcdn.com/bootstrap/3.4.1/css/bootstrap.min.css\"><script src=\"https://ajax.googleapis.com/ajax/libs/jquery/3.5.1/jquery.min.js\"></script><script src=\"https://maxcdn.bootstrapcdn.com/bootstrap/3.4.1/js/bootstrap.min.js\"></script><link href=\"style.css\" rel=\"stylesheet\" type=\"text/css\"></head><div class=\"wrapper\"><ul class=\"nav-area\"><li><a href=\"welcome.html\">Home</a></li><li><a href=\"accsummary.jsp\">Account Summary</a></li><li><a href=\"fundtransfer.html\">Transfer</a></li><li><a href=\"statement.jsp\">Statement</a></li><li><a href=\"change.html\">Change of details</a></li><li><a href=\"index.html\">Logout</a></li></ul></div><body><div class=\"welcome-text\"><div  class=\"container\" style=\"width:500px\"><div class=\"kuchnai\"> Account Statement</div><br><br>");
                writer.println("<table border=1 width=50% height=50% style=\"color:red\">");  
                writer.println("<tr><th>Date</th><th>Amount</th><th>Balance</th><tr>");
                while(det.next())
                {
                        String day=det.getString(1);
                        int am=det.getInt(2);
                        int bal=det.getInt(3);
                    
                        writer.println("<tr><td>" + day + "</td><td>" + am + "</td><td>" + bal + "</td></tr>");
                    
                }
                writer.println("</div></div></body></html>");
                } catch (ClassNotFoundException | SQLException ex) {
            Logger.getLogger(Statementservlet.class.getName()).log(Level.SEVERE, null, ex);
        }
                

        
                
    }
}



    