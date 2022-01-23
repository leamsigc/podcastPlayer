package com.podcast.podcast.item;

import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import java.net.URI;
import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("api/v1/podcast")
public class ItemController {
    private final ItemService service;

    public ItemController(ItemService service){
        this.service = service;
    }

    @PreAuthorize("permitAll()")
    @GetMapping
    public ResponseEntity<List<Item>> getAll(){
        List<Item> items = service.findAll();
        return ResponseEntity.ok().body(items);
    }


    @GetMapping("/{id}")
    public ResponseEntity<Item> findById(@PathVariable("id") Long id){
        Optional<Item> item = service.findById(id);
        return  ResponseEntity.of(item);
    }

    @PostMapping
    public ResponseEntity<Item> create(@RequestBody Item item){
        Item createdItem = service.create(item);
        URI location = ServletUriComponentsBuilder.fromCurrentRequest()
                .path("/{id}")
                .buildAndExpand(createdItem.getId())
                .toUri();
        return ResponseEntity.created(location).body(createdItem);
    }

    @PutMapping("/{id}")
    public ResponseEntity<Item> update(
            @PathVariable("id") Long id,
            @RequestBody Item updatedItem){

        Optional<Item> updateItem = service.update(id,updatedItem);
        return updateItem
                .map(value -> ResponseEntity.ok().body(value))
                .orElseGet(() -> {
                    Item itemCreated = service.create(updatedItem);
                    URI location = ServletUriComponentsBuilder.fromCurrentRequest()
                            .path("/{id}")
                            .buildAndExpand(itemCreated.getId())
                            .toUri();
                    return ResponseEntity.created(location).body(itemCreated);
                });

    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Item> delete(@PathVariable("id")Long id){
        service.delete(id);
        return  ResponseEntity.noContent().build();
    }
}
