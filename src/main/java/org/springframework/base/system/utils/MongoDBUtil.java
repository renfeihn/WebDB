//
// Source code recreated from a .class file by IntelliJ IDEA
// (powered by Fernflower decompiler)
//

package org.springframework.base.system.utils;

import com.mongodb.BasicDBObject;
import com.mongodb.CommandResult;
import com.mongodb.MongoClient;
import com.mongodb.MongoClientURI;
import com.mongodb.client.MongoCollection;
import com.mongodb.client.MongoCursor;
import com.mongodb.client.MongoDatabase;
import com.mongodb.client.MongoIterable;
import com.mongodb.client.model.Filters;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import org.bson.Document;
import org.bson.conversions.Bson;
import org.bson.types.ObjectId;
import org.springframework.base.system.utils.CryptoUtil;
import org.springframework.base.system.utils.DBUtil;

public class MongoDBUtil {
    private static MongoClient mongoClient;

    public MongoDBUtil(String databaseConfigId) throws Exception {
        MongoClientURI uri = null;
        DBUtil db = new DBUtil();
        String sql = " select id, name, databaseType , databaseName, userName ,  password, port, ip ,url ,isdefault from  treesoft_config where id=\'" + databaseConfigId + "\'";
        List list = db.executeQuery2(sql);
        Map map0 = (Map)list.get(0);
        String ip = "" + map0.get("ip");
        String port = "" + map0.get("port");
        String userName = "" + map0.get("userName");
        String databaseName = "" + map0.get("databaseName");
        String password = CryptoUtil.decode("" + map0.get("password"));
        if(password.indexOf("`") > 0) {
            password = password.split("`")[1];
        }

        (new StringBuilder()).append(map0.get("databaseType")).toString();
        if(userName.equals("")) {
            uri = new MongoClientURI("mongodb://" + ip + ":" + port);
        } else {
            uri = new MongoClientURI("mongodb://" + userName + ":" + password + "@" + ip + ":" + port + "/?authSource=" + databaseName + "&ssl=false");
        }

        mongoClient = new MongoClient(uri);
    }

    public MongoDatabase getDB(String dbName) {
        if(dbName != null && !"".equals(dbName)) {
            MongoDatabase database = mongoClient.getDatabase(dbName);
            return database;
        } else {
            return null;
        }
    }

    public MongoCollection<Document> getCollection(String dbName, String collName) {
        if(collName != null && !"".equals(collName)) {
            if(dbName != null && !"".equals(dbName)) {
                MongoCollection collection = mongoClient.getDatabase(dbName).getCollection(collName);
                return collection;
            } else {
                return null;
            }
        } else {
            return null;
        }
    }

    public List<String> getAllCollections(String dbName) {
        MongoIterable colls = this.getDB(dbName).listCollectionNames();
        ArrayList _list = new ArrayList();
        MongoCursor iterator = colls.iterator();

        while(iterator.hasNext()) {
            String name = (String)iterator.next();
            _list.add(name);
        }

        return _list;
    }

    public CommandResult getMongoStatus(String dbName) {
        CommandResult resultSet = mongoClient.getDB(dbName).getStats();
        return resultSet;
    }

    public CommandResult getMongoStatus2(String dbName) {
        CommandResult resultSet2 = mongoClient.getDB(dbName).command("serverStatus");
        return resultSet2;
    }

    public List<String> getAllDBNames() {
        MongoIterable colls = mongoClient.listDatabaseNames();
        ArrayList _list = new ArrayList();
        MongoCursor iterator = colls.iterator();

        while(iterator.hasNext()) {
            String name = (String)iterator.next();
            _list.add(name);
        }

        return _list;
    }

    public void dropDB(String dbName) {
        this.getDB(dbName).drop();
    }

    public Document runCommand(String dbName, String sql) {
        BasicDBObject arg0 = new BasicDBObject();
        Document doc = this.getDB(dbName).runCommand(arg0);
        return doc;
    }

    public Document findById(MongoCollection<Document> coll, String id) {
        new Document();
        Document myDoc;
        if(id.indexOf("ObjectId") >= 0) {
            id = id.replace("ObjectId(\"", "").replace("\")", "");
            ObjectId filter = new ObjectId(id);
            Document filter1 = new Document();
            filter1.append("_id", filter);
            myDoc = (Document)coll.find(filter1).first();
        } else {
            Document filter2 = new Document();
            filter2.append("_id", id);
            myDoc = (Document)coll.find(filter2).first();
        }

        return myDoc;
    }

    public int getCount(MongoCollection<Document> coll) {
        int count = (int)coll.count();
        return count;
    }

    public int getCountForFilter(MongoCollection<Document> coll, Bson filter) {
        MongoCursor cursor = coll.find(filter).iterator();

        int count;
        for(count = 0; cursor.hasNext(); ++count) {
            ;
        }

        return count;
    }

    public MongoCursor<Document> find(MongoCollection<Document> coll, Bson filter) {
        return coll.find(filter).iterator();
    }

    public MongoCursor<Document> findByPageForMongoDB(MongoCollection<Document> coll, Bson filter, int pageNo, int pageSize) {
        return coll.find(filter).skip((pageNo - 1) * pageSize).limit(pageSize).iterator();
    }

    public int deleteById(MongoCollection<Document> coll, String id) {
        byte count = 0;
        if(id.indexOf("ObjectId") >= 0) {
            id = id.replace("ObjectId(\"", "").replace("\")", "");
            ObjectId filter = new ObjectId(id);
            Document filter1 = new Document();
            filter1.append("_id", filter);
            coll.deleteOne(filter1);
        } else {
            Document filter2 = new Document();
            filter2.append("_id", id);
            coll.deleteOne(filter2);
        }

        return count;
    }

    public Document updateById(MongoCollection<Document> coll, String id, Document newdoc) {
        if(id.indexOf("ObjectId") >= 0) {
            id = id.replace("ObjectId(\"", "").replace("\")", "");
            ObjectId filter = new ObjectId(id);
            Bson filter1 = Filters.eq("_id", filter);
            coll.updateOne(filter1, new Document("$set", newdoc));
        } else {
            Bson filter2 = Filters.eq("_id", id);
            coll.updateOne(filter2, new Document("$set", newdoc));
        }

        return newdoc;
    }

    public void dropCollection(String databaseName, String tableName) {
        this.getDB(databaseName).getCollection(tableName).drop();
    }

    public void createCollection(String databaseName, String tableName) {
        this.getDB(databaseName).createCollection(tableName);
    }

    public void deleteCollection(String databaseName, String tableName) {
        this.getDB(databaseName).getCollection(tableName).drop();
    }

    public void close() {
        if(mongoClient != null) {
            mongoClient.close();
            mongoClient = null;
        }

    }

    public static void main(String[] args) {
        String dbName = "GC_MAP_DISPLAY_DB";
        String collName = "COMMUNITY_BJ";
    }

    public static boolean testConnection(String databaseType2, String databaseName2, String ip2, String port2, String user2, String pass2) {
        MongoClientURI uri = null;
        if(user2.equals("")) {
            uri = new MongoClientURI("mongodb://" + ip2 + ":" + port2);
        } else {
            uri = new MongoClientURI("mongodb://" + user2 + ":" + pass2 + "@" + ip2 + ":" + port2);
        }

        MongoClient mongoClient = new MongoClient(uri);
        MongoDatabase database = mongoClient.getDatabase(databaseName2);
        return database != null;
    }
}
