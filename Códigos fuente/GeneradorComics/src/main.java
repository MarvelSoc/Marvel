
import java.io.BufferedReader;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;
import java.util.Scanner;



public class main {

	public static void leerDiccionario(Map<String, ArrayList<String>> diccionario_heroes) throws IOException {
		// TODO Auto-generated method stub
		FileReader fr = new FileReader(new File("src\\moviesNodes.csv"));
		BufferedReader br = new BufferedReader(fr);
		String line, aux;
		
		while( ( line = br.readLine() ) != null )
		{
			String[] values = line.split(",");
			
			if( !values[0].equals("id") && !values[1].equals("character") )
			{
				String[] alias = values[1].split(" / ");
				ArrayList<String> lista_alias = new ArrayList<String>();
				
				// Puesto porque el csv viene con espacios para separar los nombres
				aux = alias[0].substring(0, alias[0].length() - 1);
				alias[0] = aux;
				if( alias.length > 1 )
				{
					for( int i = 1; i < alias.length; i++ )
					{
						aux = alias[i].substring(1, alias[i].length() - 1);
						alias[i] = aux;
					}
				}
				
				for( int i = 1; i < alias.length; i++ )
				{
					lista_alias.add(alias[i].toUpperCase());
				}
				diccionario_heroes.put(alias[0].toUpperCase(), lista_alias);
			}		
		}
		br.close();
	}
	
	public static void leerFicheroRelaciones( Map<String, ArrayList<String>> heroe_comics, ArrayList<String> lista_comics, Map<String, ArrayList<String>> comic_heroes ) throws IOException
	{
		FileReader fr = new FileReader(new File("src\\aristas_comics_dataset.csv"));
		BufferedReader br = new BufferedReader(fr);
		String line, aux;
		
		while( ( line = br.readLine() ) != null )
		{
			String[] values = line.split(",");
		
			if( values.length > 2 )
			{
				values[0] = values[0] + " " + values[1];
				values[1] = values[2];
			}
			
			ArrayList<String> lista_comics_heroe = new ArrayList<String>();
			
			if( !values[0].equalsIgnoreCase("hero") && !values[1].equalsIgnoreCase("comic") )
			{
				aux = values[1].substring(0, values[1].length()-1);
				if( !heroe_comics.containsKey(values[0]) )
				{
					lista_comics_heroe.add(aux);
					heroe_comics.put(values[0], lista_comics_heroe);
				}
				else
				{
					heroe_comics.get(values[0]).add(aux);
				}
				if( !lista_comics.contains(values[1]))
				{
					lista_comics.add(aux);
				}
					
			}	
		}
		for( String comic: lista_comics )
		{
			ArrayList<String> lista_heroes_comic = new ArrayList<String>();
			for( Map.Entry<String, ArrayList<String>> entry : heroe_comics.entrySet() )
			{
				String key = entry.getKey();
			    ArrayList<String> value = entry.getValue();
			    
		        if( value.contains(comic) )
		        {
		        	lista_heroes_comic.add(key);
		        }
			}
			comic_heroes.put(comic, lista_heroes_comic);
		}
		
		br.close();
	}
	
	public static void mostrarHeroesComics(Map<String, ArrayList<String>> heroe_comics)
	{
		for( Map.Entry<String, ArrayList<String>> entry : heroe_comics.entrySet() )
		{
			String key = entry.getKey();
		    ArrayList<String> value = entry.getValue();
		    
	        System.out.println(key + " " + value.toString());
		}
	}
	
	public static void mostrarComicsHeroes(Map<String, ArrayList<String>> comic_heroes)
	{
		for( Map.Entry<String, ArrayList<String>> entry : comic_heroes.entrySet() )
		{
			String key = entry.getKey();
		    ArrayList<String> value = entry.getValue();
		    
	        System.out.println(key + " " + value.toString());
		}
	}
	
