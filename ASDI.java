/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package cr.principal;

//import static cr.principal.TipoToken.IDENTIFICADOR;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Stack;

public class ASDI implements Parser{


    private int i = 0;
    private boolean hayErrores = false;
    private Token preanalisis;
    private final List<Token> tokens;
    private String[][] Matriz;
    private Stack<String> stack = new Stack<>();
    
    public ASDI(List<Token> tokens){
        this.Matriz = new String[8][11];
        
        this.tokens = tokens;
        preanalisis = this.tokens.get(i);
        //this.S.push("hola");
    }

    @Override
    public boolean parse() {
        /********************************************************
        Función principal para analisar sintácticamente el token
        actual.
        *********************************************************/
        //Stack<String> stack = new Stack<>();
        //pila.push("$");
        this.stack.push("Q");
        llenarMatriz();
        while (!this.stack.isEmpty()) {
            //Symbol es el apuntador al tope de la pila
            String symbol = this.stack.peek(); 
            
            if (isTerminal(symbol)) {
                System.out.println("La palabra '" + symbol + "' es terminal");
                 System.out.println("El preanalisis es: "+preanalisis.lexema);
                if(symbol.equals(preanalisis.lexema)){// preanalisis.tipo == tt
                    System.out.println("'"+preanalisis.lexema + "' es igual al tope de pila: " + symbol);
                    /*---------------------------------------------
                    Como el preanalisis y el token coincidieron, 
                    avanza el preanalisis al siguiente token:1
                    ---------------------------------------------*/
                    this.stack.pop();
                    i++;
                    preanalisis = tokens.get(i);
                    //System.out.println("Preanalisis= "+preanalisis.lexema + " y tope=" + symbol);
                //---------------------------------------------
                } else {
                    return false;
                }
            } else {
                System.out.println("La palabra '" + symbol + "' no es terminal");
                String production;
                int fila;
                int columna;
                fila = getFila(stack.peek());
                System.out.println("Fila: "+fila);
                columna = getColumna(preanalisis.lexema);
                System.out.println("Columna: "+columna);
                production = this.Matriz[columna][fila];
                System.out.println("Produccion: "+production);
                if(production == "#"){
                    System.out.println("\n\nError en la sintaxis de tu consulta, error:"+production);
                    break;
                }
                    
                this.stack.pop();
                if(production != null)
                    addToStack(production);
                //String[][] productions = getProductions(symbol);
                   String[][] productions = null;
            }
        }
        
        //System.out.println(getCelda(0,0,0));
        
        /*******************************************************/
        /********************************************************
        Rectifica si en el token analizado en la función anterior
        posee un error. Devolviendo un VERDADERO o FALSO.
        *********************************************************/
        if(preanalisis.tipo == TipoToken.EOF && this.stack.isEmpty()){
            System.out.println("\n\nConsulta correcta");
            return  true;
        }else {
            System.out.println("\n\nSe encontraron errores");
        }
        return false;
        //*******************************************************
    }
    
