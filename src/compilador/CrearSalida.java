package compilador;

import java.io.BufferedWriter;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.Hashtable;
import java.util.Iterator;
import java.util.Set;

@SuppressWarnings("unused")
public class CrearSalida {

	public static void crearTxtSalida(Compilador c) {
		try {
			String ruta = "Tabla de Simbolos.txt";
			BufferedWriter salida = new BufferedWriter(new FileWriter(ruta));
			
			salida.write("/*  Resultados de la compilacion  */");
			salida.newLine();
			salida.newLine();
			
			
			Hashtable<String,ArrayList<Simbolo>> tablaSimbolo = c.getTablaSimbolo();
			salida.write("/*  Tabla de Simbolos  */");
			salida.newLine();
			salida.newLine();
					
			Set<String> keys = tablaSimbolo.keySet();
		    Iterator<String> itr = keys.iterator();
		    String str; 

		    while (itr.hasNext()) { 
		       str = itr.next();
		       ArrayList<Simbolo> aux =  eliminarRepetidos(tablaSimbolo.get(str));
	    	   for(int i=0; i<aux.size(); i++) {
	    			   salida.write("Clave: " + aux.get(i).getValor() + "\t Value: " + aux.get(i).getValor());
	    			   salida.newLine();
	    	   }
			}
		    
			salida.close();
		} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
		}
	}
	
	public static ArrayList<Simbolo> eliminarRepetidos(ArrayList<Simbolo> l){
		ArrayList<Simbolo> aux = new ArrayList<Simbolo>();
	    boolean p = true;
	    
	    for(int i=0; i<l.size(); i++) {
	    	if(p) {
	    		p = false;
	    		aux.add(l.get(i));
	    	}
	    	else {
	    		boolean r = true;
	    		for(int j=0; j<aux.size(); j++) {
	    			if(aux.get(j).getValor().equals(l.get(i).getValor()))
	    				r = false;
	    		}
	    		if(r) {
	    			aux.add(l.get(i));
	    		}
	    	}
	    }
	    return aux;
	}
	
}
