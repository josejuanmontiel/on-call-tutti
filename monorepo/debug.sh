bazelisk run //projects/algorithms/on-call/001/src/main/java/com/accreativos/oncalltutti/main:Main -- --debug
bazelisk run //projects/algorithms/on-call/comparator:Solver -- --debug

export INPUT=$(pwd)/projects/algorithms/on-call/comparator/src/main/resources/input/001.csv  
export OUTPUT=$(pwd)/projects/algorithms/on-call/comparator/src/main/resources/output/001_001.csv

bazel query //projects/algorithms/on-call/... | grep ":Main$"
# echo '//projects/algorithms/on-call/001/src/main/java/com/accreativos/oncalltutti/main:Main' | grep -oP 'on-call/\d{3}' |  sed 's#on-call/##g'

bazel query //projects/algorithms/on-call/... | grep ":Main$" | grep -oP 'on-call/\d{3}' |  sed 's#on-call/##g'

bazel query //projects/algorithms/on-call/... | grep ":Main$" | while read line
do
    ALG=$(echo $line | grep -oP 'on-call/\d{3}' | sed 's#on-call/##g')
    export INPUT=$(pwd)/projects/algorithms/on-call/comparator/src/main/resources/input/001.csv  
    export OUTPUT=$(pwd)/projects/algorithms/on-call/comparator/src/main/resources/output/001_ALG$(echo $ALG).csv
    bazel run $line

    export INPUT=$(pwd)/projects/algorithms/on-call/comparator/src/main/resources/input/002.csv  
    export OUTPUT=$(pwd)/projects/algorithms/on-call/comparator/src/main/resources/output/002_ALG$(echo $ALG).csv
    bazel run $line
done



bazel run $(bazel query //projects/algorithms/on-call/...)


bazelisk build -c dbg //projects/samples/go_hello_world...

bazelisk run //projects/samples/go_web:go_web_amd64 -- --debug
bazelisk build -c dbg //projects/samples/go_web:go_web_amd64
dlv exec bazel-bin/projects/samples/go_web/go_web_amd64_/go_web_amd64
dlv exec --listen=0.0.0.0:2345 --headless --log --api-version=2 bazel-bin/projects/samples/go_web/go_web_amd64_/go_web_amd64

#!/bin/bash
$@ &
PID=$!
kill -STOP $PID
# echo $PID
# wait $PID
dlv attach $PID --listen=0.0.0.0:2345 --headless --log --api-version 2
# dlv attach <pid> --listen=:2345 --headless --api-version=2 --log