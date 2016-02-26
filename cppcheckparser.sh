PROBLEM_FILE=$1
MISC_DIR=$2
OUTPUT_FILE=$3

cppcheck $PROBLEM_FILE --xml 2>$OUTPUT_FILE
#Storing STDERR to output_file

cppcheck $PROBLEM_FILE
echo "Done"