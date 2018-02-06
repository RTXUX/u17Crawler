package xyz.rtxux.u17Crawler.model;

public class ImageInfo {
    private int image_id;
    private int ChapterID;
    private int width;
    private int height;
    private int total_tucao;
    private int type;
    private String src;
    private int lightning;
    private String is_seal;
    private int page;

    public int getImage_id() {
        return image_id;
    }

    public void setImage_id(int image_id) {
        this.image_id = image_id;
    }

    public int getChapterID() {
        return ChapterID;
    }

    public void setChapterID(int chapterID) {
        ChapterID = chapterID;
    }

    public int getWidth() {
        return width;
    }

    public void setWidth(int width) {
        this.width = width;
    }

    public int getHeight() {
        return height;
    }

    public void setHeight(int height) {
        this.height = height;
    }

    public int getTotal_tucao() {
        return total_tucao;
    }

    public void setTotal_tucao(int total_tucao) {
        this.total_tucao = total_tucao;
    }

    public int getType() {
        return type;
    }

    public void setType(int type) {
        this.type = type;
    }

    public String getSrc() {
        return src;
    }

    public void setSrc(String src) {
        this.src = src;
    }

    public int getLightning() {
        return lightning;
    }

    public void setLightning(int lightning) {
        this.lightning = lightning;
    }

    public String getIs_seal() {
        return is_seal;
    }

    public void setIs_seal(String is_seal) {
        this.is_seal = is_seal;
    }

    public int getPage() {
        return page;
    }

    public void setPage(int page) {
        this.page = page;
    }
}
