/*Clase que ejecuta el juego de Pig creando una instancia de Pig*/
public class EjecutorPig{

	public static void main(String []args){
		Pig jugar = new Pig();//Instancia de Pig
		jugar.iniciarPartida();//Inicia la partida
		System.exit(0);
	}
	
}
