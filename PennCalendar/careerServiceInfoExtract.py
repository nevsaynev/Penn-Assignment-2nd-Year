import re
import string
from helper import*

def lineProcessor(lineList):
    eventsList = [] # list of tuple
    lastLocation =-1 # start point of content
    content = []
    for i in range(0, len(lineList)):
        # delete stars
        tempLine = lineList[i].replace("*", "").strip()

        # match the first word
        m1 = re.search("Monday,.*", lineList[i])
        m2 = re.search("Tuesday,.*", lineList[i])
        m3 = re.search("Wednesday,.*", lineList[i])
        m4 = re.search("Thursday,.*", lineList[i])
        m5 = re.search("Friday,.*", lineList[i])
        m6 = re.search("Saturday,.*", lineList[i])
        m7 = re.search("Sunday,.*", lineList[i])

        # first time match
        if (m1 or m2 or m3 or m4 or m5 or m6 or m7) and lastLocation == -1:
            timeList  = tempLine.split(',')

            # find the location of weekday
            weekdayLocation = 99
            for j in range (0, len(timeList)):
                if timeList[j].strip() == 'Monday' or timeList[j].strip() == 'Tuesday' or timeList[j].strip() == 'Wednesday'\
                   or timeList[j].strip() == 'Thursday' or timeList[j].strip() == 'Friday' or timeList[j].strip() == 'Saturday'\
                   or timeList[j].strip() == 'Sunday':
                    weekdayLocation = j
                    break
            
            # if this line is compliance to pattern 1
            if len(timeList) >= 4 and len(timeList) < 6 and weekdayLocation == 0:
                # title is in the last line
                title = lineList[i - 2].replace("*", "").strip()
                # location is in the present line
                locationList = timeList[weekdayLocation + 3:]
                location = locationExtract(locationList)
                # time is in the present line
                MM, DD, start_HH, end_HH,start_MM, end_MM = timeExtract(timeList[1 : weekdayLocation + 3])
                lastLocation = i

            '''
            ################## ? pattern 2
            # if this line is compliance to pattern 2
            if len(timeList) >= 5 and len(timeList) < 8 and weekdayLocation == 1:
                # title is in the last line
                title = timeList[0]
                # location is in the present line
                locationList = timeList[weekdayLocation + 4:]
                location = locationExtract(locationList)
            '''

        elif (m1 or m2 or m3 or m4 or m5 or m6 or m7) and lastLocation != -1:
            timeList  = tempLine.split(',')
            
            content = lineList[lastLocation + 1 : i - 2]
            eventsList.append((title, location, TwoDigit(MM), TwoDigit(DD), TwoDigit(start_HH), TwoDigit(end_HH),TwoDigit(start_MM), TwoDigit(end_MM), content))
            
            # find the location of weekday
            weekdayLocation = 99
            for j in range (0, len(timeList)):
                if timeList[j].strip() == 'Monday' or timeList[j].strip() == 'Tuesday' or timeList[j].strip() == 'Wednesday'\
                   or timeList[j].strip() == 'Thursday' or timeList[j].strip() == 'Friday' or timeList[j].strip() == 'Saturday'\
                   or timeList[j].strip() == 'Sunday':
                    weekdayLocation = j
                    break
            
            # if this line is compliance to pattern 1
            if len(timeList) >= 4 and len(timeList) < 6 and weekdayLocation == 0:
                # title is in the last line
                title = lineList[i - 2].replace("*", "").strip()
                # location is in the present line
                locationList = timeList[weekdayLocation + 3:]
                location = locationExtract(locationList)
                # time is in the present line
                MM, DD, start_HH, end_HH,start_MM, end_MM = timeExtract(timeList[1 : weekdayLocation + 3])
                lastLocation = i

    ########## ? when to stop?
    #content = lineList[lastLocation:]
    #eventsList.append((title, time, location, content))
    #print eventsList
    return eventsList
    
def locationExtract(locationList):
    tempStr = ''
    for i in range(0, len(locationList)):
        tempStr += locationList[i]
    return tempStr.strip()

def timeExtract(timeSplit):
    '''extract MM DD startHH startMM endHH endMM from time'''
    flag_start = 0
    flag_end = 0
    #print time.split(',')
    #timeSplit = time.split(',')
    #print timeSplit
    date = timeSplit[0]
    clock = timeSplit[1].replace('Noon', '12:00').strip()
    
    month = date.strip().split()[0]
    MM = charToNum(month)
    #print MM
    DD = date.strip().split()[1]
    #print DD

    if '-' in clock:
        start_time = clock.split('-')[0].strip()
        end_time = clock.split('-')[1].strip()
    else:
        tmpClock = clock.strip().split()
        for i in range(0, len(tmpClock)):
            if ":" in tmpClock[i]:
                start_time = tmpClock[i]
                end_time = tmpClock[i]
                
    #print start_time
    #print end_time

    if ('p.m.' in end_time) and not('a.m.' in start_time):
        start_HH = TwlToTwen(start_time.split(':')[0].strip())
        start_MM = start_time.split(':')[1].strip()
        end_HH = TwlToTwen(end_time.split(':')[0].strip())
        end_MM = end_time.replace('p.m.', '').replace('EST', '').split(':')[1].strip()
    elif ('p.m.' in end_time) and ('a.m.' in start_time):
        start_HH = start_time.split(':')[0].strip()
        start_MM = start_time.replace('a.m.', '').split(':')[1].strip()
        end_HH = TwlToTwen(end_time.split(':')[0].strip())
        end_MM = end_time.replace('p.m.', '').replace('EST', '').split(':')[1].strip()
    else:
        start_HH = start_time.split(':')[0].strip()
        start_MM = start_time.split(':')[1].strip()
        end_HH = end_time.split(':')[0].strip()
        end_MM = end_time.replace('a.m.', '').split(':')[1].strip()

    '''
    if start_time.find('p.m.'):
        start_time = start_time.replace('p.m.','')
        flag_start = 1
    else:
        start_time =start_time.replace('a.m.' ,'')

    if end_time.find('p.m.'):
        end_time =end_time.replace('p.m.','')
        flag_end = 1
    else:
        end_time =end_time.replace('a.m.' ,'')
    
    if(flag_start ==1):
        start_HH = TwlToTwen(start_time.split('.')[0])

    start_MM = start_time.strip().split(':')[1]
    
    if(flag_end ==1):
        end_HH = TwlToTwen(end_time.split('.')[0])
    end_MM = end_time.strip().split(':')[1]
    '''
    return MM, DD, start_HH, end_HH,start_MM, end_MM

def main():
    f = open("career.txt")
    psg = f.readlines()
    print lineProcessor(psg)

if __name__ == "__main__":
    main()




