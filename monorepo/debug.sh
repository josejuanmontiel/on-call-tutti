bazelisk run //projects/algorithms/on-call/001/src/main/java/com/accreativos/oncalltutti/main:Main -- --debug

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