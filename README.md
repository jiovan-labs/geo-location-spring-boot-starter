[English](README.md) | [简体中文](README.zh-CN.md) | [繁體中文](README.zh-TW.md)

# Geo Location Spring Boot Starter

Spring Boot application integrates `GeoLocation` quickly, to provide API for the `GeoIP2` and `GeoLite2` [web services](https://dev.maxmind.com/geoip/docs/web-services?lang=en) and [databases](https://dev.maxmind.com/geoip/docs/databases?lang=en).

## Quickstart

- Import dependencies

```xml
    <dependency>
        <groupId>com.jiovan.spring.starter</groupId>
        <artifactId>geo-location-spring-boot-starter</artifactId>
        <version>LATEST</version>
    </dependency>
```

> By default, this starter will auto take effect, you can turn it off by `jiovan.geo-location.enabled = false`

- Configure Spring Boot `application.yml` with prefix `jiovan.geo-location`

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

> If you're using `local-file` only, comment `remote-site` node.

- Configure your beans with a `GeoLocationResolver` bean by constructor or `@Autowired`/`@Resource` annotation, then you can resolve locations with it as following:

| Method Return | Method Name         |
|---------------|---------------------|
| String        | getCompositeAddress |
| String        | getCountryName      |
| String        | getCityName         |

## Document

- Github: https://github.com/jiovan-labs/geo-location-spring-boot-starter
- Geoip2 github: https://github.com/maxmind/GeoIP2-java
- Geoip2 homepage: https://www.maxmind.com/en/geoip2-databases
- Maxmind database: https://www.maxmind.com/en/geoip2-services-and-databases
- Geolite2 free data: https://www.maxmind.com/en/accounts/current/geoip/downloads

## Requirement

- jdk 17+

## License

This project is under the [Apache License 2.0](https://www.apache.org/licenses/LICENSE-2.0)

See the `NOTICE.txt` file for required notices and attributions.

## Donation

You like this package? Then [donate to us](https://jiovan.com/donation) to support the development.

## Copyright

Beijing Jiovan Technology Ltd.

## Website

- Jiovan: https://jiovan.com
