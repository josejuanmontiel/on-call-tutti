# https://github.com/josejuanmontiel/on-call-tutti
# https//josejuanmontiel.github.io/on-call-tutti/devops-slides.html

# Pandoc - Revealjs - Gitlab
# https://medium.com/isovera/devops-for-presentations-reveal-js-markdown-pandoc-gitlab-ci-34d07d2c1011
# https://benjifisher.gitlab.io/slide-decks/devops-slides.html
# https://gitlab.com/benjifisher/slide-decks/-/blob/main/markdown/devops-slides.md

# Install revealjs
# - git submodule
#       git submodule add https://github.com/hakimel/reveal.js revealjs
#       git submodule update --init --recursive
## TODO - Move revealjs related to html folder (revealjs/ist + revealjs/plugin)

# Pandoc Custom Templates
# https://hackage.haskell.org/package/pandoc-3.1.11.1/docs/Text-Pandoc-Templates.html
# https://pandoc.org/chunkedhtml-demo/6-templates.html
# https://github.com/jgm/pandoc/blob/23f67c8458ea9a052dca9ed7a49151ea677d09dc/data/templates/default.revealjs#L20
# https://raw.githubusercontent.com/jgm/pandoc-templates/master/default.revealjs

# pandoc \
#  --standalone \
#  -t revealjs \
#  --template pandoc/templates/default.revealjs \
#  -o docs/devops-slides.html \
#  pandoc/markdown/devops-slides.md

# Pandoc + Plantum
# https://pandoc.org/filters.html
# https://github.com/timofurrer/pandoc-plantuml-filter
# Install: graphviz:amd64 (2.42.2-7+b4, automatic), plantuml:amd64 (1:1.2020.2+ds-3)
# pip install pandoc-plantuml-filterz
# pandoc  --standalone  -t revealjs  -o html/devops-slides.html  markdown/devops-slides.md --filter pandoc-plantuml

## TODO - Move plantuml-images to html folder

# Pandoc + Revealjs + Mas ejemplos
# https://github.com/studentkittens/pandoc-revealjs-presentation/blob/master/README.md

# Revealjs
# https://revealjs.com/code/
# https://github.com/rajgoel/reveal.js-plugins
# https://github.com/rajgoel/reveal.js-plugins/blob/master/loadcontent/README.md

## TODO - Ojo a las dependencias en la template

# Pandoc Action
# https://github.com/pandoc/pandoc-action-example
## TODO - Ver que imagen usar para pandoc + plantum (sobre todo por las versiones y como impactan en las templates) 
# https://hub.docker.com/r/pandoc/core
## Extend - https://github.com/pandoc/dockerfiles#building-custom-images
# https://hub.docker.com/r/escalope/pandoc-mermaid-plantuml

# Github actions Go
# https://docs.github.com/en/actions/quickstart
# https://github.com/marketplace/actions/setup-go-environment
# https://full-stack.blend.com/how-we-write-github-actions-in-go.html
# https://github.com/posener/goaction
    # https://github.com/posener/goreadme
# https://github.com/devopselvis/github-actions-presentation/blob/main/.github/workflows/haiku-pr-build-test-upload.yml

# Bazel Pandoc
# https://github.com/ProdriveTechnologies/bazel-pandoc/tree/master/sample

# Bazel Github action
# https://github.com/bazelbuild/setup-bazelisk

# Bazel Monorepo
# https://github.com/kriscfoster/multi-language-bazel-monorepo/tree/main

# In/Out and other build tools
# Read folder and use environment variables
# Call from script

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
