package es.florida.aev3;

import static com.mongodb.client.model.Filters.eq;
import static com.mongodb.client.model.Filters.lt;
import static com.mongodb.client.model.Filters.gt;
import static com.mongodb.client.model.Filters.and;
import com.mongodb.client.model.Filters;

import java.awt.Image;
import java.awt.image.BufferedImage;
import java.io.BufferedReader;
import java.io.ByteArrayInputStream;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.math.BigInteger;
import java.nio.file.Files;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.ArrayList;
import java.util.List;

import javax.imageio.ImageIO;
import javax.swing.ImageIcon;

import org.apache.commons.codec.binary.Base64;
import org.bson.Document;
import org.bson.conversions.Bson;
import org.json.JSONObject;

import com.mongodb.MongoClient;
import com.mongodb.client.MongoCollection;
import com.mongodb.client.MongoCursor;
import com.mongodb.client.MongoDatabase;

public class Modelo {
	/**
	 * 
	 * @author Borja_Zafra**Roberto_Martínez
	 *
	 */
	private static MongoClient mongoClient;
	private static MongoDatabase database;
	private static MongoCollection<Document> coleccionBooks;
	private static MongoCollection<Document> coleccionUsers;
	
	public Modelo() {
		
	}
	
	
	/**
	 * Conecta a base de dades
	 */
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
	
	/**
	 * Obtenim l'imatge de portada de la BBDD
	 * @param id
	 * @return
	 */
	public static String getImg(int id) {
		
		Bson query = eq("Id", id);
		MongoCursor<Document> cursor = coleccionBooks.find(Filters.exists("Anyo_nacimiento")).iterator();
		JSONObject book;
		String img="";
		while (cursor.hasNext()) {
			book = new JSONObject(cursor.next().toJson());
			img = book.getString("Thumbnail");
		}
		return img;
	}
	
	/**
	 * Reescalar la portada que es visualitza
	 * @param id
	 * @param wid
	 * @param heig
	 * @return
	 */
	public static ImageIcon convertirImg(int id, int wid, int heig) {
		Bson query = eq("Id", id);
		MongoCursor<Document> cursor = coleccionBooks.find(and(Filters.exists("Anyo_nacimiento"), query)).iterator();
		JSONObject book;
		ImageIcon imatgeIcona=null;
		while (cursor.hasNext()) {
			book = new JSONObject(cursor.next().toJson());
			byte[] btDataFile =Base64.decodeBase64(book.getString("Thumbnail"));
			try {
				BufferedImage img = ImageIO.read(new ByteArrayInputStream(btDataFile));
				Image dimg = img.getScaledInstance(wid, heig,
				        Image.SCALE_SMOOTH);
				imatgeIcona = new ImageIcon(dimg); 
				return imatgeIcona;
			} catch (IOException e) {
				e.printStackTrace();
			}
		}
		return imatgeIcona;
	}
	/**
	 * Codificar la contrasenya
	 * @param password
	 * @return
	 */
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
	/**
	 * Validacio d'usuari i contrasenya
	 * @param usu
	 * @param contra
	 * @return
	 */
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
	/**
	 * Convertir una imatge a Base64 per a posarla a la BBDD
	 * @param img
	 * @return
	 */
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
	
