API контроллер для управления заказами.

## Установка

Для использования API контроллера необходимо клонировать данный репозиторий и запустить его на вашем сервере.

## Использование

### Регистрация нового заказа

Метод: POST  
URL: /register  
Тело запроса: JSON объект с данными нового заказа  
Пример запроса:

{
    "productId": 123
}

### Пометить заказ как готовый к доставке
Метод: GET
URL: /ready
Пример запроса: /ready

### Перевести заказ в состояние "в обработке"
Метод: GET
URL: /progress
Пример запроса: /progress

### Завершить заказ
Метод: GET
URL: /complete
Пример запроса: /complete

### Отменить заказ
Метод: POST
URL: /cancel
Тело запроса: JSON объект с данными отмены заказа
Пример запроса:

{
    "details": "Товар закончился на складе"
}

### Получить информацию о заказе по его ID
Метод: GET
URL: /order/{id}
Пример запроса: /order/123

### Получить список событий для заказа по его ID
Метод: GET
URL: /order/events

#### Параметры запроса:

* orderId (ID заказа)
* pageNumber (номер страницы, по умолчанию 0)
* pageSize (размер страницы, по умолчанию 10)
Пример запроса: /order/events?orderId=123&pageNumber=0&pageSize=10




Вид БД
![Снимок](https://github.com/MaskorDev/demo-project/assets/70638326/dcea4325-a487-4f43-a332-27e504dee3e1)


