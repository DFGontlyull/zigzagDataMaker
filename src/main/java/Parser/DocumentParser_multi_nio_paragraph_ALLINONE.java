//
// Source code recreated from a .class file by IntelliJ IDEA
// (powered by Fernflower decompiler)
//

package Parser;

import DataType.df;
import DataType.stringPlate;
import Executor.connectDB;
import Judge.stopWord;
import de.bytefish.pgbulkinsert.PgBulkInsert;
import de.bytefish.pgbulkinsert.util.PostgreSqlUtils;
import mapping.dfMapping;
import mapping.indexMapping;
import mapping.offsetMapping;
import mapping.paragraphMapping;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileWriter;
import java.io.IOException;
import java.nio.ByteBuffer;
import java.nio.channels.FileChannel;
import java.nio.charset.Charset;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardOpenOption;
import java.sql.Connection;
import java.sql.SQLException;
import java.util.*;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.stream.Collectors;

public class DocumentParser_multi_nio_paragraph_ALLINONE {
//    private static final String PARAGRAPH_SPLIT_REGEX = "(?m)(?=^\\s{4})";
    private static List<stringPlate> allTerms = new ArrayList();
    private static List<stringPlate> allInOne = new ArrayList();
    private static List<stringPlate> allInOne_Index = new ArrayList();
//    private static List<stringPlate> allInOne_paragraph = new ArrayList();
    private static List<stringPlate> allInOne_df = new ArrayList();
    private static boolean stem = true;

    public DocumentParser_multi_nio_paragraph_ALLINONE() {
    }

    public static void configure(boolean stem) {
    }

    public static String removePunctuationWord(String original) {
        String result = "";
        char[] chars = original.toCharArray();
        char[] var3 = chars;
        int var4 = chars.length;

        for(int var5 = 0; var5 < var4; ++var5) {
            char character = var3[var5];
            if (character >= 'a' && character <= 'z') {
                result = result + character;
            }
        }

        return result;
    }

