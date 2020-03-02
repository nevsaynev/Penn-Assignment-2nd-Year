// typedef struct Recent_data recent_data;
// struct Recent_data {
//   int count;
//   double low;
//   double high;
//   double average;
// };

typedef struct Bucket bucket;
struct Bucket {
  double low;  
  double high;  
  double avg;
};

int update_recent(double);
void reset_recent();
void update_global();
double get_low_except();
double get_high_except();
int calculate_trend();
double round_digit(double, int);
double get_recent_avg();
double get_low();
double get_high();
double get_avg();