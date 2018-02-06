#!/usr/bin/env python3

# create and instance of the tmdbsimple class
from tmdbv3api import TMDb, Movie
import requests
import csv

tmdb = TMDb()
#import tmdbsimple as tmdb
tmdb.api_key = 'cc6acd82d00b4ae3d8d3c8cb5dc73d2a'

#Escribimos en nuestro fichero movies.csv
def storeMovies(movie, results):
    characters = []
    characterRow = ""
    for result in results['cast']:
            character = result['character'].upper()
            #Si nuestro persnaje no esta en la lista de personajes lo añadimos
            if character not in characters:
                #No queremos guardar personajes uncredited
                if "UNCREDITED" not in character:
                    characters.insert(len(characters), character)
                    characterRow+= character + " : "
    temp = len(characterRow)
    row = movie + "," + characterRow[:temp - 2] + "\n"
    movies.write(row)
    return characters

def storeNodesMovies(charactersMovie):
    for character in charactersMovie:
        if character.upper() not in allCharacters:
            allCharacters.insert(len(allCharacters), character.upper())
            id = str(len(allCharacters) - 1)
            name = character.upper()
            row = id + "," + name + " \n"
            nodes.write(row)

    return

#Lista con los id de las peliculas que hemos decidido estudiar
moviesList = [
            ('Iron man 1',                              '1726'),
            ('The incredible Hulk',                     '1724'),
            ('Iron man 2',                              '10138'),
            ('Thor 1',                                  '10195'),
            ('Capitán américa: el primer vengador',     '1771'),
            ('Los vengadores 1',                        '24428'),
            ('Iron man 3',                              '68721'),
            ('Thor 2',                                  '441829'),
            ('Capitán américa: el soldado de invierno', '100402'),
            ('Guardianes de la galaxia 1',              '11834'),
            ('Los vengadores 2',	                    '99861'),
            ('Capitán américa: civil war',              '271110'),
            ('Doctor Strange'           				'284052'),
            ('Guardianes de la galaxia 2',		        '283995'),
            ('Spiderman Homecoming',			        '315635'),
            ('Thor ragnarok',	              			'284053'),
            ('X-Men', 					                '36657'),
            ('X-Men 2', 				                '36658'),
            ('X-Men: The last stand', 			        '36668'),
            ('X-Men Origins: Wolverine',		        '2080'),
            ('X-Men: First Class',	                 	'49538'),
            ('The Wolverine',				            '76170'),
            ('X-Men Days of Future Past',		        '127585'),
            ('X-Men: Apocalypse', 			            '246655'),
            ('Logan', 				                    '263115'),
            ('Deadpool',				                '293660'),
            ('Blade',                                   '36647'),
            ('Blade II',                                '36586'),
            ('Blade Trinity',                          '36648'),
            ('Ghost Rider',                             '1250'),
            ('Ghost Rider 2: Spirit of Vengeance',      '71676'),
            ('The Fantastic Four',                      '22059'),
            ('Fantastic 4: Rise of the Silver Surfer',  '1979'),
            ('Elektra',                                 '9947'),
            ('Daredevil',                               '9480'),
            ('Spider-Man',                              '557'),
            ('Spider-Man 2',                            '558'),
            ('Spider-Man 3',                            '559'),
            ('The Amazing Spider-Man',                  '1930'),
            ('The Amazing Spider-Man 2',                '102382'),
            ('The Punisher',                            '7220'),
            ('Punisher: War Zone',                      '13056'),
            ('Ant-Man',                                 '102899')
]

#Creamos el fichero de peliculas con los actores que aparecen en ella
movies = open('movies.csv', "w")
columnTitleRow = "movie, characters\n"
movies.write(columnTitleRow)

#Creamos el fichero de nodos de las peliculas
nodes = open('moviesNodes.csv', "w")
columnTitleRow = "id, character\n"
nodes.write(columnTitleRow)

allCharacters = []

for movie in moviesList:
    #Buscamos los personajes de cada pelicula
    url = "https://api.themoviedb.org/3/movie/" + movie[1] + "/credits?api_key=cc6acd82d00b4ae3d8d3c8cb5dc73d2a"
    response = requests.get(url)

    #Si realiza bien la llamada rest
    if response.status_code == 200:
        results = response.json()
        #Guardamos los personajes de la pelicula
        charactersMovie = storeMovies(movie[0], results)
        storeNodesMovies(charactersMovie)

print("Ficheros csv de peliculas y nodos creados")
