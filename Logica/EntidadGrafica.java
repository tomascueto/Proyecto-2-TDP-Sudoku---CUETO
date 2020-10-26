package Logica;

import javax.swing.ImageIcon;

public class EntidadGrafica {

	//Atributos de la entidad grafica
	

	private ImageIcon grafico;	
	private String[] imagenes;
	
	
	//Constructor 
	
	public EntidadGrafica() {
		
		grafico = new ImageIcon();
		
		//Inicia el arreglo de 9 elementos que representan los digitos del juego. En este codigo, las imagenes estan en un package aparte.
		
		imagenes = new String[] {"/Sprites/Hannah Dundee.png", "/Sprites/Jack Tenrec.png", "/Sprites/Mess Obradovich.png", 
				"/Sprites/Mustapha Cairo.png", "/Sprites/Blade.png", "/Sprites/Bludge.png", "/Sprites/Butcher.png", 
				"/Sprites/Lash Terhune.png", "/Sprites/Black Elmer.png"};
		
	}
	
	
	//Metodo actualizar: Cambia el grafico mostrado en la GUI. El indice se corresponde con la direccion en el arreglo de String.
	
	public void actualizar(int indice) {
		
		//Como el arreglo tiene 9 elementos, los indices van de 0 a 8.
		
		if (indice > 0 && indice < 10) {
			/*Para que el ImageIcon "grafico" sea siempre el mismo y no cambie, se crea otor ImageIcon con el fin de que "grafico"
			* solo actualice su imagen basado en el nuevo ImageIcon "imagen".
			*/
			
			ImageIcon imagen = new ImageIcon(this.getClass().getResource(this.imagenes[indice-1]));
			
			this.grafico.setImage(imagen.getImage());
		}
	}

	
	
	
	
	//Setters y Getters basicos.
	
	public ImageIcon getGrafico() {
		return grafico;
	}
	
	public String[] getImagenes() {
		return imagenes;
	}
		
	
	public void setGrafico(ImageIcon graf) {
		grafico = graf;
	}
	
	public void setImagenes(String[] img) {
		imagenes = img;
	}
	
	
}
