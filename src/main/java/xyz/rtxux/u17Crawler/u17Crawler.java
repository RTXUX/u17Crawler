package xyz.rtxux.u17Crawler;

import us.codecraft.webmagic.Site;
import us.codecraft.webmagic.Spider;
import us.codecraft.webmagic.handler.CompositePageProcessor;
import us.codecraft.webmagic.handler.CompositePipeline;
import xyz.rtxux.u17Crawler.Processor.ChapterProcessor;
import xyz.rtxux.u17Crawler.Processor.ComicListProcessor;
import xyz.rtxux.u17Crawler.Processor.ComicProcessor;
import xyz.rtxux.u17Crawler.pipeline.ChapterPipeline;
import xyz.rtxux.u17Crawler.pipeline.ComicPipeline;
import xyz.rtxux.u17Crawler.utils.utils;
public class u17Crawler {


    public static void main(String[] args) {
        CompositePageProcessor cpp = new CompositePageProcessor(Site.me().setSleepTime(100));
        CompositePipeline compositePipeline = new CompositePipeline();
        compositePipeline.addSubPipeline(new ChapterPipeline());
        compositePipeline.addSubPipeline(new ComicPipeline());
        cpp.addSubPageProcessor(new ComicListProcessor());
        cpp.addSubPageProcessor(new ComicProcessor());
        cpp.addSubPageProcessor(new ChapterProcessor());
        Spider spider = Spider.create(cpp).addRequest(utils.createComicListRequest(1)).addPipeline(compositePipeline).thread(4);
        spider.run();
    }
}
