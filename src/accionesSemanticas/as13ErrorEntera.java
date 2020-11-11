package accionesSemanticas;

public class as13ErrorEntera extends AccionSemantica{

	@Override
	public int execute(StringBuffer buffer, char c) {
		buffer.delete(0, buffer.length());
		return -3;
	}

	@Override
	public boolean acomodarLinea() {
		// TODO Auto-generated method stub
		return false;
	}

}
