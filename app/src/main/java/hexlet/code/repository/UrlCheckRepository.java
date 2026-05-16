package hexlet.code.repository;

import hexlet.code.model.UrlCheck;

import java.sql.SQLException;
import java.sql.Statement;
import java.sql.Timestamp;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class UrlCheckRepository extends BaseRepository {
    public static void save(UrlCheck urlCheck) throws SQLException {
        String sql = "INSERT INTO url_checks (status_code, title, h1, description, url_id, created_at)"
                + " VALUES (?, ?, ?, ?, ?, ?)";
        try (var conn = dataSource.getConnection();
             var preparedStatement = conn.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS)) {
            preparedStatement.setInt(1, urlCheck.getStatusCode());
            preparedStatement.setString(2, urlCheck.getTitle());
            preparedStatement.setString(3, urlCheck.getH1());
            preparedStatement.setString(4, urlCheck.getDescription());
            preparedStatement.setLong(5, urlCheck.getUrlId());
            var createdAt = LocalDateTime.now();
            preparedStatement.setTimestamp(6, Timestamp.valueOf(createdAt));
            preparedStatement.executeUpdate();
            var generatedKeys = preparedStatement.getGeneratedKeys();
            if (generatedKeys.next()) {
                urlCheck.setId(generatedKeys.getLong(1));
            } else {
                throw new SQLException("DB have not returned an id after saving an entity");
            }
        }  catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    public static List<UrlCheck> findById(long urlId) throws SQLException {
        String sql = "SELECT * FROM url_checks WHERE url_id = ? ORDER BY id DESC";
        var listOfUrls = new ArrayList<UrlCheck>();

        try (var conn = dataSource.getConnection();
             var stmt = conn.prepareStatement(sql)) {
            stmt.setLong(1, urlId);
            var resultSet = stmt.executeQuery();
            while (resultSet.next()) {
                var id = resultSet.getLong("id");
                var statusCode = resultSet.getInt("status_code");
                var title = resultSet.getString("title");
                var h1 = resultSet.getString("h1");
                var description = resultSet.getString("description");
                var createAt = resultSet.getTimestamp("created_at").toLocalDateTime();
                var dataToSave = new UrlCheck(statusCode, title, h1, description, urlId, createAt);
                dataToSave.setId(id);
                listOfUrls.add(dataToSave);
            }
            return listOfUrls;
        }  catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    public static Map<Long, UrlCheck> findLast() {
        String sql = "SELECT DISTINCT ON (url_id) * FROM url_checks ORDER BY url_id DESC, created_at DESC";
        Map<Long, UrlCheck> lastCheckMap = new HashMap<>();
        try (var conn = dataSource.getConnection();
             var stmt = conn.prepareStatement(sql)) {
            var resultSet = stmt.executeQuery();
            while (resultSet.next()) {
                var listOfUrls = new UrlCheck();
                listOfUrls.setId(resultSet.getLong("id"));
                listOfUrls.setStatusCode(resultSet.getInt("status_code"));
                listOfUrls.setTitle(resultSet.getString("title"));
                listOfUrls.setH1(resultSet.getString("h1"));
                listOfUrls.setDescription(resultSet.getString("description"));
                listOfUrls.setCreatedAt(resultSet.getTimestamp("created_at").toLocalDateTime());
                var urlId = resultSet.getLong("url_id");
                listOfUrls.setUrlId(urlId);
                lastCheckMap.put(urlId, listOfUrls);
            }
            return lastCheckMap;
        }  catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }
}
