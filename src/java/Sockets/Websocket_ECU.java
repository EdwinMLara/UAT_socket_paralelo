
import Sockets.WebSocket_propiedades;
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
@ServerEndpoint("/WebsocketECU")
public class Websocket_ECU extends WebSocket_propiedades{
    
    public Websocket_ECU(){
        super("ECU_archivo.txt");
    }
    
    @OnOpen
    public void onOpen(Session user){
        users.add(user);
        System.out.println("Conected: " + user.getId());
    }
    
    @OnMessage
    public void onMessage(String onmessage) throws IOException, JSONException{
        System.out.println("ECU " + onmessage);  
        if(Manipulacion_datos_listas.isJSONValid(onmessage)){
            JSONArray array = new JSONArray(onmessage);
                    
            
            for (int i=0;i<array.length();i++){
                list_aux.add(array.get(i).toString());
            }
                                 
            if (list.isEmpty()){
                copy_list(list,list_aux);
                datos = Manipulacion_datos_listas.Crear_cadena_escritura("ECU", list_aux);
            }
            else{
                llenar_listas_aux(list,list_aux);
                datos = Manipulacion_datos_listas.Crear_cadena_escritura("ECU", list_aux);
            }
            
            list_aux.clear();
        }else{
            if(onmessage.equals("Fin")){
                Escribir_fichero ef = new Escribir_fichero();
                datos_generales = Manipulacion_datos_listas.Crear_cadena_escritura("ECU", list);
                ef.Escrbir(datos_generales,path_general);
                list.clear();
            }else{
                send_Message(onmessage);
            }
        }
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