    public void parseFiles(connectDB dbTool, Connection conns, String filePath, String fileName, int startNum, int endNum, Long startTime) throws FileNotFoundException, IOException {
        List<df> beforeDfAllTablesToList = new ArrayList<>();
        TreeMap<String, List<Integer>> allInOneIndexDfFirst = new TreeMap<>();
        TreeMap<String, Integer> allInOneIndexDfSecond = new TreeMap<>();
        System.out.println(startNum + " 번째 파일 ~ " + endNum + "번째까지");
        List<File> lists = new ArrayList();
        configure(true);
        AtomicInteger fileCounter = new AtomicInteger(0);

        dbTool.getDfTable(conns, beforeDfAllTablesToList);

        for(df nowDf : beforeDfAllTablesToList){
            List<Integer> nowList= new ArrayList<>();
            for(int i=0; i<nowDf.getDf(); i++){
                nowList.add(1);
            }
            allInOneIndexDfFirst.put(nowDf.getTerm(), nowList);
        }

        for(int i = Integer.valueOf(startNum); i <= Integer.valueOf(endNum); ++i) {
            lists.add(new File(filePath + fileName + i + ".txt"));
        }

        lists.stream().map((xFile) -> {
            TreeMap<String, Integer> allInOneIndexTf = new TreeMap<>();
            fileCounter.incrementAndGet();
            new ArrayList();
            Set<String> forIndex = new HashSet();
            new TreeMap();
            Map<String, Integer> temp_confirmTF = new TreeMap();
            new TreeMap();
            Path path = Paths.get(xFile.toURI());
            new StringBuilder();
            String readLine = "";
            if ((fileCounter.get() + 99) % 100 == 0) {
                long endTime = System.currentTimeMillis();
                String output = "Expended Time is " + (double)(endTime - startTime) / 1000.0D;
                File file = new File(filePath + "logfile.txt");

                try {
                    FileWriter fw = new FileWriter(file, true);
                    fw.write("File Count : " + fileCounter.get() + "\n");
                    fw.write(output + "\n");
                    fw.close();
                } catch (IOException var41) {
                    var41.printStackTrace();
                }
            }

            temp_confirmTF.clear();

            try {
                FileChannel channel1 = FileChannel.open(path, StandardOpenOption.READ);
                Throwable var13 = null;

                try {
                    allInOne_Index.clear();
                    ByteBuffer byteBuffer2 = ByteBuffer.allocate((int)Files.size(path));
                    channel1.read(byteBuffer2);
                    byteBuffer2.flip();
                    readLine = Charset.defaultCharset().decode(byteBuffer2).toString();
                    int offset = 0;
//                    String[] paragraphs = readLine.split("(?m)(?=^\\s{4})");
//                    List<String> arrangedParagraphs = new ArrayList();
//                    boolean ix = false;
//                    String[] var18 = paragraphs;
//                    int offset = paragraphs.length;
//                    System.out.println("파라그래프 크기 : " + offset);

//                    for(int var20 = 0; var20 < offset; ++var20) {
//                        String paragraph = var18[var20];
//                        String trimmed = paragraph.trim();
//                        if (!trimmed.equals("")) {
//                            arrangedParagraphs.add(trimmed);
//                        }
//                    }

                    int docId = Integer.valueOf(xFile.getName().replaceAll("[^0-9]", ""));
//                    offset = 0;
                    temp_confirmTF.clear();
                    Set<String> str = new TreeSet();
                    str.clear();

                    StringBuilder sb = new StringBuilder();
                    String[] splitedTerms = readLine.split("\\s+");

//                        for(String st : splitedTerms){
//                            System.out.println(st);
//                        }
                    String result = "";
                    String tempResult = "";
                    String[] tokenizedTerms = splitedTerms;
                    int var26 = splitedTerms.length;

                    String term;
                    for(int var27 = 0; var27 < var26; ++var27) {
                        String tokenWithPunctuation = tokenizedTerms[var27].toLowerCase();
                        String token = removePunctuationWord(tokenWithPunctuation);
//                            System.out.println(token);
                        term = "";
                        if (stem) {
                            term = stopWord.stemString(token);
                            if (term.equals("") || term.equals((Object)null)) {
                                continue;
                            }
                        }

                        if (!token.equals("") && !token.equals((Object)null) && !stopWord.isStopword(token) && (!stem || !stopWord.isStemmedStopword(token) && !stopWord.isStemmedStopword(term))) {
                            if (stem) {
                                tempResult = tempResult + term + " ";
                            } else {
                                tempResult = tempResult + token + " ";
                            }
//                                System.out.println(tempResult);
//                                System.out.println("docId = " + docId);
//                                System.out.println("paraNum = " + i);
//                                System.out.println("token = " + token);
//                                System.out.println("stemmedToken = " + term);
                        }
                    }

                    if (stem) {
                        result = stopWord.removeStemmedStopWords(tempResult);
                    } else {
                        result = stopWord.removeStopWords(tempResult);
                    }

                    if (!result.equals("") && !result.equals((Object)null)) {
                        tokenizedTerms = sb.toString().replaceAll("[\\W&&[^\\s]]", "").split("\\W+");
                        tokenizedTerms = result.replaceAll("[\\W&&[^\\s]]", "").split("\\W+");
//                            boolean tf = false;
                        boolean tf = false;
//                            for(String st : tokenizedTerms){
//                                System.out.println(st);
//                            }

                        String[] var54 = tokenizedTerms;
                        int var55 = tokenizedTerms.length;

                        for(int var56 = 0; var56 < var55; ++var56) {
                            term = var54[var56];
                            tf = hasValue(allInOneIndexTf, term);
//                                System.out.println("tf size = " + allInOneIndexTf.size());
                                if(tf==false){
                                    System.out.println("allInOneIndexTf false: " + term);
                                    System.out.println("allInOneIndexTf add: " + "default");
                                    allInOneIndexTf.put(term, 1);
                                    System.out.println("allInOneIndexTf now: " + allInOneIndexTf.get(term));

                                }else if(tf==true){
                                int orgValue = allInOneIndexTf.get(term);
                                allInOneIndexTf.put(term, ++orgValue);
                                System.out.println("allInOneIndexTf true: " + term);
                                System.out.println("allInOneIndexTf add: " + orgValue);
                                    System.out.println("term = " + term + ", value = " + allInOneIndexTf.get(term));
                                System.out.println("allInOneIndexTf now: " + allInOneIndexTf.get(term));
                                }

                            if (!term.equals(null) && !term.equals("")) {
                                if (term.length() > 45) {
                                    term = term.substring(0, 20);
                                }
//                                    System.out.println("test2");

                                stringPlate indexStp;
                                stringPlate paragraphStp;
                                stringPlate offsetStp;
                                if(allInOneIndexDfFirst.get(term) == null){
                                    allInOneIndexDfFirst.put(term, new ArrayList<>());
                                }

                                allInOneIndexDfFirst.get(term).add(1);

//                                paragraphStp = new stringPlate(term, docId, offset, i, 1);
//                                    System.out.println("getParagraph term = " + paragraphStp.getContent());
//                                    System.out.println("getParagraph docid = " + paragraphStp.getDocNumber());
//                                    System.out.println("getParagraph offset = " + paragraphStp.getOffset());
//                                    System.out.println("getParagraph para = " + paragraphStp.getParagraph());

//                                allInOne_paragraph.add(paragraphStp);
                                if (!forIndex.contains(term)) {
                                    indexStp = new stringPlate(docId, term, allInOneIndexTf.get(term), "");
                                    if (!indexStp.equals((Object)null)) {
                                        allInOne_Index.add(indexStp);
//                                            System.out.println(indexStp.getContent());
                                        forIndex.add(term);
                                    }
                                } else {
                                    forIndex.add(term);
                                }

                                offsetStp = new stringPlate(docId, term, offset++, 0.1);
                                if (!offsetStp.equals((Object)null)) {
                                    allInOne.add(offsetStp);
//                                        System.out.println("offsetstp = " + offsetStp.getParagraph());
                                }
                            }
                        }

                        for(String strr : allInOneIndexTf.keySet()){
                            System.out.println("allInOneIndexTf term: " + strr);
                            System.out.println("allInOneIndexTf num: " + allInOneIndexTf.get(strr));

                        }

                        for(int ind = 0; ind< allInOneIndexTf.size(); ind++){
                            System.out.println("indextf term : " + allInOne_Index.get(ind).getContent());
                            System.out.println("indextf : " + allInOneIndexTf.get(allInOne_Index.get(ind).getContent()));
//                                System.out.println("term = " + sp.getContent() + ", tf = " + sp.getTf());
                        }

                        for(int ind = 0; ind< allInOne_Index.size(); ind++){
                            System.out.println("indextf : " + allInOneIndexTf.get(allInOne_Index.get(ind).getContent()));
                            System.out.println("content : " + allInOne_Index.get(ind).getContent());
                            System.out.println("tf : " + allInOne_Index.get(ind).getTf());
                            allInOne_Index.get(ind).setTf(allInOneIndexTf.get(allInOne_Index.get(ind).getContent()));
//                                System.out.println("term = " + sp.getContent() + ", tf = " + sp.getTf());
                        }
                    }
                } catch (Throwable var42) {
                    var13 = var42;
                    throw var42;
                } finally {
                    if (channel1 != null) {
                        if (var13 != null) {
                            try {
                                channel1.close();
                            } catch (Throwable var40) {
                                var13.addSuppressed(var40);
                            }
                        } else {
                            channel1.close();
                        }
                    }
                }
            } catch (IOException var44) {
                var44.printStackTrace();
            }
            return true;


        }).collect(Collectors.toList());

        // 단어별 df 확인
        for(String setList : allInOneIndexDfFirst.keySet()){
//            allInOneIndexDfSecond.put(setList, allInOneIndexDfFirst.get(setList).size());
            allInOne_df.add(new stringPlate(setList,  allInOneIndexDfFirst.get(setList).size()));
            for(int ind = 0; ind < allInOne_Index.size(); ind++){
                if(allInOne_Index.get(ind).equals(setList)){
                    allInOne_Index.get(ind).setDf(allInOneIndexDfFirst.get(setList).size());
//                    System.out.println(allInOne_Index.get(ind));
                }
            }
        }

//        Collections.reverse(allInOne_Index);

//        for(int ind = 0; ind< allInOne_Index.size(); ind++){
//            allInOne_Index.get(ind).setTf(allInOneIndexTf.get(term).getTf());
//            System.out.println("term = " + sp.getContent() + ", tf = " + sp.getTf());
//        }
        dbTool.deleteDfs(conns);

        for(stringPlate sp  :  allInOne_df){
//            System.out.println("sp.getcontent() = " + sp.getContent());
            if(allInOneIndexDfSecond.get(sp.getContent()) != null){
                sp.setDf(allInOneIndexDfSecond.get(sp.getContent()));
                allInOne_df.remove(sp.getContent());
            }else{
                sp.setDf(1);
            }
//            System.out.println("df = " + allInOneIndexDfSecond.get(sp.getContent()));
//            sp.setDf(allInOneIndexDfSecond.get(sp.getContent()));
        }
        //

        while(allInOne_Index.remove((Object)null)) {
        }

        while(allInOne.remove((Object)null)) {
        }

        allTerms.clear();
        long endTime = System.currentTimeMillis();
        String output = "Expended Time for read is " + (double)(endTime - startTime) / 1000.0D;
        File file = new File(filePath + "logfile.txt");

        try {
            FileWriter fw = new FileWriter(file, true);
            fw.write(output + "\n");
            fw.close();
        } catch (IOException var14) {
            var14.printStackTrace();
        }

        long endTime2 = System.currentTimeMillis();
        System.out.println("Expended Time for read is " + (double)(endTime2 - startTime) / 1000.0D);
    }

