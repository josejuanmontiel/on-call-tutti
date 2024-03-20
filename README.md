# on-call-tutti

## Pandoc

First of all, in this case i'm going to use a different approach as usual, i left behind gradle and asciidoc plugins to transform into a revealjs presentation, in favour of using pandoc and markdown as base language, to later translate into revealjs presentation.

I'm going to use [this version](https://github.com/jgm/pandoc/releases/tag/3.1.11.1) and using the [reveal.default](https://github.com/jgm/pandoc/blob/3.1.1/data/templates/default.revealjs) as [template](https://pandoc.org/MANUAL.html#templates) for later customization, if needed.

To download template

    pandoc -D revealjs > pandoc/templates/default.revealjs

To add revealjs as submodule

    git submodule add https://github.com/hakimel/reveal.js revealjs
    git submodule update --init --recursive

Later move dist+plugins of revealjs to docs folder (where pandoc generate html).

Running this command

    pandoc \
    --standalone \
    -t revealjs \
    --template pandoc/templates/default.revealjs \
    -o docs/index.html \
    pandoc/markdown/index.md

I'll start from [this article](https://medium.com/isovera/devops-for-presentations-reveal-js-markdown-pandoc-gitlab-ci-34d07d2c1011)
and [this sample presentation](https://benjifisher.gitlab.io/slide-decks/index.html) with source code [is here](https://gitlab.com/benjifisher/slide-decks/-/blob/main/markdown/index.md)

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
            -o docs/index.html
            pandoc/markdown/index.md     

### Customize images

Later, we may need customize docker image to use other things in transformation (like plantum)

    https://hub.docker.com/r/escalope/pandoc-mermaid-plantuml
    https://github.com/pandoc/dockerfiles#building-custom-images

### Plantuml (in local)

    https://pandoc.org/filters.html
    https://github.com/timofurrer/pandoc-plantuml-filter
    
    Install: graphviz:amd64 (2.42.2-7+b4, automatic), plantuml:amd64 (1:1.2020.2+ds-3)
    pip install pandoc-plantuml-filterz

    pandoc  --standalone  -t revealjs  -o html/index.html  markdown/index.md --filter pandoc-plantuml

## Bazel - Monorepo

Now continuing with infrastructure, to let all the people try, we'll mix different languages in same place, and Bazel could be a good option to build a monorepo where all the code lives. To start with, we'll use [this example](https://github.com/kriscfoster/multi-language-bazel-monorepo/tree/main), seems to be a good one.

We could use [Pandoc inside Bazel](https://github.com/ProdriveTechnologies/bazel-pandoc/tree/master/sample), but to use different options that maybe not implemented in the bazel, we keep pandoc alone, outside the monorepo, because maybe use Bazel for pandoc be more difficult (to me) doing or modifying the tooling.

The final idea of this (starting with on-call proble) is mix in one place different implementations that solve one problem and measure the correctness of each other and comparing the efficient of eatch other. With monorepo (and Bazel) we can invoke each piece from one above... We'll dig into it later.

## Github actions - in go

To Continue with my learning... i'll use (also) github actions done in go... to do some part of the comparations... the base exampled explained [here](https://full-stack.blend.com/how-we-write-github-actions-in-go.html)

### Java

Now, java developer can code the algorithms inside monorepo and the most important, debug remotely with any IDE... (insede monorepo folder)

    bazelisk build //...
    bazelisk build --compilation_mode=dbg //projects/algorithms/...
    bazelisk run //projects/algorithms/on-call/001/src/main/java/com/accreativos/oncalltutti/main:Main -- --debug

...later attach to 5005 with any IDE

our use Intellij with Bazel pluggin.


### Go

WIP 

With go.. the thing is little complicate, but similar aproach, in this case run in debug mode throuht delve and connect to delve i.e. from vscode

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

our 8like in java) use any ide that support GO (Intellij, vscode ...) at the end, Bazel transform golang depencies to himself.

### Python

WIP

### .NET

Why not :D

## On-call (duty-schedule)

Now, show me the code. The first idea came from how to build a system that schedule how a group of expert, with different level of expertise, can handle the emergency call to take care of a problem in the system, as we known: "on call duty" or "on call schedule" ... and the idea is discover the best algorithm and most efficient for given range of time, where some people (with level) can be on-call to solve some emergency, how the system optimize how to discover who is the right person for the level of the problem in some particular time.

![Schema](schema2.drawio.svg)

The idea, for start is that the part of the monorepo responsible for test the different implementation, and discover which is the best, call each part given to them a environment variable with the path of a csv file with a list of time range with the identification of the person and his level, and another variable where the algorithm need to store the result csv with the list of the time range (without overlapping) and with person and which level (here if to person are available in the same range of time we prefer the most level of expertise to solve the problem... later we may store the lowest to attend another call that occur in the same time).

### Algorithms

This is the first idea, but why not to leave open to another kind of algorithm (and because of Bazel ... in any language) ... because of this we start building /monorepo/projects/algorithms/on-call/001 the first example and the other can go in 002, 003 ... later for other algorithm ot the real daily basis ... we can continue an /monorepo/projects/algorithms/OTHER/001

### How we test each algorithm?

As we said... with the environment variable INPUT we can read the csv and with OUTPUT we can store the result, this code we'll be in /monorepo/projects/algorithms/on-call/comparator folder and the initial data will be in /monorepo/projects/algorithms/on-call/comparator/src/main/resources/input/001.csv and the solution to compare the result monorepo/projects/algorithms/on-call/comparator/src/main/resources/output/001.csv ... the idea is to improve the option to test to try to beat the actual solutions. In this way if someone that came with his/her PR to the repo to solve the problem, and think that actual solution have a bug, that don't consider some edge case, can include his/her 002.csv data for intput and output and make that the actual algorithm false.

No need to say, that this algorithm need to work with 001.csv data ... in the begining, unless this data where incorrect, in that case may need to point the problem in the code and/or in the data.

## Generalize

Why not generalize the algorithms, not to stick to on-call problem, not said bubble sort, but maybe ... let me know, sure you made some interviews where the told you to solve some kind of algorithm

## Auto-solve

WIP 

I thinks how to implement the validation of the result. We start (as said before) providing an input/output for each algorithm, because of this, you can beat another algortihms with another inputs not contemplad (interesting aproach in kaggle [python](https://www.kaggle.com/learn/python) course where try a lot of [combination](https://github.com/Kaggle/learntools/tree/master/learntools/python)) ... maybe with [neural networks](https://github.com/MatheusR42/vanilla-js-neural-network) ...

From now, be your neurons the solvers of the problems, and the finders of the problems of other solvers.

Now a simple Github action in golang check if the result of input in one algorithm match to the expected result (this could be done by the solver, but then could play with github actions in go)

## Katas ...

Maybe the idea could be similar to practice one katas, but why not to try with "real" problems ... finally katas are more build from scratch, here the idea is identify problems and try to model algorithms, in the first case "on-call" the real algorirhm that try to solve is how organize a group of date range to overlap acording levels... sure it's applicable to other domains.

### Advent of code

WIP 

Not sure if [it's similar](https://adventofcode.com/2023/about), but interested in discover how it's works under the hood. 

## Even AI why not, we don't discriminare

From now, this kind of difficult to describe algorithms (if you don't split in small peaces, here is the key of the domain :) need and improve to run, and maybe more to work correctly, no problem to use them to try to beat the actual solutions.