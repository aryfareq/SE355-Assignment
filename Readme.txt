In a group of two, create a Java application to perform the following task:

1- Launch a process named "Main" to prompt the user to input a paragraph.

2- The "Main" process should then distribute each word from the paragraph randomly across a network of five processes. For example, in the sentence "Hello from AUIS," the word "Hello" could be sent to Process 3, "from" to Process 1, and "AUIS" to Process 4.

3- The "Main" process should wait 15 seconds, then collect the distributed words back from the five processes. Once collected, the paragraph should be printed in the terminal.

4- Your system should maintain Lamport clocks.

Remarks:

- You are required to work in a group of two. Be sure to include both your names and AUIS email addresses.
- Upload your project to Moodle as a .zip file.
- Your not allowed to use any API/library that we haven't studied in the class.
- Cite your references by including them in the source code files as Java comments.

Bonus Points:

+2 if your system uses vector clocks instead of Lamport clocks.
+2 if the system adheres to Causal Order delivery.
+1 if all data communications use the Google Protobuf API.

...................

1- Six processes (Servers and Clients)
2- Implement a String Scanner
3- Implement a String devider
4- Send devided Strings randomly (should include a metadata to reorder again, timestamps)
5- Thread Sleep on all processes
6- Collect the strings and build them again
7- Print the string