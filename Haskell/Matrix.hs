module Matrix
( 
   multMatrix
 , det
) where

import Data.List

--[multSum row mr | mr <- matrix]

--multSum (a:az) (b:bs) = if length az > 0 then (a * b) + multSum az bs else (a * b)
--computeRow row (r:rows) = if length rows > 0 then (multSum row r) : (computeRow row rows) else (multSum row r) : []
--multMatrix' (r:rows) m2 = if length rows > 0 then (computeRow r (transpose m2)) : multMatrix' rows m2 else (computeRow r (transpose m2)) : []
--multMatrix m1 m2 = if (length (head m1) == length m2) then multMatrix' m1 m2 else [[]]

multSum (a:az) (b:bs) = if length az > 0 then (a * b) + multSum az bs else (a * b)
computeRow row rows = [multSum r row | r <- rows]
multMatrix' rows m2 = [computeRow r  (transpose m2) | r <- rows]
multMatrix m1 m2 = if (length (head m1) == length m2) then multMatrix' m1 m2 else [[]]

min_det m = (m!!0!!0) * (m!!1!!1) - (m!!0!!1) * (m!!1!!0)

negateAlternate (x:xs) i
	| length xs == 0 = (((-1) ^i) * x) : []
	| otherwise = (((-1) ^i) * x) : (negateAlternate xs (i+1))

getSubMatrix (r:rs) i 
	| (length rs) == 0 = ((take i r) ++ (drop (i+1) r)) : []
	| otherwise = (((take i r) ++ (drop (i+1) r)) : (getSubMatrix rs i))

det m
	| length m == 2 = min_det m
	| otherwise = foldl (+) 0 [a * det (getSubMatrix (tail m) b) | (a,b) <- zip (negateAlternate (head m) 2) ([0..(length ((negateAlternate (head m) 2)))])]
