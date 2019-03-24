<?php

class SocketConnection {

	private $socket;


	public function __construct($address, $port) {
		$this->address = $address;
		$this->port = $port;
	}

	public function createConnection() {
		$this->socket = socket_create(AF_INET, SOCK_STREAM, SOL_TCP);

		if ($this->socket === false) {
		    echo "socket_create() failed: reason: " . socket_strerror(socket_last_error()) . "\n";
		    return;
		} 

		echo "Attempting to connect to {$this->address} on port {$this->port}...\n";
		$result = socket_connect($this->socket, $this->address, $this->port);
		
		if ($result === false) {
		    echo "socket_connect() failed.\nReason: ($result) " . socket_strerror(socket_last_error($this->socket)) . "\n";
		}
		else {
			echo "Connection established successfully\n";
		}

		return $result;
	}

	public function write($data) {
		$data .= "\r\n";
    	return socket_write($this->socket, $data, strlen($data));
	}

	public function read() {
		return socket_read($this->socket, 2048, PHP_NORMAL_READ);
	}

	public function close() {
		socket_close($this->socket);
	}
}