	public static void heroes_por_comic(Map<String, ArrayList<String>> diccionario_heroes, ArrayList<String> lista_comics, Map<String, ArrayList<String>> heroe_comics, Map<String, ArrayList<String>> comic_heroes )
	{
		PrintWriter fileWriter = null;
		String aux = "";
		boolean encontrado = false;
		try {
			fileWriter = new PrintWriter(new File("src\\todos_los_heroes_por_comic.csv"));
			//Añadimos la cabecera del fichero
			fileWriter.append("Id,Lista_Heroes");
			fileWriter.append("\n");
	        
	        //Vamos añadiendo los nodos
			for( Map.Entry<String, ArrayList<String>> entry : comic_heroes.entrySet()  )
			{			
				fileWriter.append(entry.getKey());
				fileWriter.append(",");
				
				for( Map.Entry<String, ArrayList<String>> entryDic : diccionario_heroes.entrySet() )
				{
					String key = entryDic.getKey();
					
					if( entry.getValue().contains(key))
				    {
				    	encontrado = true;
				    }
					else
					{
						for( int i = 0; i < entryDic.getValue().size() && !encontrado; i++ )
						{
							if( entry.getValue().contains(entry.getValue().get(i)))
								encontrado = true;
						}
					}
					
				}
				if( encontrado )
				{
					aux = aux.concat(entry.getValue().toString().substring(1, entry.getValue().toString().length()-1));
					String[] parts = aux.split(",");
					aux = "";
					for( int i = 0; i < parts.length; i++ )
					{
						aux = aux.concat(parts[i] + " : ");
					}
					
					if( aux.length() > 0 )
						aux = aux.substring(0, aux.length()-3);
				}
				encontrado = false;

				fileWriter.append( aux );
				
				fileWriter.append("\n");
				aux = "";
			}
	        
	        System.out.println("CSV file was created successfully !!!");
		} catch (FileNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (@SuppressWarnings("hiding") IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}finally{
			fileWriter.flush();
			fileWriter.close();
		}
		
	}
	
	public static void mostrarDiccionario(Map<String, ArrayList<String>> diccionario_heroes)
	{
		int num = 1;
		
		for( Map.Entry<String, ArrayList<String>> entry : diccionario_heroes.entrySet() )
		{
			String key = entry.getKey();
		    ArrayList<String> value = entry.getValue();
		    
	        System.out.println(num + ".- " + key + " " + value.toString());
	        num++;
		}
		
	}
	
	public static void buscarEnDiccionario(Map<String, ArrayList<String>> diccionario_heroes)
	{
		String nombre = "";
		
		System.out.println("Introduzca el héroe que está buscando");
		Scanner sc = new Scanner(System.in);
		nombre = sc.nextLine().toUpperCase();
		sc.close();
		
		for( Map.Entry<String, ArrayList<String>> entry : diccionario_heroes.entrySet() )
		{
			String key = entry.getKey();
		    ArrayList<String> value = entry.getValue();
	        
	        if( value.contains(nombre) || nombre.equalsIgnoreCase(key) )
	        	System.out.println( key + " " + value.toString() + "\n");
		}	
	}
	
	public static void mostrarComics(ArrayList<String> lista_comics)
	{
		for( int i = 0; i < lista_comics.size(); i++ )
		{
			System.out.println(lista_comics.get(i)+ "\n");
		}
	}
	
	public static void main(String[] args) {
		// TODO Auto-generated method stub
		ArrayList<String> lista_comics = new ArrayList<>();
		Map<String, ArrayList<String>> diccionario_heroes = new HashMap<String, ArrayList<String>>();
		Map<String, ArrayList<String>> heroe_comics = new HashMap<String, ArrayList<String>>();
		Map<String, ArrayList<String>> comic_heroes = new HashMap<String, ArrayList<String>>();
		Scanner sc = new Scanner(System.in);
		
		int op = 0;
		
		try {
			leerDiccionario(diccionario_heroes);
			leerFicheroRelaciones(heroe_comics, lista_comics, comic_heroes);
			
			do
			{
				System.out.println( "1.- Mostrar diccionario ");
				System.out.println( "2.- Mostrar comics por heroes");
				System.out.println( "3.- Mostrar héroes por comic");
				System.out.println( "4.- Mostrar listado de comics");
				System.out.println( "0.- Salir ");
				System.out.println( "Introduzca operación \n");
				
				op = sc.nextInt();
				
				switch (op) 
				{
				case 1:
					mostrarDiccionario(diccionario_heroes);
				break;
				
				case 2:
					mostrarComicsHeroes(comic_heroes);
				break;
				case 3:
					mostrarHeroesComics(heroe_comics);
				break;
				case 4:
					mostrarComics(lista_comics);
				break;
				
				}
			}while( op != 0 );
			
			sc.close();
			
			heroes_por_comic(diccionario_heroes, lista_comics, heroe_comics, comic_heroes);
			
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

	

}
