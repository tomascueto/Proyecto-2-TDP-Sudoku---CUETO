package Logica;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.Random;
import java.util.Scanner;

import javax.swing.ImageIcon;

public class Juego {

	//Atributos del juego

	private Celda [][] tablero;
	private int filas;
	
	
	//Constructor 
	
	public Juego() throws ArchivoInvalidoException{
		
		//Inicializo el tablero de 9x9 representado por una matriz de Celda.
		filas = 9;
		tablero = new Celda[filas][filas];
		
		
		
		/*Inicializo la matriz con un objeto de tipo Celda y a este objeto le asigno una fila y columna para luego poder pedirselas y chequear
		* el estado correcto de la celda
		*/
		for(int i = 0; i < filas; i++) {
			for (int j = 0; j < filas; j++) {
				tablero[i][j] = new Celda();			
			}
		}
				
		/*Luego cargo a las celdas los valores de mi archivo de 9 filas con 9 numeros.
		*	Despues de cargar cada celda con su valor entero, realizo un chequeo celda por celda para ver si cada una de ellas esta
		*	cumpliendo las reglas del juego.
		* 	Una vez chequeado esto, dando fe que el archivo contiene una solucion valida, se "retiran" algunos numeros aleatoriamente.
		* 	Por ultimo, el Tablero (parte grafica) solo mostrara aquellas celdas que contengan un valor entre 0 y 9. El resto debera ser completada.
		*/
		
		
		String linea;
		String [] lineaDividida;
		
		try {
		
			File miArchivo = new File("C:\\Users\\tomas\\eclipse-workspace2\\Proyecto2TDP-Sudoku\\src\\Niveles\\dificil.txt");
			Scanner miLector = new Scanner(miArchivo);
		
			int i=0;
		
			
			/*Mientras mi archivo tenga lineas para leer, estas seran procesadas individualmente, separando las lineas por los espacios
			* obteniendo asi, un arreglo de String (que en este caso, serian 9 enteros).
			*/
			
			while (miLector.hasNextLine()) {
		
				linea = miLector.nextLine();
				lineaDividida = linea.split(" ");
			
				for(int j = 0; j < 9; j++) {
					int valor = Integer.parseInt(lineaDividida[j]);
					tablero[i][j].setValor(valor);
				}
				
				/*
				 * Debo avanzar la variable i, pues esta me indica la fila en la que se estan inicializando las celdas. Notese que i coincide con el numero de linea
				 * que se esta leyendo en el momento.
				*/
				i++;
			}
			
			//Cierro el lector para no generar conflictos.
			miLector.close();
			
			
			/*Si bien los atributos deberian setearse al comienzo del codigo, por legibilidad decidi inicializar una bandera booleana que me permita
			* cortar en caso de que alguna de las celdas leidas desde el archivo, no cumpla con las reglas del juego.
			* En este caso, el archivo pasado como "nivel" del sudoku, no era una solucion valida.
			*/
			
			boolean estadoCorrecto = true;
			int fila = 0;
			
			//Los while anidados me permiten recorrer la grilla 9x9 con una condicion de corte.
			//Chequeo cada celda, si alguna esta mal, cambio la bandera booleana, cortando con la busqueda y con la ejecucion del programa.
			
			while (fila < 9 && estadoCorrecto) {
				
				int columna = 0;
				
				while (columna < 9 && estadoCorrecto) {
					
					if ( !celdaCorrecta(fila,columna))
						estadoCorrecto = false;
					else
						columna++;
				}
				
				if(estadoCorrecto) {
					fila++;
				}
			}
				
			
			if(!estadoCorrecto)
				throw new ArchivoInvalidoException("El archivo leido no contiene una solucion valida");
			
			//Si luego de revisar todas las celdas, estas cumplian las reglas, puedo dar fe que el sudoku tiene una solucion valida.
			
			
			
			if (estadoCorrecto) {
				
				//Genero un objeto Random que lo voy a utilizar para generar un numero entre 0,1 y 2.
				
				
				Random rnd = new Random();
				int loInicioONo;
				Celda c;
				
				for(int f = 0; f < filas; f++) {
					for(int col = 0; col < filas; col++) {
						
						loInicioONo = rnd.nextInt(3);
						
						/*Aqui se puede elegir de cierta forma, la dificultad del nivel.
						 * 	Como el codigo genera un numero aleatorio entre 0,1 y 2, se puede optar entre quitar el valor a las celdas que cumplan con una sola condicion,
						 *  o quitar la union de las celdas que cumplan dos condiciones distintas, es decir:
						 *  se quitan aquellas celdas cuyo numero aleatorio es 1, 2 o 3 (casos excluyentes)
						 *  O se quitan las celdas que representen la union de DOS de los 3 numeros anteriores (1 y 2, 0 y 1, 0 y 2, 1 y 3, 0 y 3)
						 * 
						 * En este caso, se inician las celdas cuyo numero aleatorio es 0 o 2, por lo que las que obtuvieron un 1, se reemplazan y pisan
						 * con otra instancia de Celda, haciendo que el valor de la misma sea nulo, lo que genera que en mi GUI no se muestre ningun grafico
						 * hasta clickear la etiqueta.
						 */
					
						
						if (loInicioONo == 1) 
							tablero[f][col] = new Celda();
		
					}
				}
				
			}


			
		}catch(FileNotFoundException e) {
			e.printStackTrace();
		}

	}
	
	
	//Metodo publico que me permite saber si una celda esta cumpliendo las reglas del juego o no.
	public boolean celdaCorrecta(int i, int j) {
		
		/*Para que una celda tenga un valor correcto:
		 * No se debe repetir el valor en la vila
		 * No se debe repetir el valor en la columna
		 * No se debe repetir el valor dentro del panel 3x3 que contiene a dicha celda.
		 */
		return !seRepiteEnFila(i,j) && !seRepiteEnColumna(i,j) && !seRepiteEnPanel(i,j);
	}
	
	
	
