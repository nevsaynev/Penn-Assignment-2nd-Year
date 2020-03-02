#include <stdio.h>
#include <math.h>
#include "calculator.h"

#define BUCKET_NUMBER 288

// temperature data, refresh every 5 minutes
int recent_count = 0;
double recent_low = 3000;
double recent_high = -300;
double recent_avg = 25;

// store temperature info in buckets of 5 minutes in the past 24 hours
bucket slot[BUCKET_NUMBER];
int bucket_count = 0;

// ultimate data of past 24 hours ready to return
double avg = 0;
double low = 3000;
double high = -300;

// update recent data and (if necessary) bucket info when new current temperature arrives
int update_recent(double cur_T) {
	cur_T = round_digit(cur_T, 1);
	if (cur_T < recent_low) {
		// printf("T is lower than recent_low.\n");
		recent_low = cur_T;
	}
	if (cur_T > recent_high) {
		// printf("T is higher than recent_high.\n");
		recent_high = cur_T;
	}
	recent_avg = ((recent_avg * recent_count) + cur_T) / (recent_count + 1);
	printf("cur_T: %.2f\n", cur_T);
	printf("low: %.2f\n", recent_low);
	printf("high: %.2f\n", recent_high);
	printf("average: %.2f\n", recent_avg);
	recent_count++;
	if (bucket_count == 0) {
		printf("updating global data.\n");
		low = round_digit(recent_low, 1);
		high = round_digit(recent_high, 1);
		avg = round_digit(recent_avg, 1);
	}
	if (recent_count == 3600 * 24 / BUCKET_NUMBER) {
		if (bucket_count != 0) update_global();
		slot[bucket_count % BUCKET_NUMBER].low = recent_low;
		slot[bucket_count % BUCKET_NUMBER].high = recent_high;
		slot[bucket_count % BUCKET_NUMBER].avg = recent_avg;
		bucket_count++;
		reset_recent();
	}
	return 1;
}

// refresh recent data to empty
void reset_recent() {
	recent_count = 0;
	recent_low = 3000;
	recent_high = -300;
	recent_avg = 0;
}

// update global low, high and average temperature in past 24 hours
void update_global() {
	int index = bucket_count % BUCKET_NUMBER;
	if (bucket_count < BUCKET_NUMBER) {
		if (recent_low < low) low = recent_low;
		if (recent_high > high) high = recent_high;
		avg = ((avg * bucket_count) + recent_avg) / (bucket_count + 1);
	}
	else {
		if (recent_low <= low) low = recent_low;
		else {
			if (slot[index].low == low) low = get_low_except(index);
			if (recent_low < low) low = recent_low;
		}
		if (recent_high >= high) high = recent_high;
		else {
			if (slot[index].high == high) high = get_high_except(index);
			if (recent_high > high) high = recent_high;
		}
		avg = (avg * BUCKET_NUMBER - slot[index].avg + recent_avg) / BUCKET_NUMBER;
	}
	high = round_digit(high, 1);
	low = round_digit(low, 1);
	avg = round_digit(avg, 1);
}

// return lowest value among all buckets except the one that is about to be kicked out
double get_low_except(int except) {
	double new_low = 3000;
	int i;
	for (i = 0; i < BUCKET_NUMBER; i++) {
		if (i != except && slot[i].low < new_low) new_low = slot[i].low;
	}
	return new_low;
}

// return highest value among all buckets except the one that is about to be kicked out
double get_high_except(int except) {
	double new_high = -300;
	int i;
	for (i = 0; i < BUCKET_NUMBER; i++) {
		if (i != except && slot[i].high > new_high) new_high = slot[i].high;
	}
	return new_high;
}

// get temperature trend compared to an hour ago
// return 0 if steady, 1 if increasing, -1 if decreasing
int calculate_trend() {
	int target;
	if (bucket_count == 0) {
		if (recent_avg > recent_low + 1) return 1;
		else if (recent_avg < recent_high - 1) return -1;
		else return 0;
	}
	if (bucket_count < 12) target = 0;
	else target = (bucket_count - 12) % BUCKET_NUMBER;
	if (recent_avg > slot[target].avg + 1) return 1;
	else if (recent_avg < slot[target].avg - 1) return -1;
	else return 0;
}

// round the number according to customized number of decimals
double round_digit(double number, int decimal) {
	int i;
	int times = 1;
	for (i = 0; i < decimal; i++) times = times * 10;
	return floorf(number * times + 0.5) / times;
}

// return the average temperature in the past 5 minutes
double get_recent_avg() { return recent_avg; }

// return lowest temperature in the past 24 hours
double get_low() { return low; }

// return highest temperature in the past 24 hours
double get_high() { return high; }

// return average temperature in the past 24 hours
double get_avg() { return avg; }