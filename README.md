# JvChat
Чат на java
## Запустить ПО после клонирования с git
- Установить зависимости, если это вариант для пользователей
``` bash
sudo scripts/dependencies/install_dependencies.sh -a -p users
```
- Установить зависимости, если это вариант для серверов
``` bash
sudo scripts/dependencies/install_dependencies.sh -a -p servers
```
- Установить зависимости, если это вариант для тестов
``` bash
sudo scripts/dependencies/install_dependencies.sh -a -p tests
```
- Создать БД по умолчанию, если это серверный вариант
``` bash
sudo scripts/db/make_default_db.sh
```
- Собрать и запустить (следует указать верный профиль: users/servers/tests)
``` bash
scripts/build/build_run.sh -m -c -p users -i 192.168.23.1
```
- Вернуть БД к начальному виду (перенакатить)
``` bash
scripts/db/db_creator.py
```
## Доступные профили сборки
| Профиль | Назначение |
| --- | --- |
| users | Профиль для сборки пользовательской конфигурации ПО с графикой |
| servers | Профиль для сборки серверной конфигурации ПО без графики, консольный вариант |
| tests | Профиль для сборки тестовой конфигурации ПО и прогона всех доступных юнит-тестов |
### Собрать для пользователей (профиль users)
На место $ip вы вставляете свой IP-адрес сервера.  
Gradle:
```
clean build bootRun --args='--ipServer=$ip' -Pusers
```
Maven:
```
clean install spring-boot:run -Pusers -Dspring-boot.run.arguments=--ipServer=$ip
```
Если нужно указать порт при запуске, то на место ```$ip``` вы вставляете свой IP-адрес сервера, на место ```$port``` вы вставляете свой порт сервера.  
Gradle:
```
clean build bootRun --args="--ipServer=$ip --portServer=$port" -Pusers
```
Maven:
```
clean install spring-boot:run -Pusers -Dspring-boot.run.arguments="--ipServer=$ip --portServer=$port"
```
### Собрать для серверов (профиль servers)
Gradle:
```
clean build bootRun -Pservers
```
Maven:
```
clean install spring-boot:run -Pservers
```
После данной сборки следует указать IP-адрес сервера, порт сервера и количество допустимых подключений. Либо везде нажать Enter, для того, чтоб использовать значения по умолчанию.
### Собрать для тестов (профиль tests)
Это нужно для запуска юнит-тестов.  
Gradle:
```
clean build bootRun -Ptests
```
Maven:
```
clean install spring-boot:run -Ptests
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
