package uk.ac.cardiff.team5.graphium.data.jdbc.repository;

import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;

import org.springframework.stereotype.Repository;

import uk.ac.cardiff.team5.graphium.domain.FileDisplayer;

import java.util.List;

@Repository
public class FileRepository {

    private JdbcTemplate jdbc;
    private RowMapper<FileDisplayer> fileMapper;

    public FileRepository(JdbcTemplate jdbcOperations) {

        jdbc = jdbcOperations;

        fileMapper = (rs, i) -> new FileDisplayer(
                rs.getString("file_id"),
                rs.getString("file_name"),
                rs.getString("file_type"),
                rs.getString("tag"),
                rs.getString("access_level"),
                rs.getString("comment"),
                rs.getString("date"),
                rs.getString("username"),
                rs.getString("subject"));
    }


    public List<FileDisplayer> findAllByOrg(String username) {

        return jdbc.query(
                "call getFilesForOrganisation(?);",
                new Object[]{username},
                fileMapper);

    }

    public List<FileDisplayer> findAllPublic() {
        return jdbc.query(
                "SELECT files.file_id, files.file_name, files.file_type,files.tag,files.access_level, files.comment, files.date,users.username,files.subject FROM graphium.files JOIN users on files.user_id = users.user_id where files.access_level = 'public' ORDER BY files.date;",
                new Object[]{},
                fileMapper);
    }

    public List<FileDisplayer> findAllPartners(String username) {
        return jdbc.query(
                "call getPartnersFiles(?);",
                new Object[]{username},
                fileMapper);
    }

    public List<FileDisplayer> findAllFiles(String username) {
        return jdbc.query(
                "call getAllFiles(?);",
                new Object[]{username},
                fileMapper);
    }
}

