package satudroid.project1.satubh;

import java.util.ArrayList;
import java.util.List;

import org.xml.sax.Attributes;
import org.xml.sax.SAXException;
import org.xml.sax.helpers.DefaultHandler;

/**
 * Class ini digunakan untuk parse xml content.
 */
public class RssHandler extends DefaultHandler {

    private List<Message> messages;
    private Message message;
    private StringBuilder sb;

    @Override
    public void characters(char[] ch, int start, int length)
            throws SAXException {
        super.characters(ch, start, length);
        sb.append(ch, start, length);
    }

    @Override
    public void startDocument() throws SAXException {
        super.startDocument();
        messages = new ArrayList<Message>();
        sb = new StringBuilder();
    }

    @Override
    public void startElement(String uri, String localName, String qName,
            Attributes attributes) throws SAXException {

        if (localName.equalsIgnoreCase("item")) {
            message = new Message();
        }
    }

    @Override
    public void endElement(String uri, String localName, String qName)
            throws SAXException {
        if (message != null) {
            if (localName.equalsIgnoreCase("title")) {
                String title = sb.toString().trim();
                message.setTitle(title);
            } else if (localName.equalsIgnoreCase("link")) {
                message.setLink(sb.toString().trim());
            } else if (localName.equalsIgnoreCase("description")) {
                message.setDesc(sb.toString().trim());
            } else if (localName.equalsIgnoreCase("pubDate")) {
                message.setPubDate(sb.toString().trim());
            } else if (localName.equalsIgnoreCase("item")) {
                messages.add(message);
            }
        }
        sb.setLength(0);
    }

    public List<Message> getMessages() {
        return messages;
    }

}
