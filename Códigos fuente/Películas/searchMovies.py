#!/usr/bin/env python3

# create and instance of the tmdbsimple class
from tmdbv3api import TMDb, Movie

tmdb = TMDb()
#import tmdbsimple as tmdb
tmdb.api_key = 'cc6acd82d00b4ae3d8d3c8cb5dc73d2a'

userMovie = ""
while userMovie == "":
    userMovie = str(input("Escribe la película que quieres buscar:"))

movie = Movie()
search = movie.search(userMovie)
for result in search:
    print(result.title, result.id, result.release_date)
