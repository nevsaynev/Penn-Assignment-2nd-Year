import re
import string
from helper import *

# title, location, month, day, starthour, endhour,startmin, endmin
def lineProcessor(lineList):
    eventsList = []
    lastLocation =-1
    content = []
    for i in range(0, len(lineList)):
        m = re.search("Location:.*", lineList[i])
        
        if m and lastLocation ==-1:
            location  = m.group(0).split(':')[1].strip()
            clock = lineList[i - 1].replace("Time:", "").strip()
            title = lineList[i -3].strip()
            date = lineList[i - 2].replace("Date:","").strip()

            time1 = dateExtract(date)
            MM,DD = time1
            MM = TwoDigit(MM)
            DD = TwoDigit(DD)
            #print time1
            time2 = clockExtract(clock)
            start_HH, end_HH,start_MM, end_MM = time2
            start_HH = TwoDigit(start_HH)
            end_HH = TwoDigit(end_HH)
            start_MM = TwoDigit(start_MM)
            end_MM = TwoDigit(end_MM)
            #time = timeExtractSEASGEN(rawTime)

            lastLocation = i + 1
            
        
        elif m and lastLocation !=-1:
            
            
            content = lineList[lastLocation:i-4]
            lastLocation =i + 1

            #print title
            #print ('\n')
            #print date
            #print ('\n')
            #print clock
            #print ('\n')
            #print location
            #print ('\n')
            #print content
            #print ('\n')
           
            #eventsList.append((title, date, clock, location, content))
            #eventsList.append((title, time1,time2, location, content))
            eventsList.append((title, location,MM,DD,start_HH, end_HH,start_MM, end_MM,  content))

            location  = m.group(0).split(':')[1].strip()
            clock = lineList[i - 1].replace("Time:", "").strip()
            title = lineList[i -3].strip()
            date = lineList[i - 2].replace("Date:","").strip()
            time1 = dateExtract(date)
            MM,DD = time1
            MM = TwoDigit(MM)
            DD = TwoDigit(DD)
            
            #print time1
            time2 = clockExtract(clock)
            start_HH, end_HH,start_MM, end_MM = time2
            start_HH = TwoDigit(start_HH)
            end_HH = TwoDigit(end_HH)
            start_MM = TwoDigit(start_MM)
            end_MM = TwoDigit(end_MM)
            #print time2
            #lastLocation =i+3

            
            
    
            #time = timeExtract(rawTime)

            

    content = lineList[lastLocation:]
    
   
    #eventsList.append((title, time1, time2, location, content))
    eventsList.append((title, location,MM,DD,start_HH, end_HH,start_MM, end_MM,  content))
    #print eventsList
            
    return eventsList
 
       

##    content = lineList[lastLocation:]
##    eventsList.append((title, time, locatison, content))
    
            
def dateExtract(date):
    if re.search("th", date):
       day = date.split(',')[1].strip() 
       MM = charToNum(day.split(' ')[0])
       MM = TwoDigit(MM)
       DD = day.split(' ')[1].replace("th", "").strip()
       DD = TwoDigit(DD)
       return MM, DD 
    MM = charToNum('January')
    DD = charToNum('January')
    MM = TwoDigit(MM)
    DD = TwoDigit(DD)
    return MM, DD 


def clockExtract(clock):
  if re.search(":*-*:", clock):  
    flag_start = 0
    flag_end = 0
    start_time = clock.split('-')[0] 
    end_time = clock.split('-')[1] 
    if start_time.find('pm'): 
        start_time = start_time.replace('pm','') 
        flag_start = 1
    else: 
        start_time =start_time.replace('am','') 
  
    if end_time.find('PM'): 
        end_time =end_time.replace('pm','') 
        flag_end = 1
    else: 
        end_time =end_time.replace('am','') 
      
    if(flag_start ==1): 
        start_HH = TwlToTwen(start_time.split(':')[0]) 
          
    start_MM = start_time.split(':')[1].strip() 
      
    if(flag_end ==1): 
        end_HH = TwlToTwen(end_time.split(':')[0]) 
    end_MM = end_time.split(':')[1].strip()
    #if re.search("*am*", start_MM):start_MM = start_MM.replace("am", "").strip()
    if "am" in start_MM:
        start_MM = start_MM.replace("am", "").strip()
        start_HH = str(int(start_HH) - 12) 
    return start_HH, end_HH,start_MM, end_MM
  start_HH = '00'
  end_HH = '00'
  start_MM = '00'
  end_MM = '00'
  return start_HH, end_HH,start_MM, end_MM

##def charToNum(x): 
##    ''' Change month format from char to digit'''
##    #Strips leading/trailing white space form x 
##    x= x.strip() 
##    return { 
##        'January': 1, 
##        'February': 2, 
##        'March': 3, 
##        'April': 4, 
##        'May': 5, 
##        'June': 6, 
##        'July': 7, 
##        'August': 8, 
##        'September': 9, 
##        'October': 10, 
##        'Novermber': 11, 
##        'December': 12, 
##        }.get(x, 0) 
  

##def TwlToTwen(x): 
##    ''' Change time format from 12hour to 24 hour'''
##    #Strips leading/trailing white space form x 
##    x=x.strip() 
##    return{ 
##        '1': 13, 
##        '2': 14, 
##        '3': 15, 
##        '4': 16, 
##        '5': 17, 
##        '6': 18, 
##        '7': 19, 
##        '8': 20, 
##        '9': 21, 
##        '10': 22, 
##        '11': 23, 
##        }.get(x,12) 



#def main():
    #f = open("gse.txt")
    #psg = f.readlines()
    #print lineProcessorSEASGEN(psg)
    #lineProcessorSEASGEN(psg)
    #for i in range(0, len(eventsList)):
         #print eventsList[i]
    
    

    
if __name__ == "__main__":
    main()






