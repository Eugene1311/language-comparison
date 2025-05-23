package service

import (
	"go-event-service/interfaces"
	"go-event-service/model"
	"go.uber.org/zap"
)

type DomainEventService struct {
	eventRepository interfaces.EventsRepository
	logger          *zap.Logger
}

func NewDomainEventService(eventRepository interfaces.EventsRepository, logger *zap.Logger) DomainEventService {
	return DomainEventService{
		eventRepository: eventRepository,
		logger:          logger,
	}
}

func (service DomainEventService) Save(event model.Event) (model.Event, error) {
	service.logger.Info("Saving event", zap.Any("event", event))
	_, err := service.eventRepository.Save(event)
	if err != nil {
		service.logger.Error("Error while saving event", zap.Any("event", event), zap.String("error", err.Error()))
		return model.Event{}, err
	}

	return event, nil
}
