package es.florida.aev3;

import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;
import javax.swing.JLabel;
import javax.swing.JTextField;
import javax.swing.JButton;
import javax.swing.JFileChooser;

import java.awt.event.ActionListener;
import java.io.File;
import java.awt.event.ActionEvent;
import javax.swing.SwingConstants;
import javax.swing.JPasswordField;

public class Vista extends JFrame {
	
	//Declarar interfaz
	private JPanel contentPane;
	private JTextField txtAnyNax;
	private JTextField txtAnyPub;
	private JTextField txtEditorial;
	private JTextField txtAuthor;
	private JTextField txtTitle;
	private JTextField txtId;
	private JTextField txtNumPag;
	private JButton btnCrear;
	private JButton btnImageFile;
	private JLabel lblRoute;
	private JButton btnLoadConnection;
	private JTextField txtUser;
	private JPasswordField psfPassword;
	private JButton btnLogin;

	public Vista() {
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setBounds(100, 100, 897, 480);
		contentPane = new JPanel();
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));

		setContentPane(contentPane);
		contentPane.setLayout(null);
		
		JLabel lblAnyNax = new JLabel("Any de Naiximent:");
		lblAnyNax.setBounds(506, 240, 103, 20);
		contentPane.add(lblAnyNax);
		
		txtAnyNax = new JTextField();
		txtAnyNax.setEnabled(false);
		txtAnyNax.setBounds(619, 240, 86, 20);
		contentPane.add(txtAnyNax);
		txtAnyNax.setColumns(10);
		
		txtAnyPub = new JTextField();
		txtAnyPub.setEnabled(false);
		txtAnyPub.setColumns(10);
		txtAnyPub.setBounds(619, 269, 86, 20);
		contentPane.add(txtAnyPub);
		
		JLabel lblAnyPub = new JLabel("Any de Publicaci\u00F3:");
		lblAnyPub.setBounds(506, 269, 103, 20);
		contentPane.add(lblAnyPub);
		
		txtEditorial = new JTextField();
		txtEditorial.setEnabled(false);
		txtEditorial.setColumns(10);
		txtEditorial.setBounds(565, 300, 86, 20);
		contentPane.add(txtEditorial);
		
		JLabel lblEditorial = new JLabel("Editorial:");
		lblEditorial.setBounds(506, 300, 67, 20);
		contentPane.add(lblEditorial);
		
		txtAuthor = new JTextField();
		txtAuthor.setEnabled(false);
		txtAuthor.setColumns(10);
		txtAuthor.setBounds(551, 209, 86, 20);
		contentPane.add(txtAuthor);
		
		JLabel lblAuthor = new JLabel("Autor:");
		lblAuthor.setBounds(506, 209, 67, 20);
		contentPane.add(lblAuthor);
		
		txtTitle = new JTextField();
		txtTitle.setEnabled(false);
		txtTitle.setColumns(10);
		txtTitle.setBounds(541, 178, 86, 20);
		contentPane.add(txtTitle);
		
		JLabel lblTitle = new JLabel("Titol:");
		lblTitle.setBounds(506, 178, 67, 20);
		contentPane.add(lblTitle);
		
		txtId = new JTextField();
		txtId.setEnabled(false);
		txtId.setColumns(10);
		txtId.setBounds(541, 149, 86, 20);
		contentPane.add(txtId);
		
		JLabel lblId = new JLabel("ID:");
		lblId.setBounds(506, 149, 67, 20);
		contentPane.add(lblId);
		
		txtNumPag = new JTextField();
		txtNumPag.setEnabled(false);
		txtNumPag.setColumns(10);
		txtNumPag.setBounds(629, 331, 86, 20);
		contentPane.add(txtNumPag);
		
		JLabel lblNumPag = new JLabel("Numero de paginas: ");
		lblNumPag.setBounds(506, 331, 121, 20);
		contentPane.add(lblNumPag);
		
		btnCrear = new JButton("Mandar");
		btnCrear.setEnabled(false);
		btnCrear.setBounds(506, 391, 89, 23);
		contentPane.add(btnCrear);
		
		JLabel lblSelectImage = new JLabel("Imatge de Portada: ");
		lblSelectImage.setBounds(506, 361, 121, 20);
		contentPane.add(lblSelectImage);
		
		btnImageFile = new JButton("Seleccionar");
		btnImageFile.setEnabled(false);
		btnImageFile.setBounds(626, 361, 103, 23);
		contentPane.add(btnImageFile);
		
		lblRoute = new JLabel("");
		lblRoute.setHorizontalAlignment(SwingConstants.TRAILING);
		lblRoute.setBounds(733, 361, 140, 20);
		contentPane.add(lblRoute);
		
		btnLoadConnection = new JButton("Cargar Conexi\u00F3");
		btnLoadConnection.setBounds(107, 52, 132, 23);
		contentPane.add(btnLoadConnection);
		
		txtUser = new JTextField();
		txtUser.setEnabled(false);
		txtUser.setColumns(10);
		txtUser.setBounds(153, 22, 86, 20);
		contentPane.add(txtUser);
		
		JLabel lblUser = new JLabel("Usuari:");
		lblUser.setBounds(107, 21, 67, 20);
		contentPane.add(lblUser);
		
		JLabel lblPass = new JLabel("Contrasenya: ");
		lblPass.setHorizontalAlignment(SwingConstants.TRAILING);
		lblPass.setBounds(253, 21, 80, 20);
		contentPane.add(lblPass);
		
		psfPassword = new JPasswordField();
		psfPassword.setEnabled(false);
		psfPassword.setBounds(343, 22, 86, 19);
		contentPane.add(psfPassword);
		
		btnLogin = new JButton("Logejarse");
		btnLogin.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
			}
		});
		btnLogin.setEnabled(false);
		btnLogin.setBounds(263, 53, 103, 23);
		contentPane.add(btnLogin);
		this.setVisible(true);
	}

	public JPanel getContentPane() {
		return contentPane;
	}

	public JTextField getTxtAnyNax() {
		return txtAnyNax;
	}

	public JTextField getTxtAnyPub() {
		return txtAnyPub;
	}

	public JTextField getTxtEditorial() {
		return txtEditorial;
	}

	public JTextField getTxtAuthor() {
		return txtAuthor;
	}

	public JTextField getTxtTitle() {
		return txtTitle;
	}

	public JTextField getTxtId() {
		return txtId;
	}

	public JTextField getTxtNumPag() {
		return txtNumPag;
	}
	
	public JButton getBtnCrear() {
		return btnCrear;
	}

	public JButton getBtnImageFile() {
		return btnImageFile;
	}

	public JLabel getLblRoute() {
		return lblRoute;
	}

	public JButton getBtnLoadConnection() {
		return btnLoadConnection;
	}

	public JTextField getTxtUser() {
		return txtUser;
	}

	public JPasswordField getPsfPassword() {
		return psfPassword;
	}

	public JButton getBtnLogin() {
		return btnLogin;
	}
}
