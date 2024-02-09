# Build

on-call-tutti/monorepo$ MYRUN=value1 bazelisk run //projects/algorithms/on-call/comparator:Solver
on-call-tutti/monorepo$ bazelisk run //projects/algorithms/on-call/001/src/main/java/com/accreativos/oncalltutti/main:Main -- --debug

on-call-tutti/monorepo$ bazel build $(bazel query //projects/algorithms/on-call/... | grep -E ":main")