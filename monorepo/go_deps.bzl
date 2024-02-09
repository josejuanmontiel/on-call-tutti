load("@bazel_gazelle//:deps.bzl", "go_repository")

def go_dependencies():
    go_repository(
        name = "com_github_gorilla_mux",
        importpath = "github.com/gorilla/mux",
        sum = "h1:i40aqfkR1h2SlN9hojwV5ZA91wcXFOvkdNIeFDP5koI=",
        version = "v1.8.0",
    )
    go_repository(
        name = "com_github_sethvargo_go_githubactions",
        importpath = "github.com/sethvargo/go-githubactions",
        sum = "h1:Gbr36trCAj6uq7Rx1DolY1NTIg0wnzw3/N5WHdKIjME=",
        version = "v1.2.0",
    )
