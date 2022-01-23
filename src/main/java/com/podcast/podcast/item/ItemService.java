package com.podcast.podcast.item;

import org.springframework.data.map.repository.config.EnableMapRepositories;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Optional;

@Service
@EnableMapRepositories
public class ItemService {
    private final CrudRepository<Item,Long> repository;

    public ItemService(CrudRepository<Item, Long> repository) {
        this.repository = repository;

        this.repository.saveAll(defaultPodcast());
    }

    private static List<Item> defaultPodcast() {
        return List.of(
                new Item(
                        1L,
                        "Podcast 1",
                        "2020-08-01",
                        "",
                        "https://leamsigc.com/podcast"
                ),
                new Item(
                        2L,
                        "Podcast 2",
                        "2020-08-01",
                        "",
                        "https://leamsigc.com/podcast"
                )
        );
    }


    public List<Item> findAll(){
        List<Item> list = new ArrayList<>();
        Iterable<Item> items = repository.findAll();

        items.forEach(list::add);
        return list;
    }

    public Optional<Item> findById(Long id){
        return repository.findById(id);
    }

    public Item create(Item item){
        Item Copy = new  Item(
                new Date().getTime(),
                item.getName(),
                item.getPublish(),
                item.getDescription(),
                item.getUrl()
        );
        return repository.save(Copy);
    }
    public Optional<Item> update(Long id ,Item itemWithUpdates){
      return   repository.findById(id)
                .map(oldItem -> {
                    Item UpdatedItem = oldItem.updateWith(itemWithUpdates);
                    return repository.save(UpdatedItem);
                });
    }

    public void delete(Long id){
        repository.deleteById(id);
    }

}
