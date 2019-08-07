//
// Source code recreated from a .class file by IntelliJ IDEA
// (powered by Fernflower decompiler)
//

package mapping;

import de.bytefish.pgbulkinsert.mapping.AbstractMapping;
import java.sql.Connection;
import DataType.stringPlate;

public class offsetMapping extends AbstractMapping<stringPlate> {
    private String schema = "";
    private Connection connection;

    public offsetMapping(String table, Connection connection) {
        super("zigzag", table);
        this.connection = connection;
        this.mapText("term", stringPlate::getContent);
        this.mapInteger("docid", stringPlate::getDocNumber);
        this.mapInteger("offsets", stringPlate::getOffset);
//        this.mapInteger("paragraph", stringPlate::getParagraph);

    }
}
