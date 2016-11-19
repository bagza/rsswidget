package com.example.gryazin.rsswidget.data.network;

import android.app.IntentService;
import android.content.Intent;
import android.util.Log;

import com.example.gryazin.rsswidget.RssApplication;
import com.example.gryazin.rsswidget.data.Repository;
import com.example.gryazin.rsswidget.data.update.UpdateScheduler;
import com.example.gryazin.rsswidget.domain.FeedItem;
import com.example.gryazin.rsswidget.domain.RssSettings;

import java.io.IOException;
import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Iterator;
import java.util.List;

import javax.inject.Inject;

import static android.content.ContentValues.TAG;

/**
 * Created by bag on 19.11.16.
 */

public class NetworkFetchService extends IntentService {

    private static final int NET_CONNECT_TIMEOUT_MILLIS = 15000;  // 15 seconds
    private static final int NET_READ_TIMEOUT_MILLIS = 10000;  // 10 seconds

    @Inject
    Repository repository;
    @Inject
    UpdateScheduler updateScheduler;

    public NetworkFetchService() {
        super("com.example.gryazin.rsswidget.data.network.NetworkFetchService");
        inject();
    }

    private void inject(){
        RssApplication.getAppComponent().inject(this);
    }

    @Override
    protected void onHandleIntent(Intent intent) {
        Collection<? extends RssSettings> allSettings = repository.getAllSettings();
        fetchAndSaveAllFeeds(allSettings);
        repository.saveTimestamp(System.currentTimeMillis());
        kickUpdateForAll(allSettings);
    }

    private void fetchAndSaveAllFeeds(Collection<? extends RssSettings> allSettings){
        for (RssSettings settings : allSettings){
            String channelUrl = settings.getRssUrl();
            Collection<FeedPojo> feedPojos = fetchFeedsByUrl(channelUrl);
            Log.d("FETCH RESULT", "feedPojos:, " + (feedPojos != null && feedPojos.size() > 0 ? ((FeedPojo)feedPojos.toArray()[0]).getTitle() : ""));
            Collection<FeedItem> feeds = FeedPojoMapper.mapAll(feedPojos, channelUrl);
            repository.storeFeeds(feeds);
        }
    }

    private void kickUpdateForAll(Collection<? extends RssSettings> allSettings){
        int[] ids = new int[allSettings.size()];
        Iterator<? extends RssSettings> iterator = allSettings.iterator();
        int pos = 0;
        while (iterator.hasNext()){
            ids[pos++] = iterator.next().getAppWidgetId();
        }
        updateScheduler.refreshAllWidgets(ids);

    }

    private Collection<FeedPojo> fetchFeedsByUrl(String url) throws NetworkFetchException {
        Log.i(TAG, "Beginning network synchronization");
        try {
            final URL location = new URL(url);
            InputStream stream = null;
            try {
                Log.i(TAG, "Streaming data from network: " + location);
                stream = downloadUrl(location);
                return new RssFeedParser().parseAndClose(stream);
                // Makes sure that the InputStream is closed after the app is
                // finished using it.
            }
            finally {
                if (stream != null) {
                    stream.close();
                }
            }
        } catch (Exception e) {
            throw new NetworkFetchException("Url: " + url, e);
        }
        //Log.i(TAG, "Network synchronization complete");
    }

    private InputStream downloadUrl(final URL url) throws IOException {
        HttpURLConnection conn = (HttpURLConnection) url.openConnection();
        conn.setReadTimeout(NET_READ_TIMEOUT_MILLIS);
        conn.setConnectTimeout(NET_CONNECT_TIMEOUT_MILLIS);
        conn.setRequestMethod("GET");
        conn.setDoInput(true);
        // Starts the query
        conn.connect();
        return conn.getInputStream();
    }
}
