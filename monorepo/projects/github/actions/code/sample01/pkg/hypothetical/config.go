package hypothetical

import (
	"time"

	githubactions "github.com/sethvargo/go-githubactions"
)

type Config struct {
	Role          string
	LeaseDuration time.Duration
	// Directory path
	Path string
}

func NewFromInputs(action *githubactions.Action) (*Config, error) {
	lease := action.GetInput("lease-duration")
	d, err := time.ParseDuration(lease)
	if err != nil {
		return nil, err
	}

	c := Config{
		Path:          action.GetInput("path"),
		Role:          action.GetInput("role"),
		LeaseDuration: d,
	}
	return &c, nil
}
