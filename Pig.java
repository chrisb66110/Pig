import javax.swing.JOptionPane;

/*La clase Pig contiene el codigo para jugar pig contra la maquina*/
public class Pig{

    int pTotalUsuario;//Lleva el puntaje total del usuario
    int pTotalCompu;//Lleva el puntaje total de la computadora
    
    //Constructor de objetos de la clase
    public Pig(){
        pTotalUsuario=0;
        pTotalCompu=0;
    }
    
    //La funcion dado retorna un numero aleatorio entre 1 y 6, que representa los valores de los dados
    public int dado(){
        return (int)((Math.random()*6)+1);
    }
    
    //La funcion jugadorInicial retorna un numero aleatorio entre 0 y 1, 0 si comienza el Usuario, 1 si comienza la computadora
    public int jugadorInicial(){
        return (int)((Math.random()*2));
    }
    
    //La funcion solicitarNombre retorna una String con el nombre del usuario despues de solicitarlo con JOptionPane
    public String solicitarNombre(){
        String nombreUsuario = JOptionPane.showInputDialog("Escribe tu nombre","Nombre de Usuario");
        return (nombreUsuario == null)? "Usuario": nombreUsuario;
    }
    
    //El metodo iniciarPartida es el que se encarga de iniciar la partida de los jugadores dependiendo de quien es el jugador que comienza
    public void iniciarPartida(){
        String nombreUsuario = solicitarNombre();//Almacena el nombre del usuario
        int jugadorInicial=jugadorInicial();// Almacena el jugador que inicia la partida
        int respJugada=-1;//Almacena la decicion que tomo el usuario(lanzar, tirar, salir, o si salio porque ya llego a 100)
        boolean condi=true;//Almacena true si las condiciones de la partida permiten continuar, false si ya hay una condicion para salir de la partida
        if(jugadorInicial==0){//En caso de que inicie el jugador
            JOptionPane.showMessageDialog(null,"Comienza "+nombreUsuario,"PIG",-1);
            while(condi){//Lleva las repeticiones de cada ronda, mientras no se ha llegado a 100 o el usuario no precione salir
                JOptionPane.showMessageDialog(null,"Turno de "+nombreUsuario,"PIG",-1);
                respJugada=jugadaUsuario(nombreUsuario);//Almacena lo que paso en la jugada del usuario
                if(respJugada==1){//En caso de que ya llegara a 100 el puntaje, para que no tire la computadora y salga de una vez
                    condi=false;
                }else{
                    if(respJugada!=-1){//Solo para comprobar que el jugador no prescionó el boton salir
                        JOptionPane.showMessageDialog(null,"Turno de la Computadora","PIG",-1);
                        respJugada=jugadaCompu(nombreUsuario);//Almacena lo que paso en la jugada de la computadora
                        if(respJugada==1){//En caso de que ya llegara a 100 el puntaje
                            condi=false;
                        }
                    }else{//En caso de que el jugador prescionó el boton salir
                        condi=false;
                    }
                }
            }
        }else{//En caso de que inicie la computadora
            JOptionPane.showMessageDialog(null,"Comienza la computadora","PIG",-1);
            while(condi){//Lleva las repeticiones de cada ronda, mientras no se ha llegado a 100 o el usuario no precione salir
                JOptionPane.showMessageDialog(null,"Turno de la Computadora","PIG",-1);
                respJugada=jugadaCompu(nombreUsuario);//Almacena lo que paso en la jugada de la computadora
                if(respJugada==1){//En caso de que ya llegara a 100 el puntaje, para que no tire el usuario y salga de una vez
                    condi=false;
                }else{
                    JOptionPane.showMessageDialog(null,"Turno de "+nombreUsuario,"PIG",-1);
                    respJugada=jugadaUsuario(nombreUsuario);//Almacena lo que paso en la jugada del usuario
                    if(respJugada==1 || respJugada == -1){//En caso de que ya llegara a 100 el puntaje o que el jugador prescionó el boton salir
                        condi=false;
                    }
                }
            }
        }
        if(respJugada!=-1){//Da felicidades al jugador que gano en caso de no salir antes de finalizar la partida
            JOptionPane.showMessageDialog(null,((pTotalUsuario>=pTotalCompu)?"Felicidades me Gano":"Te Gane")+"\n\nPunteje de "+nombreUsuario+": "+pTotalUsuario+"\nPuntaje de la computadora: "+pTotalCompu,"PIG",-1);
        }
    }
    
    /*La funcion revisarDados recibe 2 enteros que son los valores de los dados a revisar, 
      retorna 2 si ambos dados son 6, 1 si alguno de los dados es 6, 0 si ninguno de los dados es 6
    */
    public int revisarDados(int dado1, int dado2){
        int retorno=-1;//Almacena el valor que se va a retornar
        if(dado1==6 && dado2==6){//En caso de que ambos dados sean 6
            retorno=2;
        }else{
            if(dado1==6 || dado2==6){//en caso de que alguno de los dados sea 6
                retorno=1;
            }else{//En caso de que ningun dado sea 6
                retorno=0;
            }
        }
        return retorno;
    }
    
