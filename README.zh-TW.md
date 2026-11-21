[English](README.md) | [簡體中文](README.zh-CN.md) | [繁體中文](README.zh-TW.md)

# 地理位置 Spring Boot Starter

讓 Spring Boot 應用快速整合 `GeoLocation`，為 `GeoIP2` 與 `GeoLite2` 的[網路服務](https://dev.maxmind.com/geoip/docs/web-services?lang=en)和[資料庫](https://dev.maxmind.com/geoip/docs/databases?lang=en)提供 API。

## 快速開始

- 匯入相依性

```xml
    <dependency>
        <groupId>com.jiovan.spring.starter</groupId>
        <artifactId>geo-location-spring-boot-starter</artifactId>
        <version>LATEST</version>
    </dependency>
```

> 預設情況下，此 starter 會自動生效，你可以透過 `jiovan.geo-location.enabled = false` 關閉它。

- 使用字首 `jiovan.geo-location` 設定 Spring Boot 的 `application.yml`

```yml
jiovan:
    geo-location:
        local-file:
            country-db: 'classpath:/ipaddr/GeoLite2-Country_20220701/GeoLite2-Country.mmdb'
            city-db: 'classpath:/ipaddr/GeoLite2-City_20220701/GeoLite2-City.mmdb'
            asn-db: 'classpath:/ipaddr/GeoLite2-ASN_20220701/GeoLite2-ASN.mmdb'
        remote-site:
            account-id: 999999
            license-key: 'foobar'
```

> 如果只使用 `local-file`，請將 `remote-site` 節點註釋掉。

- 透過建構函式或 `@Autowired`/`@Resource` 註解注入一個 `GeoLocationResolver` Bean，然後即可用它解析位置資訊，如下所示：

| 方法返回值 | 方法名 |
|---------------|---------------------|
| String | getCompositeAddress |
| String | getCountryName |
| String | getCityName |

## 文件

- GitHub：https://github.com/jiovan-labs/geo-location-spring-boot-starter
- Geoip2 GitHub：https://github.com/maxmind/GeoIP2-java
- Geoip2 主頁：https://www.maxmind.com/en/geoip2-databases
- MaxMind 資料庫：https://www.maxmind.com/en/geoip2-services-and-databases
- GeoLite2 免費資料：https://www.maxmind.com/en/accounts/current/geoip/downloads

## 環境要求

- jdk 17+

## 許可證

本專案採用 [Apache License 2.0](https://www.apache.org/licenses/LICENSE-2.0) 許可證。

請參閱 `NOTICE.txt` 檔案瞭解所需的宣告與歸屬資訊。

## 捐贈

喜歡這個工具包嗎？歡迎[向我們捐贈](https://jiovan.com/donation)以支援開發。

## 版權

北京玖漫科技有限公司

## 網站

- 玖漫：https://jiovan.com
