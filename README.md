# JvChat
Чат на java
## Запустить ПО после клонирования с git
- Установить зависимости, если это вариант для пользователей
``` bash
sudo scripts/dependencies/install_dependencies.sh -a -p users
```
- Создать БД по умолчанию, если это серверный вариант
``` bash
sudo scripts/db/make_default_db.sh
```
- Собрать и запустить
``` bash
scripts/build/build_run.sh -m -c -p users -i 192.168.23.1
```
- Вернуть БД к начальному виду (перенакатить)
``` bash
scripts/db/db_creator.py
```
## Конфигурации для запуска из среды разработки
На место $ip вы вставляете свой IP-адрес сервера  
Gradle:
```
clean build bootRun --args='--ipServer=$ip' -Pusers
```
Maven:
```
clean install spring-boot:run -Pusers -Dspring-boot.run.arguments=--ipServer=$ip
```
## Скрипты и их назначение
| Путь к скрипту | Назначение |
| --- | --- |
| scripts/dependencies/install_dependencies.sh | Устанавливает отсутствующие зависимости в ПО |
| scripts/dependencies/check_and_install_dependencies.sh | Проверяет и устанавливает отсутствующие зависимости в ПО |
| scripts/build/build.sh | Проверяет установлены ли зависимости и собирает ПО |
| scripts/build/build_run.sh | Проверяет установлены ли зависимости и собирает с помощью скрипта scripts/build/build.sh и запускает ПО |
| scripts/db/db_creator.py | Очищает и создает заново БД с помощью рядом лежащих скриптов *.sql |
| scripts/db/pre_inst_db.sh | Может установить все зависимости как scripts/dependencies/install_dependencies.sh, создает пользователей с паролями, конфигурирует их в БД, настраивает pg_hba.conf  |
| scripts/db/make_default_db.sh | Запускает скрипты scripts/db/db_creator.py и pre_inst_db.sh с параметрами по умолчанию |
