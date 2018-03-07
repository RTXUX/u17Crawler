package xyz.rtxux.u17Crawler.Processor;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import us.codecraft.webmagic.Page;
import us.codecraft.webmagic.Request;
import us.codecraft.webmagic.handler.SubPageProcessor;
import xyz.rtxux.u17Crawler.model.ComicInfo;
import xyz.rtxux.u17Crawler.utils.utils;

import java.util.List;

public class ComicListProcessor implements SubPageProcessor {

    public MatchOther processPage(Page page) {
        JSONObject response = JSON.parseObject(page.getRawText());
        if (response.getIntValue("code") == 1) {
            List<ComicInfo> comics = response.getJSONArray("comic_list").toJavaList(ComicInfo.class);
            page.putField("Type", 1);
            for (ComicInfo comic : comics) {
                if (utils.getRecord().getComics().contains(comic.getComic_id())) {
                    continue;
                }
                Request request = new Request("http://www.u17.com/comic/" + comic.getComic_id() + ".html");
                request.putExtra("Type", 2);
                request.putExtra("Comic", comic);
                page.addTargetRequest(request);
            }
            page.addTargetRequest(utils.createComicListRequest(Integer.parseInt(page.getRequest().getExtra("Page").toString()) + 1));
        }
        return MatchOther.NO;
    }

    public boolean match(Request request) {
        if (request.getExtra("Type").equals(1)) {
            return true;
        }
        return false;
    }
}

