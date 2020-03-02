/* 
This code primarily comes from 
http://www.prasannatech.net/2008/07/socket-programming-tutorial.html
and
http://www.binarii.com/files/papers/c_sockets.txt
 */

#include <sys/types.h>
#include <sys/socket.h>
#include <netinet/in.h>
#include <arpa/inet.h>
#include <stdio.h>
#include <stdlib.h>
#include <unistd.h>
#include <errno.h>
#include <string.h>

#include <fcntl.h>
#include <signal.h>
#include <termios.h>
#include <pthread.h>
#include <time.h>
#include "server.h"
#include "arduino.h"

clock_t begin,end;
clock_t very_beginning;
double standby_time = 0;
double running_time;
int stand_by =0;

int start_server(int PORT_NUMBER)
{

  // structs to represent the server and client
  struct sockaddr_in server_addr,client_addr;    
  
  int sock; // socket descriptor

  // 1. socket: creates a socket descriptor that you later use to make other system calls
  if ((sock = socket(AF_INET, SOCK_STREAM, 0)) == -1) {
    perror("Socket");
    exit(1);
  }
  int temp;
  if (setsockopt(sock,SOL_SOCKET,SO_REUSEADDR,&temp,sizeof(int)) == -1) {
    perror("Setsockopt");
    exit(1);
  }

  // configure the server
  server_addr.sin_port = htons(PORT_NUMBER); // specify port number
  server_addr.sin_family = AF_INET;         
  server_addr.sin_addr.s_addr = INADDR_ANY; 
  bzero(&(server_addr.sin_zero),8); 
  
  // 2. bind: use the socket and associate it with the port number
  if (bind(sock, (struct sockaddr *)&server_addr, sizeof(struct sockaddr)) == -1) {
    perror("Unable to bind");
    exit(1);
  }

  // 3. listen: indicates that we want to listn to the port to which we bound;
  // second arg is number of allowed connections
  if (listen(sock, 5) == -1) {
    perror("Listen");
    exit(1);
  }
      
  // once you get here, the server is set up and about to start listening
  printf("\nServer configured to listen on port %d\n", PORT_NUMBER);
  fflush(stdout);
    while (1) {
    // 4. accept: wait here until we get a connection on that port
    
    int sin_size = sizeof(struct sockaddr_in);
    int fd = accept(sock, (struct sockaddr *)&client_addr,(socklen_t *)&sin_size);
    printf("Server got a connection from (%s, %d)\n",
      inet_ntoa(client_addr.sin_addr),ntohs(client_addr.sin_port));

    // buffer to read data into
    char request[1024];
    
    // 5. recv: read incoming message into buffer
    int bytes_received = recv(fd,request,1024,0);
    // null-terminate the string
    request[bytes_received] = '\0';
    printf("Here comes the message:\n");
    printf("%s\n", request);
    
    char * token1;
    token1 = strtok(request, " ");
    token1 = strtok(NULL," ");

    // this is the message that we'll send back
    /* it actually looks like this:
      {
         "curT": "T: <temperature>"
      }
    */
    

    char message[256];
    char temp[50];
    printf("token1: %s\n", token1);
    if (arduino_error()) {
      strcpy(message, "{\n\"error\": \"Sensor Error!\"\n}\n");
    } else {
      if (strcmp(token1,"/pause")==0){
          send_arduino('p');
           if(stand_by==0){
            begin = clock();
            running_time = (((double)(clock() - very_beginning)) / CLOCKS_PER_SEC) - standby_time;
            stand_by = 1;
          }
          strcpy(message, "{\n\"pause\": \"Paused\"\n}\n");
      }else if(strcmp(token1,"/resume")==0){
          send_arduino('r');
          if(stand_by==1){
          end = clock();
          stand_by = 0;
          }
          standby_time += (double)(end - begin) / CLOCKS_PER_SEC;
          strcpy(message, "{\n\"resume\": \"Resumed\"\n}\n");
      }else if(strcmp(token1,"/convert")==0){
          send_arduino('c');
          strcpy(message, "{\n\"convert\": \"Converted\"\n}\n");
      }
      else if(strcmp(token1,"/show")==0){
        if (is_paused()) {
          strcpy(message, "{\n\"curT\": \"Please resume the sensor first\"\n}\n");
        } else {
        strcpy(message, "{\n\"curT\": \"T: ");
        get_temp(temp);
        strcat(message, temp);
        strcat(message, "\"\n}\n");
        }
      }
      else if (strcmp(token1,"/data")==0) {
        get_data(message);
        strcat(message, "\"\n}\n");
      }
      else if (strcmp(token1,"/trend")==0) {
        strcpy(message, "{\n\"trend\": \"Trend: ");
        get_trend(message);
        strcat(message, "\"\n}\n");
      }
      else if(strcmp(token1,"/help")==0){
        send_arduino('s');
        if (in_help() == 1) strcpy(message, "{\n\"help\": \"Help sent\"\n}\n");
        else strcpy(message, "{\n\"help\": \"Help cancelled\"\n}\n");
      }
      else if(strcmp(token1,"/time")==0){
        if (stand_by == 0) 
          running_time = (((double)(clock() - very_beginning)) / CLOCKS_PER_SEC) - standby_time;
        strcpy(message, "{\n\"runtime\": \"Time: ");
        char time[128];
        double_to_string(time,running_time);
        strcat(message,time);
        strcat(message, " seconds");
        strcat(message, "\"\n}\n");
      }
    }

    char *reply = (char *)malloc(sizeof(char) * (strlen(message) + 1));
    strcpy(reply, message);
    strcpy(message, "");
    // char *reply = "{\n\"name\": \"cit595\"\n}\n";
    
    // 6. send: send the message over the socket
    // note that the second argument is a char*,
    // and the third is the number of chars
    printf("%s", reply);
    send(fd, reply, strlen(reply), 0);
    free(reply);
    //printf("Server sent message: %s\n", reply);

    // 7. close: close the socket connection
    close(fd);
  }
  close(sock);
  printf("Server closed connection\n");

  return 0;
} 




int main(int argc, char *argv[])
{

  // check the number of arguments
  if (argc != 2)
    {
      printf("\nUsage: server [port_number]\n");
      exit(0);
    }
   pthread_t thread_id;
   // successly initial arduino
   if(init_arduino() == 1){
       pthread_create(&thread_id, NULL, receive_arduino, NULL);
       very_beginning = clock();
   }else{
       printf("Error occured when initializing arduino.");
   }

   int PORT_NUMBER = atoi(argv[1]);
   start_server(PORT_NUMBER);
   pthread_join(thread_id, NULL);

}
