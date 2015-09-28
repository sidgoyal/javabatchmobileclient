package com.msusevusal.javabatchmobileclient.model;

import com.msusevusal.javabatchmobileclient.db.RestState;

/**
 * Created by sid on 9/21/15.
 */
public class JsonResponseObject  {

    private long id;
    private long parentId;
    private RestState state;
    private String body;

    public JsonResponseObject(){};



    @Override
    public int hashCode() {
        return (int) id;
    }

    @Override
    public boolean equals(Object o) {
        if( o == null) return false;
        return (id == ((JsonResponseObject)o).getId());
    }

    public JsonResponseObject(long id, long parentId, RestState state, String body){
        this.id = id;
        this.parentId = parentId;
        this.state = state;
        this.body = body;
    }


    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public long getParentId() {
        return parentId;
    }

    public void setParentId(long parentId) {
        this.parentId = parentId;
    }

    public RestState getState() {
        return state;
    }

    public void setState(RestState state) {
        this.state = state;
    }

    public String getBody() {
        return body;
    }

    public void setBody(String body) {
        this.body = body;
    }

    @Override
    public String toString() {
        return "JsonResponseObject{" +
                "id=" + id +
                ", parentId=" + parentId +
                ", state=" + state +
                ", body='" + body + '\'' +
                '}';
    }
}
