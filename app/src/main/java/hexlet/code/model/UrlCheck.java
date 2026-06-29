package hexlet.code.model;

import lombok.Getter;
import lombok.Setter;
import lombok.NoArgsConstructor;
import java.time.LocalDateTime;

@Getter
@Setter
@NoArgsConstructor
public class UrlCheck {
    private Long id;
    private Long urlId;
    private Integer statusCode;
    private String h1;
    private String title;
    private String description;
    private LocalDateTime createdAt;
}
