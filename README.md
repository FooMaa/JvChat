# JvChat
Desktop chat in Java
## Run software after git clone
- Install dependencies if this is an option for users:
``` bash
sudo scripts/dependencies/install_dependencies.sh -a -p users
```
- Install dependencies if this is an option for servers:
``` bash
sudo scripts/dependencies/install_dependencies.sh -a -p servers
```
- Install dependencies if this is an option for tests:
``` bash
sudo scripts/dependencies/install_dependencies.sh -a -p tests
```
- Create a default database if this is a server option:
``` bash
sudo scripts/db/make_default_db.sh
```
- Build and run (you must specify the correct profile: users/servers/tests):
``` bash
scripts/build/build_run.sh -m -c -p users -i 192.168.23.1
```
- Restore the database to its original state (re-roll):
``` bash
scripts/db/db_creator.py
```
## Available build profiles
| Profile | Purpose                                                                                   |
|---------|-------------------------------------------------------------------------------------------|
| users   | Profile for assembling a custom software configuration with graphics                      |
| servers | Profile for building a server configuration of software without graphics, console version |
| tests   | A profile for building a test software configuration and running all available unit tests |
### Collect for users (users profile)
In place of `$ip` you insert your server IP address:
Gradle:
```
clean build bootRun --args='--ipServer=$ip' -Pusers
```
Maven:
```
clean install spring-boot:run -Pusers -Dspring-boot.run.arguments=--ipServer=$ip
```
If you need to specify a port at startup, then in place of `$ip` you insert your server IP address, in place of `$port` you insert your server port:  
Gradle:
```
clean build bootRun --args="--ipServer=$ip --portServer=$port" -Pusers
```
Maven:
```
clean install spring-boot:run -Pusers -Dspring-boot.run.arguments="--ipServer=$ip --portServer=$port"
```
### Build for servers (servers profile)
Gradle:
```
clean build bootRun -Pservers
```
Maven:
```
clean install spring-boot:run -Pservers
```
After this build, you should specify the server IP address, server port, and the number of allowed connections. Alternatively, press Enter everywhere to use the default values.
### Build for tests (tests profile)
This is necessary to run unit tests: 
Gradle:
```
clean build bootRun -Ptests
```
Maven:
```
clean install spring-boot:run -Ptests
```
## Scripts and their purpose
| Path to the script                                     | Purpose                                                                                                                                                             |
|--------------------------------------------------------|---------------------------------------------------------------------------------------------------------------------------------------------------------------------|
| scripts/dependencies/install_dependencies.sh           | Installs missing software dependencies                                                                                                                              |
| scripts/dependencies/check_and_install_dependencies.sh | Checks and installs missing software dependencies                                                                                                                   |
| scripts/build/build.sh                                 | Checks if dependencies are installed and compiles the software                                                                                                      |
| scripts/build/build_run.sh                             | Checks if dependencies are installed and builds using the script scripts/build/build.sh and runs the software                                                       |
| scripts/db/db_creator.py                               | Clears and recreates the database using a number of *.sql scripts located in a folder named after the database schema                                               |
| scripts/db/pre_inst_db.sh                              | Can install all dependencies as scripts/dependencies/install_dependencies.sh, creates users with passwords, configures them in the database, configures pg_hba.conf |
| scripts/db/make_default_db.sh                          | Runs scripts/db/db_creator.py and pre_inst_db.sh with default parameters                                                                                            |
