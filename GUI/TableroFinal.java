package GUI;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Component;
import java.awt.EventQueue;

import javax.swing.ImageIcon;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;
import javax.swing.border.LineBorder;

import Logica.ArchivoInvalidoException;
import Logica.Celda;
import Logica.Cronometro;
import Logica.Juego;

import java.awt.GridLayout;
import java.awt.Image;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.util.Timer;
import java.awt.FlowLayout;
import javax.swing.BoxLayout;
import java.awt.Label;
import java.awt.Dimension;
import javax.swing.SwingConstants;
import java.awt.Panel;
import java.awt.Frame;

public class TableroFinal extends JFrame {
	
	/*Atributos:
	 * 
	 * 	Un atributo de tipo Juego que controla la parte logica del mismo.
	 * 	El contentPane, que es el contenedor principal del JFrame.
	 * 
	 */
	private Juego juego;
	private JPanel contentPane;
	
	
	//Main para iniciar el JFrame.
	
	
	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					TableroFinal frame = new TableroFinal();
					frame.setVisible(true);
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		});
	}

	//Constructor del frame
	
	
	public TableroFinal() {
		
		//Seteo el estado maximizado por defecto, para que el tablero del Sudoku sea mas legible al momento de comenzar.
		
		setExtendedState(Frame.MAXIMIZED_BOTH);
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setBounds(100, 100, 450, 300);
		contentPane = new JPanel();
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
		setContentPane(contentPane);
		contentPane.setLayout(new BorderLayout(0, 0));
		
		JPanel panel = new JPanel();
		panel.setBorder(null);
		contentPane.add(panel, BorderLayout.CENTER);
		panel.setLayout(new GridLayout(9, 9, 0, 0));
		
		JPanel panel_1 = new JPanel();
		contentPane.add(panel_1, BorderLayout.NORTH);
		
		JLabel lblNewLabel = new JLabel("");
		ImageIcon graficoDeTitulo = new ImageIcon(TableroFinal.class.getResource("/GUI/Titulo.jpg"));
		panel_1.add(lblNewLabel);

		
		Image imagenEscalada = graficoDeTitulo.getImage().getScaledInstance(600, 142, java.awt.Image.SCALE_SMOOTH);
		graficoDeTitulo.setImage(imagenEscalada);
		
		lblNewLabel.setIcon(graficoDeTitulo);
		lblNewLabel.repaint();

		
		
		JPanel crono = new Cronometro();
		crono.setSize(50, 50);
		panel_1.add(crono);
		
		try {
			
			/*Creo una instancia de juego y por cada grilla del tablero de 9x9, creo una etiqueta, recupero su valor, y si este es nulo, seteo un oyente
			* a la etiqueta, que luego me permitira, haciendo clicks sobre la misma, actualizar el contenido grafico, afectando tambien la parte logica.
			*/
			
			juego = new Juego();
			
			
			for (int i = 0; i < juego.cantidadFilas(); i++) {
				
				for (int j = 0; j < juego.cantidadFilas(); j++) {
					
					int fila = i;
					int columna = j;
					
					Celda c = juego.getCelda(i, j);
				
					ImageIcon grafico = c.getEntidadGrafica().getGrafico();
					
					
					JLabel etiqueta = new JLabel();
					etiqueta.setBorder(new LineBorder(Color.BLACK, 2));
					etiqueta.setIcon(grafico);
					panel.add(etiqueta);
					
				
					if(c.getValor() == null) {

						etiqueta.addMouseListener(new MouseAdapter() {
							public void mouseClicked(MouseEvent e) {
								
								juego.accionar(c);
								reDimensionar(etiqueta,grafico);
								
								
								
								/*
								 * En caso de que despues de actualizarse la celda sea incorrecta, se le setea una manera grafica de reconocerlo.
								 * El borde se vuelve rojo y un texto aparece en el centro de la etiqueta.
								 * Una vez que la etiqueta se vuelve una "correcta" el borde desaparece y el texto cambia.
								 * 
								 */
								
								if (!juego.celdaCorrecta(fila, columna)) {
									etiqueta.setBorder(new LineBorder(Color.RED));
									etiqueta.setText("Incorrecta");
									etiqueta.setHorizontalTextPosition(etiqueta.CENTER);
									etiqueta.setVerticalTextPosition(etiqueta.CENTER);
								}
								else {
									
									
									etiqueta.setBorder(null);
									etiqueta.setText("Correcta");
									etiqueta.setHorizontalTextPosition(etiqueta.CENTER);
									etiqueta.setVerticalTextPosition(etiqueta.CENTER);
									
								}

								/*Si se cumple con la victoria, se desactiva las etiquetas con el fin de que no vuelvan a ser clickeadas.
								 * Tambien se mostrara un pop-up con un dialogo avisando que se ha vencido el nivel. Luego el programa se cerrara.
								 */
								
								
								if(juego.victoria()) {
	
									for (Component cp : contentPane.getComponents() ){
								        cp.setEnabled(false);
									}
									
									JOptionPane.showMessageDialog(null, "Victoria!");
									
									System.exit(0);
								}
								
							}
						});	
					}
				}
			}



		}
		catch(ArchivoInvalidoException e) {e.printStackTrace();
		}

	}
	
	
	//Metodo que redimensiona la imagen de la etiqueta, basandose en el tamaño de la JLabel.
	private void reDimensionar(JLabel label, ImageIcon grafico) {
		Image image = grafico.getImage();
		if (image != null) {  
			Image newimg = image.getScaledInstance(label.getWidth(), label.getHeight(),  java.awt.Image.SCALE_SMOOTH);
			grafico.setImage(newimg);
			label.repaint();
		}
	}
	

}
