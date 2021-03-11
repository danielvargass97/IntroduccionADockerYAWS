package edu.eci.arep.app;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.MalformedURLException;
import java.net.URL;
import java.net.URLConnection;
import spark.Request;
import spark.Response;
import static spark.Spark.*;

public class RoundRobin {
    
    private static int aux = 1;
    public static void main(String args[]) {
        port(getPort());
        get("/Data", (req, res) -> DataPage(req, res) );
        get("/Results", (req, res) -> resultsPage(req, res));

    }

    private static String DataPage(Request req, Response res) {
        String dataPage
                = "<!DOCTYPE html>"
                + "<html>"
                +"<body style=\"background-color:#F8F9C1;\">" 
    		+"<font align=\"center\"  >"
                + "<h2>String Logs</h2>"
                + "<form action=\"/Results\">"
                + "  Enter string <br>"
                + "  <input type=\"text\" name=\"param\" >"
                + "  <br><br>"
                + "  <input type=\"submit\" value=\"Submit\">"
                + "</form>"
                + "</body>"
                + "</html>";
        return dataPage;
    }
    
    private static String resultsPage(Request req, Response res) throws MalformedURLException, IOException{
        
        String param = req.queryParams("param").replace(" ","+");        
        BufferedReader buffer = null;            
        URL roundService = null;
        if (aux==1){
        	roundService = new URL("http://ec2-54-236-9-109.compute-1.amazonaws.com:34000/Resultados?param="+param);
        } else if (aux==2){
        	roundService = new URL("http://ec2-54-236-9-109.compute-1.amazonaws.com:34001/Resultados?param="+param);
        } else if (aux==3){
        	roundService = new URL("http://ec2-54-236-9-109.compute-1.amazonaws.com:34002/Resultados?param="+param);
        }            
        URLConnection urlcon = roundService.openConnection(); 
        
        buffer = new BufferedReader(new InputStreamReader(urlcon.getInputStream()));		
        
        BufferedReader reader = new BufferedReader(
                
        new InputStreamReader(System.in));
        
        String line = buffer.readLine(); 
        
        System.out.println(line);
        buffer.close();
        reader.close();
        return line.replace("/", "").replace(",","");
    }
    


    static int getPort() {
        if (System.getenv("PORT") != null) {
            return Integer.parseInt(System.getenv("PORT"));
        }
        return 4567;
    }

}