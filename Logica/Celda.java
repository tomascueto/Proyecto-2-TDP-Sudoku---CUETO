package Logica;

public class Celda {

	
	//Atributos de la celda

	private Integer valor;
	private EntidadGrafica entidadGrafica;
	
	
	
	//Constructor de la clase Celda.
	
	
	
	//Inicializa ambos valores en null.
	
	public Celda() {
		valor = null;
		entidadGrafica = new EntidadGrafica();
	}
	
	
	//Metodo actualizar que me asegura el cambio de imagenes de la celda.
	public void actualizar() {
		
		if(valor == null)
			valor = 1;
		else {
			
			if(valor < 9)
				valor++;
			else
				valor = 1;

		}

		entidadGrafica.actualizar(valor);
	}
	
	
	
	//Setter del valor. Al ser un objeto de la clase wrapper Integer, puede ser nulo. En valor debe oscilar entre 0 y 9
	
	public void setValor(Integer val) {
		
		//El valor no debe ser nulo, y debe ser menor a la cantidad de elementos posibles a colocar en la celda (en este caso,9).
		
		if( val != null && val < 10) {
			this.valor = val;
			entidadGrafica.actualizar(val);
		}
		//En caso de que no se cumpla, se "reseteara" el valor a 1, permitiendo que aquellas celdas con valor nulo puedan comenzar a utilizarse en el juego.
		else {
			this.valor = 1;
		}

	}
	

	
	

	//Getter del valor de la celda.
	public Integer getValor() {
		return valor;
	}
	
	//Getter que retorna la entidad grafica
	public EntidadGrafica getEntidadGrafica() {
		return entidadGrafica;
	}
	
}
