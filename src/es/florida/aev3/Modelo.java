package es.florida.aev3;

import static com.mongodb.client.model.Filters.eq;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.math.BigInteger;
import java.nio.file.Files;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;

import org.apache.commons.codec.binary.Base64;
import org.bson.Document;
import org.json.JSONObject;

import com.mongodb.MongoClient;
import com.mongodb.client.MongoCollection;
import com.mongodb.client.MongoCursor;
import com.mongodb.client.MongoDatabase;

public class Modelo {
	
	private static MongoClient mongoClient;
	private static MongoDatabase database;
	private static MongoCollection<Document> coleccionBooks;
	private static MongoCollection<Document> coleccionUsers;
	
	public Modelo() {
		
	}
	
	public static void conectarDDBB() {
		File archivo = new File("conexionDDBB.json");
		try {
			FileReader fr = new FileReader(archivo);
			BufferedReader br = new BufferedReader(fr);
			String conexion = br.readLine();
			br.close();
			fr.close();
			JSONObject obj = new JSONObject(conexion);
			mongoClient = new MongoClient(obj.getString("Cliente"), obj.getInt("Puerto"));
			database = mongoClient.getDatabase(obj.getString("Database"));
			coleccionBooks = database.getCollection(obj.getString("Coleccion1"));
			coleccionUsers = database.getCollection(obj.getString("Coleccion2"));
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
	
	public static String codificarPass(String password) {
		MessageDigest md = null;
		try {
			md = MessageDigest.getInstance("SHA-256");
		} 
		catch (NoSuchAlgorithmException e) {		
			e.printStackTrace();
			return null;
		}
		byte[] hash = md.digest(password.getBytes());
		StringBuffer sb = new StringBuffer();
		for(byte b : hash) {        
			sb.append(String.format("%02x", b));
		}
		return sb.toString();
	}
	
	public static boolean comprobarUsers(String usu, String contra) {
		
		MongoCursor<Document> cursor = coleccionUsers.find().iterator();
		while (cursor.hasNext()) {
			JSONObject obj = new JSONObject(cursor.next());
			if(usu.equals(obj.getString("user"))&&contra.equals(obj.getString("pass"))){
				return true;
			}
		}
		return false;
	}
	
	public static String convertirBase64(File img) {
		String img64 = "";
		try {
			byte[] fileContent = Files.readAllBytes(img.toPath());
			img64 = Base64.encodeBase64String(fileContent);
		} catch (IOException e) {
			e.printStackTrace();
		}
		return img64;
	}
	
	public static void crearBook(int id, String title, String autor, int any, int nax, String editorial, int pags, File img) {
		Document doc = new Document();
		doc.append("Id", id);
		doc.append("Titulo", title);
		doc.append("Autor", autor);
		doc.append("Anyo_nacimiento", nax);
		doc.append("Anyo_publicacion", any);
		doc.append("Editorial", editorial);
		doc.append("Numero_paginas", pags);
		doc.append("Thumbnail", convertirBase64(img));
		coleccionBooks.insertOne(doc);
	}
	
	public static void actualitzarBook(int id, String title, String autor, int any, int nax, String editorial, int pags, File img) {
		Document doc = new Document();
		doc.append("Id", id);
		doc.append("Titulo", title);
		doc.append("Autor", autor);
		doc.append("Anyo_nacimiento", nax);
		doc.append("Anyo_publicacion", any);
		doc.append("Editorial", editorial);
		doc.append("Numero_paginas", pags);
		doc.append("Thumbnail", convertirBase64(img));
		coleccionBooks.updateOne(eq("Id", id), new Document("$set",
				new Document(doc)));
	}
	
	public static void borrarBook(int id) {
		coleccionBooks.deleteOne(eq("Id", id)); 
	}
	
	public static void borrarColeecio() {
		coleccionBooks.drop(); 
	}
}
