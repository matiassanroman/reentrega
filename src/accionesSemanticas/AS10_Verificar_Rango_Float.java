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
		try {
			if( (buffer.toString().contains(".")) || (buffer.toString().contains("f")) ){
				System.out.println("ENTROOOOOOOOOOOOOOOOO: " + buffer.toString());
				String numero = buffer.toString();
				double flotante;
				Simbolo s;
				if (numero.contains("f")) {
					flotante = Double.parseDouble(numero.replace('f', 'E'));
					if(String.valueOf(flotante).contains("E"))
						s = new Simbolo(String.valueOf(flotante).replace('E', 'f'));
					else
						s = new Simbolo(String.valueOf(normalizar(flotante)));
				}
				else {
					flotante = Double.parseDouble(numero);
					s = new Simbolo(String.valueOf(normalizar(flotante)));
				}
				
				s.setTipo("float");
				// Si la cte ya est� en la TS, retornar reference
				if(TablaSimbolo.contains(s) )  return TablaToken.get("CTE");
				else {
					TablaSimbolo.put(s.getValor(),s);
					return TablaToken.get("CTE");
				}
			}
			else {
				System.out.println("Error: constante flotante mal escrita, en linea nro: " + compilador.Compilador.nroLinea);
				buffer.delete(0, buffer.length());
				return 0;
			}

		} catch (Exception e) {
			System.out.println("Error en la linea "+compilador.Compilador.nroLinea+": CTE FLOTANTE mal escrita");
			buffer.delete(0, buffer.length());
			return 0;
		}
		
		
			}

	public static String normalizar(Double numero) {
		int contador = 0;
		String aux = "";
		String union = "";
        String [] division = numero.toString().split("\\."); 
        
        //Caso de 0.0
        if(numero.equals(0.0))
        	return String.valueOf(numero);
        //Caso de 1.051
        if(Integer.valueOf(division[0]) >= 1 && Integer.valueOf(division[0]) <= 9) {
		    return String.valueOf(numero).replace('E', 'f');
        }
        // Caso de 100.001
        else if(Integer.valueOf(division[0]) > 9) {
        	 for(int i=1; i<division[0].length(); i++){
		         aux = aux + String.valueOf(division[0].charAt(i));
		         contador++;
		     } 
        	 return String.valueOf(division[0].charAt(0)) + "." + aux + "f+" + contador;  
        }
        //Caso de 0.0001050
        else {
        	int j=0;
        	contador--;
        	String subdivision = division[1];
        	
        	while(j < subdivision.length() && Character.getNumericValue(subdivision.charAt(j)) < 1) {
        		contador--;
        		j++;
        	}
        	
        	for(int i=j; i<subdivision.length(); i++) {
        		if(i==j) {
        			aux = subdivision.charAt(i) + ".";
        		}
        		else {
        			aux = aux + subdivision.charAt(i);	
        		}
        	}
        	return aux + "f" + contador;
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
