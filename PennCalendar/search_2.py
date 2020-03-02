import imaplib
import email

def get_first_text_block(self, email_message_instance):
    maintype = email_message_instance.get_content_maintype()
    if maintype == 'multipart':
        for part in email_message_instance.get_payload():
            if part.get_content_maintype() == 'text':
                #print part.get_payload()
                return part.get_payload()
    elif maintype == 'text':
        #print email_message_instance.get_payload()
        return email_message_instance.get_payload()

def get_txt(keywords, fileName):

    mail = imaplib.IMAP4_SSL('imap.gmail.com')
    mail.login('tommu180@gmail.com', 'LandRover199')
    mail.list()
    # Out: list of "folders" aka labels in gmail.
    mail.select("INBOX") # connect to inbox.

    # note that if you want to get text content (body) and the email contains
    # multiple payloads (plaintext/ html), you must parse each message separately.
    # use something like the following: (taken from a stackoverflow post)



    result, data = mail.search(None, "ALL")

    #print result
    #print data

    f = '(BODY ' + keywords + ' SENTSINCE 01-Jan-2014)'
    #f = '(BODY "SEAS Calendar" SENTSINCE 01-Jan-2014)'
    #f = '(FROM ' + fromAddr + ' SENTSINCE 01-Jan-2014)'
    j = "(SENTSINCE 01-Jan-2014)"
    result, data = mail.search(None, f)

    #print result
    #print data

    ##ids = data[0] # data is a list.
    ##id_list = ids.split() # ids is a space separated string
    ##latest_email_id = id_list[-1] # get the latest

    ids = data[0] # data is a list.
    print ids
    id_list = ids.split() # ids is a space separated string
    #print id_list #it is a list
    #print type(id_list[-1]) # type is string

    newfile = open(fileName, "w")
    for id in range(len(id_list)):
        print id_list[id]
        latest_email_id = id_list[id] # get the id
        result, data = mail.fetch(latest_email_id, "(RFC822)") # fetch the email body (RFC822) for the given ID
        raw_email = data[0][1] # here's the body, which is raw text of the whole email
        # including headers and alternate payloads
        email_message = email.message_from_string(raw_email)
        #print email_message['To']
        #print email.utils.parseaddr(email_message['From']) # for parsing "Yuji Tomita" <yuji@grovemade.com>
        #print type(email_message)
        #print email_message.items() # print all headers
        
        wr = get_first_text_block(mail, email_message)
        #print type(wr)
        #print wr
        if wr is not None: 
            newfile.write(" "+wr)
    newfile.close()
