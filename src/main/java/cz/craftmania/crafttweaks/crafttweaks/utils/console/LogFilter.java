package cz.craftmania.crafttweaks.crafttweaks.utils.console;

import cz.craftmania.crafttweaks.crafttweaks.Main;
import org.apache.logging.log4j.Level;
import org.apache.logging.log4j.Marker;
import org.apache.logging.log4j.core.Filter;
import org.apache.logging.log4j.core.LifeCycle;
import org.apache.logging.log4j.core.LogEvent;
import org.apache.logging.log4j.core.Logger;
import org.apache.logging.log4j.message.Message;

import java.util.Objects;

public class LogFilter implements Filter {
    private Main plugin;

    public LogFilter(final Main plugin) {
        this.plugin = plugin;
    }

    public Filter.Result checkMessage(final String message) {
        if (!plugin.getStringList("console.hide-message").isEmpty()) {
            for (final String s : Objects.requireNonNull(plugin.getStringList("console.hide-message"))) {
                if (message.contains(s)) {
                    this.plugin.getEng().addHiddenMsg();
                    return Filter.Result.DENY;
                }
            }
        }
        return Filter.Result.NEUTRAL;
    }

    public LifeCycle.State getState() {
        try {
            return LifeCycle.State.STARTED;
        } catch (Exception ex) {
            return null;
        }
    }

    public void initialize() {
    }

    public boolean isStarted() {
        return true;
    }

    public boolean isStopped() {
        return false;
    }

    public void start() {
    }

    public void stop() {
    }

    @Override
    public Result filter(Logger logger, Level level, Marker marker, String msg, Object... params) {
        return this.checkMessage(msg);
    }

    @Override
    public Result filter(Logger logger, Level level, Marker marker, String message, Object p0) {
        return this.checkMessage(message);
    }

    @Override
    public Result filter(Logger logger, Level level, Marker marker, String message, Object p0, Object p1) {
        return this.checkMessage(message);
    }

    @Override
    public Result filter(Logger logger, Level level, Marker marker, String message, Object p0, Object p1, Object p2) {
        return this.checkMessage(message);
    }

    @Override
    public Result filter(Logger logger, Level level, Marker marker, String message, Object p0, Object p1, Object p2, Object p3) {
        return this.checkMessage(message);
    }

    @Override
    public Result filter(Logger logger, Level level, Marker marker, String message, Object p0, Object p1, Object p2, Object p3, Object p4) {
        return this.checkMessage(message);
    }

    @Override
    public Result filter(Logger logger, Level level, Marker marker, String message, Object p0, Object p1, Object p2, Object p3, Object p4, Object p5) {
        return null;
    }

    @Override
    public Result filter(Logger logger, Level level, Marker marker, String message, Object p0, Object p1, Object p2, Object p3, Object p4, Object p5, Object p6) {
        return null;
    }

    @Override
    public Result filter(Logger logger, Level level, Marker marker, String message, Object p0, Object p1, Object p2, Object p3, Object p4, Object p5, Object p6, Object p7) {
        return this.checkMessage(message);
    }

    @Override
    public Result filter(Logger logger, Level level, Marker marker, String message, Object p0, Object p1, Object p2, Object p3, Object p4, Object p5, Object p6, Object p7, Object p8) {
        return this.checkMessage(message);
    }

    @Override
    public Result filter(Logger logger, Level level, Marker marker, String message, Object p0, Object p1, Object p2, Object p3, Object p4, Object p5, Object p6, Object p7, Object p8, Object p9) {
        return this.checkMessage(message);
    }

    @Override
    public Result filter(Logger logger, Level level, Marker marker, Object msg, Throwable t) {
        return this.checkMessage(msg.toString());
    }

    @Override
    public Result filter(Logger logger, Level level, Marker marker, Message msg, Throwable t) {
        return this.checkMessage(msg.getFormattedMessage());
    }

    @Override
    public Result filter(LogEvent event) {
        return this.checkMessage(event.getMessage().getFormattedMessage());
    }

    public Filter.Result getOnMatch() {
        return Filter.Result.NEUTRAL;
    }

    public Filter.Result getOnMismatch() {
        return Filter.Result.NEUTRAL;
    }
}

