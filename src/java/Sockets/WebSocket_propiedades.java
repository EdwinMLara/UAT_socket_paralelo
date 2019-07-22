/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Sockets;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.HashSet;
import java.util.Iterator;
import java.util.List;
import java.util.Set;
import javax.websocket.Session;

/**
 *
 * @author emlar
 */
public class WebSocket_propiedades {
    public static List<String> list;
    public static List<String> list_aux;
    public static Set<Session> users; 
    
    public static String path_general = "C:\\Users\\emlar\\OneDrive\\Documentos\\NetBeansProjects\\socket_paralelo\\web\\";
    public static String datos,datos_generales;
    
    public WebSocket_propiedades(String archivo,Set<Session> users, List<String> list,List<String> list_aux){
        WebSocket_propiedades.path_general = path_general.concat(archivo);
        WebSocket_propiedades.users = users;
        WebSocket_propiedades.list = list;
        WebSocket_propiedades.list_aux = list_aux;
        
    }
    
      public static void send_Message(String onmessage) throws IOException{
        Iterator<Session> interator = users.iterator();
        while(interator.hasNext()){
            interator.next().getBasicRemote().sendText(onmessage);
        }
    }
    
    public void llenar_listas_aux(List<String> list,List<String> list_aux){
        if(list.isEmpty()){
            list_aux.stream().forEach((aux) -> {
                list.add(aux);
            });
        }else{
            list.addAll(list_aux);
        }
    }
    
    public void copy_list(List<String> list, List<String> list_aux){
        list_aux.stream().forEach((aux)->{
            list.add(aux);
        });
    }
       
}