    private void llenarMatriz(){
        /*******************************
        Matriz[1.][2.][3.]
        1. Son Filas
        2. Son Columnas
        3. Son Arreglos de palabras
        ********************************/
        
        //*****COLUMNA 0********
        
        for(int k=10; k>=0; k--){
            if(k == 10)
                this.Matriz[0][k]="T from D select"; //Q -> select D from T
            else
                this.Matriz[0][k]="#";
        }
        //*****COLUMNA 1********
        for(int k=10; k>=0; k--){
            if(k == 6 || k == 4)
                this.Matriz[1][k]=null;
            else
                this.Matriz[1][k]="#";
        }
        //*****COLUMNA 2********
        for(int k=10; k>=0; k--){
            if(k == 9)
                this.Matriz[2][k]="P distinct"; //D -> distinct P
            else
                this.Matriz[2][k]="#";
        }
        //*****COLUMNA 3********
        
        for(int k=10; k>=0; k--){
            if(k == 9)
                this.Matriz[3][k]="P";
            else if(k==8)
                this.Matriz[3][k]="asterisco";
            else
                this.Matriz[3][k]="#";
        }
        //*****COLUMNA 4********
        for(int k=10; k>=0; k--){
            if(k == 6)
                this.Matriz[4][k]="A coma";
            else if(k == 4)
                this.Matriz[4][k]=null;
            else if(k == 2)
                this.Matriz[4][k]="T coma";
            else if(k == 0)
                this.Matriz[4][k]=null;
            else
                this.Matriz[4][k]="#";
        }
        //*****COLUMNA 5********
        for(int k=10; k>=0; k--){
            if(k == 9)
                this.Matriz[5][k]="P";
            else if(k == 8)
                this.Matriz[5][k]="A";
            else if(k == 7)
                this.Matriz[5][k]="A1 A2"; //A -> A2 A1
            else if(k == 5)
                this.Matriz[5][k]="A3 identificador"; //A2 -> id A3
            else if(k == 3)
                this.Matriz[5][k]="T1 T2"; //T -> T2T1
            else if(k == 1)
                this.Matriz[5][k]="T3 identificador"; //T2 -> idT3
            else if(k == 0)
                this.Matriz[5][k]="identificador";    //T3 -> id
            else
                this.Matriz[5][k]="#";
        }
        //*****COLUMNA 6********
        for(int k=10; k>=0; k--){
            if(k == 4)
                this.Matriz[6][k]="identificador punto"; //A3 -> .id
            else
                this.Matriz[6][k]="#";
        }
        //*****COLUMNA 7********
        for(int k=10; k>=0; k--){
            if(k == 0 || k == 2)
                this.Matriz[7][k]=null;
            else
                this.Matriz[7][k]="#";
        }
        //**********************
        //*******IMPRIMIR MATRIZ:*******
        /*for(int f=0; f<=7; f++){ //FILA
            System.out.println("Columna"+f);
            for(int c=10; c>=0; c--){ //COLUMNA
                System.out.println(this.Matriz[f][c][0]);
            }
            
        }
        */
        //**********************
        
    }
    private String getCelda(int fila, int columna){
        return this.Matriz[fila][columna];
    }
    int getFila(String palabra){
        System.out.println("Funcion 'fila' está buscando a '" + palabra + "' en la matriz");
         switch (palabra) {
            case "Q":
                return 10;
            case "D":
                return 9;
            case "P":
                return 8;
            case "A":
                return 7;
            case "A1":
                return 6;
             case "A2":
                return 5;
            case "A3":
                return 4;
            case "T":
            return 3;
            case "T1":
                return 2;
            case "T2":
                return 1;
            case "T3":
                return 0;
            default:
                System.out.println("Error al obtener la fila de la matriz");
                return -1;
        }
    }
    int getColumna(String palabra){
        System.out.println("Funcion 'columna' está buscando a '" + palabra + "' en la matriz");
        
        switch (palabra) {
            case "$":
                //System.out.println(7);
                return 7;
            case "punto":
                //System.out.println(6);
                return 6;
            case "identificador":
                //System.out.println(5);
                return 5;
            case "coma":
                //System.out.println(4);
                return 4;
            case "asterisco":
                //System.out.println(3);
                return 3;
            case "distinct":
                //System.out.println(2);
                return 2;
            case "from":
                //System.out.println(1);
                return 1;
            case "select":
                //System.out.println(0);
                return 0;
            default:
                //System.out.println("Error al obtener la columna de la matriz");
                return -1;
        }
    }
    /*
    int getColumna(String nt){
        if(noTerminales.get(nt) == null){
            System.out.println("Error al obtener una columna de la matriz");
            return -1;
        }
        else{
            return noTerminales.get(nt);
        }
    }
    */
        
    private void impPila(Stack S){
       while(!S.empty()){
           System.out.println(S.peek()+ ",");
           S.pop();
       }
    }
    private void addToStack(String source){
        char caracter = 0;
        String palabra = "";
        source = source + " ";
        //int inicioPalabra = 0; No hace falta
        for(int i=0; i<source.length(); i++){
            caracter = source.charAt(i);
            System.out.println(" -"+i+" "+caracter);
            if(Character.isAlphabetic(caracter) || Character.isDigit(caracter) ){
                palabra = palabra + caracter;
            }
            else{
                System.out.println("AddToStack agrega la palabra '"+palabra+"' a la pila, valor de i: "+i+"/"+source.length());
                this.stack.push(palabra);
                //i--;
                palabra="";
                
            }
        }
    }
    
    private boolean isTerminal(String symbol){
        System.out.println("Se ejecuta la función 'isTerminal'");
        int index = getColumna(symbol);
        //int index = terminales.get(symbol);
        //si el resultado se encuentra en el rango del número total de terminales: 7 a 0
        //significa que es un terminal, de lo contrario devolvería un null.
        if(index <=7 && index>=0) //getColumna(symbol) ==null index<=7 && index>=0
            return true;
        else  
            return false;
    }
    
   

}