    public void bulkInsertDfDataTest(List<stringPlate> propertiesList, Connection conn) throws SQLException {
        PgBulkInsert<stringPlate> bulkInsert = new PgBulkInsert(new dfMapping("term_df", conn));
        bulkInsert.saveAll(PostgreSqlUtils.getPGConnection(conn), propertiesList.stream());
    }

    public void bulkInsertIndexData(List<stringPlate> propertiesList, Connection conn) throws SQLException {
        PgBulkInsert<stringPlate> bulkInsert = new PgBulkInsert(new indexMapping("term_index", conn));
        bulkInsert.saveAll(PostgreSqlUtils.getPGConnection(conn), propertiesList.stream());
    }

    public void bulkInsertOffsetData(List<stringPlate> propertiesList, Connection conn) throws SQLException {
        PgBulkInsert<stringPlate> bulkInsert = new PgBulkInsert(new offsetMapping("term_offset", conn));
        bulkInsert.saveAll(PostgreSqlUtils.getPGConnection(conn), propertiesList.stream());
    }

//    public void bulkInsertParagraphData(List<stringPlate> properties2, Connection conn) throws SQLException {
//        PgBulkInsert<stringPlate> bulkInsert = new PgBulkInsert(new paragraphMapping("term_paragraph", conn));
//        bulkInsert.saveAll(PostgreSqlUtils.getPGConnection(conn), properties2.stream());
//    }