    /*La funcion jugadaUsuario recibe una String que es el nombre del usuario solo para mostrarlo en el JOptionPane
      retorna 2 si la jugada del jugador termina porque al menos un dado era 6 o el jugador decidio terminar el lanzamiento de dados
      retorna 1 si la jugada del jugador termina porque el puntaje total mas el temporal supera 100 puntos 0 porque le dio salir
    */
    public int jugadaUsuario(String nombre){
        int salir = 0;//Dato a retornar 
        int dado1=0;//Almacena el valor del dado 1
        int dado2=0;//Almacena el valor del dado 2
        int pTemporal = 0;//Almacena el valor del puntaje temporal
        int seleccion=0;//Almacena lo que selecciono el usuario(lanzar,parar,salir)
        int revisados=0;//Almacena el valor de los dados revisados, si son 6 o no
        while(pTotalUsuario<100 && salir==0){
            dado1=dado();//Carga un valor al dado 1
            dado2=dado();//Carga un valor al dado 2
            pTemporal+=dado1+dado2;
            revisados=revisarDados(dado1,dado2);
            if(revisados==2){//En caso de que ambos dados sean 6, eliminan el total del usuario y cambia salir para salir del turno del jugador
                JOptionPane.showMessageDialog(null,"Dado 1: "+dado1+"\nDado 2: "+dado2+"\n\nPerdio el puntaje total","PIG-Turno de "+nombre+".",-1);
                pTotalUsuario=0;
                salir=2;
            }else{
                if(revisados==1){//En caso de que alguno de los dados sea 6, elimina el temporal y cambia salir para salir del turno del jugador
                    JOptionPane.showMessageDialog(null,"Dado 1: "+dado1+"\nDado 2: "+dado2+"\n\nPerdio el puntaje temporal","PIG-Turno de "+nombre+".",-1);
                    pTemporal=0;
                    salir=2;
                }else{
                    if(pTotalUsuario+pTemporal >= 100){//En caso de que la suma del temporal y el total sea >= 100, para finalizar la partida
                        pTotalUsuario+=pTemporal;
                        JOptionPane.showMessageDialog(null,"Dado 1: "+dado1+"\nDado 2: "+dado2+"\n\nPuntaje final de "+nombre+": "+pTotalUsuario+".","PIG-Turno de "+nombre,-1);
                        salir=1;
                    }else{//Para que el usuario seleccione que quiere hacer(lanzar, parar, salir), en caso de no pase ninguna de las anteriores
                        String [] opciones = {"Lazar","Parar","Salir"};
                        seleccion = JOptionPane.showOptionDialog(null,"Puntaje de "+nombre+": "+pTotalUsuario+
                                "\nPuntaje de Computadora: "+pTotalCompu+
                                "\n\nPuntaje Temporal: "+pTemporal+
                                "\n\nDado 1: "+dado1+
                                "\nDado 2: "+dado2,
                                "PIG-Turno de "+nombre+".",JOptionPane.DEFAULT_OPTION,-1,null,opciones,opciones[0]);
                    }
                }
            }
            if(seleccion==1){//En caso de que selecciono parar, cambia salir, para salir del turno del usuario y suma el temporal al total
                salir=2;
                pTotalUsuario+=pTemporal;
                pTemporal=0;
            }else{
                if(seleccion==2){//En caso de que selecciono salir
                    salir=-1;
                }
            }
        }
        return salir;
    }
    
