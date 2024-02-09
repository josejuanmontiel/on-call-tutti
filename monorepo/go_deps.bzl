load("@bazel_gazelle//:deps.bzl", "go_repository")

def go_dependencies():
    go_repository(
        name = "com_github_gorilla_mux",
        importpath = "github.com/gorilla/mux",
        sum = "h1:TuBL49tXwgrFYWhqrNgrUNEY92u81SPhu7sTdzQEiWY=",
        version = "v1.8.1",
    )
    go_repository(
        name = "com_github_sethvargo_go_githubactions",
        importpath = "github.com/sethvargo/go-githubactions",
        sum = "h1:Gbr36trCAj6uq7Rx1DolY1NTIg0wnzw3/N5WHdKIjME=",
        version = "v1.2.0",
    )
