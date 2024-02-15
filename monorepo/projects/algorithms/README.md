# Algorithms

## The idea...
...of this (starting with on-call algorithm) is mix in one place different implementations of one algorithm to measure the correctness of each other and comparing the efficient ot others. With monorepo (and Bazel) we can invoke each peace from one above... We'll dig into it later.

## How it works

on-call-tutti/monorepo$ MYRUN=value1 bazelisk run //projects/algorithms/on-call/comparator:Solver

on-call-tutti/monorepo$ bazelisk run //projects/algorithms/on-call/001/src/main/java/com/accreativos/oncalltutti/main:Main -- --debug

on-call-tutti/monorepo$ bazel build $(bazel query //projects/algorithms/on-call/... | grep -E ":main")

### Modules

### Environment variables

### Test files

### Go action to check result

WIP

### Score