    /*La funcion jugadaCompu recibe una String que es el nombre del usuario solo para mostrarlo en el JOptionPane
      retorna 2 si la jugada de la computodora termina porque al menos un dado era 6 o la computadora decidio terminar el lanzamiento de dados
      retorna 1 si la jugada de la computodora termina porque el puntaje total mas el temporal supera 100 puntos
    */
    public int jugadaCompu(String nombre){
        int salir = 0;//Dato a retornar 
        int dado1=0;//Almacena el valor del dado 1
        int dado2=0;//Almacena el valor del dado 2
        int pTemporal = 0;//Almacena el valor del puntaje temporal
        int seleccion=0;//Almacena lo que selecciono la computadora(lanzar,parar,salir)
        int revisados=0;//Almacena el valor de los dados revisados, si son 6 o no
        while(pTotalCompu<100 && salir==0){
            dado1=dado();//Carga un valor al dado 1
            dado2=dado();//Carga un valor al dado 2
            pTemporal+=dado1+dado2;
            revisados=revisarDados(dado1,dado2);
            if(revisados==2){//En caso de que ambos dados sean 6, eliminan el total de la computadora y cambia salir para salir del turno de la computadora
                JOptionPane.showMessageDialog(null,"Dado 1: "+dado1+"\nDado 2: "+dado2+"\n\nPerdí el puntaje total","PIG-Turno de la computadora",-1);
                pTotalCompu=0;
                salir=2;
            }else{
                if(revisados==1){//En caso de que alguno de los dados sea 6, elimina el temporal y cambia salir para salir del turno de la computadora
                    JOptionPane.showMessageDialog(null,"Dado 1: "+dado1+"\nDado 2: "+dado2+"\n\nPerdí el puntaje temporal","PIG-Turno de la computadora",-1);
                    pTemporal=0;
                    salir=2;
                }else{
                    if(pTotalCompu+pTemporal >= 100){//En caso de que la suma del temporal y el total sea >= 100, para finalizar la partida
                        pTotalCompu+=pTemporal;
                        JOptionPane.showMessageDialog(null,"Dado 1: "+dado1+"\nDado 2: "+dado2+"\n\nPuntaje final de la Computadora: "+pTotalCompu+".","PIG-Turno de la computadora",-1);
                        salir=1;
                    }else{//Para que la computadora seleccione que quiere hacer(lanzar, parar, salir), en caso de no pase ninguna de las anteriores
                        JOptionPane.showMessageDialog(null,"Puntaje de "+nombre+": "+pTotalUsuario+
                                "\nPuntaje de Computadora: "+pTotalCompu+
                                "\n\nPuntaje Temporal: "+pTemporal+
                                "\n\nDado 1: "+dado1+
                                "\nDado 2: "+dado2,
                                "PIG-Turno de la computadora",-1);
                        seleccion = estrategia(pTemporal);
                    }
                }
            }
            if(seleccion==1){//Si la computadora decidio no volver a tirar los dados
                salir=2;
                pTotalCompu+=pTemporal;
                pTemporal=0;
            }
        }
        return salir;
    }
    
    /*La funcion estrategia recibe u int que es el puntaje temporal de la computadora cuando esta lanzando dados, 
      retorna 1 si no debe de volver a tirar los dados
      retorna 0 si no debe de volver a tirar los dados
    */
    public int estrategia(int pTemporal){
        int valor=0;//valor a retornar, 0 porque se busca en que casos no debe volver a tirar y ahí se cambia
        int diferencia=pTotalUsuario-pTotalCompu;//Es la diferencia que hay entre el puntaje del usuario y la computadora
        if((diferencia>=40 && pTemporal>=20)){//En caso de que la diferencia sea >= 40 y el puntaje es mayor a 20, entonces devuelve que no debe tirar de nuevo
            valor=1;
        }else{
            if((diferencia>=30 && pTemporal>=15)){//En caso de que la diferencia sea >= 30 y el puntaje es mayor a 15, entonces devuelve que no debe tirar de nuevo
                valor=1;
            }else{
                if((diferencia>=20 && pTemporal>=10)){//En caso de que la diferencia sea >= 20 y el puntaje es mayor a 10, entonces devuelve que no debe tirar de nuevo
                    valor=1;
                }
            }
        }
        if(pTotalCompu>=pTotalUsuario && pTemporal>=10){//En caso de que el puntaje de la compu sea >= al del usuario y el temporal es >= 10, entonces devulve que no debe vovler a tirar
            valor=1;
        }
        if(pTotalCompu>=90 && pTemporal>=2){//En caso de que el puntaje de la compu sea >= 90 y el temporal es >= 2, entonces devulve que no debe vovler a tirar
            valor=1;
        }else{
            if(pTotalCompu>=80 && pTemporal>=6){//En caso de que el puntaje de la compu sea >= 80 y el temporal es >= 6, entonces devulve que no debe vovler a tirar
                valor=1;
            }else{
                if(pTotalCompu>=60 && pTemporal>=12){//En caso de que el puntaje de la compu sea >= 60 y el temporal es >= 12, entonces devulve que no debe vovler a tirar
                    valor=1;
                }else{
                    if(pTotalCompu>=40 && pTemporal>=15){//En caso de que el puntaje de la compu sea >= 40 y el temporal es >= 15, entonces devulve que no debe vovler a tirar
                        valor=1;
                    }else{
                        if(pTotalCompu<=40 && pTemporal>=20){//En caso de que el puntaje de la compu sea <= 40 y el temporal es >= 20, entonces devulve que no debe vovler a tirar
                            valor=1;
                        }
                    }
                }
            }
        }
        if((diferencia>=6&&diferencia-pTemporal<=5)){//En caso de que la deferencia menos el temporal de la compu sea <=5
            valor=1;
        }
        if(pTotalCompu+pTemporal>=pTotalUsuario && pTemporal>10){//En caso de que la suma del total y el temporal supere o iguale al del usuario y el temporal sea mayor a 10
            valor= 1;
        }
        if(pTotalUsuario+15<=pTotalCompu && pTemporal<15 && pTotalCompu<=85){//En caso de que el puntaje total de la compu sea <= 85, le gane al del usuario por 15 y el temporal sea < 15
            valor=0;
        }
        return valor;
    }
        
}
