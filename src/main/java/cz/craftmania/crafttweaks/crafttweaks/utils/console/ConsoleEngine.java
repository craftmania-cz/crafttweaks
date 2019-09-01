package cz.craftmania.crafttweaks.crafttweaks.utils.console;

import cz.craftmania.crafttweaks.crafttweaks.Main;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.core.Logger;

public class ConsoleEngine implements EngineInterface {
    private Main plugin;
    private int msgHidden;

    public ConsoleEngine(final Main csf) {
        this.msgHidden = 0;
        this.plugin = csf;
    }

    @Override
    public int getHiddenMessagesCount() {
        return this.msgHidden;
    }

    @Override
    public void addHiddenMsg() {
        ++this.msgHidden;
    }

    @Override
    public void hideConsoleMessages() {
        ((Logger) LogManager.getRootLogger()).addFilter(new LogFilter(this.plugin));
    }
}
