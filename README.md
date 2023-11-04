# JvChat
Чат на java с maven
## Запустить ПО после клонирования с git
- Установить зависимости
``` bash
sudo scripts/requirements/install_requirements.sh
```
- Создать БД по умолчанию
``` bash
sudo scripts/db/make_default_db.sh
```
- Собрать и запустить
``` bash
scripts/build/build_run.sh
```
- Вернуть БД к начальному виду (перенакатить)
``` bash
scripts/db/db_creator.py
```
## Скрипты и их назначение
| Путь к скрипту | Назначение |
| --- | --- |
| scripts/requirements/install_requirements.sh | Проверяет наличие зависимостей в ПО и устанавливает отсутствующие |
| scripts/build/build.sh | Проверяет установлены ли зависимости и собирает ПО |
| scripts/build/build_run.sh | Собирает с помощью скрипта scripts/build/build.sh и запускает ПО |
| scripts/db/db_creator.py | Очищает и создает заново БД с помощью рядом лежащих скриптов *.sql |
| scripts/db/pre_inst_db.sh | Может установить все зависимости как scripts/requirements/install_requirements.sh, создает пользователей с паролями, конфигурирует их в БД, настраивает pg_hba.conf  |
| scripts/db/make_default_db.sh | Запускает скрипты scripts/db/db_creator.py и pre_inst_db.sh с параметрами по умолчанию |
