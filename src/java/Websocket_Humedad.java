
import java.io.IOException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.HashSet;
import java.util.Iterator;
import java.util.List;
import java.util.Set;
import javax.websocket.OnClose;
import javax.websocket.OnError;
import javax.websocket.OnMessage;
import javax.websocket.OnOpen;
import javax.websocket.Session;
import javax.websocket.server.ServerEndpoint;
import org.json.JSONArray;
import org.json.JSONException;

/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

/**
 *
 * @author emlar
 */
@ServerEndpoint("/WebsocketHumedad")
public class Websocket_Humedad {
    static List<String> list_Humedad = new ArrayList<>();
    static List<String> list_Humedad_aux = new ArrayList<>();
    static Set<Session> users = Collections.synchronizedSet(new HashSet<Session>());
    
    final static String path = "C:\\Users\\emlar\\OneDrive\\Documentos\\NetBeansProjects\\socket_paralelo\\web\\humedad_archivo.txt";
    final static String current_path = "C:\\Users\\emlar\\OneDrive\\Documentos\\NetBeansProjects\\socket_paralelo\\web\\current_humedad_archivo.txt";
    
    
    @OnOpen
    public void onOpen(Session user){
        users.add(user);
        System.out.println("Conected: " + user.getId());
    }
    
    @OnMessage
    public void onMessage(String onmessage) throws IOException, JSONException{
                 System.out.println("Humedad " + onmessage);  
                 if(Manipulacion_datos_listas.isJSONValid(onmessage)){
            JSONArray array = new JSONArray(onmessage);
                    
            
            for (int i=1;i<array.length();i++){
                list_Humedad_aux.add(array.get(i).toString());
            }
                            
                   
            if (list_Humedad.isEmpty()){
                copy_list(list_Humedad,list_Humedad_aux);
                Escribir_fichero ef = new Escribir_fichero();
                ef.Escrbir(Manipulacion_datos_listas.Crear_cadena_escritura("Humedad", list_Humedad_aux),current_path);
            }
            else{
                llenar_listas_aux(list_Humedad,list_Humedad_aux);
                Escribir_fichero ef = new Escribir_fichero();
                ef.Escrbir(Manipulacion_datos_listas.Crear_cadena_escritura("Humedad", list_Humedad_aux),current_path);
            }
            
            list_Humedad_aux.clear();
        }else{
            if(onmessage.equals("Fin")){
                Escribir_fichero ef = new Escribir_fichero();
                ef.Escrbir(Manipulacion_datos_listas.Crear_cadena_escritura("Humedad", list_Humedad),path);
            }else{
                send_Message(onmessage);
            }
        }
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
    
     @OnClose
    public void handleClose(Session userSession){
        users.remove(userSession);
    }
    
    @OnError
    public void handleError(Throwable t){
        StackTraceElement[] stackTrace = null;
        t.setStackTrace(stackTrace);
    }
    
}
