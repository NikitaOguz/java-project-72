package hexlet.code.dto;

import hexlet.code.model.Url;

import hexlet.code.model.UrlCheck;
import lombok.Getter;
import lombok.Setter;
import lombok.NoArgsConstructor;
import lombok.RequiredArgsConstructor;
import lombok.NonNull;

import java.time.format.DateTimeFormatter;
import java.util.List;

@RequiredArgsConstructor
@NoArgsConstructor
@Getter
@Setter
public class UrlPage extends BasePage {
    DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy/MM/dd HH:mm");

    @NonNull private Url url;
    @NonNull private List<UrlCheck> urlsCheck;

}
