package hexlet.code.repository;

import hexlet.code.model.Url;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.Statement;

import java.sql.Timestamp;

import java.util.ArrayList;
import java.util.List;

public class UrlRepository extends BaseRepository {

    public static Url save(Url url) throws Exception {

        Url existing = findByName(url.getName());
        if (existing != null) {
            return existing;
        }

        String sql = "INSERT INTO urls(name, created_at) VALUES (?, ?)";

        try (Connection conn = dataSource.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS)) {

            stmt.setString(1, url.getName());
            stmt.setTimestamp(2, new Timestamp(System.currentTimeMillis()));

            stmt.executeUpdate();

            ResultSet keys = stmt.getGeneratedKeys();
            if (keys.next()) {
                url.setId(keys.getLong(1));
            }

            return url;
        }
    }
    public static Url find(Long id) throws Exception {

        String sql = "SELECT * FROM urls WHERE id = ?";

        try (
                Connection conn = dataSource.getConnection();
                PreparedStatement stmt = conn.prepareStatement(sql)
        ) {

            stmt.setLong(1, id);

            ResultSet rs = stmt.executeQuery();

            if (rs.next()) {

                Url url = new Url();

                url.setId(rs.getLong("id"));
                url.setName(rs.getString("name"));
                url.setCreatedAt(rs.getTimestamp("created_at"));

                return url;
            }

            return null;
        }
    }

    public static Url findByName(String name) throws Exception {

        String sql = "SELECT * FROM urls WHERE name = ?";

        try (
                Connection conn = dataSource.getConnection();
                PreparedStatement stmt = conn.prepareStatement(sql)
        ) {

            stmt.setString(1, name);

            ResultSet rs = stmt.executeQuery();

            if (rs.next()) {

                Url url = new Url();

                url.setId(rs.getLong("id"));
                url.setName(rs.getString("name"));
                url.setCreatedAt(rs.getTimestamp("created_at"));

                return url;
            }

            return null;
        }
    }

    public static List<Url> getEntities() throws Exception {

        List<Url> urls = new ArrayList<>();

        String sql = "SELECT * FROM urls ORDER BY created_at DESC";

        try (
                Connection conn = dataSource.getConnection();
                Statement stmt = conn.createStatement()
        ) {

            ResultSet rs = stmt.executeQuery(sql);

            while (rs.next()) {

                Url url = new Url();

                url.setId(rs.getLong("id"));
                url.setName(rs.getString("name"));
                url.setCreatedAt(rs.getTimestamp("created_at"));

                urls.add(url);
            }
        }

        return urls;
    }
}
