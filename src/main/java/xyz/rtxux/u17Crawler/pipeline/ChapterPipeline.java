package xyz.rtxux.u17Crawler.pipeline;

import org.apache.ibatis.session.SqlSession;
import us.codecraft.webmagic.Request;
import us.codecraft.webmagic.ResultItems;
import us.codecraft.webmagic.Task;
import us.codecraft.webmagic.handler.SubPipeline;
import xyz.rtxux.u17Crawler.Mapper.ImageInfoMapper;
import xyz.rtxux.u17Crawler.model.ImageInfo;
import xyz.rtxux.u17Crawler.utils.utils;

import java.util.List;

public class ChapterPipeline implements SubPipeline {

    private ThreadLocal<ImageInfoMapper> imageInfoMapperThreadLocal;
    private ThreadLocal<SqlSession> sessionThreadLocal;

    public ChapterPipeline() {
        imageInfoMapperThreadLocal = new ThreadLocal<>();
        sessionThreadLocal = new ThreadLocal<>();
    }

    private SqlSession getSession() {
        if (sessionThreadLocal.get() == null) {
            sessionThreadLocal.set(utils.getSession());
        }
        return sessionThreadLocal.get();
    }

    private ImageInfoMapper getImageInfoMapper() {
        if (imageInfoMapperThreadLocal.get() == null) {
            imageInfoMapperThreadLocal.set(getSession().getMapper(ImageInfoMapper.class));
        }
        return imageInfoMapperThreadLocal.get();
    }


    @Override
    public MatchOther processResult(ResultItems resultItems, Task task) {
        ImageInfoMapper imageInfoMapper = getImageInfoMapper();
        List<ImageInfo> images = (List<ImageInfo>) resultItems.get("Data");
        for (ImageInfo image : images) {
            imageInfoMapper.save(image);
        }
        getSession().commit();
        if (resultItems.getRequest().getExtra("LastChapter").equals(1)) {
            int ComicID = (int) resultItems.getRequest().getExtra("ComicID");
            utils.getDoneComics().add(ComicID);
        }
        return MatchOther.NO;
    }

    @Override
    public boolean match(Request request) {
        if (request.getExtra("Type").equals(3)) {
            return true;
        }
        return false;
    }
}
