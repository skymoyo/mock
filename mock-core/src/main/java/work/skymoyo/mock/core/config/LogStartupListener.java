package work.skymoyo.mock.core.config;

import ch.qos.logback.classic.Level;
import ch.qos.logback.classic.Logger;
import ch.qos.logback.classic.LoggerContext;
import ch.qos.logback.classic.spi.LoggerContextListener;
import ch.qos.logback.core.Context;
import ch.qos.logback.core.spi.ContextAwareBase;
import ch.qos.logback.core.spi.LifeCycle;

/**
 * 项目名称:      skymoyo-common
 * 模块名称:
 * 说明:
 * JDK 版本:      1.8
 * 作者(@author): skymoyo
 * 创建日期:      2020/9/7 19:39
 */
public class LogStartupListener extends ContextAwareBase implements LoggerContextListener, LifeCycle {

    private boolean started = false;

    /**
     * logback.xml用到的${appName}
     */
    private static final String APP_NAME_KEY = "appName";

    /**
     * logback.xml用到的${log.path}
     */
    private static final String LOG_FOLDER_KEY = "log.path";

    @Override
    public boolean isResetResistant() {
        return true;
    }

    @Override
    public void onStart(LoggerContext loggerContext) {

    }

    @Override
    public void onReset(LoggerContext loggerContext) {

    }

    @Override
    public void onStop(LoggerContext loggerContext) {

    }

    @Override
    public void onLevelChange(Logger logger, Level level) {

    }

    @Override
    public void start() {
        if (started) {
            return;
        }
        started = true;
        Context context = getContext();
        context.putProperty("tid", "");
    }

    @Override
    public void stop() {

    }

    @Override
    public boolean isStarted() {
        return started;
    }
}
