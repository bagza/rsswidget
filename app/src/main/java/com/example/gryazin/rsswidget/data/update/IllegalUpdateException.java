package com.example.gryazin.rsswidget.data.update;

/**
 * Created by Dmitry Gryazin on 20.11.2016.
 *
 * means there was illegal update call to receiver while the settings were not set yet.
 *
 * * I think the doc lies that "the system will not send the ACTION_APPWIDGET_UPDATE broadcast when a configuration Activity is launched".
 * I DO recieve it on both genymotion and AVD. And that's sad.
 *
 * Need to ignore the first update OR handle this exception somewhere.
 */
public class IllegalUpdateException extends IllegalArgumentException {
    public IllegalUpdateException(int widgetId) {
        super("Illegal update called for widget " + widgetId);
    }
}