	/**
	 * Insertar un registre a la BBDD
	 * @param id
	 * @param title
	 * @param autor
	 * @param any
	 * @param nax
	 * @param editorial
	 * @param pags
	 * @param img
	 */
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
	/**
	 * Actualitzar un registre a la BBDD
	 * @param id
	 * @param title
	 * @param autor
	 * @param any
	 * @param nax
	 * @param editorial
	 * @param pags
	 * @param img
	 */
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
	/**
	 * Borrar un registre a la BBDD
	 * @param id
	 */
	public static void borrarBook(int id) {
		coleccionBooks.deleteOne(eq("Id", id)); 
	}
	/**
	 * Borrar la coleccio de la BBDD
	 */
	public static void borrarColeecio() {
		coleccionBooks.drop(); 
	}
	/**
	 * Tabla per defecte que es mostra al programa amb l'informacio basica.
	 * @return
	 */
	public static String defaultSearch() {
		MongoCursor<Document> cursor = coleccionBooks.find().iterator();
		JSONObject book;
		List<String> rows = new ArrayList<String>();
		String row = "";
		while (cursor.hasNext()) {
			book = new JSONObject(cursor.next().toJson());
			row = "<TR><TD>" + book.getInt("Id") + "</TD><TD>" + book.getString("Titulo") + "</TD><TD>"
					+ book.getString("Autor") + "</TD></TR>";
			rows.add(row);
		}
		String head = "<HTML><BODY><TABLE border=1 align=center><TR><TH>ID</TH><TH>TITOL</TH><TH>AUTOR</TH></TR>";
		String close = "</TABLE></BODY></HTML>";
		String table = head;
		for (String r : rows) {
			table += r;
		}
		table += close;
		return table;
	}
	/**
	 * Fer una busqueda de tipus Equal.
	 * @param id
	 * @return
	 */
	public static String buscarEqual(int id) {
		Bson query = eq("Id", id);
		MongoCursor<Document> cursor = coleccionBooks.find(and(Filters.exists("Anyo_nacimiento"), query)).iterator();
		JSONObject book;
		List<String> rows = new ArrayList<String>();
		String row = "";
		while (cursor.hasNext()) {
			book = new JSONObject(cursor.next().toJson());
			row = "<TR><TD>" + book.getInt("Id") + "</TD><TD>" + book.getString("Titulo") + "</TD><TD>"
					+ book.getString("Autor") + "</TD><TD>" + book.getInt("Anyo_nacimiento") + "</TD><TD>"
					+ book.getInt("Anyo_publicacion") + "</TD><TD>" + book.getString("Editorial") + "</TD><TD>"
					+ book.getInt("Numero_paginas") + "</TD></TR>";
			rows.add(row);
		}
		String head = "<HTML><BODY><TABLE border=1 align=center><TR><TH>ID</TH><TH>TITOL</TH><TH>AUTOR</TH><TH>ANY DE NAIXMENT</TH><TH>ANY DE PUBLICACIO</TH><TH>EDITORIAL</TH><TH>PAGINES</TH></TR>";
		String close = "</TABLE></BODY></HTML>";
		String table = head;
		for (String r : rows) {
			table += r;
		}
		table += close;
		return table;
	}
	/**
	 * Fer una busqueda de tipus Less Than.
	 * @param id
	 * @return
	 */
	public static String buscarMenor(int id) {
		Bson query = lt("Id", id);
		MongoCursor<Document> cursor = coleccionBooks.find(and(Filters.exists("Anyo_nacimiento"), query)).iterator();
		JSONObject book;
		List<String> rows = new ArrayList<String>();
		String row = "";
		while (cursor.hasNext()) {
			book = new JSONObject(cursor.next().toJson());
			row = "<TR><TD>" + book.getInt("Id") + "</TD><TD>" + book.getString("Titulo") + "</TD><TD>"
					+ book.getString("Autor") + "</TD><TD>" + book.getInt("Anyo_nacimiento") + "</TD><TD>"
					+ book.getInt("Anyo_publicacion") + "</TD><TD>" + book.getString("Editorial") + "</TD><TD>"
					+ book.getInt("Numero_paginas") + "</TD></TR>";
			rows.add(row);
		}
		String head = "<HTML><BODY><TABLE border=1 align=center><TR><TH>ID</TH><TH>TITOL</TH><TH>AUTOR</TH><TH>ANY DE NAIXMENT</TH><TH>ANY DE PUBLICACIO</TH><TH>EDITORIAL</TH><TH>PAGINES</TH></TR>";
		String close = "</TABLE></BODY></HTML>";
		String table = head;
		for (String r : rows) {
			table += r;
		}
		table += close;
		return table;
	}
	/**
	 * Fer una busqueda de tipus Greater Than.
	 * @param id
	 * @return
	 */
	public static String buscarMatjor(int id) {
		Bson query = gt("Id", id);
		MongoCursor<Document> cursor = coleccionBooks.find(and(Filters.exists("Anyo_nacimiento"), query)).iterator();
		JSONObject book;
		List<String> rows = new ArrayList<String>();
		String row = "";
		while (cursor.hasNext()) {
			book = new JSONObject(cursor.next().toJson());
			row = "<TR><TD>" + book.getInt("Id") + "</TD><TD>" + book.getString("Titulo") + "</TD><TD>"
					+ book.getString("Autor") + "</TD><TD>" + book.getInt("Anyo_nacimiento") + "</TD><TD>"
					+ book.getInt("Anyo_publicacion") + "</TD><TD>" + book.getString("Editorial") + "</TD><TD>"
					+ book.getInt("Numero_paginas") + "</TD></TR>";
			rows.add(row);
		}
		String head = "<HTML><BODY><TABLE border=1 align=center><TR><TH>ID</TH><TH>TITOL</TH><TH>AUTOR</TH><TH>ANY DE NAIXMENT</TH><TH>ANY DE PUBLICACIO</TH><TH>EDITORIAL</TH><TH>PAGINES</TH></TR>";
		String close = "</TABLE></BODY></HTML>";
		String table = head;
		for (String r : rows) {
			table += r;
		}
		table += close;
		return table;
	}
}
