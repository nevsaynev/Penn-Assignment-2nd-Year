try:
  from xml.etree import ElementTree
except ImportError:
  from elementtree import ElementTree
import gdata.calendar.data
import gdata.calendar.client
import gdata.acl.data
import atom.data
import time
from helper import *

def createCalendar(tp):
  lst = list(tp)
  title = lst[0].strip()
  location = lst[1].strip()
  MM = lst[2].strip()
  DD = lst[3].strip()
  start_HH = lst[4].strip()
  end_HH = lst[5].strip()
  start_MM = lst[6].strip()
  end_MM = lst[7].strip()

  content = stringLstToString(lst[8])
  #content = lst[8].strip()
  #content = lst[8]

  
  start_time = '2014-'+MM+'-'+DD+'T'+start_HH+':'+start_MM+':00'
  end_time = '2014-'+MM+'-'+DD+'T'+end_HH+':'+end_MM+':00'

  calendar_client = gdata.calendar.client.CalendarClient(source='yourCo-yourAppName-v1')
  calendar_client.ClientLogin('pennappstest@gmail.com', 'pennapps', calendar_client.source)



  event = gdata.calendar.data.CalendarEventEntry()
  event.title = atom.data.Title(text=title)
  event.content = atom.data.Content(text=content)
  event.where.append(gdata.calendar.data.CalendarWhere(value=location))

     #if start_time is None:
        # Use current time for the start_time and have the event last 1 hour
  #start_time = time.strftime('%Y-%m-%dT%H:%M:%S.000Z', time.gmtime())
  #end_time = time.strftime('%Y-%m-%dT%H:%M:%S.000Z', time.gmtime(time.time() + 3600))
  event.when.append(gdata.calendar.data.When(start=start_time, end=end_time))

  #event.color = gdata.calendar.data.ColorProperty(value='#2952A3')

  new_event = calendar_client.InsertEvent(event)

