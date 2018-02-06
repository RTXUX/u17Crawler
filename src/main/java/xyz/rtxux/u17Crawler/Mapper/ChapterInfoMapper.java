package xyz.rtxux.u17Crawler.Mapper;

import org.apache.ibatis.annotations.Insert;
import xyz.rtxux.u17Crawler.model.ChapterInfo;

public interface ChapterInfoMapper {


    @Insert("INSERT into chapters(ID,ComicID,Name) VALUES(#{ID}, #{ComicID}, #{Name})")
    public void save(ChapterInfo chapter);
}
