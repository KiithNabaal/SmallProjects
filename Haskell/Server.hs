-- Modified server from realworldhaskell (http://book.realworldhaskell.org/read/sockets-and-syslog.html)

module Server
(
	serveLog
) where

import Data.Bits
import Network.Socket
import Network.BSD
import Data.List
import Control.Concurrent
import Control.Concurrent.MVar
import System.IO
import Matrix

type HandlerFunc = Handle -> String -> IO()

serveLog :: String -> IO()
serveLog port = withSocketsDo $ 
	do
		addrinfos <- getAddrInfo (Just (defaultHints {addrFlags = [AI_PASSIVE]})) Nothing (Just port)
		let serverAddr = head addrinfos
		sock <- socket(addrFamily serverAddr) Stream defaultProtocol
		bindSocket sock (addrAddress serverAddr)
		listen sock 5
		lock <- newMVar()
		procRequests lock sock
		where
			procRequests :: MVar() -> Socket -> IO()
			procRequests lock mastersock =
				do 
					(connsock, clientAddr) <- accept mastersock
					forkIO $ procMessages lock connsock clientAddr
					procRequests lock mastersock
					
			procMessages :: MVar() -> Socket -> SockAddr -> IO()
			procMessages lock connsock clientAddr =
				do connhdl <- socketToHandle connsock ReadWriteMode
				   hSetBuffering connhdl LineBuffering
				   req <- hGetContents connhdl
				   handleRequest connhdl req
				  	
toJSON :: String -> String			
toJSON val = "{ result: " ++ val ++ " }"
			
handleRequest :: HandlerFunc
handleRequest conn httpReq
	| ((head $ words httpReq) == "GET") = (hPutStr conn ("HTTP/1.1 200 Ok\r\n\r\n" ++ httpReq ++ "\r\n")) >> (hClose conn)
	| otherwise = (hPutStr conn "HTTP/1.1 404\r\n\r\n<html>Request does not map</html>\r\n") >> (hClose conn)
