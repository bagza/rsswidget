CREATE TABLE feeds (
    feed_guid text NOT NULL PRIMARY KEY,
    feed_title text NOT NULL,
    feed_description text NOT NULL,
    feed_url text NOT NULL,
    channel_url text NOT NULL,
    feed_pubdate integer
);

CREATE TABLE channels (
    channel_url text NOT NULL PRIMARY KEY
);

CREATE TABLE settings (
    widget_id integer NOT NULL PRIMARY KEY,
    channel_url text NOT NULL
);

CREATE TABLE view_states (
    widget_id integer NOT NULL PRIMARY KEY,
    last_guid text NOT NULL,
    last_position integer NOT NULL,
    FOREIGN KEY (widget_id) REFERENCES settings(widget_id) ON DELETE CASCADE  ON UPDATE CASCADE
);