package com.app.LaborNakuriBackend.config;

import org.apache.logging.log4j.core.Appender;
import org.apache.logging.log4j.core.Layout;
import org.apache.logging.log4j.core.LogEvent;
import org.apache.logging.log4j.core.appender.AbstractAppender;
import org.apache.logging.log4j.core.config.plugins.Plugin;
import org.apache.logging.log4j.core.config.plugins.PluginAttribute;
import org.apache.logging.log4j.core.config.plugins.PluginElement;
import org.apache.logging.log4j.core.config.plugins.PluginFactory;
import org.apache.logging.log4j.core.layout.PatternLayout;

import java.io.Serializable;

@Plugin(name = "CustomAppender", category = "Core", elementType = Appender.ELEMENT_TYPE)
public class CustomAppender extends AbstractAppender {

    protected CustomAppender(String name, Layout<? extends Serializable> layout) {
        super(name, null, layout);
    }

    @Override
    public void append(LogEvent event) {
        // Custom behavior - for example, print to console with a custom prefix
        String message = new String(getLayout().toByteArray(event));
        System.out.println("Custom Log: " + message);
    }

    // Factory method to create the CustomAppender
    @PluginFactory
    public static Appender createAppender(
            @PluginAttribute("name") String name,
            @PluginElement("Layout") Layout<? extends Serializable> layout) {
        if (layout == null) {
            layout = PatternLayout.createDefaultLayout();
        }
        return new CustomAppender(name, layout);
    }
}