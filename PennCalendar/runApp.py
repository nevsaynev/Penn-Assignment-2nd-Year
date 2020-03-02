#from  data_seasGen_2 import*
#from careerServiceInfoExtract import*
import seas
import data_seasGen_2
import careerServiceInfoExtract
from CalenderAPI import*
from search_2 import*
def runApp():
    
    get_txt("\"info@seas.upenn.edu\"", 'seas.txt')
    f = open("seas.txt")
    #f = open("seasCalendar.txt")
    psg = f.readlines()
    for item in seas.lineProcessor(psg):
        createCalendar(item)
        #print item
        
    get_txt("\"sgwak@seas.upenn.edu\"", 'seasGen.txt')
    f = open("seasGen.txt")
    psg = f.readlines()
    for item in data_seasGen_2.lineProcessor(psg):
        createCalendar(item)
        #print item
    
    get_txt("\"Career News for Master's Students\"", 'careerS.txt')
    f = open("careerS.txt")
    psg = f.readlines()
    for item in careerServiceInfoExtract.lineProcessor(psg):
        createCalendar(item)
        #print item
    

