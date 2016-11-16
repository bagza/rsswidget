CREATE TABLE feeds (
    _id integer AUTOINCREMENT NOT NULL,
    feed_guid text NOT NULL PRIMARY KEY,
    feed_title text NOT NULL,
    feed_description text NOT NULL,
    feed_url text NOT NULL,
    channel_url text NOT NULL,
    feed_pubdate integer
);

CREATE TABLE channels (
    _id integer AUTOINCREMENT NOT NULL,
    channel_url text NOT NULL PRIMARY KEY
);

CREATE TABLE settings (
    _id integer AUTOINCREMENT NOT NULL,
    widget_id integer NOT NULL PRIMARY KEY,
    channel_url text NOT NULL
    FOREIGN KEY (channel_url) REFERENCES channels(channel_url) ON DELETE CASCADE  ON UPDATE CASCADE
);

CREATE TABLE view_states (
    _id integer AUTOINCREMENT NOT NULL,
    widget_id integer NOT NULL PRIMARY KEY,
    last_guid text NOT NULL,
    last_position integer NOT NULL
    FOREIGN KEY (widget_id) REFERENCES settings(widget_id) ON DELETE CASCADE  ON UPDATE CASCADE
);