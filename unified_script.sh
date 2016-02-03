PROBLEM_FILE=$1
MISC_DIR=$2
OUTPUT_FILE=$3
PARSER="/Users/Emma/Desktop/Projects/Parser/src/Driver"
JSOUP="/Users/emma/Desktop/Projects/jsoup-1.8.3.jar"
CENTRAL_SYSTEM='/Users/Emma/Desktop/Projects/CentralSystem'
/Applications/checker-277/scan-build -o $MISC_DIR gcc -c $PROBLEM_FILE
cd $MISC_DIR
x=$(ls -t $MISC_DIR | head -n 1)
cd $x
y=`ls -d report*`
echo "This is y: " $y
REPORT="$MISC_DIR/$x/$y"
echo "This is the final directory" $REPORT
PARSED_OUTPUT=`java -jar /users/emma/desktop/parser.jar "$REPORT"`
echo $PARSED_OUTPUT
cd /Users/Emma/Desktop/Projects/CentralSystem/src
java -cp .:/Users/emma/downloads/antlr-4.5.1-complete.jar Driver "$PARSED_OUTPUT" "$PROBLEM_FILE" "$OUTPUT_FILE"