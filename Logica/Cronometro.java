package Logica;

import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.GridLayout;
import java.awt.Image;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.ImageIcon;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.Timer;

public class Cronometro extends JPanel{

	private Timer cronometro;
	
	private int unidadHoras, unidadMinutos, unidadSegundos, decenaSegundos, decenaMinutos, decenaHoras;
	private ActionListener acciones;
	private String[] numeros = { "/numeros/0.png", "/numeros/1.png", "/numeros/2.png", "/numeros/3.png", "/numeros/4.png", "/numeros/5.png",
			"/numeros/6.png", "/numeros/7.png", "/numeros/8.png", "/numeros/9.png"};
	private JLabel h,m,s,dh,dm,ds, dosPuntos1, dosPuntos2;
	
	
	public Cronometro(){
		
		
		this.setSize(new Dimension(100,100));
		setLayout(new FlowLayout());

		//Escalo un ImageIcon con el digito 0 para que este sea el icono de las etiquetas al iniciar el cronometro.
		
		ImageIcon inicial = new ImageIcon (this.getClass().getResource(numeros[0]));
		Image imagen = inicial.getImage().getScaledInstance(40, 40, Image.SCALE_SMOOTH);
		inicial.setImage(imagen);
	
		
		//Instancio las etiquetas y les seteo el icono
		
		dh = new JLabel("");
		dh.setIcon(inicial);
	
		h = new JLabel("");
		h.setIcon(inicial);
		
		dosPuntos1 = new JLabel("");
		dosPuntos1.setIcon( new ImageIcon ( new ImageIcon(this.getClass().getResource("/numeros/dosPuntos.png")).getImage().getScaledInstance(40, 40, Image.SCALE_SMOOTH)));
		
		dm = new JLabel("");
		dm.setIcon(inicial);
		
		m = new JLabel("");
		m.setIcon(inicial);
		
		dosPuntos2 = new JLabel("");
		dosPuntos2.setIcon( new ImageIcon ( new ImageIcon(this.getClass().getResource("/numeros/dosPuntos.png")).getImage().getScaledInstance(40, 40, Image.SCALE_SMOOTH)));
		
		ds = new JLabel("");
		ds.setIcon(inicial);
		
		s = new JLabel("");
		s.setIcon(inicial);
		
		//Agrego todas las etiquetas al panel
		
		this.add(dh);
		this.add(h);
		this.add(dosPuntos1);
		this.add(dm);
		this.add(m);
		this.add(dosPuntos2);
		this.add(ds);
		this.add(s);
		
		
		
		//Debo crear un objeto ActionListener que determine las acciones a realizar cada "x" tiempo.
		
		acciones = new ActionListener() {
			
			/*Lo que hace el action listener es asegurarse de que cada digito no se sobrepase de 9 (en caso de las unidades de minutos y segundos) y que no se
			*	pase de 6 (decenas de horas, minutos y segundos)
			*/
			
			public void actionPerformed(ActionEvent e){
				unidadSegundos++;
				
				if(unidadSegundos == 10) {
					unidadSegundos = 0;
					decenaSegundos++;
				}
				
				if (decenaSegundos == 6) {
					
					decenaSegundos = 0;
					unidadMinutos++;
					
				}
				
				if(unidadMinutos == 10) {
					unidadMinutos = 0;
					decenaMinutos++;
				}
				
				if(decenaMinutos == 6) {
					decenaMinutos=0;
					unidadHoras++;
				}
				
				if(unidadHoras == 10) {
					unidadHoras = 0;
					decenaHoras++;
				}

				
				actualizarEtiquetas();

		     }
		};
		
		
		/*Al crear un nuevo objeto de tipo Timer, los parametros necesarios son: Cada cuantos milisegundos queremos repetir las acciones, y luego un ActionListner
		* que determina el conjunto de acciones a repetir cada esos milisegundos.
		*/
		
		cronometro = new Timer(1000, acciones);
		cronometro.start();
		
		
	}
	
	
	private void actualizarEtiquetas() {	
		
		//Actualizo cada etiqueta con la imagen correspondiente al digito que me indique la parte "logica" del cronometro.
		
		dh.setIcon(	new ImageIcon ( new ImageIcon(this.getClass().getResource(numeros[decenaHoras])).getImage().getScaledInstance(40, 40, Image.SCALE_SMOOTH))); 
		h.setIcon(	new ImageIcon ( new ImageIcon(this.getClass().getResource(numeros[unidadHoras])).getImage().getScaledInstance(40, 40, Image.SCALE_SMOOTH))); 
		dm.setIcon(	new ImageIcon ( new ImageIcon(this.getClass().getResource(numeros[decenaMinutos])).getImage().getScaledInstance(40, 40, Image.SCALE_SMOOTH))); 
		m.setIcon(	new ImageIcon ( new ImageIcon(this.getClass().getResource(numeros[unidadMinutos])).getImage().getScaledInstance(40, 40, Image.SCALE_SMOOTH))); 
		ds.setIcon(	new ImageIcon ( new ImageIcon(this.getClass().getResource(numeros[decenaSegundos])).getImage().getScaledInstance(40, 40, Image.SCALE_SMOOTH))); 
		s.setIcon(	new ImageIcon ( new ImageIcon(this.getClass().getResource(numeros[unidadSegundos])).getImage().getScaledInstance(40, 40, Image.SCALE_SMOOTH))); 


		
		
		
		
	}
	
	
	
}
