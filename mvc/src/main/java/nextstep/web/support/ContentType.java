package nextstep.web.support;

import java.util.Arrays;
import nextstep.mvc.exception.PageNotFoundException;

public enum ContentType {
    CSS(".css", "text/css"),
    JAVASCRIPT(".js", "application/javascript"),
    HTML(".html", "text/html; charset=UTF-8"),
    SVG(".svg", "image/svg+xml"),
    JSP(".jsp", "text/html; charset=UTF-8");

    private final String extensionType;
    private final String contentType;

    ContentType(String extensionType, String contentType) {
        this.extensionType = extensionType;
        this.contentType = contentType;
    }

    public static String contentType(String url) {
        return Arrays.stream(values())
                .filter(content -> url.endsWith(content.extensionType))
                .findAny()
                .orElseThrow(PageNotFoundException::new)
                .contentType;
    }

    public static String headerKey() {
        return "Content-Type";
    }

    public String contentType() {
        return contentType;
    }

    public String extensionType() {
        return extensionType;
    }
}
