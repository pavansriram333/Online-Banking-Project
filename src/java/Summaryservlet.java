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
import java.util.ArrayList;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import static org.apache.tomcat.jni.User.uid;

/**
 *
 * @author Pawan
 */
public class Summaryservlet extends HttpServlet {

    /**
     * Processes requests for both HTTP <code>GET</code> and <code>POST</code>
     * methods.
     *
     * @param request servlet request
     * @param response servlet response
     * @throws ServletException if a servlet-specific error occurs
     * @throws IOException if an I/O error occurs
     * @throws java.sql.SQLException
     */
    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
                                         HttpSession session = request.getSession();
                String original_uid = (String) session.getAttribute("uname");
        response.setContentType("text/html;charset=UTF-8");
        ResultSet det;
                            String uname="";
                    String acc="";
                    String ph="";
                    String mail="";
                    String pass="";
                    int bal=0;
        try (PrintWriter out = response.getWriter()) {
                            Class.forName("com.mysql.jdbc.Driver");
                Connection con;
                con = DriverManager.getConnection("jdbc:mysql://localhost:8282/student","project","123456");
                           String sqf = "select * from customer_det where user_id= ?";
                PreparedStatement psf=con.prepareStatement(sqf);
                psf.setString(1,original_uid);
                det=psf.executeQuery();

                while(det.next()){
                    uname=(det.getString(1));
                    acc=(det.getString(2));
                    ph=(det.getString(3));
                    mail=(det.getString(4));
                    pass=(det.getString(5));
                    bal=(det.getInt(6));
                }
              
                out.println("<%@ page language=\"java\" contentType=\"text/html; charset=ISO-8859-1\" pageEncoding=\"ISO-8859-1\"%><!DOCTYPE html><html><head><link href=\"https://fonts.googleapis.com/css2?family=Lato:wght@400;900&display=swap\" rel=\"stylesheet\"><meta charset=\"utf-8\"><meta name=\"viewport\" content=\"width=device-width, initial-scale=1\"><link rel=\"stylesheet\" href=\"https://maxcdn.bootstrapcdn.com/bootstrap/3.4.1/css/bootstrap.min.css\"><script src=\"https://ajax.googleapis.com/ajax/libs/jquery/3.5.1/jquery.min.js\"></script><script src=\"https://maxcdn.bootstrapcdn.com/bootstrap/3.4.1/js/bootstrap.min.js\"></script><link href=\"style.css\" rel=\"stylesheet\" type=\"text/css\"></head><div class=\"wrapper\"><ul class=\"nav-area\"><li><a href=\"welcome.html\">Home</a></li><li><a href=\"accsummary.jsp\">Account Summary</a></li><li><a href=\"fundtransfer.html\">Transfer</a></li><li><a href=\"index.html\">Statement</a></li><li><a href=\"change.jsp\">Change of details</a></li><li><a href=\"login.jsp\">Logout</a></li></ul></div><body><div class=\"welcome-text\"><div  class=\"container\" style=\"width:500px\"><div class=\"kuchnai\"> Account Details</div><br><br><label for=\"exampleFormControlInput1\">Email address:"+mail+" </label><br><br><label for=\"exampleFormControlTextarea1\">USER:"+uname+"</label><br><br><label for=\"phonenumber\">Phone:"+ph+"</label><br><br><label for=\"Balance\">Balance:"+bal+"</label><br><br><label for=\"ACnumber\">A/C Number:"+acc+"</label><br><br></div></div></body></html>");
                
    }   catch (ClassNotFoundException | SQLException ex) {
            Logger.getLogger(Summaryservlet.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
}

    // <editor-fold defaultstate="collapsed" desc="HttpServlet methods. Click on the + sign on the left to edit the code.">
    /**
     * Handles the HTTP <code>GET</code> method.
     *
     * @param request servlet request
     * @param response servlet response
     * @throws ServletException if a servlet-specific error occurs
     * @throws IOException if an I/O error occurs
     */
