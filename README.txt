This software is licensed under ASLv2 (see LICENSE.txt) and uses third party libraries that are distributed under their
own license terms (see LICENSE-3RD-PARTY.txt).

HowTo:
1. Checkout this repo with Git.
2. Install rabbitmq
   - brew install rabbitmq (MacOS)
3. Start rabbitmq
   - rabbitmq-server
3. Enable rabbitmq-management
   - rabbitmq-plugins enable rabbitmq_management
4. Visit the management page on localhost and create a user "admin" with password "admin". 
   Please make sure to change this for production. Hardcoded credentials will be removed later =).
   - localhost:15672
5. Run IRCData
   - ICRDataStarter Class
6. Run ICR
   - ICRStarter Class
   
Finally, both clients should be visible in the rabbitmq management tool and there should be some log output in the console.

For more detailed information, take a look at the sources:
https://spring.io/guides/gs/messaging-rabbitmq/
https://www.rabbitmq.com/management.html
