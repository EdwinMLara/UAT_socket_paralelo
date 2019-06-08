/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

import java.io.IOException;
import java.io.PrintWriter;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 *
 * @author emlar
 */
@WebServlet(urlPatterns = {"/Datos"})
public class Datos extends HttpServlet {
    String datos;
    final static String current_celda_path = "C:\\Users\\emlar\\OneDrive\\Documentos\\NetBeansProjects\\socket_paralelo\\web\\current_celda_archivo.txt";
    final static String current_flujo_path = "C:\\Users\\emlar\\OneDrive\\Documentos\\NetBeansProjects\\socket_paralelo\\web\\current_flujo_archivo.txt";
    
    final static String celda_path = "C:\\Users\\emlar\\OneDrive\\Documentos\\NetBeansProjects\\socket_paralelo\\web\\celda_archivo.txt";
    final static String flujo_path = "C:\\Users\\emlar\\OneDrive\\Documentos\\NetBeansProjects\\socket_paralelo\\web\\flujo_archivo.txt";
    
    final static String ecu_path = "C:\\Users\\emlar\\OneDrive\\Documentos\\NetBeansProjects\\socket_paralelo\\web\\ECU_archivo.txt";
    final static String current_ecu_path = "C:\\Users\\emlar\\OneDrive\\Documentos\\NetBeansProjects\\socket_paralelo\\web\\current_ECU_archivo.txt";
    
    final static String encoder_path = "C:\\Users\\emlar\\OneDrive\\Documentos\\NetBeansProjects\\socket_paralelo\\web\\Encoder_archivo.txt";
    final static String current_encoder_path = "C:\\Users\\emlar\\OneDrive\\Documentos\\NetBeansProjects\\socket_paralelo\\web\\current_Encoder_archivo.txt";
    
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
        
        String datos_generales = request.getParameter("Datos_generales");
        switch (datos_generales) {
            case "0":
                datos = cadena_impresion(current_celda_path,current_flujo_path,current_ecu_path,current_encoder_path);
                break;
            case "1":
                datos = cadena_impresion(celda_path,flujo_path,ecu_path,encoder_path);
                break;
            default:
                datos = "Los parámetors de la petición estan erroneos";
                break;
        }
    
        try (PrintWriter out = response.getWriter()) {
            /* TODO output your page here. You may use following sample code. */
            out.println("<!DOCTYPE html>");
            out.println("<html>");
            out.println("<head>");
            out.println("<title>Servlet Datos</title>");            
            out.println("</head>");
            out.println("<body>");
            out.println("<p>"+datos+"</p>");
            out.println("</body>");
            out.println("</html>");
        }
    }
    
    public String cadena_impresion(String path1,String path2,String path3,String path4){
        String datos2;
        Leer_fichero fichero_celda = new Leer_fichero(path1);
        datos2 = fichero_celda.leer();
        Leer_fichero fichero_flujo = new Leer_fichero(path2);
        datos2 = datos2 + fichero_flujo.leer();
        Leer_fichero fichero_ecu = new Leer_fichero(path3);
        datos2 = datos2 + fichero_ecu.leer();
        Leer_fichero fichero_encoder = new Leer_fichero(path4);
        datos2 = datos2 + fichero_encoder.leer();
        return  datos2;
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
