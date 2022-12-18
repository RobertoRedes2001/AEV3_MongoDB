package es.florida.aev3;

import static javax.swing.JOptionPane.showMessageDialog;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.File;

import javax.swing.Icon;
import javax.swing.ImageIcon;
import javax.swing.JFileChooser;

import es.florida.aev3.Modelo;
import es.florida.aev3.Vista;

public class Controlador {
	private Modelo modelo;
	private Vista vista;
	private ActionListener actionListener_CrearRegistre, actionListener_SeleccionarImatge,actionListener_CargarConexio,
	actionListener_Login;
	
	Controlador(Modelo m, Vista v){
		this.modelo=m;
		this.vista=v;
		control();
	}
	
	public void control() {
		
		actionListener_CargarConexio = new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				modelo.conectarDDBB();
				vista.getTxtUser().setEnabled(true);
				vista.getPsfPassword().setEnabled(true);
				vista.getBtnLogin().setEnabled(true);
			}	
		};
		vista.getBtnLoadConnection().addActionListener(actionListener_CargarConexio);
		
		actionListener_Login = new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				if(modelo.comprobarUsers(vista.getTxtUser().getText(), modelo.codificarPass(vista.getPsfPassword().getText()))){
					showMessageDialog(null, "Benbingut "+vista.getTxtUser().getText()+".");
					vista.getTxtId().setEnabled(true);
					vista.getTxtTitle().setEnabled(true);
					vista.getTxtAuthor().setEnabled(true);
					vista.getTxtAnyNax().setEnabled(true);
					vista.getTxtEditorial().setEnabled(true);
					vista.getTxtAnyPub().setEnabled(true);
					vista.getTxtNumPag().setEnabled(true);
					vista.getBtnCrear().setEnabled(true);
					vista.getBtnImageFile().setEnabled(true);
				}else {
					showMessageDialog(null, "Usuari o Contrasenya incorrectes.");
				}
			}	
		};
		vista.getBtnLogin().addActionListener(actionListener_Login);
		
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
				modelo.crearBook(Integer.parseInt(vista.getTxtId().getText()), vista.getTxtTitle().getText(), vista.getTxtAuthor().getText(), Integer.parseInt(vista.getTxtAnyNax().getText()), Integer.parseInt(vista.getTxtAnyPub().getText()), vista.getTxtEditorial().getText(), Integer.parseInt(vista.getTxtNumPag().getText()), imageFile);
			}
		};
		vista.getBtnCrear().addActionListener(actionListener_CrearRegistre);
	}
}
