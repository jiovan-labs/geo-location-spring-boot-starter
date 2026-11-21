/*
 * Copyright (c) 2020 Jiovan Ltd. All rights reserved.
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *     https://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package com.jiovan.spring.starter.geolocation.config;


import java.io.IOException;
import org.apache.commons.lang3.BooleanUtils;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.boot.autoconfigure.condition.ConditionalOnBooleanProperty;
import org.springframework.boot.autoconfigure.condition.ConditionalOnMissingBean;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.util.CollectionUtils;
import com.maxmind.geoip2.DatabaseReader;
import com.maxmind.geoip2.WebServiceClient;
import com.jiovan.plexus.common.toolkit.util.InetProxyWraps;
import com.jiovan.plexus.common.toolkit.util.NumberUtilsWraps;
import com.jiovan.spring.starter.geolocation.composer.GeoLocationResolver;
import com.jiovan.spring.starter.geolocation.composer.impl.DefaultGeoLocationResolver;
import com.jiovan.spring.starter.geolocation.enumeration.GeoDatabaseType;
import com.jiovan.spring.starter.geolocation.property.GeoLocationProperties;
import com.jiovan.spring.starter.geolocation.util.GeoDatabaseUtils;
import jakarta.annotation.Nonnull;
import jakarta.annotation.Nullable;


/**
 * Configuration for GEO location
 *
 * @author David Hsing
 *
 * @see "https://dev.maxmind.com/geoip/geolite2-free-geolocation-data"
 * @see "https://maxmind.github.io/GeoIP2-java/"
 */
@Configuration(proxyBeanMethods = false)
@EnableConfigurationProperties(value = GeoLocationProperties.class)
@ConditionalOnBooleanProperty(prefix = GeoLocationAutoConfiguration.PROPERTIES_PREFIX, name = "enabled", matchIfMissing = true)
public class GeoLocationAutoConfiguration {
    public static final String PROPERTIES_PREFIX = "jiovan.geo-location";    // $NON-NLS-1$
    public static final String COUNTRY_DATABASE_READER = "geoCountryDatabaseReader";    // $NON-NLS-1$
    public static final String CITY_DATABASE_READER = "geoCityDatabaseReader";    // $NON-NLS-1$
    public static final String ASN_DATABASE_READER = "geoAsnDatabaseReader";    // $NON-NLS-1$
    public static final String WEB_SERVICE_CLIENT = "geoWebServiceClient";    // $NON-NLS-1$
    public static final String LOCATION_RESOLVER = "geoLocationResolver";    // $NON-NLS-1$

    /**
     * Returns a {@link com.maxmind.geoip2.DatabaseReader} for the local country database
     *
     * @param properties the geo location properties providing the country database location and cache settings
     *
     * @return the country {@link com.maxmind.geoip2.DatabaseReader}
     *
     * @throws java.io.IOException if the country database cannot be opened or read
     */

    @Bean(name = COUNTRY_DATABASE_READER)
    @ConditionalOnMissingBean(name = COUNTRY_DATABASE_READER)
    @ConditionalOnBooleanProperty(prefix = PROPERTIES_PREFIX + ".local-file", name = "enabled", matchIfMissing = true)
    @ConditionalOnProperty(prefix = PROPERTIES_PREFIX + ".local-file", name = "country-db")
    public DatabaseReader countryDatabaseReader(@Nonnull GeoLocationProperties properties) throws IOException {
        return GeoDatabaseUtils.getDatabaseReader(properties, GeoDatabaseType.COUNTRY);
    }

    /**
     * Returns a {@link com.maxmind.geoip2.DatabaseReader} for the local city database
     *
     * @param properties the geo location properties providing the city database location and cache settings
     *
     * @return the city {@link com.maxmind.geoip2.DatabaseReader}
     *
     * @throws java.io.IOException if the city database cannot be opened or read
     */

    @Bean(name = CITY_DATABASE_READER)
    @ConditionalOnMissingBean(name = CITY_DATABASE_READER)
    @ConditionalOnBooleanProperty(prefix = PROPERTIES_PREFIX + ".local-file", name = "enabled", matchIfMissing = true)
    @ConditionalOnProperty(prefix = PROPERTIES_PREFIX + ".local-file", name = "city-db")
    public DatabaseReader cityDatabaseReader(@Nonnull GeoLocationProperties properties) throws IOException {
        return GeoDatabaseUtils.getDatabaseReader(properties, GeoDatabaseType.CITY);
    }

