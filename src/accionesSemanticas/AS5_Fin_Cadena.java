package accionesSemanticas;

import java.util.HashMap;
import java.util.Hashtable;
import compilador.Simbolo;


public class AS5_Fin_Cadena extends AccionSemantica{
	
	Hashtable<String,Simbolo> tablaSimbolo;
	HashMap<String,Integer> tablaToken;  
	Simbolo s;
	
	// Constructor
	public AS5_Fin_Cadena(Hashtable<String, Simbolo> tablaSimbolo, HashMap<String, Integer> tablaToken) {
		this.tablaSimbolo = tablaSimbolo;
		this.tablaToken = tablaToken;
	}

	// Al finalizar de reconocer una cadena, si esta el la tabla
	// se retorna sino se la agrega y luego se retorna
	public int execute(StringBuffer buffer, char c) {
		if( (buffer.toString().length() >= 2) && (buffer.toString().charAt(0) == '"') && (buffer.toString().charAt(buffer.toString().length()-1) == '"') ) {
			this.s = new Simbolo(buffer.toString());
			s.setTipo("Cadena");
			// Si est� en la tabla
			if(tablaSimbolo.contains(this.s) ){ return tablaToken.get("CADENA"); }
			// Si no est� en la tabla
			else{                                			
				tablaSimbolo.put(s.getValor(),s);
				return tablaToken.get("CADENA");
			}
		}
		else {
			System.out.println("Error en la linea "+compilador.Compilador.nroLinea+": CADENA mal escrita");
			buffer.delete(0, buffer.length());
			return 0;
		}
		
	}

	// Al ser una cadena de texto no se necesitar�
	// acomodar la linea con lo cual retorna false
	public boolean acomodarLinea() {
		return true;
	}

}
