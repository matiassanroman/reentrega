package accionesSemanticas;

import java.util.HashMap;
import java.util.Hashtable;

import compilador.Simbolo;

public class AS10_Verificar_Rango_Float extends AccionSemantica{

	Hashtable<String,Simbolo> TablaSimbolo;
	HashMap<String,Integer> TablaToken;  
	Simbolo s;

	// Se definen los rangos de las variables
	// tipo float
	static double minimalValorFloat = -3.40282347E38;
	static double minValorFloat     = -1.17549435E-38;
	static double maxValorFloat     =  1.17549435E-38;
	static double maximalValorFloat =  3.40282347E38;
	static double cero = 0.0;
	
	// Contructor
	public AS10_Verificar_Rango_Float(Hashtable<String, Simbolo> tablaSimbolo, HashMap<String, Integer> tablaToken) {
		TablaSimbolo = tablaSimbolo;
		TablaToken = tablaToken;
	}

	public int execute(StringBuffer buffer, char c) {
		String numero = buffer.toString();
		double flotante;
		Simbolo s;
		if (numero.contains("f")) {
			flotante = Double.parseDouble(numero.replace('f', 'E'));
			s = new Simbolo(normalizar(flotante));
		}
		else {
			flotante = Double.parseDouble(numero);
			s = new Simbolo(String.valueOf(normalizar(flotante)));
		}
		
		System.out.println("FLOTANTE: " + flotante);
		
		System.out.println("VALOR: " + s.getValor());
		s.setTipo("float");
		// Si la cte ya está en la TS, retornar reference
		if(TablaSimbolo.contains(s) )  return TablaToken.get("CTE");
		else {
			TablaSimbolo.put(s.getValor(),s);
			System.out.println("Holaaa: " + s.getValor());
			return TablaToken.get("CTE");
		}
	}

	public static String normalizar(Double numero) {
		int contador = 0;
		String aux = "";
		String union = "";
        String [] division = numero.toString().split("\\."); 
        if(division[0].length() > 1)
		    for(int i=1; i<division[0].length(); i++){
		         aux = aux + String.valueOf(division[0].charAt(i));
		         contador++;
		     }
		return union = division[0].charAt(0) + "." + aux + division[1] + "f+" + String.valueOf(contador);
	}
	
	public static boolean estaEnRango(String s) {
		double flotante;
		if (s.contains("f"))
			flotante = Double.parseDouble(s.replace('f', 'E'));
		else
			flotante = Double.parseDouble(s);
		
		if ( ((flotante>minimalValorFloat) && (flotante < minValorFloat)) || (flotante==cero)  || ((flotante>maxValorFloat) && (flotante < maximalValorFloat)) ) 
			return true;
		else  // SI esta fuera de los rangos retornar error
			return false;	
	}
	
	@Override
	public boolean acomodarLinea() {
		return true;
	}

}
