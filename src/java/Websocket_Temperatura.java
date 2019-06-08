
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
@ServerEndpoint("/WebsocketCelda")
public class Websocket_Temperatura {
    static List<String> list_Celda = new ArrayList<>();
    static List<String> list_Celda_aux = new ArrayList<>();
    static Set<Session> users = Collections.synchronizedSet(new HashSet<Session>());
    
    final static String path = "C:\\Users\\emlar\\OneDrive\\Documentos\\NetBeansProjects\\socket_paralelo\\web\\celda_archivo.txt";
    final static String current_path = "C:\\Users\\emlar\\OneDrive\\Documentos\\NetBeansProjects\\socket_paralelo\\web\\current_celda_archivo.txt";
    
    @OnOpen
    public void onOpen(Session user){
        users.add(user);
        System.out.println("Conected: " + user.getId());
    }
    
    @OnMessage
    public void onMessage(String onmessage) throws IOException, JSONException{
        
        System.out.println("Celda" + onmessage);  

        if(Manipulacion_datos_listas.isJSONValid(onmessage)){
            JSONArray array = new JSONArray(onmessage);
                    
            
            for (int i=0;i<array.length();i++){
                list_Celda_aux.add(array.get(i).toString());
            }
                            
                   
            if (list_Celda.isEmpty()){
                copy_list(list_Celda,list_Celda_aux);
                Escribir_fichero ef = new Escribir_fichero();
                ef.Escrbir(Manipulacion_datos_listas.Crear_cadena_escritura("Celda", list_Celda_aux),current_path);
            }
            else{
                llenar_listas_aux(list_Celda,list_Celda_aux);
                Escribir_fichero ef = new Escribir_fichero();
                ef.Escrbir(Manipulacion_datos_listas.Crear_cadena_escritura("Celda", list_Celda_aux),current_path);
            }
            
            list_Celda_aux.clear();
        }else{
            if(onmessage.equals("Fin")){
                Escribir_fichero ef = new Escribir_fichero();
                ef.Escrbir(Manipulacion_datos_listas.Crear_cadena_escritura("Celda", list_Celda),path);
                list_Celda.clear();
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
