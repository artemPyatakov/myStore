

<b>Задача:</b>
1. Создание backend для информационной системы "Интернет-магазин".
2. Система должна обеспечивать возможность получать данные путем REST-запросов к серверу. Ответ должен отправляться в виде JSON.
3. Список операций (клиент):
- отображение списка категорий товаров (все)
- отображение списка товаров в категории (по переданной категории)
- отображение информации по товару (по идентификатору товара)
- добавление товара в корзину (со стороны сервера нужно обеспечить хранение корзины во время жизни http-сессии).
- заказ товара (список идентификаторов товаров из корзины (как вариант - id корзины - зависит от подхода), имя/фамилия клиента, телефон)
4. Список операций (администратор):
- возможность входа в защищенную часть магазина (по логину/паролю), а также выхода. Операции, перечисленные ниже, доступны после успешного входа в панель администратора.
- добавление/изменение/удаление категории (по id категории)
- добавление/изменение/удаление товара (id категории, id товара)
- просмотр списка заказов
- изменение статуса конкретного заказа (NEW -> IN_PROGRESS -> COMPLETE)
- сохранять список всех выполненных (COMPLETE) заказов в CSV со всеми атрибутами

В папке <b>sqlScript</b> скрипты для создания и наполнения базы

Проект периодически дополняется/переписывается.
