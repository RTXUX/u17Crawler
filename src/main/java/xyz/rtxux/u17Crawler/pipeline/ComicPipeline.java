package xyz.rtxux.u17Crawler.pipeline;

import org.apache.ibatis.session.SqlSession;
import us.codecraft.webmagic.Request;
import us.codecraft.webmagic.ResultItems;
import us.codecraft.webmagic.Task;
import us.codecraft.webmagic.handler.SubPipeline;
import xyz.rtxux.u17Crawler.Mapper.ChapterInfoMapper;
import xyz.rtxux.u17Crawler.Mapper.ComicInfoMapper;
import xyz.rtxux.u17Crawler.model.ChapterInfo;
import xyz.rtxux.u17Crawler.model.ComicInfo;
import xyz.rtxux.u17Crawler.utils.utils;

import java.util.List;

public class ComicPipeline implements SubPipeline {

    private ThreadLocal<ComicInfoMapper> comicInfoMapperThreadLocal;
    private ThreadLocal<ChapterInfoMapper> chapterInfoMapperThreadLocal;
    private ThreadLocal<SqlSession> sessionThreadLocal;

    public ComicPipeline() {
        comicInfoMapperThreadLocal = new ThreadLocal<>();
        chapterInfoMapperThreadLocal = new ThreadLocal<>();
        sessionThreadLocal = new ThreadLocal<>();
    }

    private SqlSession getSession() {
        if (sessionThreadLocal.get() == null) {
            sessionThreadLocal.set(utils.getSession());
        }
        return sessionThreadLocal.get();
    }

    private ChapterInfoMapper getChapterInfoMapper() {
        if (chapterInfoMapperThreadLocal.get() == null) {
            chapterInfoMapperThreadLocal.set(getSession().getMapper(ChapterInfoMapper.class));
        }
        return chapterInfoMapperThreadLocal.get();
    }

    private ComicInfoMapper getComicInfoMapper() {
        if (comicInfoMapperThreadLocal.get() == null) {
            comicInfoMapperThreadLocal.set(getSession().getMapper(ComicInfoMapper.class));
        }
        return comicInfoMapperThreadLocal.get();
    }

    @Override
    public MatchOther processResult(ResultItems resultItems, Task task) {
        ComicInfoMapper comicInfoMapper = getComicInfoMapper();
        ChapterInfoMapper chapterInfoMapper = getChapterInfoMapper();
        ComicInfo comic = (ComicInfo) resultItems.get("Comic");
        try {
            comicInfoMapper.save(comic);
        } catch (Exception e) {
            e.printStackTrace();
        }
        List<ChapterInfo> chapters = (List<ChapterInfo>) resultItems.get("Chapters");
        for (ChapterInfo chapter : chapters) {
            chapterInfoMapper.save(chapter);
        }
        getSession().commit();
        return MatchOther.NO;
    }

    @Override
    public boolean match(Request request) {
        if (request.getExtra("Type").equals(2)) {
            return true;
        }
        return false;
    }
}
