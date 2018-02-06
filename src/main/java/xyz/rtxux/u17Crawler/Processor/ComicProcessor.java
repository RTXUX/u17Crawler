package xyz.rtxux.u17Crawler.Processor;

import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import us.codecraft.webmagic.Page;
import us.codecraft.webmagic.Request;
import us.codecraft.webmagic.handler.SubPageProcessor;
import us.codecraft.webmagic.selector.Html;
import xyz.rtxux.u17Crawler.model.ChapterInfo;
import xyz.rtxux.u17Crawler.model.ComicInfo;
import xyz.rtxux.u17Crawler.utils.utils;

import java.util.LinkedList;
import java.util.List;

public class ComicProcessor implements SubPageProcessor{
    public MatchOther processPage(Page page) {
        Html html = page.getHtml();
        Document doc = html.getDocument();
        ComicInfo comic = (ComicInfo)page.getRequest().getExtra("Comic");
        comic.setDescription(doc.select(".ti2").first().text());
        page.putField("Comic",comic);
        List<ChapterInfo> chapters = new LinkedList<ChapterInfo>();
        for (Element chapter_element : doc.select("#chapter").first().children())
        {
            Element element = chapter_element.child(0);
            ChapterInfo chapter = new ChapterInfo();
            chapter.setID(utils.getChapterIdByAttribute(element.attr("id")));
            chapter.setComicID(comic.getComic_id());
            chapter.setName(element.attr("title"));
            chapters.add(chapter);
            Request readRequest = new Request("http://www.u17.com/chapter/" + chapter.getID()+".html");
            readRequest.putExtra("ChapterID",chapter.getID());
            readRequest.putExtra("Type",3);
            page.addTargetRequest(readRequest);
        }
        page.putField("Chapters",chapters);
        return MatchOther.NO;
    }

    public boolean match(Request request) {
        if (request.getExtra("Type").equals(2))
        {
            return true;
        }
        return false;
    }
}
