#include <sys/types.h>
#include <errno.h>
#include <fcntl.h>
#include <stdio.h>
#include <stdlib.h>
#include <signal.h>
#include <string.h>
#include <termios.h>
#include <unistd.h>
#include <pthread.h>
#include <time.h>
#include "arduino.h"
#include "calculator.h"

int fd;  // arduino file
char buf[100];
char buf2[1025];
char buf3[1025];
pthread_mutex_t* buffer_lock;
pthread_mutex_t* fd_lock;
// 1 when arduino is on, 0 when arduino is paused
int receive_flag;
// 1 when arduino is blinking, 0 when arduino is not
int help_flag;
// Celsius is 1, Fahrenheit is 0
int isC;
// is 1 when Arduino is disconnected
int arduino_error_flag;
double cur_T;
clock_t last;

// Initialize Arduino
int init_arduino(){
    fd = open("/dev/tty.usbmodem1421", O_RDWR);
    if(fd == -1) {
      arduino_error_flag = 1;
      return 0;
    }
    struct termios options;
    tcgetattr(fd,&options);
    cfsetispeed(&options, 9600);
    cfsetospeed(&options, 9600);
    tcsetattr(fd,TCSANOW, &options);
    buffer_lock = (pthread_mutex_t*) malloc (sizeof(pthread_mutex_t));
    fd_lock = (pthread_mutex_t*) malloc (sizeof(pthread_mutex_t));
    pthread_mutex_init(buffer_lock, NULL);
    pthread_mutex_init(fd_lock, NULL);
    receive_flag = 1;
    help_flag = 0;
    isC = 1;
    arduino_error_flag = 0;
    return 1;
}

// The scecond thread that keep reading input from Arduino
void* receive_arduino(void* not_used) {
  int last_bytes;
  pthread_mutex_lock(buffer_lock);
  int local_flag  = receive_flag;
  pthread_mutex_unlock(buffer_lock);

  while(1) {
    if(local_flag == 1){
      pthread_mutex_lock(fd_lock);
      int bytes_read = read(fd, buf, 100);
      pthread_mutex_unlock(fd_lock);
      if (bytes_read == last_bytes) {
        if ((((double)(clock() - last)) / CLOCKS_PER_SEC) > 2 && init_arduino() == 0) {
          // printf("Arduino error detected\n");
          arduino_error_flag = 1;
        }
      }
      else {
        last = clock();
      }
      if(bytes_read > 0) {
        strncat(buf2, buf, bytes_read);
        if(buf[bytes_read - 1] == '\n') {
          pthread_mutex_lock(buffer_lock);
          strcpy(buf3, buf2);
          printf("%s\n", buf3);
          pthread_mutex_unlock(buffer_lock);
          strcpy(buf2, "");
          cur_T = get_temp_double();
          update_recent(cur_T);
        }
      }
      last_bytes = bytes_read;
    }
    pthread_mutex_lock(buffer_lock);
    local_flag  = receive_flag;
    pthread_mutex_unlock(buffer_lock);
  }
  pthread_exit(NULL);
}

// Send signal to Arduino
void send_arduino(char signal) {
    // Tell the Arduino to pause
    if(signal == 'p'){
        pthread_mutex_lock(buffer_lock);
        receive_flag = 0;
        pthread_mutex_unlock(buffer_lock);
    // Tell the Arduino to resume
    }else if(signal == 'r'){
        pthread_mutex_lock(buffer_lock);
        receive_flag = 1;
        pthread_mutex_unlock(buffer_lock);
        last = clock();
    // Tell the Arduino to switch between Celsuis and Fahrenheit
    }else if(signal == 'c'){
        if(isC){
            isC = 0;
        }else{
            isC = 1;
        }
    // Send SOS signal to Arduino
    }else if(signal == 's'){
      help_flag = 1 - help_flag;
    }
    pthread_mutex_lock(fd_lock);
    int bytes_written = write(fd, &signal, 1);
    pthread_mutex_unlock(fd_lock);
}

// Close Arduino
void close_arduino(){
    close(fd);
    pthread_mutex_destroy(buffer_lock);
    pthread_mutex_destroy(fd_lock);
}

// Get current temperature
int get_temp(char *result){
    double temp = get_temp_double();
    double_to_string(result, temp);
    if(isC == 1){
     strcat(result, "C");
    }else{
     double f = temp * 9 / 5 + 32;
     if(sprintf(result, "%.1f",f) == 0){
       strcpy(result, "No message");
     }
     strcat(result, "F");
    }
    return 1;
}

// Extract most recent temperature reading from Arduino
double get_temp_double() {
  char* token;
  char local[1024];

  pthread_mutex_lock(buffer_lock);
  strcpy(local, buf3);
  pthread_mutex_unlock(buffer_lock);
  token = strtok(local, " ");
  token = strtok(NULL, " ");
  token = strtok(NULL, " ");
  token = strtok(NULL, " ");
  if(token != NULL){
    double c = atof(token);
    double recent_avg = get_recent_avg();
    if (c > recent_avg + 10 || c < recent_avg - 10) {
      sleep(1);
      return get_temp_double();
    }
    else return c;
  }
  else {
    sleep(1);
    return get_temp_double();
  }
}

// Convert temperature from double to string
int double_to_string(char *result, double number) {
  double local;
  local = number;
  sprintf(result, "%.1f", local);
  return 1;
}

// Collect average, lowest and highest temperature
int get_data(char *result) {
  char temp[128];
  strcpy(result, "{\n\"data\": \"Average: ");
  if (isC == 1) {
    double_to_string(temp, get_avg());
    strcat(temp, "C");
  }
  else {
    double c = get_avg();
    double f = c * 9 / 5 + 32;
    double_to_string(temp, f);
    strcat(temp, "F");
  }
  strcat(result, temp);
  strcpy(temp, "");
  strcat(result, "#low: ");
  if (isC == 1) {
    double_to_string(temp, get_low());
    strcat(temp, "C");
  }
  else {
    double c = get_low();
    double f = c * 9 / 5 + 32;
    double_to_string(temp, f);
    strcat(temp, "F");
  }
  strcat(result, temp);
  strcpy(temp, "");
  strcat(result, "#high: ");
  if (isC == 1) {
    double_to_string(temp, get_high());
    strcat(temp, "C");
  }
  else {
    double c = get_high();
    double f = c * 9 / 5 + 32;
    double_to_string(temp, f);
    strcat(temp, "F");
  }
  strcat(result, temp);
  strcpy(temp, "");
  return 1;
}

// Get the trend of temperature.
// Return: 0 is steady, 1 is increasing, -1 is decreasing
int get_trend(char *result) {
  int trend = calculate_trend();
  if (trend == 1) strcat(result, "increasing");
  else if (trend == 0) strcat(result, "steady");
  else strcat(result, "decreasing");
  return 1;
}

// return 1 if Arduino is disconnected, 0 the otherwise
int arduino_error() {
  return arduino_error_flag;
}

// return 1 if Arduino is paused, 0 the otherwise
int is_paused() {
  return 1 - receive_flag;
}

// return 1 if Arduino is flashing SOS, 0 the otherwise
int in_help() {
  return help_flag;
}

//int main(int argc, char* args[]){
//    init_arduino();
//    pthread_t thread_id;
//    pthread_create(&thread_id, NULL, &receive_arduino, NULL);
//    pthread_join(thread_id, NULL);
//    return 1;
//}
