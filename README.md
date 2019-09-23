# How to Run
----------
# Step 1 - Download maven 3.2 or above.
# Step 2 - Set path of maven 3.2 untill bin Eg.  D:\<dir>\apache-maven-3.6.2-bin\apache-maven-3.6.2\bin
# Step 3 - Please check if mvn is running in console or editor you are using.
# Step 5 - Clone repo
        https://github.com/sudhir05/mybank.git
# Step 6 - Go to repo
        cd mybank
# Step 7 - Run maven command to test and generate the jar.
        mvn clean install
# Step 8 - Above will run test cases and create target folder.
# Step 9 - Go to target/classes folder
        cd target/classes
# Step 10 - Run application code
         java com.mybank.ProcessCSV ACC998877 "20/10/2018 12:00:00" "20/10/2018 19:00:00"
# Step 11 - Change input parameter to test other scenario.
