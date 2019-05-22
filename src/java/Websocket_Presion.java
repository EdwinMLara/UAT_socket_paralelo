
import java.io.IOException;
import java.util.Collections;
import java.util.HashSet;
import java.util.Iterator;
import java.util.Set;
import javax.websocket.OnClose;
import javax.websocket.OnError;
import javax.websocket.OnMessage;
import javax.websocket.OnOpen;
import javax.websocket.Session;
import javax.websocket.server.ServerEndpoint;

/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

/**
 *
 * @author emlar
 */
@ServerEndpoint("/WebsocketPresion")
public class Websocket_Presion {
    static Set<Session> users = Collections.synchronizedSet(new HashSet<Session>());
    
    @OnOpen
    public void onOpen(Session user){
        users.add(user);
        System.out.println("Conected: " + user.getId());
    }
    
    @OnMessage
    public void onMessage(String onmessage) throws IOException{
                 System.out.println("Presion " + onmessage);  
                 send_Message(onmessage);
    }
    
    
    public static void send_Message(String onmessage) throws IOException{
        Iterator<Session> interator = users.iterator();
        while(interator.hasNext()){
            interator.next().getBasicRemote().sendText(onmessage);
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
