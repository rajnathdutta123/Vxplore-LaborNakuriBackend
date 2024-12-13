package com.app.LaborNakuriBackend.config;

import org.apache.logging.log4j.core.Appender;
import org.apache.logging.log4j.core.Core;
import org.apache.logging.log4j.core.Filter;
import org.apache.logging.log4j.core.LogEvent;
import org.apache.logging.log4j.core.appender.AbstractAppender;
import org.apache.logging.log4j.core.config.Property;
import org.apache.logging.log4j.core.config.plugins.Plugin;
import org.apache.logging.log4j.core.config.plugins.PluginAttribute;
import org.apache.logging.log4j.core.config.plugins.PluginElement;
import org.apache.logging.log4j.core.config.plugins.PluginFactory;
import org.apache.logging.log4j.core.filter.ThresholdFilter;

@Plugin(name = "ExceptionAppender", category = Core.CATEGORY_NAME, elementType = Appender.ELEMENT_TYPE, printObject = true)
public class ExceptionAppender extends AbstractAppender {

    public ExceptionAppender(String name, Filter filter) {
        super(name, filter, null, true, Property.EMPTY_ARRAY);
    }

    @PluginFactory
    public static ExceptionAppender createAppender(@PluginAttribute("name") String name,
                                                   @PluginElement("ThresholdFilter") ThresholdFilter filter) {
        return new ExceptionAppender(name, filter);
    }

    public void append(LogEvent event) {
        System.out.println("Exception Appender invoked... ");
        if (event.getLevel().equals(org.apache.logging.log4j.Level.ERROR)) {
            System.out.println(event.getThrown());
            System.out.println(event.getSource());
            // Write log message to custom destination (e.g., file, database, etc.)
            System.out.println("Writing log message to external destination ...");
        }
    }
}