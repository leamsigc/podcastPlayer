package com.podcast.podcast.item;

import org.springframework.data.annotation.Id;

public class Item {
    private final Long id;
    private final String name;
    private final String publish;
    private final String Description;
    private final String url;

    public Item(Long id, String name, String publish, String Description, String url) {
        this.id = id;
        this.name = name;
        this.publish = publish;
        this.Description = Description;
        this.url = url;
    }
    @Id
    public Long getId() {
        return id;
    }

    public String getDescription() {
        return Description;
    }

    public String getName() {
        return name;
    }

    public String getPublish() {
        return publish;
    }

    public String getUrl() {
        return url;
    }

    public Item updateWith(Item item){
        return new Item(this.id, item.name, item.publish,item.Description,item.url);
    }
}
