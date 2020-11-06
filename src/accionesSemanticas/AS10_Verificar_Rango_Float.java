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
		if (numero.contains("f"))
			flotante = Double.parseDouble(numero.replace('f', 'E'));
		else {
			falta caso 00.0001025
			y exp negativo
			String buffer = "109.10";
	        String [] division = buffer.toString().split("\\."); 
	        String aux = "";
	        int primero = 0;
	        int contador = 0;
			if(division[0].length() > 1)
			    for(int i=0; i<division[0].length(); i++){
			        if(Character.getNumericValue(division[0].charAt(i)) != 0){
			            primero = i;
			            for(int j=i+1; j<division[0].length(); j++){
			             aux = aux + String.valueOf(division[0].charAt(j));
			             contador++;
			            }
			            break;
			        }
			    }
			        
			String union = division[0].charAt(primero) + "." + aux + division[1] + "f+" + String.valueOf(contador) ;
			System.out.println("Hello World " + union);
		}
			flotante = Double.parseDouble(numero);
		
		this.s = new Simbolo(numero);
		s.setTipo("float");
		// Si la cte ya está en la TS, retornar reference
		if(TablaSimbolo.contains(this.s) )  return TablaToken.get("CTE");
		else {
			s.setUso("CTE");
			TablaSimbolo.put(s.getValor(),s);
			return TablaToken.get("CTE");
		}
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
