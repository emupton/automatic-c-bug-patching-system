PROBLEM_FILE=$1 #String file directory of the problem .c file
MISC_DIR=$2 #Misc directory for files generated during the bug fixing process,
#user should provide a directory they want to use for these files to be #generated in
OUTPUT_FILE=$3 #The output file directory
PARSER="java -jar /users/emma/desktop/parser.jar " #Location of the parser.jar file
CENTRAL_SYSTEM='/Users/emma/Desktop/Projects/FinalYear/fixBugAntlr/CentralSystem/src' #Location of the central system's directory
/Applications/checker-277/scan-build -o $MISC_DIR gcc -c $PROBLEM_FILE
#-enable-checker alpha.unix.SimpleStream 
#Calling scan-build to detect bugs and output the file report .html files to MISC_DIR
cd $MISC_DIR
x=$(ls -t $MISC_DIR | head -n 1)
#getting the first directory that will contain releant problem report files, including the .html
cd $x
y=`ls -d report*`
#obtaining the report.html file (report files are of the format xreport.html, where x is an arbritrary string of numbers)
REPORT="$MISC_DIR/$x/$y"
#storing the report file location
echo "Scan-build's report is stored at: " $REPORT
PARSED_OUTPUT=`$PARSER "$REPORT"`
#parsing the report file, will output the bug description and line numbers
echo $PARSED_OUTPUT
#displaying the parsed output for dedbugging purposes
cd $CENTRAL_SYSTEM
#changing directory to the location of the central system
javac -cp .:/Users/emma/downloads/antlr-4.5.1-complete.jar *.java
java -cp .:/Users/emma/downloads/antlr-4.5.1-complete.jar Driver "$PARSED_OUTPUT" "$PROBLEM_FILE" "$OUTPUT_FILE"
#sending the parsed output along with the problem file and output file directories to the central system
#the central system then fixes the bug(s) and outputs a patched file