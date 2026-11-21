[English](README.md) | [简体中文](README.zh-CN.md) | [繁體中文](README.zh-TW.md)

# 地理位置 Spring Boot Starter

让 Spring Boot 应用快速集成 `GeoLocation`，为 `GeoIP2` 与 `GeoLite2` 的[网络服务](https://dev.maxmind.com/geoip/docs/web-services?lang=en)和[数据库](https://dev.maxmind.com/geoip/docs/databases?lang=en)提供 API。

## 快速开始

- 导入依赖

```xml
    <dependency>
        <groupId>com.jiovan.spring.starter</groupId>
        <artifactId>geo-location-spring-boot-starter</artifactId>
        <version>LATEST</version>
    </dependency>
```

> 默认情况下，此 starter 会自动生效，你可以通过 `jiovan.geo-location.enabled = false` 关闭它。

- 使用前缀 `jiovan.geo-location` 配置 Spring Boot 的 `application.yml`

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

> 如果只使用 `local-file`，请将 `remote-site` 节点注释掉。

- 通过构造函数或 `@Autowired`/`@Resource` 注解注入一个 `GeoLocationResolver` Bean，然后即可用它解析位置信息，如下所示：

| 方法返回值 | 方法名 |
|---------------|---------------------|
| String | getCompositeAddress |
| String | getCountryName |
| String | getCityName |

## 文档

- GitHub：https://github.com/jiovan-labs/geo-location-spring-boot-starter
- Geoip2 GitHub：https://github.com/maxmind/GeoIP2-java
- Geoip2 主页：https://www.maxmind.com/en/geoip2-databases
- MaxMind 数据库：https://www.maxmind.com/en/geoip2-services-and-databases
- GeoLite2 免费数据：https://www.maxmind.com/en/accounts/current/geoip/downloads

## 环境要求

- jdk 17+

## 许可证

本项目采用 [Apache License 2.0](https://www.apache.org/licenses/LICENSE-2.0) 许可证。

请参阅 `NOTICE.txt` 文件了解所需的声明与归属信息。

## 捐赠

喜欢这个工具包吗？欢迎[向我们捐赠](https://jiovan.com/donation)以支持开发。

## 版权

北京玖漫科技有限公司

## 网站

- 玖漫：https://jiovan.com
