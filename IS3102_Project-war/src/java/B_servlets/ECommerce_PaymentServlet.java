/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package B_servlets;

import java.io.IOException;
import java.io.PrintWriter;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

/**
 *
 * @author Ibrahim
 */
@WebServlet(name = "ECommerce_PaymentServlet", urlPatterns = {"/ECommerce_PaymentServlet"})
public class ECommerce_PaymentServlet extends HttpServlet {

    /**
     * Processes requests for both HTTP <code>GET</code> and <code>POST</code>
     * methods.
     *
     * @param request servlet request
     * @param response servlet response
     * @throws ServletException if a servlet-specific error occurs
     * @throws IOException if an I/O error occurs
     */
    protected void processRequest(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        response.setContentType("text/html;charset=UTF-8");
        HttpSession session = request.getSession();
        try (PrintWriter out = response.getWriter()) {
            int datecheck = -1;
            String name = request.getParameter("txtName");
            String cardno = request.getParameter("txtCardNo");
            String securitycode = request.getParameter("txtSecuritycode");
            String Month = request.getParameter("Month");
            String year = request.getParameter("year");

            
            Pattern Mastercard = Pattern.compile("^5[1-5][0-9]{14}$");
            Matcher MastercardMatcher = Mastercard.matcher(cardno);
            boolean matchFound = MastercardMatcher.matches();
            
            Pattern Mastercard2 = Pattern.compile("^2[2-7][0-9]{14}$");
            Matcher MastercardMatcher2 = Mastercard2.matcher(cardno);
            boolean matchFound2 = MastercardMatcher2.matches();
        
            Pattern Visa = Pattern.compile("^4[0-9]{12}(?:[0-9]{1,6})?$");
            Matcher VisaMatcher = Visa.matcher(cardno);
            boolean matchFound3 = VisaMatcher.matches();
            
            try {
             SimpleDateFormat fullMonthFormat = new SimpleDateFormat("MMMM,yyyy"); 
             String getdate = Month + "," + year;
             Date date = new Date();
             String dateString = fullMonthFormat.format(date);
             Date inputdate = fullMonthFormat.parse(getdate);
             Date currentdate = fullMonthFormat.parse(dateString);
             
             datecheck = inputdate.compareTo(currentdate);
             
            }
            catch (ParseException e) {
                out.println("Error in parsing date");
            } 
            
            
            if (name.trim().equals("") || cardno.trim().equals("") || securitycode.trim().equals("") || year.trim().equals("")) {
                String result = "Please fill in all the fields for payment";
            response.sendRedirect("/IS3102_Project-war/B/SG/shoppingCart.jsp?errMsg=" + result);
            }
            else if (matchFound == false && matchFound2 == false && matchFound3 == false) {
                String result = "Please use a valid card number";
            response.sendRedirect("/IS3102_Project-war/B/SG/shoppingCart.jsp?errMsg=" + result);
            }
            else if (securitycode.length() != 3) {
                String result = "Please use a valid CCV";
            response.sendRedirect("/IS3102_Project-war/B/SG/shoppingCart.jsp?errMsg=" + result);
            }
            else if (year.length() != 4) {
                String result = "Please enter a valid year";
            response.sendRedirect("/IS3102_Project-war/B/SG/shoppingCart.jsp?errMsg=" + result);
            }
            else if (datecheck == -1) {
                String result = "Card expired";
            response.sendRedirect("/IS3102_Project-war/B/SG/shoppingCart.jsp?errMsg=" + result);
            }
            
            else {
                String result = "Thank you for shopping at Island Furniture. You have checkout successfully!";
            response.sendRedirect("/IS3102_Project-war/B/SG/shoppingCart.jsp?goodMsg=" + result);
                    
                
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
    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        processRequest(request, response);
    }

    /**
     * Handles the HTTP <code>POST</code> method.
     *
     * @param request servlet request
     * @param response servlet response
     * @throws ServletException if a servlet-specific error occurs
     * @throws IOException if an I/O error occurs
     */
    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        processRequest(request, response);
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
