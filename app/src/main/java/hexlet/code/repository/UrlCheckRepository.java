package hexlet.code.repository;

import hexlet.code.model.UrlCheck;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.Statement;

import java.sql.Timestamp;

import java.time.LocalDateTime;
import java.util.*;

public class UrlCheckRepository extends BaseRepository {

    public static UrlCheck save(UrlCheck check) throws Exception {

        String sql = """
                INSERT INTO url_checks
                (url_id, status_code, h1, title, description, created_at)
                VALUES (?, ?, ?, ?, ?, ?)
                """;
        try (
                Connection conn = dataSource.getConnection();
                PreparedStatement stmt = conn.prepareStatement(
                        sql,
                        Statement.RETURN_GENERATED_KEYS
                )
        ) {
            stmt.setLong(1, check.getUrlId());
            stmt.setInt(2, check.getStatusCode());

            stmt.setString(3, check.getH1());
            stmt.setString(4, check.getTitle());
            stmt.setString(5, check.getDescription());

            stmt.setTimestamp(6, Timestamp.valueOf(LocalDateTime.now()));
            stmt.executeUpdate();

            ResultSet keys = stmt.getGeneratedKeys();

            if (keys.next()) {
                check.setId(keys.getLong(1));
            }
            return check;
        }
    }

    public static List<UrlCheck> findByUrlId(Long urlId) throws Exception {

        List<UrlCheck> checks = new ArrayList<>();
        String sql = """
                SELECT *
                FROM url_checks
                WHERE url_id = ?
                ORDER BY created_at DESC
                """;

        try (
                Connection conn = dataSource.getConnection();
                PreparedStatement stmt = conn.prepareStatement(sql)
        ) {

            stmt.setLong(1, urlId);

            ResultSet rs = stmt.executeQuery();

            while (rs.next()) {

                UrlCheck check = new UrlCheck();

                check.setId(rs.getLong("id"));
                check.setUrlId(rs.getLong("url_id"));
                check.setStatusCode(rs.getInt("status_code"));

                check.setH1(rs.getString("h1"));
                check.setTitle(rs.getString("title"));
                check.setDescription(rs.getString("description"));
                check.setCreatedAt(rs.getTimestamp("created_at").toLocalDateTime());

                checks.add(check);
            }
        }
        return checks;
    }
    public static Map<Long, UrlCheck> findLatestChecks() throws Exception {

        String sql = """
        SELECT uc.*
        FROM url_checks uc
        INNER JOIN (
            SELECT url_id, MAX(id) AS max_id
            FROM url_checks
            GROUP BY url_id
        ) latest
        ON uc.id = latest.max_id
        """;

        Map<Long, UrlCheck> result = new HashMap<>();

        try (
                Connection conn = dataSource.getConnection();
                PreparedStatement stmt = conn.prepareStatement(sql)
        ) {

            ResultSet rs = stmt.executeQuery();

            while (rs.next()) {
                UrlCheck check = buildUrlCheck(rs);
                result.put(check.getUrlId(), check);
            }
        }

        return result;
    }
    public static Integer getLastCheckStatusCode(Long urlId) throws Exception {

        var latestChecks = findLatestChecks();

        var check = latestChecks.get(urlId);

        return check != null ? check.getStatusCode() : null;
    }
    private static UrlCheck buildUrlCheck(ResultSet rs) throws Exception {

        UrlCheck check = new UrlCheck();

        check.setId(rs.getLong("id"));
        check.setUrlId(rs.getLong("url_id"));
        check.setStatusCode(rs.getInt("status_code"));
        check.setH1(rs.getString("h1"));
        check.setTitle(rs.getString("title"));
        check.setDescription(rs.getString("description"));
        check.setCreatedAt(rs.getTimestamp("created_at").toLocalDateTime());

        return check;
    }
}
