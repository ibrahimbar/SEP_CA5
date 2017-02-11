/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package B_servlets;


import HelperClasses.ShoppingCartLineItem;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.ArrayList;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import javax.ws.rs.client.Client;
import javax.ws.rs.client.ClientBuilder;
import javax.ws.rs.client.Invocation;
import javax.ws.rs.client.WebTarget;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

/**
 *
 * @author SEP-33
 */
@WebServlet(name = "ECommerce_AddFurnitureToListServlet", urlPatterns = {"/ECommerce_AddFurnitureToListServlet"})
public class ECommerce_AddFurnitureToListServlet extends HttpServlet {

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
        response.setContentType("text/html;charset=UTF-8");
        String id = request.getParameter("id");
        String SKU = request.getParameter("SKU");
        String name = request.getParameter("name");
        String imageURL = request.getParameter("imageURL");
        double price = Double.parseDouble(request.getParameter("price"));
        Long l = (Long) (session.getAttribute("countryID"));
        String z = getQuantity(l, SKU);
        int itemquantity = Integer.parseInt(z);
        int quantity = 1;
        int check = 0;
        int finalquantity = 0;
        
        
        ArrayList<ShoppingCartLineItem> shoppingCart = (ArrayList<ShoppingCartLineItem>) (session.getAttribute("shoppingCart"));
        for (ShoppingCartLineItem item : shoppingCart) { 
            if (item.getSKU().equals(SKU)) {
                check++;
                item.setQuantity((item.getQuantity() + 1));
                finalquantity = (item.getQuantity() + 1);
            }
        }
        
        
        
        if ((itemquantity > 0) && (finalquantity < itemquantity)) {
            String result = "Added product to cart";
            response.sendRedirect("/IS3102_Project-war/B/SG/shoppingCart.jsp?goodMsg=" + result);
            if (check == 0) {
                ShoppingCartLineItem s = new ShoppingCartLineItem();
                s.setCountryID(l);
                s.setId(id);
                s.setImageURL(imageURL);
                s.setName(name);
                s.setPrice(price);
                s.setQuantity(quantity);
                s.setSKU(SKU);
                
               shoppingCart.add(s); 
            }
            
        }
        
        else {
            String result = "Product is out of stock";
            response.sendRedirect("/IS3102_Project-war/B/SG/shoppingCart.jsp?errMsg=" + result);
        }
        
        session.setAttribute("shoppingCart", shoppingCart);

    }
    
    public String getQuantity(Long countryID, String SKU) {
        Client client = ClientBuilder.newClient();
        WebTarget target = client
                .target("http://localhost:8080/IS3102_WebService-Student/webresources/entity.countryentity").path("getQuantity")
                .queryParam("countryID", countryID)
                .queryParam("SKU", SKU);
        
        Invocation.Builder invocationBuilder = target.request(MediaType.APPLICATION_JSON);
        Response response = invocationBuilder.get();
        System.out.println("status: " + response.getStatus());

        if (response.getStatus() != 200) {
            return "error";
        }
        
       String x = "";
       x = response.readEntity(String.class);
       return x;
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
