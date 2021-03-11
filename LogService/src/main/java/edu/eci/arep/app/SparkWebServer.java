package edu.eci.arep.app;

import org.json.JSONObject;
import static spark.Spark.port;
import static spark.Spark.get;
import static spark.Spark.post;

public class SparkWebServer {
    
    public static void main(String... args){
          
        port(getPort());
        get("/", (req,res) -> { 
            conexionDB cDB = new conexionDB();
            JSONObject json = new JSONObject();

            json.put("name", cDB.getName());
            return json;
        });

        post("/",(req,res) ->{
            conexionDB cDB = new conexionDB();
            cDB.addName(req.body());
            return "";
        });

    }

    private static int getPort() {
        if (System.getenv("PORT") != null) {
            return Integer.parseInt(System.getenv("PORT"));
        }
        return 4567;
    }
    
}
