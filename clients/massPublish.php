<?php

include_once './SocketConnection.php';

$address = '127.0.0.1';
$service_port = 4000;

$startTime = microtime(true);

$total = $argv[1] ? intval($argv[1]) : 1;
$sockets = [];

/* Create a TCP/IP socket. */
for($i = 0; $i < $total; $i++) {
	$socket = new SocketConnection($address, $service_port);	
	$sockets[] = $socket;
}

for($i = 0; $i < $total; $i++) {
	$sockets[$i]->createConnection();
	$sockets[$i]->write("PUBLISH default event-{$i}");	
}

foreach ($sockets as $socket) {
	$socket->close();
}

echo "\r\n" . (microtime(true) - $startTime) . "\r\n";

$socket->close();