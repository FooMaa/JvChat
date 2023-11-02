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
