# Система платежей.

## **Возмозможности клиента:**

* создавать карты (неограничено) и блокировать карты
* пополнять баланс карт
* совершать переводы: по номеру карты, по номеру телефона, между своими картами
* оплачивать услуги
* смотреть историю всех операций с фильтрацией
* ограничение на сумму оплаты/количество платежей(?)

## Возможности админа:

* блокировать клиентов
* блокировать карты
* удалять пользователей(?)

## Возможности главного админа:

* создавать/удалять админов

## Сущности:

* User
* Client
* Card
* Payment
* Replenishment
* Transfer

## Используемые фреймворки:

* Java 21
* Spring(boot, web, security)
* Hibernate
* PostgresSQL (субд)
* Junit-5 (тестирование)
* Maven (сборка)

