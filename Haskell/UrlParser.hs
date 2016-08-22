module UrlParser 
(
	getQuery
,	splitStr
) where

getQuery header = last $ init $ words header

buildToken str delim = takeWhile (/= delim) str

splitStr [] delim = []
splitStr str delim = 
	let token = (buildToken str delim)
	in token : (splitStr (drop ((length token) + 1) str) delim)
