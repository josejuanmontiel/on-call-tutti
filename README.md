# on-call-tutti

## Pandoc

First of all, in this case i'm going to use a different approach as usual, i left behind gradle and asciidoc plugins to transform into a revealjs presentation, in favour of using pandoc and markdown as base language, to later translate into revealjs presentation.

I'm going to use [this version](https://github.com/jgm/pandoc/releases/tag/3.1.11.1) and using the [reveal.default](https://github.com/jgm/pandoc/blob/3.1.1/data/templates/default.revealjs) as [template](https://pandoc.org/MANUAL.html#templates) for later customization, if needed.

To download template

    pandoc -D revealjs > pandoc/templates/default.revealjs

To add revealjs as submodule

    git submodule add https://github.com/hakimel/reveal.js revealjs
    git submodule update --init --recursive

Later move dist+plugins of revealjs to docs folder (where pandoc generte html).

Running this command

    pandoc \
    --standalone \
    -t revealjs \
    --template pandoc/templates/default.revealjs \
    -o docs/devops-slides.html \
    pandoc/markdown/devops-slides.md

I'll start from [this article](https://medium.com/isovera/devops-for-presentations-reveal-js-markdown-pandoc-gitlab-ci-34d07d2c1011)
and [this sample presentation](https://benjifisher.gitlab.io/slide-decks/devops-slides.html) with source code [is here](https://gitlab.com/benjifisher/slide-decks/-/blob/main/markdown/devops-slides.md)

### Pandoc extra styles

Add

    -H styles/styles.html

to the processor

## Github actions

And as i want to automate the process, i'll use (not gitlab) GitHub actions.

### Pandoc

To automate, we start with this [simple action](https://github.com/pandoc/pandoc-action-example) as you can see [here](https://raw.githubusercontent.com/josejuanmontiel/on-call-tutti/develop/.github/workflows/pandoc.yml)

      - uses: docker://pandoc/core:3.1.1-ubuntu
        with:
          args: >- # allows you to break string into multiple lines
            --standalone
            -t revealjs
            --template pandoc/templates/default.revealjs
            -o docs/devops-slides.html
            pandoc/markdown/devops-slides.md     

### Customize images

Later, we may need customize image to user other things in transformation.

    https://hub.docker.com/r/escalope/pandoc-mermaid-plantuml
    https://github.com/pandoc/dockerfiles#building-custom-images

### Plantuml

    https://pandoc.org/filters.html
    https://github.com/timofurrer/pandoc-plantuml-filter
    
    Install: graphviz:amd64 (2.42.2-7+b4, automatic), plantuml:amd64 (1:1.2020.2+ds-3)
    pip install pandoc-plantuml-filterz

    pandoc  --standalone  -t revealjs  -o html/devops-slides.html  markdown/devops-slides.md --filter pandoc-plantuml

## Bazel - Monorepo

Now continuing with infrastructure, the good objective will be mix different languages in same place, and Bazel could be a good option to build a monorepo where all the code lives. To start with [some example](https://github.com/kriscfoster/multi-language-bazel-monorepo/tree/main) this seems to be a good one.

A few words continuing with github actions, where Bazel is supported. But the idea is use github action to build this Bazel monorepo.

We could use Bazel with pandoc, but to show different options, we keep pandoc alone, because maybe use Bazel for pandoc be more difficult (to me) doing or modifying the tooling.

With a monorepo, well be easier mixing languages... and better integrating different blocks of code, to act as one.

The final idea of this (starting with on-call algorithm) is mix in one place different implementatios of one algoritm to meassure the correctness of each other and comparing the efficient ot others. With monorepo (and Bazel) we can invoke each peace from one above...  

## Github actions - in go

To Continue with my learning... i'll use (also) github actions done in go... to do some part of the comparations...

    https://docs.github.com/en/actions/quickstart
    https://github.com/marketplace/actions/setup-go-environment
    https://full-stack.blend.com/how-we-write-github-actions-in-go.html
    https://github.com/posener/goaction
    https://github.com/posener/goreadme
    https://github.com/devopselvis/github-actions-presentation/blob/main/.github/workflows/haiku-pr-build-test-upload.yml

### Java

Now, java developer can code the algorithms inside monorepo and the most important, debug remotely with any IDE...

    bazel build --compilation_mode=dbg //projects/...
    bazelisk run //projects/algorithms/on-call/001/src/main/java/com/accreativos/oncalltutti/main:Main -- --debug

Later attach to 5005 with any IDE

### Go

With go.. the thing is little complicate, but similar... in the middle we can use delve

    https://stackoverflow.com/questions/39058823/how-to-use-delve-debugger-in-visual-studio-code
    https://vscode-debug-specs.github.io/go/
    https://docs.stack.build/bazel-stack-vscode-go/debugging/delve
    https://github.com/golang/vscode-go/wiki/debugging#remote-debugging

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

## On-call (duty-schedule)

Now, show me the code. The first idea came from how to build a system that schedule how a group of expert, with different level of expertise, can handle the emergency call to handle problem of the system, as we knonw: "on call duty" or "on call schedule" ... and the idea is discover the best algortihm and most efficent for given a number of range of time, where some people (with level) can be on-call to solve some emergency, how the system optimize how to discover who is the right person for the level of the problem in some particular time.

The idea, for start is that the part of the monorepo responsible for test the different implementation, and discover which is the best, call each part given to them a environment variable with the path of a csv file with a list of time range with the identification of the person and his level, and another variable where the algorithm need to store the result csv with the list of the time range (without overlapping) and with person and which level (here if to person are available in the same range of time we prefer the most level of expertise to solve the problem... later we may store the lowest to attend another call that occur in the same time).

### Algorithms

This is the first idea, but why not to leave open to another kind of algorithm (and because of Bazel ... in any language) ... because of this we start building /monorepo/projects/algorithms/on-call/001 the first example and the other can go in 002, 003 ... later for other algorithm ot the real daily basis ... we can continue an /monorepo/projects/algorithms/OTHER/001

### How to test

As we said... with the environment variable INPUT_FILE we can read the csv and with OUTPUT_FILE we can store the result, this code we'll be in /monorepo/projects/algorithms/on-call/comparator folder and the initial data will be in /monorepo/projects/algorithms/on-call/comparator/src/main/resources/input/001.csv and the solution to compare the result monorepo/projects/algorithms/on-call/comparator/src/main/resources/output/001.csv ... the idea is to improve the option to test to try to beat the actual solutions. In this way if someone that came with his/her PR to the repo to solve the problem, and think that actual solution have a bug, that don't consider some edge case, can include his/her 002.csv data for intput and output and make that the actual algorithm false.

No need to say, that this algorithm need to work with 001.csv data ... in the begining, unless this data where incorrect, in that case may need to point the problem in the code and/or in the data.