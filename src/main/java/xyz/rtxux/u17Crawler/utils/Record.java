package xyz.rtxux.u17Crawler.utils;

import java.io.Serializable;
import java.util.Set;
import java.util.concurrent.ConcurrentHashMap;

public class Record implements Serializable {
    private Set<Integer> Comics;
    private Set<Integer> Chapters;

    public Record() {
        Comics = ConcurrentHashMap.newKeySet();
        Chapters = ConcurrentHashMap.newKeySet();

    }

    public Set<Integer> getComics() {
        return Comics;
    }

    public Set<Integer> getChapters() {
        return Chapters;
    }

}
