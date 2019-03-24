<?php

include_once './SocketConnection.php';

$address = '127.0.0.1';
$service_port = 8000;

$startTime = microtime(true);
/* Create a TCP/IP socket. */
$socket = new SocketConnection($address, $service_port);
$socket->createConnection();
$socket->write("HELLO");

echo $socket->read();

$command = $argv[1];

switch ($command) {
	case 'PUT':
			$socket->write("PUT {$argv[2]} {$argv[3]}");
		break;
	
	default:
			$socket->write("GET {$argv[2]}");
			echo $socket->read(2048);
		break;
}

$socket->write("QUIT");

echo "\r\n" . (microtime(true) - $startTime) . "\r\n";

$socket->close();