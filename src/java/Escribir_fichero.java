
import java.io.FileWriter;
import java.io.PrintWriter;

/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

/**
 *
 * @author emlar
 */
public class Escribir_fichero {
    
    public Escribir_fichero(){
    }
    
    void Escrbir(String msj,String Path){
        FileWriter fichero = null;
        PrintWriter pw = null;
        
        try{
            fichero = new FileWriter(Path);
            pw =  new PrintWriter(fichero);
            pw.print(msj);
        }
        catch(Exception e){
            e.printStackTrace();
        }finally{
            try{
                if(null != fichero)
                    fichero.close();
            }
            catch(Exception e2){
                e2.printStackTrace();
            }
        }
        
    }
    
}
