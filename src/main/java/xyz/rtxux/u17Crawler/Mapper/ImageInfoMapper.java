package xyz.rtxux.u17Crawler.Mapper;

import org.apache.ibatis.annotations.Insert;
import xyz.rtxux.u17Crawler.model.ImageInfo;

public interface ImageInfoMapper {

    @Insert("INSERT into images(ID,ChapterID,URL) VALUES(#{image_id}, #{ChapterID}, #{src})")
    public void save(ImageInfo image);
}
