/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package B_servlets;

import HelperClasses.Member;
import java.io.IOException;
import java.io.PrintWriter;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import javax.ws.rs.client.Client;
import javax.ws.rs.client.ClientBuilder;
import javax.ws.rs.client.Entity;
import javax.ws.rs.client.Invocation;
import javax.ws.rs.client.WebTarget;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;


/**
 *
 * @author Ibrahim
 */
@WebServlet(name = "ECommerce_MemberEditProfileServlet", urlPatterns = {"/ECommerce_MemberEditProfileServlet"})
public class ECommerce_MemberEditProfileServlet extends HttpServlet {

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
            HttpSession session = request.getSession();
            String result;
            String name = request.getParameter("name");
            String email = request.getParameter("email");
            String phone = request.getParameter("phone");
            String address = request.getParameter("address");
            int securityQuestion = Integer.parseInt(request.getParameter("securityQuestion"));
            String securityAnswer = request.getParameter("securityAnswer");
            int age = Integer.parseInt(request.getParameter("age"));
            int income = Integer.parseInt(request.getParameter("income"));
            String password = request.getParameter("password");
            String repassword = request.getParameter("repassword");
        response.setContentType("text/html;charset=UTF-8");
        PrintWriter out = response.getWriter();
        try {
            
            
//            out.println(name);
//            out.println(email);
//            out.println(phone);
//            out.println(address);
//            out.println(securityQuestion);
//            out.println(securityAnswer);
//            out.println(age);
//            out.println(income);
            String outcome = updateMember(email, name, phone, address, securityQuestion, securityAnswer, age, income);

            //out.println(outcome);
            Member m = new Member();
            m.setName(name);
            m.setEmail(email);
            m.setPhone(phone);
            m.setCity("Singapore");
            m.setAddress(address);
            m.setSecurityQuestion(securityQuestion);
            m.setSecurityAnswer(securityAnswer);
            m.setAge(age);
            m.setIncome(income);
            session.setAttribute("member", m);
                session.setAttribute("memberName", m.getName());
                result = "Saved!";
                response.sendRedirect("/IS3102_Project-war/B/SG/memberProfile.jsp?goodMsg=" + result);
        }
        
        catch (Exception ex) {
            out.println(ex);
            ex.printStackTrace();
        }
    }
    
    public String updateMember(String email, String name, String phone, String address, int securityQuestion,
            String securityAnswer, int age, int income) {
        Client client = ClientBuilder.newClient();
        WebTarget target = client
                .target("http://localhost:8080/IS3102_WebService-Student/webresources/entity.memberentity").path("updateMember")
                .queryParam("email", email)
                .queryParam("name", name)
                .queryParam("phone", phone)
                .queryParam("address", address)
                .queryParam("securityquestion", securityQuestion)
                .queryParam("securityanswer", securityAnswer)
                .queryParam("age", age)
                .queryParam("income", income);
        

        Invocation.Builder invocationBuilder = target.request(MediaType.APPLICATION_JSON);
        Response response = invocationBuilder.put(Entity.entity("", "application/json"));
        System.out.println("status: " + response.getStatus());
        String x = "";
                if (response.getStatus() == Response.Status.OK.getStatusCode()) {
            return x = "success";
        } else {
            return x = "failure";
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
