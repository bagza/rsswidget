package com.example.gryazin.rsswidget.data.network;

import android.text.format.Time;
import android.util.Xml;

import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.NodeList;
import org.xml.sax.SAXException;
import org.xmlpull.v1.XmlPullParser;
import org.xmlpull.v1.XmlPullParserException;

import java.io.IOException;
import java.io.InputStream;
import java.text.ParseException;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;

/**
 * Created by bag on 19.11.16.
 * Copypaste from google atom-sample
 */
public class RssFeedParser {
    // Constants indicting XML element names that we're interested in
    private static final int TAG_GUID = 1;
    private static final int TAG_TITLE = 2;
    private static final int TAG_DATE = 3;
    private static final int TAG_LINK = 4;
    private static final int TAG_DESCRIPTION = 5;

    // We don't use XML namespaces
    private static final String ns = null;

    /**
     * @throws org.xmlpull.v1.XmlPullParserException on error parsing feed.
     * @throws java.io.IOException on I/O error.
     */
    public List<FeedPojo> parseAndClose(InputStream in)
            throws XmlPullParserException, IOException, ParseException, ParserConfigurationException, SAXException {
        /*try {
            XmlPullParser parser = Xml.newPullParser();
            parser.setFeature(XmlPullParser.FEATURE_PROCESS_NAMESPACES, false);
            parser.setInput(in, null);
            parser.nextTag();
            return readFeed(parser);
        } finally {
            in.close();
        }*/

        DocumentBuilderFactory dbf = DocumentBuilderFactory
                .newInstance();
        DocumentBuilder db = dbf.newDocumentBuilder();

        //using db (Document Builder) parse xml data and assign
        //it to Element
        Document document = db.parse(in);
        Element element = document.getDocumentElement();

        //take rss nodes to NodeList
        NodeList nodeList = element.getElementsByTagName("item");
        List<FeedPojo> feeds = new ArrayList<>();

        if (nodeList.getLength() > 0) {
            for (int i = 0; i < nodeList.getLength(); i++) {

                Element entry = (Element) nodeList.item(i);
                Element _guidE = (Element) entry.getElementsByTagName("guid").item(0);
                Element _titleE = (Element) entry.getElementsByTagName("title").item(0);
                Element _descriptionE = (Element) entry.getElementsByTagName("description").item(0);
                Element _pubDateE = (Element) entry.getElementsByTagName("pubDate").item(0);
                Element _linkE = (Element) entry.getElementsByTagName("link").item(0);

                String _title = _titleE.getFirstChild().getNodeValue();
                String _description = _descriptionE.getFirstChild().getNodeValue();
                String _pubDate = _pubDateE.getFirstChild().getNodeValue();
                String _link = _linkE.getFirstChild().getNodeValue();
                String _guid = _guidE.getFirstChild().getNodeValue();

                //create RssItemObject and add it to the ArrayList
                FeedPojo feed = new FeedPojo(_title, _description, _pubDate, _guid, _link);

                feeds.add(feed);
            }
        }
        return feeds;
    }

    /**
     * Decode a feed attached to an XmlPullParser.
     *
     * @param parser Incoming XMl
     * @throws org.xmlpull.v1.XmlPullParserException on error parsing feed.
     * @throws java.io.IOException on I/O error.
     */
    private List<FeedPojo> readFeed(XmlPullParser parser)
            throws XmlPullParserException, IOException, ParseException {
        List<FeedPojo> items = new ArrayList<>();
        parser.require(XmlPullParser.START_TAG, ns, "item");
        while (parser.next() != XmlPullParser.END_TAG) {
            if (parser.getEventType() != XmlPullParser.START_TAG) {
                continue;
            }
            String name = parser.getName();

            if (name.equals("item")) {
                items.add(readEntry(parser));
            } else {
                skip(parser);
            }
        }
        return items;
    }

    /**
     * Parses the contents of an entry. If it encounters a title, summary, or link tag, hands them
     * off to their respective "read" methods for processing. Otherwise, skips the tag.
     */
    private FeedPojo readEntry(XmlPullParser parser)
            throws XmlPullParserException, IOException, ParseException {
        parser.require(XmlPullParser.START_TAG, ns, "item");
        String title = null;
        String description = null;
        String url = null;
        String date = null;
        String guid = null;

        while (parser.next() != XmlPullParser.END_TAG) {
            if (parser.getEventType() != XmlPullParser.START_TAG) {
                continue;
            }
            String name = parser.getName();
            if (name.equals("guid")){
                // Example: <id>urn:uuid:218AC159-7F68-4CC6-873F-22AE6017390D</id>
                guid = readTag(parser, TAG_GUID);
            } else if (name.equals("title")) {
                // Example: <title>Article title</title>
                title = readTag(parser, TAG_TITLE);
            } else if (name.equals("link")) {
                url = readTag(parser, TAG_LINK);
            } else if (name.equals("pubDate")) {
                date = readTag(parser, TAG_DATE);
            } else if (name.equals("description")) {
                description = readTag(parser, TAG_DATE);
            } else {
                skip(parser);
            }
        }
        return new FeedPojo(title, description, date, guid, url);
    }

    /**
     * Process an incoming tag and read the selected value from it.
     */
    private String readTag(XmlPullParser parser, int tagType)
            throws IOException, XmlPullParserException {
        String tag = null;
        String endTag = null;

        switch (tagType) {
            case TAG_GUID:
                return readBasicTag(parser, "guid");
            case TAG_TITLE:
                return readBasicTag(parser, "title");
            case TAG_DATE:
                return readBasicTag(parser, "published");
            case TAG_LINK:
                return readBasicTag(parser, "link");
            case TAG_DESCRIPTION:
                return readBasicTag(parser, "description");
            default:
                throw new IllegalArgumentException("Unknown tag type: " + tagType);
        }
    }

    /**
     * Reads the body of a basic XML tag, which is guaranteed not to contain any nested elements.
     *
     * <p>You probably want to call readTag().
     *
     * @param parser Current parser object
     * @param tag XML element tag name to parseAndClose
     * @return Body of the specified tag
     * @throws java.io.IOException
     * @throws org.xmlpull.v1.XmlPullParserException
     */
    private String readBasicTag(XmlPullParser parser, String tag)
            throws IOException, XmlPullParserException {
        parser.require(XmlPullParser.START_TAG, ns, tag);
        String result = readText(parser);
        parser.require(XmlPullParser.END_TAG, ns, tag);
        return result;
    }

    private String readText(XmlPullParser parser) throws IOException, XmlPullParserException {
        String result = null;
        if (parser.next() == XmlPullParser.TEXT) {
            result = parser.getText();
            parser.nextTag();
        }
        return result;
    }

    private void skip(XmlPullParser parser) throws XmlPullParserException, IOException {
        if (parser.getEventType() != XmlPullParser.START_TAG) {
            throw new IllegalStateException();
        }
        int depth = 1;
        while (depth != 0) {
            switch (parser.next()) {
                case XmlPullParser.END_TAG:
                    depth--;
                    break;
                case XmlPullParser.START_TAG:
                    depth++;
                    break;
            }
        }
    }
}
