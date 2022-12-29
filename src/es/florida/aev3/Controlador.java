package es.florida.aev3;

import static javax.swing.JOptionPane.showMessageDialog;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.File;

import javax.swing.Icon;
import javax.swing.ImageIcon;
import javax.swing.JFileChooser;
import javax.swing.JOptionPane;

import es.florida.aev3.Modelo;
import es.florida.aev3.Vista;

public class Controlador {
	/**
	 * 
	 * @author Borja_Zafra**Roberto_Martínez
	 *
	 */
	private Modelo modelo;
	private Vista vista;
	private ActionListener actionListener_CrearRegistre, actionListener_SeleccionarImatge,actionListener_CargarConexio,
	actionListener_Login, actionListener_EsborrarRegistre,actionListener_ActualitzarRegistre,actionListener_EsborrarColeccio,
	actionListener_Busqueda;
	
	/**
	 * Constructor de Controlador
	 * @param m
	 * @param v
	 */
	Controlador(Modelo m, Vista v){
		this.modelo=m;
		this.vista=v;
		control();
	}
	
	public void control() {
		
		actionListener_CargarConexio = new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				Modelo.conectarDDBB();
				vista.getTxtUser().setEnabled(true);
				vista.getPsfPassword().setEnabled(true);
				vista.getBtnLogin().setEnabled(true);
			}	
		};
		vista.getBtnLoadConnection().addActionListener(actionListener_CargarConexio);
		
		actionListener_Login = new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				if(Modelo.comprobarUsers(vista.getTxtUser().getText(), Modelo.codificarPass(vista.getPsfPassword().getText()))){
					showMessageDialog(null, "Benbingut "+vista.getTxtUser().getText()+".");
					vista.getTxtId().setEnabled(true);
					vista.getTxtTitle().setEnabled(true);
					vista.getTxtAuthor().setEnabled(true);
					vista.getTxtAnyNax().setEnabled(true);
					vista.getTxtEditorial().setEnabled(true);
					vista.getTxtAnyPub().setEnabled(true);
					vista.getTxtNumPag().setEnabled(true);
					vista.getBtnCrear().setEnabled(true);
					vista.getBtnUpdate().setEnabled(true);
					vista.getBtnDeleteOne().setEnabled(true);
					vista.getBtnDeleteAll().setEnabled(true);
					vista.getBtnImageFile().setEnabled(true);
					vista.getBtnSearch().setEnabled(true);
					vista.getEpHTML().setEnabled(true);
					vista.getEpHTML().setText(Modelo.defaultSearch());
					vista.getCmbQuery().setEnabled(true);
				}else {
					showMessageDialog(null, "Usuari o Contrasenya incorrectes.");
				}
			}	
		};
		vista.getBtnLogin().addActionListener(actionListener_Login);
		
		/**
		 * Obrim un FileChooser per a seleccionar una imatge 
		 */
		actionListener_SeleccionarImatge = new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				JFileChooser seleccion = new JFileChooser();
				seleccion.setFileSelectionMode(JFileChooser.FILES_ONLY);
				int returnVal = seleccion.showOpenDialog(seleccion);
			
				if (returnVal == JFileChooser.APPROVE_OPTION) {
					vista.getLblRoute().setText(seleccion.getSelectedFile().getAbsolutePath());
				}
			}
		};
		vista.getBtnImageFile().addActionListener(actionListener_SeleccionarImatge);
		
		actionListener_CrearRegistre = new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				File imageFile = new File (vista.getLblRoute().getText());
				Modelo.crearBook(Integer.parseInt(vista.getTxtId().getText()), vista.getTxtTitle().getText(), vista.getTxtAuthor().getText(), Integer.parseInt(vista.getTxtAnyNax().getText()), Integer.parseInt(vista.getTxtAnyPub().getText()), vista.getTxtEditorial().getText(), Integer.parseInt(vista.getTxtNumPag().getText()), imageFile);
				showMessageDialog(null, "Registre creat.");
				vista.getTxtId().setText("");
				vista.getTxtTitle().setText("");
				vista.getTxtAuthor().setText("");
				vista.getTxtAnyNax().setText("");
				vista.getTxtAnyPub().setText(""); 
				vista.getTxtEditorial().setText("");
				vista.getTxtNumPag().setText("");
				vista.getLblRoute().setText("");
				vista.getEpHTML().setText(Modelo.defaultSearch());
			}
		};
		vista.getBtnCrear().addActionListener(actionListener_CrearRegistre);
		
		actionListener_ActualitzarRegistre = new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				int opt = JOptionPane.showConfirmDialog(null, "Desea actualitzar el registre?", "UPDATE", JOptionPane.YES_NO_OPTION, JOptionPane.QUESTION_MESSAGE);
				if(opt==0) {
					File imageFile = new File (vista.getLblRoute().getText());
					Modelo.actualitzarBook(Integer.parseInt(vista.getTxtId().getText()), vista.getTxtTitle().getText(), vista.getTxtAuthor().getText(), Integer.parseInt(vista.getTxtAnyNax().getText()), Integer.parseInt(vista.getTxtAnyPub().getText()), vista.getTxtEditorial().getText(), Integer.parseInt(vista.getTxtNumPag().getText()), imageFile);
					showMessageDialog(null, "Registre actualitzat.");
					vista.getTxtId().setText("");
					vista.getTxtTitle().setText("");
					vista.getTxtAuthor().setText("");
					vista.getTxtAnyNax().setText("");
					vista.getTxtAnyPub().setText(""); 
					vista.getTxtEditorial().setText("");
					vista.getTxtNumPag().setText("");
					vista.getLblRoute().setText("");
					vista.getEpHTML().setText(Modelo.defaultSearch());
					vista.getLblThumbnail().setIcon(null);
				}else {
					showMessageDialog(null, "Actualitzacio cancelat.");
				}
			}
		};
		vista.getBtnUpdate().addActionListener(actionListener_ActualitzarRegistre);
		
		actionListener_EsborrarRegistre = new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				int opt = JOptionPane.showConfirmDialog(null, "Desea borrar el registre?", "DELETE", JOptionPane.YES_NO_OPTION, JOptionPane.QUESTION_MESSAGE);
				if(opt==0) {
					Modelo.borrarBook(Integer.parseInt(vista.getTxtId().getText()));
					showMessageDialog(null, "Registre esborrat.");
					vista.getTxtId().setText("");
					vista.getEpHTML().setText(Modelo.defaultSearch());
					vista.getLblThumbnail().setIcon(null);
				}else {
					showMessageDialog(null, "Esborrat cancelat.");
				}
			}
		};
		vista.getBtnDeleteOne().addActionListener(actionListener_EsborrarRegistre);
		
		actionListener_EsborrarColeccio = new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				int opt = JOptionPane.showConfirmDialog(null, "Desea borrar la entera coleccio?", "DELETE ALL", JOptionPane.YES_NO_OPTION, JOptionPane.QUESTION_MESSAGE);
				if(opt==0) {
					Modelo.borrarColeecio();
					showMessageDialog(null, "Coleccio esborrada.");
					vista.getEpHTML().setText(Modelo.defaultSearch());
				}else {
					showMessageDialog(null, "Esborrat cancelat.");
				}
			}
		};
		vista.getBtnDeleteAll().addActionListener(actionListener_EsborrarColeccio);
		
		/**
		 * Despleguem un ComboBox desde el cual seleccionem el tipus de consulta.
		 * En base al valor seleccionat mostrara una consulta u otra.
		 */
		actionListener_Busqueda = new ActionListener() {
			@SuppressWarnings("static-access")
			public void actionPerformed(ActionEvent e) {
				int pick = vista.getCmbQuery().getSelectedIndex()+1;
				switch(pick) {
				case 1:
					vista.getEpHTML().setText(modelo.buscarEqual(Integer.parseInt(vista.getTxtId().getText())));
					vista.getLblThumbnail().setIcon(modelo.convertirImg(Integer.parseInt(vista.getTxtId().getText()),
							vista.getLblThumbnail().getWidth(),vista.getLblThumbnail().getHeight()));
						break;
				case 2:
					vista.getEpHTML().setText(modelo.buscarMenor(Integer.parseInt(vista.getTxtId().getText())));
					vista.getLblThumbnail().setIcon(null);
						break;
				case 3:
					vista.getEpHTML().setText(modelo.buscarMatjor(Integer.parseInt(vista.getTxtId().getText())));	
					vista.getLblThumbnail().setIcon(null);
						break;
				}
			}	
		};
		vista.getBtnSearch().addActionListener(actionListener_Busqueda);
	}
}
