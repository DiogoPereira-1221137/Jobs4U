RCOMP 2023-2024 Project 2 - planning
===========================================
### Sprint master: 1221133 ###

# 1. Sprint's backlog #

1. 1221133 - US 3001 As Candidate, I want to be notified in my application when the state of one of my applications changes.
             US 3000 As Candidate, I want to list all my applications and their state (including the number of applicants).


2. 1221148 - US 3003 As Customer, I want to be notified in my application when the state (phase) of my job openings changes.


3. 1220651 - US 1016 As Customer Manager, I want the system to notify candidates, by email, of the result of the verification process.


4. 1221137 - US 1020 As Customer Manager, I want to publish the results of the selection of candidates for a job opening, so that candidates and customer are notified by email of the result.
             US 3002 As Customer, I want to list all my job openings, including job reference, position, active since, number of applicants.


# 2. Technical decisions and coordination #
##  2.1. Protocol description ##
- It´s a **TCP** (Transmission Control Protocol) based client-server protocol.


- The client application takes the initiative of establishing a TCP connection with the server
   application, for such the client application is required to know (IP address or DNS name) the
   node where the server application is running and the TCP port number where the server
   application is accepting TCP connections.


- After the TCP connection is established, the connected applications exchange messages with
  the format described in Section 2.


- Once established, the TCP connection between the client application and the server
   application is kept alive and is used for all required data exchanges (requests and responses)
   while the client application is running.


- All message exchanges between the client application and the server application must follows
a very restrict client-server pattern:  the client application sends one request message, and the
server application sends back one response message.

## 2.2. Messages format ##


|    Field    | Offset (bytes) | Length (bytes) |                                                                                                                                                                                         Description                                                                                                                                                                                          |
|: -----------: |: --------------: |: --------------: |: --------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------: |
|   VERSION   |       0        |       1        |                                                                                                           Message format version. This field is a single byte and should be interpreted as an unsigned integer (value 0 to 255). The present message format version number is one                                                                                                            |
|    CODE     |       1        |       1        |                                                                                                                           This field identifies the type of request or response, it should be interpreted as an unsigned integer (value 0 to 255). See Section 3.                                                                                                                            |
| DATA1_LEN_L |       2        |       1        |   These two fields are used to specify the length in bytes of the following DATA1 field. Both these fields are to be interpreted as unsigned integer numbers (value 0 to 255).<br/> The length of the DATA1 field is to be calculated as:  <br/>DATA1_LEN_L + 256 x DATA1_LEN_M.<br/>If the resulting value is zero, it means DATA1 does not exist, and the message has ended at this point   |
| DATA1_LEN_M |       3        |       1        |   These two fields are used to specify the length in bytes of the following DATA1 field. Both these fields are to be interpreted as unsigned integer numbers (value 0 to 255).<br/> The length of the DATA1 field is to be calculated as:  <br/>DATA1_LEN_L + 256 x DATA1_LEN_M.<br/>If the resulting value is zero, it means DATA1 does not exist, and the message has ended at this point   |
|    DATA1    |       4        |       -        |                                                                                                  First chunk of data, contains data to meet the specific needs of the participating applications, its existence and the content depend on the message’s code (type of request or response).                                                                                                  |
| DATA2_LEN_L |       -        |       1        | These two fields are used to specify the length in bytes of the following DATA2 field. Both these fields are to be interpreted as unsigned integer numbers (value 0 to 255). <br/> The length of the DATA2 field is to be calculated as:  <br/> DATA2_LEN_L + 256 x DATA2_LEN_M  <br/>If the resulting value is zero, it means DATA2 does not exist, and the message has ended at this point. |
| DATA2_LEN_M |       -        |       1        | These two fields are used to specify the length in bytes of the following DATA2 field. Both these fields are to be interpreted as unsigned integer numbers (value 0 to 255). <br/> The length of the DATA2 field is to be calculated as:  <br/> DATA2_LEN_L + 256 x DATA2_LEN_M  <br/>If the resulting value is zero, it means DATA2 does not exist, and the message has ended at this point. |
| DATA2_LEN_M |       -        |       -        |                                                                                                 Second chunk of data, contains data to meet the specific needs of the participating applications, its existence and the content depend on the message’s code (type of request or response).                                                                                                  |



## 2.3. Message codes

| CODE |   Type   |                                                                                                                                              Meaning                                                                                                                                              |
|:----:|:--------:|:-------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------:|
|  0   | Request  |                                                                      COMMTEST – Communications test request with no other effect on the server application than the response with an ACK message. This request has no data.                                                                       |  
|  1   | Request  |                                 DISCONN – End of session request. The server application is supposed to respond with an ACK message, afterwards both client and server applications are expected to close the session (TCP connection). This request has no data.                                 |  
|  2   | Response |                                                                               ACK – Generic acknowledgment and success response message. Used in response to a successful request. This response contains no data.                                                                                | 
|  3   | Response | ERR – Error response message. Used in response to unsuccessful requests that caused an error. This response message may carry a human readable phrase explaining the error. If used, the phrase is carried in the DATA1 field as string of ASICII codes, it’s not required to be null terminated. |
|  4   | Request  |         AUTH – User authentication request carrying the username in DATA1 and the user’s password in DATA2, both are strings of ASICII codes and are not required to be null terminated. If the authentication is successful, the server application response is ACK, otherwise it’s ERR          |
|  5   | Request  |                                                                                                                            REQ_NOTFLIST: request notification by email                                                                                                                            |  
|  6   | Response |                                                                                                                               RESP_NOTFLIST: notification by email                                                                                                                                |  
|  7   | Request  |                                                                                                                         REQ_UNREAD: request number of notification unread                                                                                                                         | 
|  8   | Response |                                                                                                                            RESP_UNREAD: number of notification unread                                                                                                                             |
|  9   | Request  |                                                                                                                           REQ_JOBOP: requests to list all job openings                                                                                                                            |
|  10  | Response |                                                                                            RESP_JOBOP: lists all job openings (including job reference, position, active since, number of applicants)                                                                                             |
|  11  | Request  |                                                                                                               REQ_HMUCHJOBAPP: requests number of job applications for job opening                                                                                                                |
|  12  | Response |                                                                                                                   RESP_HMUCHJOBAPP: number of job applications for job opening                                                                                                                    |
|  13  | Request  |                                                                                                                      REQ_MARKREAD: requests to mark a notification has read                                                                                                                       |
|  14  | Request  |                                                                                                                        REQ_SENDNOTF: sends an email thought a notificaiton                                                                                                                        |
|  15  | Request  |                                                                                                         REQ_SENDPUBLSIH: requests to send a email publishing the results of the selection                                                                                                         |
|  16  | Response |                                                                                                              RESP_SENDPUBLSIH: sends a email publishing the results of the selection                                                                                                              |
|  17  | Request  |                                                                                                               REQ_JOBAPPSTATE: request to list all job applications and their state                                                                                                               |
|  18  | Response |                                                                                                                    RESP_JOBAPPSTATE: list all job applications and their state                                                                                                                    |
|  19  | Request  |                                                                                                            REQ_JOBAPPRESULT: request to send an email with the job application result                                                                                                             |
|  20  | Response |                                                                                                                 RESP_JOBAPPRESULT: sends an email with the job application result                                                                                                                 |
