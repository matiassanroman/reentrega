package compilador;

public class Simbolo {

	public String valor;       //lexema
	public String tipo = "";   // int - float - string
	private boolean declarado = false;

	public Simbolo(String valor) {
	this.valor = valor;
	}

	public String getValor() {
		return valor;
	}

	public void setValor(String valor) {
		this.valor = valor;
	}

	public String getTipo() {
		return tipo;
	}

	public void setTipo(String tipo) {
		this.tipo = tipo;
	}

	public boolean isDeclarado() {
		return declarado;
	}

	public void setDeclarado(boolean declarado) {
		this.declarado = declarado;
	}

	
	
	@Override
	public boolean equals(Object o) {
	Simbolo s = (Simbolo) o;
	if (this.valor.equals(s.getValor())  /*&& this.ambiente.equals(s.getAmbiente()) && this.tipo.equals(s.getTipo()) && this.uso.equals(s.getUso())*/) {  
	return true;
	}
	return false;
	}
	
} 