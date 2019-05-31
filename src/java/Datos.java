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
    final static String current_temperatura_path = "C:\\Users\\emlar\\OneDrive\\Documentos\\NetBeansProjects\\socket_paralelo\\web\\current_temperatura_archivo.txt";
    final static String current_humedad_path = "C:\\Users\\emlar\\OneDrive\\Documentos\\NetBeansProjects\\socket_paralelo\\web\\current_humedad_archivo.txt";
    
    final static String temperatura_path = "C:\\Users\\emlar\\OneDrive\\Documentos\\NetBeansProjects\\socket_paralelo\\web\\temperatura_archivo.txt";
    final static String humedad_path = "C:\\Users\\emlar\\OneDrive\\Documentos\\NetBeansProjects\\socket_paralelo\\web\\humedad_archivo.txt";
    
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
        if (datos_generales.equals("0")){
            datos = cadena_impresion(current_temperatura_path,current_humedad_path);
        }else if(datos_generales.equals("1")){
            datos = cadena_impresion(temperatura_path,humedad_path);
        }else{
            datos = "Los parámetors de la petición estan erroneos";
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
    
    public String cadena_impresion(String path1,String path2){
        String datos;
        Leer_fichero fichero_temperatura = new Leer_fichero(path1);
        datos = fichero_temperatura.leer();
        Leer_fichero fichero_humedad = new Leer_fichero(path2);
        datos = datos + fichero_humedad.leer();
        return  datos;
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
