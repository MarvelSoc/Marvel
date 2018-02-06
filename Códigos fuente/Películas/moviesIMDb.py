#!/usr/bin/env python3
#IMDB no va bien
# create and instance of the IMDb class
from imdb import IMDb
ia = IMDb(accessSystem='http')

# Search for a movie (get a list of Movie objects).
#s_result = ia.search_movie('Iron Man')

ironMan = ia.get_movie('0371745')
print("Valores que podemos buscar de la película")
print (ironMan.keys())
#print (ironMan['director'])
if ironMan:
    cast = ironMan.get('cast')
    if cast:
        topActors = 5
        for actor in cast[:topActors]:
            print("{0} as {1}".format(actor['name'], actor.currentRole))
    else:
        print("No cast")

# get a movie and print its director(s)
#the_matrix = ia.get_movie('0133093')
#print(the_matrix['director'])

# Search for a movie (get a list of Movie objects).
#s_result = ia.search_movie('Iron Man')
