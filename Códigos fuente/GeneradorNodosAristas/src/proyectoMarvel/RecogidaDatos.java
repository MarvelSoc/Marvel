package proyectoMarvel;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Scanner;

public class RecogidaDatos
{
	private static int contNombres;
	private static int contRelaciones;
	private static ArrayList<String> nombrePersonajes = new ArrayList<>();
	private static ArrayList<Relacion> relaciones = new ArrayList<>();
	private static String[] nombres;
	
	public static void abrirArchivo(String tipo) throws IOException
	{
		File f = null;
		System.out.println("Abriendo archivo...");
		
		if(tipo.equalsIgnoreCase("PELICULAS"))
			f = new File("movies.csv");
		else if(tipo.equalsIgnoreCase("COMICS"))
			f = new File("todos_los_heroes_por_comic.csv");
		
		nombres = new String[(int) f.length()];
		
		FileReader fr = new FileReader(f);
		BufferedReader br = new BufferedReader(fr);
		
		contNombres = 0;
		contRelaciones = 0;
		
		int i = 0;
		String linea;
		String[] lineaCortada;
		
		while((linea = br.readLine()) != null)
		{
			if(i == 0)
				i++;
			
			else
			{
				lineaCortada = linea.split(",");
				nombres[i-1] = lineaCortada[1];
				contabilizar(nombres[i-1], tipo);
				i++;
			}
		}
		
		fr.close();
		br.close();
		System.out.println("Cerrando archivo...");
	}
	
	public static void contabilizar(String lineaNombres, String tipo)
	{
		String aux = lineaNombres.trim();
		String[] nombresAux;
		String aux2;
		
		if(aux.charAt(0) == '\"')
		{
			nombresAux = aux.split("\"");
			aux2 = nombresAux[1];
			
			if(tipo.equalsIgnoreCase("PELICULAS"))
				 nombresAux = aux2.split(" : ");
			else
				nombresAux = aux2.split(" :  ");
			
		}
		else
		{
			if(tipo.equalsIgnoreCase("PELICULAS"))
				 nombresAux = aux.split(" : ");
			else
				nombresAux = aux.split(" :  ");
		}
		
		String nombreAux1, nombreAux2;
		String[] personaje;
		
		for (int i = 0; i < nombresAux.length; i++) 
		{
			if(tipo.equalsIgnoreCase("PELICULAS"))
				personaje = nombresAux[i].split(" / ");
			else
				personaje = nombresAux[i].split("/");
			
			if(personaje.length > 2)
				nombreAux1 = personaje[1] + " / " + personaje[2];
			else if(personaje.length == 2)
				nombreAux1 = personaje[0] + " / " + personaje[1];
			else
				nombreAux1 = nombresAux[i];
			
			if(nombreAux1.charAt(nombreAux1.length()-1) == '/')
			{
				personaje = nombreAux1.split("/");
				nombreAux1 = personaje[0];
			}
			
			existeNombre(nombreAux1);
			
			for (int j = i+1; j < nombresAux.length; j++) 
			{
				if(tipo.equalsIgnoreCase("PELICULAS"))
					personaje = nombresAux[j].split(" / ");
				else
					personaje = nombresAux[j].split("/");
				
				if(personaje.length > 2)
					nombreAux2 = personaje[1] + " / " + personaje[2];
				else if(personaje.length == 2)
					nombreAux2 = personaje[0] + " / " + personaje[1];
				else
					nombreAux2 = nombresAux[j];
				
				if(nombreAux2.charAt(nombreAux2.length()-1) == '/')
				{
					personaje = nombreAux2.split("/");
					nombreAux2 = personaje[0];
				}
				
				existeNombre(nombreAux2);
				existeRelacion(nombreAux1, nombreAux2);
				
			}
		}
	}
	
	private static void existeNombre(String nombreAux1)
	{
		int i = 0;
		if(contNombres == 0)
		{
			nombrePersonajes.add(nombreAux1);
			contNombres++;
		}
		else
		{
			while(!nombrePersonajes.get(i).equalsIgnoreCase(nombreAux1) && i < (contNombres-1))
			{
				i++;
			}
			if(!nombrePersonajes.get(i).equalsIgnoreCase(nombreAux1) && i == (contNombres-1))
			{
				nombrePersonajes.add(nombreAux1);
				contNombres++;
			}
		}
	}
	
	
	public static void existeRelacion(String nombre1, String nombre2)
	{
		Relacion aux = null;
		String nom1=null;
		String nom2=null;
		boolean enc = false;
		int j = 0;
		
		if(contRelaciones == 0)
		{
			aux = new Relacion(nombre1, nombre2, 1);
			relaciones.add(aux);
			contRelaciones++;
		}
		else
		{	
			while(j < relaciones.size() && !enc)
			{
				aux = relaciones.get(j);
				nom1 = aux.getNombre1();
				nom2 = aux.getNombre2();
								
				if((nombre1.equals(nom1) && nombre2.equals(nom2)) || (nombre1.equals(nom2) && nombre2.equals(nom1)))
				{
					aux.aumentarContador();
					contRelaciones++;
					enc = true;
				}
				j++;

			}
			if(!enc){
				aux = new Relacion(nombre1,nombre2,1);
				relaciones.add(aux);
				contRelaciones++;
			}
			
		}
	}

	public static void crearArchivoNodos(String tipo) throws IOException
	{
		System.out.println("Creando archivo nodos...");
		
		FileWriter fw = new FileWriter(new File(tipo.toLowerCase() + "-nodos.csv"));
		BufferedWriter bw = new BufferedWriter(fw);
		
		bw.write("Id;Label\n");
		
		for (int i = 0; i < contNombres; i++) 
			bw.write(nombrePersonajes.get(i) + ";" + nombrePersonajes.get(i) + "\n");
		
		System.out.println("Archivo nodos creado OK");
		
		bw.close();
	}
	
	public static void crearArchivoAristas(String tipo) throws IOException
	{
		System.out.println("Creando archivo aristas...");
		
		FileWriter fw = new FileWriter(new File(tipo.toLowerCase() + "-aristas.csv"));
		BufferedWriter bw = new BufferedWriter(fw);
		
		bw.write("Source;Target;Weight;Type\n");
		
		for (int i = 0; i < relaciones.size(); i++) 
			bw.write(relaciones.get(i).getNombre1()+";"+ relaciones.get(i).getNombre2()+ ";"+ relaciones.get(i).getCont() + ";" + "Undirected\n");
		
		System.out.println("Archivo aristas creado OK");
		bw.close();
	}
	
	public static void main(String[] args) throws IOException
	{
		Scanner sc = new Scanner(System.in);
		System.out.println("¿Que archivo deseas generar: Peliculas | Comics?");
		String tipo = sc.nextLine().toUpperCase();
		
		abrirArchivo(tipo);
		crearArchivoNodos(tipo);
		crearArchivoAristas(tipo);
		sc.close();
	}

}
