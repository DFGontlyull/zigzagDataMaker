//
// Source code recreated from a .class file by IntelliJ IDEA
// (powered by Fernflower decompiler)
//

package mapping;

import de.bytefish.pgbulkinsert.mapping.AbstractMapping;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import DataType.stringPlate;

public class indexMapping extends AbstractMapping<stringPlate> {
    private String schema = "";
    private Connection connection;

    public indexMapping(String table, Connection connection) {
        super("zigzag", table);
        this.connection = connection;
        this.mapText("term", stringPlate::getContent);
        this.mapInteger("docid", stringPlate::getDocNumber);
        this.mapInteger("tf", stringPlate::getTf);
        this.mapInteger("df", stringPlate::getDf);
    }

    private int getRowCount() throws SQLException {
        Statement s = this.connection.createStatement();
        ResultSet r = s.executeQuery(String.format("SELECT COUNT(*) AS rowcount FROM %s.unit_test", this.schema));
        r.next();
        int count = r.getInt("rowcount");
        r.close();
        return count;
    }
}
