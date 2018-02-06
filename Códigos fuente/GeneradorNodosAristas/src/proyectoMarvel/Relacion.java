package proyectoMarvel;

public class Relacion {

	private String nombre1, nombre2;
	private int contador = 0;
	
	public Relacion(String nombre1, String nombre2, int cont)
	{
		this.nombre1 = nombre1;
		this.nombre2 = nombre2;
		this.contador = cont;
	}
	
	public String getNombre1(){
		return this.nombre1;
	}
	
	public String getNombre2(){
		return this.nombre2;
	}
	
	public int getCont(){
		return this.contador;
	}
	
	public void aumentarContador()
	{
		this.contador++;
	}
	
}
