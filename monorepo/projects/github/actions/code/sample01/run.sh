env \
    'GITHUB_API_URL=https://api.github.com' \
    'GITHUB_REPOSITORY=blend/repo-that-uses-an-action' \
    'GITHUB_OUTPUT=/tmp/console.txt' \
    'INPUT_ROLE=user' \
    'INPUT_LEASE-DURATION=1h' \
    'INPUT_PATH=/home/jose/workspace/on-call-tutti/monorepo/projects/algorithms/on-call/comparator/src/main/resources/output' \
go run ./cmd/hypothetical/main.go

on-call-tutti/monorepo$ bazelisk build //projects/github/actions/code/sample01/cmd/hypothetical:main
on-call-tutti/monorepo$ sudo rm projects/github/actions/release/sample01/main-linux-amd64-4b7de504558cac5083795023fe26985549ef6e0b  
on-call-tutti/monorepo$ cp bazel-bin/projects/github/actions/code/sample01/cmd/hypothetical/main_/main projects/github/actions/release/sample01/main-linux-amd64-4b7de504558cac5083795023fe26985549ef6e0b 
