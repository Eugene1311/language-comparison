package repository

import (
	"bytes"
	"encoding/json"
	"errors"
	"fmt"
	"github.com/elastic/go-elasticsearch/v8"
	"go-event-service/model"
	"go.uber.org/zap"
	"log"
)

type ElasticEventRepository struct {
	elasticSearchClient *elasticsearch.Client
	eventsIndex         string
	logger              *zap.Logger
}

func NewElasticEventRepository(elasticSearchClient *elasticsearch.Client, eventsIndex string, logger *zap.Logger) ElasticEventRepository {
	return ElasticEventRepository{
		elasticSearchClient: elasticSearchClient,
		eventsIndex:         eventsIndex,
		logger:              logger,
	}
}

func (repository ElasticEventRepository) Save(event model.Event) (*string, error) {
	data, err := json.Marshal(event)
	if err != nil {
		return nil, err
	}
	res, err := repository.elasticSearchClient.Index(repository.eventsIndex, bytes.NewReader(data))

	if err != nil {
		repository.logger.Error("Error getting response", zap.Error(err))
		return nil, err
	}
	defer res.Body.Close()

	if res.IsError() {
		errorMessage := fmt.Sprintf("[%s] Error indexing document", res.Status())
		return nil, errors.New(errorMessage)
	} else {
		var r map[string]interface{}
		if err := json.NewDecoder(res.Body).Decode(&r); err != nil {
			repository.logger.Error("Error parsing the response body", zap.Error(err))
			return nil, err
		} else {
			log.Printf("Saved Event with document id %s, version=%d", r["_id"], int(r["_version"].(float64)))
			repository.logger.Info("Saved Event with document",
				zap.Any("document id", r["_id"]),
				zap.Any("document version", r["_version"]),
			)
			documentId := r["_id"].(string)
			return &documentId, nil
		}
	}
}