    public void executor(Connection conn, String filePath, long startTime) {
        long endTime;

        // df용
        try {
            this.bulkInsertDfDataTest(allInOne_df, conn);
            endTime = System.currentTimeMillis();
            System.out.println("Expended Time for df is " + (double)(endTime - startTime) / 1000.0D);
        } catch (SQLException var11) {
            var11.printStackTrace();
        }
        // index용
        try {
            this.bulkInsertIndexData(allInOne_Index, conn);
            endTime = System.currentTimeMillis();
            System.out.println("Expended Time for index is " + (double)(endTime - startTime) / 1000.0D);
        } catch (SQLException var12) {
            var12.printStackTrace();
        }
        // offset용
        try {
            this.bulkInsertOffsetData(allInOne, conn);
            endTime = System.currentTimeMillis();
            System.out.println("Expended Time for offset is " + (double)(endTime - startTime) / 1000.0D);
        } catch (SQLException var11) {
            var11.printStackTrace();
        }
        // paragraph용
//        try {
//            this.bulkInsertParagraphData(allInOne_paragraph, conn);
//            endTime = System.currentTimeMillis();
//            System.out.println("Expended Time for paragraph is " + (double)(endTime - startTime) / 1000.0D);
//        } catch (SQLException var11) {
//            var11.printStackTrace();
//        }
        // 파라그래프용 만들어야 함
//        try {
//            this.bulkInsertOffsetData(allInOne, conn);
//            endTime = System.currentTimeMillis();
//            System.out.println("Expended Time for offset is " + (double)(endTime - startTime) / 1000.0D);
//        } catch (SQLException var11) {
//            var11.printStackTrace();
//        }


        endTime = System.currentTimeMillis();
        String output = "Expended totalTime is " + (double)(endTime - startTime) / 1000.0D;
        System.out.println(output);
        File file = new File(filePath + "logfile.txt");

        try {
            FileWriter fw = new FileWriter(file, true);
            fw.write(output + "\n");
            fw.close();
        } catch (IOException var10) {
            var10.printStackTrace();
        }

        allInOne.clear();
        allInOne_Index.clear();
    }

    public static boolean hasValue(TreeMap<String, Integer> m, String value) {
        for(String o: m.keySet()) {
//            System.out.println("map = " + o + ", original = " + value);
            if(o.equals(value)) {
                return true;
            }
        }
        return false;
    }

    public static boolean hasAtomValue(TreeMap<String, AtomicInteger> m, Object value) {
        for(Object o: m.keySet()) {
            if(m.get(o).equals(value)) {
                return true;
            }
        }
        return false;
    }

    public static Object getKey(TreeMap<Integer, String> m, Object value) {
        for(Object o: m.keySet()) {
            if(m.get(o).equals(value)) {
                return o;
            }
        }
        return null;
    }
}
