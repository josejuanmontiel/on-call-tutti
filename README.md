# on-call-tutti

## Pandoc

First of all, in this case i'm going to use a different aproach as usual, i left behind gradle and asciidoc pluging to transform into a revealjs presentantion, in favour of using pandoc and markdown as base languaje, to later translate into revealjs presentation.

I'm going to use this version, https://github.com/jgm/pandoc/releases/tag/3.1.11.1 and using the reveal.default template https://pandoc.org/MANUAL.html#templates for later customization.

To download template 

pandoc -D revealjs > pandoc/templates/default.revealjs

To add reveal as submodule

git submodule add https://github.com/hakimel/reveal.js revealjs
git submodule update --init --recursive

Later move dist+plugins to docs folder.

Running this command

pandoc \
 --standalone \
 -t revealjs \
 --template pandoc/templates/default.revealjs \
 -o docs/devops-slides.html \
 pandoc/markdown/devops-slides.md

I'll start from this article https://medium.com/isovera/devops-for-presentations-reveal-js-markdown-pandoc-gitlab-ci-34d07d2c1011
and this sample presentation https://benjifisher.gitlab.io/slide-decks/devops-slides.html with source code is here https://gitlab.com/benjifisher/slide-decks/-/blob/main/markdown/devops-slides.md

## Github actions

### Pandoc

#### Customize images

## Monorepo

### Java

### Go

## Github actions - in go

## On-call (duty-schedule)

### Algorithms

### How to test
