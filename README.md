## Дипломный проект профессии «Тестировщик ПО»

### Документация
1. [План автоматизации тестирования](https://github.com/sevastyanov1982/Diplom/blob/master/documentation/Plan.md)
2. [Отчет по итогам тестирования](https://github.com/sevastyanov1982/Diplom/blob/master/documentation/Report.md)
3. [Отчет по итогам автоматизации тестирования](https://github.com/sevastyanov1982/Diplom/blob/master/documentation/Summary.md)

### Необходимое окружение:
* установленный Node.js
* установленный Docker
* установленная IntelliJ IDEA
* Java 11
* браузер

### Процедура установки, настройки и запуска ПО

#### Перед запуском авто-тестов, необходимо:
* Уcтановить программы "Intellij IDEA Ultimate", "Docker", "Docker-compose", для работы с контейнерами "MySQL", 
  "PostgreSQL", "Node-app"

* Проверить наличие установленных версий библиотек в файле "build.gradle", 
  необходимых для запуска авто-тестов

* Запустить контейнеры "MySQL", "PostgreSQL", "Node-app" в "Docker-compose"

* Запустить SUT для "MySQL" или "PostgreSQL"

1. Для запуска контейнеров "MySQL", "PostgreSQL", "Node-app", ввести в 
   терминал следующую команду: ```docker-compose up -d --force-recreate```
2. Запустить SUT командой 
* Для MySQL: ```java -Dspring.datasource.url=jdbc:mysql://localhost:3306/app -jar artifacts/aqa-shop.jar```
* Для PostgreSQL: ```java -Dspring.datasource.url=jdbc:postgresql://localhost:5432/app -jar artifacts/aqa-shop.jar```
3. Запустить тесты командой:  
* Для MySQL: ```./gradlew clean test -Ddb.url=jdbc:mysql://localhost:3306/app -Dlogin=app -Dpassword=pass -Dapp.url=http://localhost:8080```
* Для PostgreSQL: ```./gradlew clean test -Ddb.url=jdbc:postgresql://localhost:5432/postgres -Dlogin=app -Dpassword=pass -Dapp.url=http://localhost:8080```
4. Для запуска и просмотра отчета "Allure" по результатам тестирования выполнить команду:
   ```./gradlew allureReport```, затем ```./gradlew allureServe```
  
