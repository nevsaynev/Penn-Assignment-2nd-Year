import re
from helper import*

DIVIDE = '------'

def lineProcessor(lineList):
    '''read line by line and return the turple that is needed for calendarAPI'''
    eventsList = []
    lastLocation =-1
    content = []
    for i in range(0, len(lineList)):
        m = re.search(DIVIDE, lineList[i])
        
        if m and lastLocation ==-1:
            date  = lineList[i+2]
            #print date
            clock = lineList[i+3]
            #print clock
            MM,DD = dateExtract(date)
            start_HH, end_HH,start_MM, end_MM= clockExtract(clock)
            #print MM,DD,start_HH, end_HH,start_MM, end_MM
            title = re.split('[:-]', lineList[i+5])[0].strip()
            #print title
            
            lastLocation = i+5
            
        
        elif m and lastLocation !=-1:
            
            
            content = lineList[lastLocation:i-1]
            #print content
            location = lineList[i-3].strip()
            #print location
            eventsList.append((title,  location,MM,DD,start_HH, end_HH,start_MM, end_MM, content))
            date  = lineList[i+2]
            #print date
            clock = lineList[i+3]
            #print clock
            MM,DD = dateExtract(date)
            start_HH, end_HH,start_MM, end_MM= clockExtract(clock)
            #print start_HH, end_HH,start_MM, end_MM
            title = re.split('[:-]', lineList[i+5])[0].strip()
            #print title
            
            lastLocation = i+5

    content = lineList[lastLocation:-6]
    location = lineList[i-7].strip()

    eventsList.append((title, location,MM,DD,start_HH, end_HH,start_MM, end_MM, content))

    return eventsList
 
def dateExtract(date):
    if len(date.split(','))>1:
        day = date.split(',')[1].strip()
        MM = TwoDigit(charToNum(day.split(' ')[0]))
        DD = TwoDigit(day.split(' ')[1].strip())
        return MM, DD
    else:
        return '' 


def clockExtract(clock):
    if clock.split('to')>1:
        flag_start = 0
        flag_end = 0
        start_time = clock.split('to')[0].strip()
        start_time =start_time.replace('AM','')
        #print start_time
        
        end_time = clock.split('to')[1].strip()
        end_time =end_time.replace('AM','')
        #print end_time
        
        if 'PM' in start_time:
            start_time = start_time.replace('PM','')
            start_HH = TwoDigit(TwlToTwen(start_time.split(':')[0]))
        else:
            start_HH = TwoDigit(start_time.split(':')[0].strip())              
           
            
        if 'PM' in end_time:
            end_time =end_time.replace('PM','')
            end_HH = TwoDigit(TwlToTwen(end_time.split(':')[0]))
        else:
            end_HH = TwoDigit(end_time.split(':')[0].strip())              


        start_MM = TwoDigit(start_time.split(':')[1].strip())
        end_MM = TwoDigit(end_time.split(':')[1].strip())
        return start_HH, end_HH,start_MM, end_MM
    else:
        return ''











