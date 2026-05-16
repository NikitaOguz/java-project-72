package hexlet.code.dto;

import hexlet.code.model.Url;


import hexlet.code.model.UrlCheck;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

import java.time.format.DateTimeFormatter;
import java.util.List;
import java.util.Map;

@AllArgsConstructor
@Getter
@Setter
public class UrlsCheckPage extends BasePage {
    DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy/MM/dd HH:mm");
    private List<Url> urls;
    private Map<Long, UrlCheck> lastCheck;

    public UrlsCheckPage(List<Url> urls, Map<Long, UrlCheck> lastCheck) {
        this.urls = urls;
        this.lastCheck = lastCheck;

    }

}
