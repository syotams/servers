<?php

include_once './SocketConnection.php';

$address = '127.0.0.1';
$service_port = 4000;

$request = processRequest();

$startTime = microtime(true);
/* Create a TCP/IP socket. */
$socket = new SocketConnection($address, $service_port);
$socket->createConnection();

$socket->write($request);

//echo $socket->read();

//$socket->write("QUIT");

echo "\r\n" . (microtime(true) - $startTime) . "\r\n";

$socket->close();

function processRequest() {
	$date = date("Y-m-d H:i:s");

	$command = 'PUBLISH';
	$channel = 'default';
	$event = '';

	//die("argc = ". $argc);

	if($argc == 2) {
		$event .= $argv[1];
	}
	else if($argc > 2) {
		$command = $argv[1];
		$channel = $argv[2];
	}
	if($argc > 3) {
		for($i=3; $i<count($argv); $i++) {
			$event .= $argv[$i];
		}
	}

	$request = "{$command} {$channel} " . $event;
	echo $request;

	return request;
}