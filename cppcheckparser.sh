PROBLEM_FILE=$1
MISC_DIR=$2

OUTPUT=$(cppcheck $PROBLEM_FILE 2>&1)
#Storing both STDERR and STDOUT output

echo $OUTPUT