/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package edu.eci.arep.app;


import com.mongodb.DB;
import com.mongodb.DBCollection;
import com.mongodb.DBObject;
import com.mongodb.MongoClient;
import com.mongodb.MongoClientURI;
import com.mongodb.client.MongoDatabase;
import java.util.List;
import org.json.JSONObject;
import org.bson.Document;


/**
 *
 * @author Daniel
 */
public class conexionDB {
    

    private static MongoClient mongoClient;
    private static DB DB;
    private static DBCollection  coll;
    
    
    public conexionDB(){
        
        mongoClient = new MongoClient(new MongoClientURI("mongodb://ec2-3-85-237-66.compute-1.amazonaws.com:27018"));
        DB = mongoClient.getDB("DBLab5");
        coll = DB.getCollection("coleccion");
       
        
    }
    
    public List<DBObject> getName() {
        
        List<DBObject> datos = coll.find().toArray();
        if (datos.size() > 10) {
            int aux = datos.size() - 10;
            if (aux >= 0){
                return datos.subList(aux, datos.size());
            }
            else{
                return datos.subList(0, datos.size());
            }
            
        } else {
            return datos;
        }
    }

    public void addName(String entrada) {
        
        JSONObject name = new JSONObject(entrada);
        MongoDatabase DB = mongoClient.getDatabase("cadenas");
        Document doc = new Document();
        doc.append("name", name.get("name"));
        DB.getCollection("coleccion").insertOne(doc);
    }
}
