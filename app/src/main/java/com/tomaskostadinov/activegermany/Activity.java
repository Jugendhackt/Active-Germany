package com.tomaskostadinov.activegermany;

/**
 * Created by Tomas on 03.08.2015
 */
public class Activity {
    public Activity(String url, String title, String description, String max_persons) {
        this.title = title;
        this.description = description;
        this.url = url;
        this.max_persons = max_persons;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getTitle() {

        return title;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public void setMax_persons(String max_persons) {
        this.max_persons = max_persons;
    }

    public String getDescription() {
        return description;
    }

    public String getMax_persons() {
        return max_persons;
    }

    public String title;
    public String description;
    public String max_persons;
    public String url;
}


