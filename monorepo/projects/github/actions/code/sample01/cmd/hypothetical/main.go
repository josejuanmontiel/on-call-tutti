package main

import (
	"context"

	githubactions "github.com/sethvargo/go-githubactions"

	"github.com/josejuanmontiel/on-call-tutti/monorepo/projects/github/actions/code/sample01/pkg/hypothetical"
)

func run() (error, *githubactions.Action) {
	ctx := context.Background()
	action := githubactions.New()

	cfg, err := hypothetical.NewFromInputs(action)
	if err != nil {
		return err, action
	}

	return hypothetical.Run(ctx, *cfg), action
}

func main() {
	err, action := run()
	action.SetOutput("result", "Sample result")
	if err != nil {
		action.Fatalf("%v", err)
	}
}
