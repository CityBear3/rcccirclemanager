[![CI](https://github.com/CityBear3/rcccirclemanager/actions/workflows/github-ci.yml/badge.svg)](https://github.com/CityBear3/rcccirclemanager/actions/workflows/github-ci.yml)
# RCC CIRCLE MANAGER

---

## 説明
このアプリケーションは立命館コンピュータクラブ向けのサークル管理サービスのサーバーサイドです。

## 環境構築
アプリケーションを実行するにはJava17が必要です。

### 実行
以下の環境変数をenvファイルに記載してください。
```dotenv
POSTGRES_DB={YOUR_DB_NAME}
POSTGRES_USER={YOUR_DB_USER}
POSTGRES_PASSWORD={YOUR_DB_PASSWORD}
POSTGRES_URL=jdbc:postgresql://{YOU_DB_HOST}:5432/${POSTGRES_DB}
REDIS_URL=redis://{YOUR_REDIS_HOST}:6379
ALLOWED_ORIGIN=http://localhost:3000
```
以下のコマンドでサーバーを起動
```commandline
./gradlew dockerBuild

docker compose up -d
```

### テスト
セッションストレージとテスト用データベースを起動する必要があります。
```commandline
./gradlew test
```

### データベースのマイグレーション
事前にデータベースを起動する必要があります。
```commandline
./gradlew flywayMigrate
```

[//]: # (## Micronaut 3.6.1 Documentation)

[//]: # ()
[//]: # (- [User Guide]&#40;https://docs.micronaut.io/3.6.1/guide/index.html&#41;)

[//]: # (- [API Reference]&#40;https://docs.micronaut.io/3.6.1/api/index.html&#41;)

[//]: # (- [Configuration Reference]&#40;https://docs.micronaut.io/3.6.1/guide/configurationreference.html&#41;)

[//]: # (- [Micronaut Guides]&#40;https://guides.micronaut.io/index.html&#41;)

[//]: # ()
[//]: # (---)

[//]: # ()
[//]: # (- [Shadow Gradle Plugin]&#40;https://plugins.gradle.org/plugin/com.github.johnrengelman.shadow&#41;)

[//]: # ()
[//]: # (## Feature jdbc-hikari documentation)

[//]: # ()
[//]: # (- [Micronaut Hikari JDBC Connection Pool documentation]&#40;https://micronaut-projects.github.io/micronaut-sql/latest/guide/index.html#jdbc&#41;)

[//]: # ()
[//]: # (## Feature security documentation)

[//]: # ()
[//]: # (- [Micronaut Security documentation]&#40;https://micronaut-projects.github.io/micronaut-security/latest/guide/index.html&#41;)

[//]: # ()
[//]: # (## Feature openapi documentation)

[//]: # ()
[//]: # (- [Micronaut OpenAPI Support documentation]&#40;https://micronaut-projects.github.io/micronaut-openapi/latest/guide/index.html&#41;)

[//]: # ()
[//]: # (- [https://www.openapis.org]&#40;https://www.openapis.org&#41;)

[//]: # ()
[//]: # (## Feature swagger-ui documentation)

[//]: # ()
[//]: # (- [Micronaut Swagger UI documentation]&#40;https://micronaut-projects.github.io/micronaut-openapi/latest/guide/index.html&#41;)

[//]: # ()
[//]: # (- [https://swagger.io/tools/swagger-ui/]&#40;https://swagger.io/tools/swagger-ui/&#41;)

[//]: # ()
[//]: # (## Feature test-resources documentation)

[//]: # ()
[//]: # (- [Micronaut Test Resources documentation]&#40;https://micronaut-projects.github.io/micronaut-test-resources/latest/guide/&#41;)

[//]: # ()
[//]: # (## Feature http-client documentation)

[//]: # ()
[//]: # (- [Micronaut HTTP Client documentation]&#40;https://docs.micronaut.io/latest/guide/index.html#httpClient&#41;)

[//]: # ()
[//]: # (## Feature data-jdbc documentation)

[//]: # ()
[//]: # (- [Micronaut Data JDBC documentation]&#40;https://micronaut-projects.github.io/micronaut-data/latest/guide/index.html#jdbc&#41;)

[//]: # ()
[//]: # (## Feature security-session documentation)

[//]: # ()
[//]: # (- [Micronaut Security Session documentation]&#40;https://micronaut-projects.github.io/micronaut-security/latest/guide/index.html#session&#41;)


