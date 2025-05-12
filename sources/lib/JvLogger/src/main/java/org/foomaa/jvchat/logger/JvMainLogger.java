package org.foomaa.jvchat.logger;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;


class JvMainLogger {
    private static Logger logger;

    JvMainLogger() {
        logger = LoggerFactory.getLogger(JvMainLogger.class);
    }

    public void Debug(String text) {
        logger.debug(text);
    }

    public void Info(String text) {
        logger.info(text);
    }

    public void Warn(String text) {
        logger.warn(text);
    }

    public void Error(String text) {
        logger.error(text);
    }

    public void Trace(String text) {
        logger.trace(text);
    }
}