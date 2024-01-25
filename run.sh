# https://github.com/josejuanmontiel/on-call-tutti

# https://medium.com/isovera/devops-for-presentations-reveal-js-markdown-pandoc-gitlab-ci-34d07d2c1011
# https://benjifisher.gitlab.io/slide-decks/devops-slides.html
# https://gitlab.com/benjifisher/slide-decks/-/blob/main/markdown/devops-slides.md

pandoc \
 --standalone \
 -t revealjs \
 -o html/devops-slides.html \
 markdown/devops-slides.md

## Install 
# TODO - git submodule
#      - Move revealjs related to html folder (revealjs/ist + revealjs/plugin)

# https://pandoc.org/filters.html
# https://github.com/timofurrer/pandoc-plantuml-filter
# Install: graphviz:amd64 (2.42.2-7+b4, automatic), plantuml:amd64 (1:1.2020.2+ds-3)
# pip install pandoc-plantuml-filterz
# pandoc  --standalone  -t revealjs  -o html/devops-slides.html  markdown/devops-slides.md --filter pandoc-plantuml

## Install - Move plantuml-images to html folder

# Templates
# https://hackage.haskell.org/package/pandoc-3.1.11.1/docs/Text-Pandoc-Templates.html
# https://pandoc.org/chunkedhtml-demo/6-templates.html
# https://github.com/jgm/pandoc/blob/23f67c8458ea9a052dca9ed7a49151ea677d09dc/data/templates/default.revealjs#L20

# Ejemplos
# https://github.com/studentkittens/pandoc-revealjs-presentation/blob/master/README.md

# Revealjs
# https://revealjs.com/code/
# https://github.com/rajgoel/reveal.js-plugins
# https://github.com/rajgoel/reveal.js-plugins/blob/master/loadcontent/README.md

# TODO - Ojo a las dependencias

# Github actions 
# https://docs.github.com/en/actions/quickstart
# https://github.com/marketplace/actions/setup-go-environment
# https://full-stack.blend.com/how-we-write-github-actions-in-go.html
# https://github.com/posener/goaction
    # https://github.com/posener/goreadme
# https://github.com/devopselvis/github-actions-presentation/blob/main/.github/workflows/haiku-pr-build-test-upload.yml


# Bazel
# https://github.com/bazelbuild/setup-bazelisk
# https://github.com/ProdriveTechnologies/bazel-pandoc/tree/master/sample

# Pandoc Action
# https://github.com/pandoc/pandoc-action-example

# On-call
# https://www.atlassian.com/incident-management/on-call/on-call-schedules#common-mistakes-of-on-call-scheduling
# https://grafana.com/oss/oncall/
    # https://github.com/grafana/oncall/tree/dev/engine/common
# https://www.ilert.com
# https://www.pagerduty.com
    # https://github.com/marketplace/actions/pagerduty-on-call
    # https://github.com/PagerDuty/incident-response-docs/blob/master/docs/oncall/being_oncall.md
# https://github.com/linkedin/oncall/blob/master/e2e/test_override.py

# https://github.com/encoredev/example-app-oncall/blob/main/schedules/schedules.go
# https://github.com/form3tech-oss/go-pagerduty-oncall-report
# https://github.com/djlauk/oncasc
# https://github.com/alpha74/onCallApp/blob/main/app.js
# 