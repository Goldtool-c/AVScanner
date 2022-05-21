package gladyshev.DAO;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;

@Component
public class DAO {
    private JdbcTemplate jdbcTemplate;
    private String table = "sequences";
    private RowMapper rm = new SequenceMapper();
    @Autowired
    public DAO(JdbcTemplate jdbcTemplate)
    {
        this.jdbcTemplate = jdbcTemplate;
        jdbcTemplate.update("CREATE TABLE IF NOT EXISTS "+table+" (id integer, bytes varchar)");
    }

    public byte[] getSequence(int id)
    {
        List sequence = jdbcTemplate.query("SELECT bytes FROM "+table+" WHERE id = "+id, rm);
        return parseSequence((String) sequence.get(0));
    }
    public void setSequence(String sequence)
    {
        if(sequenceValid(sequence)) {
            jdbcTemplate.update("Insert into " + table + " values (" + (getNumOfSequences() + 1) + ", '" + sequence + "')");
        }
    }
    public int getNumOfSequences() {
        return jdbcTemplate.queryForObject("SELECT count(*) FROM "+table, Integer.class);
    }

    public List getAll()
    {
        return jdbcTemplate.query("SELECT * FROM "+table, rm);
    }
    private byte[] parseSequence(String sequence)
    {
        List<String> res = new ArrayList<>();
        char separator = ',';
        StringBuilder sb = new StringBuilder();
        for (int i = 0; i <sequence.length(); i++) {
            if(sequence.charAt(i)!=separator)
            {
                sb.append(sequence.charAt(i));
            }
            else {
                res.add(sb.toString());
                sb = new StringBuilder();
            }
        }
        res.add(sb.toString());
        byte[] byteRes = new byte[res.size()];
        for (int i = 0; i < res.size(); i++) {
            byteRes[i] = Byte.decode(res.get(i));
        }
        return byteRes;
    }
    private boolean sequenceValid(String sequence)
    {
        try {
            parseSequence(sequence);
        }
        catch (NumberFormatException e)
        {
            e.printStackTrace();
            return false;
        }
        return true;
    }
}