	private boolean seRepiteEnFila(int i, int j) {
		Celda c = tablero[i][j];
		
		
		Integer valorDeCelda = c.getValor();
		Integer valorCeldaContraria;
		
		
		boolean seRepite = false;
		int columna = 0;
		
		
		if(valorDeCelda != null) {
			
			while (columna < filas && !seRepite) {
				
				if (columna != j) {
					
					valorCeldaContraria = tablero[i][columna].getValor();
					
					if(valorCeldaContraria != null) {
						
						if (valorDeCelda.equals(valorCeldaContraria))
						{
							seRepite = true;	
						}
						else
							columna++;
					}
					else
						columna++;
				}
				else
					columna++;			
			}
			
		}
		
		if(seRepite)
			System.out.println("Se repite en fila");
		
		return seRepite;
	}
	
	
	private boolean seRepiteEnColumna(int i, int j) {
		Celda c = tablero[i][j];
		
		Integer valorDeCelda = c.getValor();
		Integer valorCeldaContraria;
		
		
		boolean seRepite = false;
		int fila = 0;
		
		//Si el valor de la celda fuese nulo, no se repetiria, pues no tiene valor alguno. Por ende, no estaria inflingiendo ninguna regla del juego.
		
		if (valorDeCelda != null ) {
			
			
			//Recorro las columnas perteneciente a la fila "fila" y en caso de encontrar un valor repetido, el metodo retorna verdadero.
			
			while (fila < filas && !seRepite) {
				
				if (fila != i) {
					
					valorCeldaContraria = tablero[fila][j].getValor();
							
					if (valorCeldaContraria != null) {
						
						if (valorDeCelda == valorCeldaContraria)
							seRepite = true;
						else
							fila++;
						
					}
					else
						fila++;
					
					;
				}
				else
					fila++;			
			}
		}
		
		return seRepite;
	}
	
	
	private boolean seRepiteEnPanel(int i, int j) {
		
		
		//Como se trata de enteros, las variables filaInicialDePanel y columnaInicialDePanel se inicializan con -1.
		
		int filaInicialDePanel = -1;
		
		int filaFinalDePanel;
		int columnaInicialDePanel = -1;
		int columnaFinalDePanel;
		
		Integer valorDeCelda = tablero[i][j].getValor();
		Integer valorDeCeldaContraria;
		
		boolean seRepite = false;
		
		
		
		//Si el valor de la celda pasada por parametro no es nulo, hago la busqueda del mismo valor en panel, sino devuelvo false.
		
		if (valorDeCelda != null){
		
			/*Teniendo en cuenta que: Tanto las filas como columnas, al dividirse en paneles o sub-cuadrados de 3x3, poseen los mismos indices.
			* Las filas pertenecientes al primer sub-cuadrado seran las de indice 0,1 y 2. Lo mismo las columnas.
			* Por eso, dependiendo de la fila y columna a la que pertenezca la celda, se puede obtener la fila inicial y la columna inicial del panel a chequear.
			*/
			
			
			//Busco el indice de la fila inicial: 
			
			if (0 <= i && i <= 2) {
				filaInicialDePanel = 0;
			}
			else {
				if (2 < i && i <= 5) 
					filaInicialDePanel = 3;
				else
				{
					if (5 < i && i <= 8)
						filaInicialDePanel = 6;
				}
			}
			
			//Busco el indice de la columna inicial
			
			if (0 <= j && j <= 2)
				columnaInicialDePanel = 0;
			else {
				
				if (2 < j && j <= 5) 
					columnaInicialDePanel = 3;
				else
					if (5 < j && j <= 8) {
						columnaInicialDePanel = 6;
					}
			}
			
			/*Sabiendo lo anterior, la fila y columna final del panel seran la fila/columna inicial + 2. Pero para facilitar el recorrido del "while"
			* Se le suman 3 unidades, haciendo luego que el while recorra MIENTRAS los indices sean menores a los valores finales dados.
			*
			*/
			
			filaFinalDePanel = filaInicialDePanel + 3;
			columnaFinalDePanel = columnaInicialDePanel + 3;
			
			
			//Se ejecutara si y solo si ambos indices son positivos
			
			
			if ( filaInicialDePanel >= 0 && columnaInicialDePanel >= 0) { //Si ninguno de los valores es "-1" (pues lo inicializamos con ese valor)
				
				while(filaInicialDePanel < filaFinalDePanel && !seRepite) {
					
					while(columnaInicialDePanel < columnaFinalDePanel && !seRepite) {
					
						if(filaInicialDePanel == i && columnaInicialDePanel == j)
							columnaInicialDePanel++;
						
						else {
							valorDeCeldaContraria = tablero[filaInicialDePanel][columnaInicialDePanel].getValor();
							
							if(valorDeCeldaContraria != null) {
								
								if(valorDeCeldaContraria.equals(valorDeCelda)) 
									seRepite = true;								
								else
									columnaInicialDePanel++;
							}
							else
								columnaInicialDePanel++;		
						}
							
					}	
					
					if (!seRepite) {
						filaInicialDePanel++;
						columnaInicialDePanel = columnaInicialDePanel - 2; //MUY importante: se resetea la columna a su valor inicial, quitandole las 2 unidades recorridas.
					}
			
			}
		}

			
		}
		
		return seRepite;

	}
	
	
	//Llama a actualizar a la celda
	public void accionar(Celda c) {
		c.actualizar();
	}
		

	
	//Getters utiles para la parte logica del juego.
	
	public Celda getCelda(int fila, int columna) {
		return tablero[fila][columna];
	}
	
	public int cantidadFilas() {
		return filas;
	}
	
	
	public boolean victoria() {
		boolean todasCorrectas = true;
		int fila = 0;
		int columna = 0;
		Integer valorDeCelda;
		
		while (fila < filas && todasCorrectas) {
			while (columna < filas && todasCorrectas) {
				valorDeCelda = tablero[fila][columna].getValor();
				
				if ( valorDeCelda == null)
					todasCorrectas = false;
				else {
					if ( !celdaCorrecta(fila,columna))
						todasCorrectas = false;
					else
						columna++;	
				}
					
				
			}
			
			if (todasCorrectas) {
				columna = 0;
				fila++;
			}
			
			
		}
		
		
		return todasCorrectas;
		
		
		
		
	}
	
}
	
	
