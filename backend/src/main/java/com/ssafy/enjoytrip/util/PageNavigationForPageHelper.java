package com.ssafy.enjoytrip.util;

import com.github.pagehelper.Page;

import lombok.Data;

@Data
public class PageNavigationForPageHelper {

    private int naviSize = 5;
    private int countPerPage = 10;

    private boolean startRange;
    private boolean endRange;

    private int startPage;
    private int endPage;
    private String navigation;


    private Page<?> page;
    private String path;

    public PageNavigationForPageHelper(Page<?> page, String path) {
        this.page = page;
        this.startRange = page.getPageNum() <= naviSize;
        this.endRange = (page.getPages() - 1) / naviSize * naviSize < page.getPageNum();
        this.startPage = (page.getPageNum() - 1) / naviSize * naviSize + 1;
        this.endPage = startPage + naviSize - 1;
        if (page.getPages() < endPage) {
            endPage = page.getPages();
        }
        this.path = path;
        navigation = makePageNavigation();
    }

    public String makePageNavigation() {
        StringBuilder str = new StringBuilder();

        str.append("<ul class='pagination  justify-content-center'>");
        str.append(String.format("<li class='page-item'><a href='%s=%d' class='page-link'>최신</a></li>", path, 1));
        str.append(String.format("<li class='page-item'><a href='%s=%d' class='page-link'>이전</a></li>", path, startRange ? 1 : startPage - 1));
        for (int i = startPage; i <= endPage; i++) {
            str.append(String.format("<li class='%s'>", page.getPageNum() == i ? "page-item active" : "page-item"));
            str.append(String.format("<a href='%s=%d' class='page-link'>%d</a>", path, i, i));
            str.append("</li>");
        }
        str.append(String.format("<li class='page-item'><a href='%s=%d' class='page-link'>다음</a></li>", path, endRange ? endPage : (endPage + 1)));
        str.append(String.format("<li class='page-item'><a  href='%s=%d'   class='page-link'>마지막</a></li>", path, page.getPages()));
        str.append("</ul>");
        return str.toString();
    }

}
