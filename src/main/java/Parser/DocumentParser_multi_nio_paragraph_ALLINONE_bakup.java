////
//// Source code recreated from a .class file by IntelliJ IDEA
//// (powered by Fernflower decompiler)
////
//
//package Parser;
//
//import de.bytefish.pgbulkinsert.PgBulkInsert;
//import de.bytefish.pgbulkinsert.util.PostgreSqlUtils;
//import java.io.File;
//import java.io.FileNotFoundException;
//import java.io.FileWriter;
//import java.io.IOException;
//import java.nio.ByteBuffer;
//import java.nio.channels.FileChannel;
//import java.nio.charset.Charset;
//import java.nio.file.Files;
//import java.nio.file.Path;
//import java.nio.file.Paths;
//import java.nio.file.StandardOpenOption;
//import java.sql.Connection;
//import java.sql.SQLException;
//import java.util.ArrayList;
//import java.util.HashSet;
//import java.util.List;
//import java.util.Map;
//import java.util.Set;
//import java.util.TreeMap;
//import java.util.TreeSet;
//import java.util.concurrent.atomic.AtomicInteger;
//import java.util.stream.Collectors;
//import DataType.stringPlate;
//import Executor.indexMapping_shorted;
//import Executor.offsetMapping_shorted;
//import Executor.paragraphMapping_shorted;
//import Judge.stopWord;
//
//public class DocumentParser_multi_nio_paragraph_ALLINONE {
//    private static final String PARAGRAPH_SPLIT_REGEX = "(?m)(?=^\\s{4})";
//    private static List<stringPlate> allTerms = new ArrayList();
//    private static List<stringPlate> allInOne = new ArrayList();
//    private static List<stringPlate> allInOne_Index = new ArrayList();
//    private static boolean stem = true;
//
//    public DocumentParser_multi_nio_paragraph_ALLINONE() {
//    }
//
//    public static void configure(boolean stem) {
//    }
//
//    public static String removePunctuationWord(String original) {
//        String result = "";
//        char[] chars = original.toCharArray();
//        char[] var3 = chars;
//        int var4 = chars.length;
//
//        for(int var5 = 0; var5 < var4; ++var5) {
//            char character = var3[var5];
//            if (character >= 'a' && character <= 'z') {
//                result = result + character;
//            }
//        }
//
//        return result;
//    }
//
//    public void parseFiles(String filePath, String fileName, int startNum, int endNum, Long startTime) throws FileNotFoundException, IOException {
//        System.out.println(startNum + " 번째 파일 ~ " + endNum + "번째까지");
//        List<File> lists = new ArrayList();
//        configure(true);
//        AtomicInteger fileCounter = new AtomicInteger(0);
//
//        for(int i = Integer.valueOf(startNum); i <= Integer.valueOf(endNum); ++i) {
//            lists.add(new File(filePath + fileName + i + ".txt"));
//        }
//
//        lists.stream().map((xFile) -> {
//            fileCounter.incrementAndGet();
//            new ArrayList();
//            Set<String> forIndex = new HashSet();
//            new TreeMap();
//            Map<String, Integer> temp_confirmTF = new TreeMap();
//            new TreeMap();
//            Path path = Paths.get(xFile.toURI());
//            new StringBuilder();
//            String readLine = "";
//            if ((fileCounter.get() + 99) % 100 == 0) {
//                long endTime = System.currentTimeMillis();
//                String output = "Expended Time is " + (double)(endTime - startTime) / 1000.0D;
//                File file = new File(filePath + "logfile.txt");
//
//                try {
//                    FileWriter fw = new FileWriter(file, true);
//                    fw.write("File Count : " + fileCounter.get() + "\n");
//                    fw.write(output + "\n");
//                    fw.close();
//                } catch (IOException var41) {
//                    var41.printStackTrace();
//                }
//            }
//
//            temp_confirmTF.clear();
//
//            try {
//                FileChannel channel1 = FileChannel.open(path, StandardOpenOption.READ);
//                Throwable var13 = null;
//
//                try {
//                    ByteBuffer byteBuffer2 = ByteBuffer.allocate((int)Files.size(path));
//                    channel1.read(byteBuffer2);
//                    byteBuffer2.flip();
//                    readLine = Charset.defaultCharset().decode(byteBuffer2).toString();
//                    String[] paragraphs = readLine.split("(?m)(?=^\\s{4})");
//                    List<String> arrangedParagraphs = new ArrayList();
//                    boolean ix = false;
//                    String[] var18 = paragraphs;
//                    int offset = paragraphs.length;
//
//                    for(int var20 = 0; var20 < offset; ++var20) {
//                        String paragraph = var18[var20];
//                        String temp = paragraph.trim();
//                        if (!temp.trim().equals("")) {
//                            arrangedParagraphs.add(temp);
//                        }
//                    }
//
//                    int docId = Integer.valueOf(xFile.getName().replaceAll("[^0-9]", ""));
//                    offset = 0;
//                    temp_confirmTF.clear();
//                    Set<String> str = new TreeSet();
//                    str.clear();
//
//                    for(int i = 0; i < arrangedParagraphs.size(); ++i) {
//                        StringBuilder sb = new StringBuilder();
//                        String[] splitedTerms = ((String)arrangedParagraphs.get(i)).split("\\s+");
//                        String result = "";
//                        String tempResult = "";
//                        String[] tokenizedTerms = splitedTerms;
//                        int var26 = splitedTerms.length;
//
//                        String term;
//                        for(int var27 = 0; var27 < var26; ++var27) {
//                            String tokenWithPunctuation = tokenizedTerms[var27];
//                            String token = removePunctuationWord(tokenWithPunctuation);
//                            term = "";
//                            if (stem) {
//                                term = stopWord.stemString(token);
//                                if (term.equals("") || term.equals((Object)null)) {
//                                    continue;
//                                }
//                            }
//
//                            if (!token.equals("") && !token.equals((Object)null) && !stopWord.isStopword(token) && (!stem || !stopWord.isStemmedStopword(token) && !stopWord.isStemmedStopword(term))) {
//                                if (stem) {
//                                    tempResult = tempResult + term + " ";
//                                } else {
//                                    tempResult = tempResult + token + " ";
//                                }
//
//                                System.out.println("docId = " + docId);
//                                System.out.println("paraNum = " + i);
//                                System.out.println("token = " + token);
//                                System.out.println("stemmedToken = " + term);
//                            }
//                        }
//
//                        if (stem) {
//                            result = stopWord.removeStemmedStopWords(tempResult);
//                        } else {
//                            result = stopWord.removeStopWords(tempResult);
//                        }
//
//                        if (!result.equals("") && !result.equals((Object)null)) {
//                            tokenizedTerms = sb.toString().replaceAll("[\\W&&[^\\s]]", "").split("\\W+");
//                            boolean tf = false;
//                            String[] var54 = tokenizedTerms;
//                            int var55 = tokenizedTerms.length;
//
//                            for(int var56 = 0; var56 < var55; ++var56) {
//                                term = var54[var56];
//                                if (!term.equals((Object)null) && !term.equals("")) {
//                                    if (term.length() > 45) {
//                                        term = term.substring(0, 20);
//                                    }
//
//                                    stringPlate stp;
//                                    if (!forIndex.contains(term)) {
//                                        stp = new stringPlate(docId, term);
//                                        if (!stp.equals((Object)null)) {
//                                            allInOne_Index.add(stp);
//                                            forIndex.add(term);
//                                        }
//                                    } else {
//                                        forIndex.add(term);
//                                    }
//
//                                    stp = new stringPlate(docId, term, i, offset++);
//                                    if (!stp.equals((Object)null)) {
//                                        allInOne.add(stp);
//                                    }
//                                }
//                            }
//                        }
//                    }
//                } catch (Throwable var42) {
//                    var13 = var42;
//                    throw var42;
//                } finally {
//                    if (channel1 != null) {
//                        if (var13 != null) {
//                            try {
//                                channel1.close();
//                            } catch (Throwable var40) {
//                                var13.addSuppressed(var40);
//                            }
//                        } else {
//                            channel1.close();
//                        }
//                    }
//
//                }
//            } catch (IOException var44) {
//                var44.printStackTrace();
//            }
//
//            return true;
//        }).collect(Collectors.toList());
//
//        while(allInOne_Index.remove((Object)null)) {
//        }
//
//        while(allInOne.remove((Object)null)) {
//        }
//
//        allTerms.clear();
//        long endTime = System.currentTimeMillis();
//        String output = "Expended Time for read is " + (double)(endTime - startTime) / 1000.0D;
//        File file = new File(filePath + "logfile.txt");
//
//        try {
//            FileWriter fw = new FileWriter(file, true);
//            fw.write(output + "\n");
//            fw.close();
//        } catch (IOException var14) {
//            var14.printStackTrace();
//        }
//
//        long endTime2 = System.currentTimeMillis();
//        System.out.println("Expended Time for read is " + (double)(endTime2 - startTime) / 1000.0D);
//    }
//
//    public void bulkInsertIndexDataTest(List<stringPlate> propertiesList, Connection conn) throws SQLException {
//        PgBulkInsert<stringPlate> bulkInsert = new PgBulkInsert(new indexMapping_shorted("test_index", conn));
//        bulkInsert.saveAll(PostgreSqlUtils.getPGConnection(conn), propertiesList.stream());
//    }
//
//    public void bulkInsertOffsetDataTest(List<stringPlate> propertiesList, Connection conn) throws SQLException {
//        PgBulkInsert<stringPlate> bulkInsert = new PgBulkInsert(new offsetMapping_shorted("test_offset", conn));
//        bulkInsert.saveAll(PostgreSqlUtils.getPGConnection(conn), propertiesList.stream());
//    }
//
//    public void bulkInsertParagraphDataTest(List<stringPlate> properties2, Connection conn) throws SQLException {
//        PgBulkInsert<stringPlate> bulkInsert = new PgBulkInsert(new paragraphMapping_shorted("test_paragraph", conn));
//        bulkInsert.saveAll(PostgreSqlUtils.getPGConnection(conn), properties2.stream());
//    }
//
//    public void executor(Connection conn, String filePath, long startTime) {
//        long endTime;
//        try {
//            this.bulkInsertIndexDataTest(allInOne_Index, conn);
//            endTime = System.currentTimeMillis();
//            System.out.println("Expended Time for index is " + (double)(endTime - startTime) / 1000.0D);
//        } catch (SQLException var12) {
//            var12.printStackTrace();
//        }
//
//        try {
//            this.bulkInsertOffsetDataTest(allInOne, conn);
//            endTime = System.currentTimeMillis();
//            System.out.println("Expended Time for offset is " + (double)(endTime - startTime) / 1000.0D);
//        } catch (SQLException var11) {
//            var11.printStackTrace();
//        }
//
//        endTime = System.currentTimeMillis();
//        String output = "Expended totalTime is " + (double)(endTime - startTime) / 1000.0D;
//        System.out.println(output);
//        File file = new File(filePath + "logfile.txt");
//
//        try {
//            FileWriter fw = new FileWriter(file, true);
//            fw.write(output + "\n");
//            fw.close();
//        } catch (IOException var10) {
//            var10.printStackTrace();
//        }
//
//        allInOne.clear();
//        allInOne_Index.clear();
//    }
//}
