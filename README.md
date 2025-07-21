# Bishop Prototype API

REST API для отправки задач и получения метрик, построенный с использованием стартера Synthetic Human Core, который обеспечивает аудит, логирование и сбор метрик.

---

## Эндпоинты

### POST `/api/bishop/tasks`

Отправка новой задачи.

#### Пример запроса:
```json
POST /api/bishop/tasks
Content-Type: application/json

{
  "description": "Проверка безопасности",
  "priority": "CRITICAL",
  "author": "Ripley",
  "time": "2025-07-21T10:00:00Z"
}
```

#### Ответ:
```json
"CRITICAL task executed immediately"
```
---

### GET `/api/metrics/tasks/summary`

Возвращает агрегированные метрики задач.

#### Пример ответа:
```json
{
  "total_submitted": 12.0,
  "critical_executed": 5.0,
  "rejected": 1.0,
  "queue_size": 3.0,
  "by_author": {
    "Ripley": 7.0,
    "Ash": 5.0
  }
}
```

Метрики собираются через Micrometer из `MeterRegistry`.