    /**
     * Returns a {@link com.maxmind.geoip2.DatabaseReader} for the local ASN database
     *
     * @param properties the geo location properties providing the ASN database location and cache settings
     *
     * @return the ASN {@link com.maxmind.geoip2.DatabaseReader}
     *
     * @throws java.io.IOException if the ASN database cannot be opened or read
     */

    @Bean(name = ASN_DATABASE_READER)
    @ConditionalOnMissingBean(name = ASN_DATABASE_READER)
    @ConditionalOnBooleanProperty(prefix = PROPERTIES_PREFIX + ".local-file", name = "enabled", matchIfMissing = true)
    @ConditionalOnProperty(prefix = PROPERTIES_PREFIX + ".local-file", name = "asn-db")
    public DatabaseReader asnDatabaseReader(@Nonnull GeoLocationProperties properties) throws IOException {
        return GeoDatabaseUtils.getDatabaseReader(properties, GeoDatabaseType.ASN);
    }

    /**
     * Returns a {@link com.maxmind.geoip2.WebServiceClient} configured from the remote site settings
     *
     * @param properties the geo location properties providing the account ID, license key, host, timeouts and proxy settings
     *
     * @return the configured {@link com.maxmind.geoip2.WebServiceClient}
     */

    @Bean(name = WEB_SERVICE_CLIENT)
    @ConditionalOnMissingBean(name = WEB_SERVICE_CLIENT)
    @ConditionalOnBooleanProperty(prefix = PROPERTIES_PREFIX + ".remote-site", name = "enabled", matchIfMissing = true)
    @ConditionalOnProperty(prefix = PROPERTIES_PREFIX + ".remote-site", name = {"account-id", "license-key"})
    public WebServiceClient webServiceClient(@Nonnull GeoLocationProperties properties) {
        GeoLocationProperties.RemoteSite remoteSite = properties.getRemoteSite();
        WebServiceClient.Builder builder = new WebServiceClient.Builder(remoteSite.getAccountId(), remoteSite.getLicenseKey());
        if (StringUtils.isNotBlank(remoteSite.getHost())) {
            builder.host(remoteSite.getHost());
        }
        if (NumberUtilsWraps.isPositive(remoteSite.getPort())) {
            builder.port(remoteSite.getPort());
        }
        if (BooleanUtils.isFalse(remoteSite.getUseHttps())) {
            builder.disableHttps();
        }
        if (!CollectionUtils.isEmpty(remoteSite.getLocales())) {
            builder.locales(remoteSite.getLocales());
        }
        if (remoteSite.getConnectTimeout() != null) {
            builder.connectTimeout(remoteSite.getConnectTimeout());
        }
        if (remoteSite.getRequestTimeout() != null) {
            builder.requestTimeout(remoteSite.getRequestTimeout());
        }
        if (StringUtils.isNotBlank(remoteSite.getProxyHost()) && NumberUtilsWraps.isPositive(remoteSite.getProxyPort())) {
            builder.proxy(InetProxyWraps.ofProxySelector(remoteSite.getProxyHost(), remoteSite.getProxyPort()));
        }
        return builder.build();
    }

    /**
     * Returns a {@link com.jiovan.spring.starter.geolocation.composer.GeoLocationResolver} composed of the given database readers and web service client
     *
     * @param properties the geo location properties whose {@code discard-lan} setting controls whether LAN addresses are discarded
     * @param countryReader the country database reader, may be {@code null}
     * @param cityReader the city database reader, may be {@code null}
     * @param webClient the web service client, may be {@code null}
     *
     * @return the configured {@link com.jiovan.spring.starter.geolocation.composer.GeoLocationResolver}
     */

    @Bean(name = LOCATION_RESOLVER)
    @ConditionalOnMissingBean(name = LOCATION_RESOLVER)
    public GeoLocationResolver locationResolver(@Nonnull GeoLocationProperties properties, @Nullable @Qualifier(value = COUNTRY_DATABASE_READER) DatabaseReader countryReader, @Nullable @Qualifier(value = CITY_DATABASE_READER) DatabaseReader cityReader, @Nullable @Qualifier(value = WEB_SERVICE_CLIENT) WebServiceClient webClient) {
        return new DefaultGeoLocationResolver(countryReader, cityReader, webClient, BooleanUtils.isTrue(properties.getDiscardLan()));
    }
}
