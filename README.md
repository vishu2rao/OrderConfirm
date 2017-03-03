# OrderConfirm

If you need a solution to have email based transaction(order) confirmation with timely (UUID) generated REST links between user and vendor.
Then this code can help how to get confimed secured way.
All vendors involved should get emails asking them to confirm. User should get an email that they will be notified once all vendors concerned confirmed.
Once vendors confirm that they will be able to provide services via email link, the user should receive confirmation.



You are looking for the interface :
       a) You will provide me the ID's both transaction and service id's  you need interface to generate confirm links based on ids.
       b) You are looking for the interface which will confirm accepted/rejected based on vendor clicks on above confirmed links.
       c) Based on user clicks return confirmed TransactionID and ServiceID.


Here is the code I have done using Spring based rest services which will above  tasks: This complete eclipse project so that you can modify if required the way you wanted  

What it does is it will take id's like below from you like  http://localhost:8080/eventing/createlinks?tid=tr123124234,sid=servid234235345
Will return you clickable link(s) like..

http://localhost:8080/eventing/serviceconfirmation/tr123124234/servid234235345?accepted

Now , you can send one more link(separate)  to reject it or you can set timeout in minutes to auto expire .  I have commented the code so that you can use it if you wanted.
I added enough comments about each of these in the code.

You could have one more link like :http://localhost:8080/eventing/serviceconfirmation/tr123124234/servid234235345?rejected
OR
http://localhost:8080/eventing/serviceconfirmation/tr123124234/servid234235345/1464993800514?accepted


I am not sure if you wanted to encrypt any of these ID's or not , I have kept code and provision if you wanted to encrypt it.


Now, second part is that after clicking confirmation link the service will return you both id's with "accepted" status.

Like,   " You have accepted for service with TransactionID=tr123124234 and Service id=servid234235345"

