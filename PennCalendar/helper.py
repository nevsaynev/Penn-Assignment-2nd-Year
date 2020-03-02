def charToNum(x):
    ''' Change month format from char to digit'''
    #Strips leading/trailing white space form x
    x= x.strip()
    return {
        'January': '1',
        'February': '2',
        'March': '3',
        'April': '4',
        'May': '5',
        'June': '6',
        'July': '7',
        'August': '8',
        'September': '9',
        'October': '10',
        'Novermber': '11',
        'December': '12',
        }.get(x, '0')

def TwlToTwen(x):
    ''' Change time format from 12hour to 24 hour'''
    #Strips leading/trailing white space form x
    x=x.strip()
    return{
        '1': '13',
        '2': '14',
        '3': '15',
        '4': '16',
        '5': '17',
        '6': '18',
        '7': '19',
        '8': '20',
        '9': '21',
        '10': '22',
        '11': '23',
        }.get(x,'12')
 
def TwoDigit(x):
    if len(x.strip()) ==1:
        return '0'+x
    return x

def stringLstToString(x):
     y = ''
     for item in x:
         if type(item) == str and (item is not '\n'):
             y = y + item
             
     y.strip()
     return y
     
