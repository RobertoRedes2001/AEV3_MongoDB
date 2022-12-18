package es.florida.aev3;

public class Principal {
	public static void main(String[] args) {
		
		Modelo modelo = new Modelo();
		Vista vista = new Vista();
		Controlador cont = new Controlador(modelo, vista);
		
	}
}
