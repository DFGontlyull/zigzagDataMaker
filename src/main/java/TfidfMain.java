//
// Source code recreated from a .class file by IntelliJ IDEA
// (powered by Fernflower decompiler)
//

import java.io.IOException;
import java.net.URISyntaxException;
import java.sql.Connection;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import Executor.connectDB;
import Parser.DocumentParser_multi_nio_paragraph_ALLINONE;

class TfidfMain {
    public static void main(String[] args) throws IOException, SQLException, URISyntaxException {
        connectDB db = new connectDB(args[0], "postgres");
        Connection conn = db.connect().getConnection();
        List<Connection> connList = new ArrayList();
        long startTime = System.currentTimeMillis();
        DocumentParser_multi_nio_paragraph_ALLINONE dp2 = new DocumentParser_multi_nio_paragraph_ALLINONE();
        dp2.parseFiles(db, conn, args[1], args[2], Integer.parseInt(args[3]), Integer.parseInt(args[4]), startTime);
        dp2.executor(conn, args[1], startTime);
        Iterator var7 = connList.iterator();

        while(var7.hasNext()) {
            Connection cont = (Connection)var7.next();
            cont.close();
        }

        conn.close();
        System.out.println("conn close()");
    }
}
