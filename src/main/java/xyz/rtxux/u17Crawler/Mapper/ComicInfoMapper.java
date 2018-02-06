package xyz.rtxux.u17Crawler.Mapper;

import org.apache.ibatis.annotations.Insert;
import xyz.rtxux.u17Crawler.model.ComicInfo;

public interface ComicInfoMapper {

    @Insert("INSERT into comics(ID,Name,Description) VALUES(#{comic_id}, #{name}, #{Description})")
    public void save(ComicInfo comic);
}