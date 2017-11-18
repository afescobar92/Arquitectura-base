package com.cosmo.arquitecturamvpbase.model;

import org.simpleframework.xml.Element;
import org.simpleframework.xml.Root;

import java.io.Serializable;

/**
 * Created by user on 07/11/2017.
 */
@Root(name = "note")
public class Note implements Serializable{

    @Element(name = "to")
    String to;
    @Element(name = "from")
    String from;
    @Element(name = "heading")
    String heading;
    @Element(name = "body")
    String body;

    public String getTo() {
        return to;
    }

    public void setTo(String to) {
        this.to = to;
    }

    public String getFrom() {
        return from;
    }

    public void setFrom(String from) {
        this.from = from;
    }

    public String getHeading() {
        return heading;
    }

    public void setHeading(String heading) {
        this.heading = heading;
    }

    public String getBody() {
        return body;
    }

    public void setBody(String body) {
        this.body = body;
    }
}
