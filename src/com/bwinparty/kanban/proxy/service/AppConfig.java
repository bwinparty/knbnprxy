/*
 * knbnprxy - Kanban Proxy
 *
 * Copyright 2014   bwin.party digital entertainment plc
 *                  http://www.bwinparty.com
 * Developer: Lukas Prettenthaler
 */
package com.bwinparty.kanban.proxy.service;

import org.apache.commons.configuration.ConfigurationException;
import org.apache.commons.configuration.XMLConfiguration;
import org.apache.commons.configuration.reloading.FileChangedReloadingStrategy;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * Created by zyclonite on 24/03/14.
 */
public class AppConfig extends XMLConfiguration {
    private static AppConfig ourInstance;
    private static final Logger LOG = LoggerFactory.getLogger(AppConfig.class);

    static {
        ourInstance = new AppConfig("config.xml");
    }

    public static AppConfig getInstance() {
        return ourInstance;
    }

    private AppConfig(final String fileName) {
        super();
        this.setReloadingStrategy(new FileChangedReloadingStrategy());
        this.setDelimiterParsingDisabled(true);
        init(fileName);
    }

    private void init(final String fileName) {
        setFileName(fileName);
        try {
            load();
            LOG.debug("Configuration loaded");
        } catch (ConfigurationException ex) {
            LOG.error("Configuration not loaded!");
        }
    }
}
