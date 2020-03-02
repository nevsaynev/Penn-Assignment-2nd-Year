all: server

server: arduino
	cc -o server server.c arduino.o calculator.o -lpthread
	
arduino: calculator
	cc -c arduino.c
calculator:
	cc -c calculator.c

clean:
	rm *.o server
