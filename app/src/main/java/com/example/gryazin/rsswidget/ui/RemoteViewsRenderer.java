package com.example.gryazin.rsswidget.ui;

import android.app.PendingIntent;
import android.appwidget.AppWidgetManager;
import android.content.Context;
import android.content.Intent;
import android.text.Html;
import android.view.View;
import android.widget.RemoteViews;

import com.example.gryazin.rsswidget.R;
import com.example.gryazin.rsswidget.data.update.WidgetsRefreshService;

import java.text.SimpleDateFormat;
import java.util.Date;

import javax.inject.Inject;

/**
 * Created by Dmitry Gryazin on 17.11.2016.
 */

public class RemoteViewsRenderer {
    private Context context;

    @Inject
    public RemoteViewsRenderer(Context context) {
        this.context = context;
    }

    public RemoteViews getRenderedViews(int appWidgetId, FeedViewModel viewModel){
        RemoteViews remoteViews = new RemoteViews(context.getPackageName(), R.layout.single_item_rss_widget);
        renderViewsWithViewModel(remoteViews, viewModel);
        renderButtons(remoteViews, appWidgetId, viewModel);
        return remoteViews;
    }

    private void renderViewsWithViewModel(RemoteViews remoteViews, FeedViewModel viewModel){
        String timeUpdateString;
        if (viewModel.maybeGetTimestamp().isPresent()){
            SimpleDateFormat timeFormat = new SimpleDateFormat("kk:mm:ss");
            timeUpdateString = timeFormat.format(new Date(viewModel.maybeGetTimestamp().get()));
        }
        else {
            timeUpdateString = "...";
        }
        remoteViews.setTextViewText(R.id.text_title, viewModel.getTitle());
        remoteViews.setTextViewText(R.id.text_subtitle, context.getString(R.string.feed_update_string, timeUpdateString));
        remoteViews.setTextViewText(R.id.text_body, Html.fromHtml(viewModel.getBody()));
    }

    //TODO refactor duplicate code
    //Wisdom Use widgetId as request code, so intent are different from different widgets, overwritten otherwise.
    private void renderButtons(RemoteViews remoteViews, int appWidgetId, FeedViewModel viewModel){
        if (viewModel.hasNext()){
            Intent intent=new Intent(context, WidgetsRefreshService.class);
            intent.putExtra(AppWidgetManager.EXTRA_APPWIDGET_ID, appWidgetId);
            intent.setAction(WidgetsRefreshService.ACTION_SHOW_NEXT);
            PendingIntent pi = PendingIntent.getService(context, appWidgetId, intent, PendingIntent.FLAG_UPDATE_CURRENT);
            remoteViews.setOnClickPendingIntent(R.id.button_right, pi);
            remoteViews.setViewVisibility(R.id.button_right, View.VISIBLE);
        }
        else {
            remoteViews.setViewVisibility(R.id.button_right, View.GONE);
        }

        if (viewModel.hasPrev()){
            Intent intent=new Intent(context, WidgetsRefreshService.class);
            intent.putExtra(AppWidgetManager.EXTRA_APPWIDGET_ID, appWidgetId);
            intent.setAction(WidgetsRefreshService.ACTION_SHOW_PREV);
            PendingIntent pi = PendingIntent.getService(context, appWidgetId, intent, PendingIntent.FLAG_UPDATE_CURRENT);
            remoteViews.setOnClickPendingIntent(R.id.button_left, pi);
            remoteViews.setViewVisibility(R.id.button_left, View.VISIBLE);
        }
        else {
            remoteViews.setViewVisibility(R.id.button_left, View.GONE);
        }
    }
}
