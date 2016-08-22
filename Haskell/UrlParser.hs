module UrlParser 
(
	getQuery
,	splitStr
) where

getQuery req = last $ init $ words req

buildToken str delim = takeWhile (/= delim) str

splitStr [] delim = []
splitStr str delim = 
	let token = (buildToken str delim)
	in token : (splitStr (drop ((length token) + 1) str) delim)
