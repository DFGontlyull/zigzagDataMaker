//
// Source code recreated from a .class file by IntelliJ IDEA
// (powered by Fernflower decompiler)
//

package Executor;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.PrintStream;
import java.nio.ByteBuffer;
import java.nio.channels.FileChannel;
import java.nio.charset.Charset;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardOpenOption;
import java.sql.Connection;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Stream;
import Judge.stopWord;

public class saveAllTerms {
    private static boolean stem = true;

    public saveAllTerms() {
    }

    public void inputTerms(Connection conn, String filePath) throws FileNotFoundException, IOException {
        File[] allfiles = (new File(filePath)).listFiles();
        List<File> lists = Arrays.asList(allfiles);
        Stream var10000 = lists.stream().map((xfile) -> {
            System.out.println(xfile.getName() + "파일 진행중");
            Path path = Paths.get(xfile.toURI());

            try {
                FileChannel channel1 = FileChannel.open(path, StandardOpenOption.READ);
                Throwable var4 = null;

                try {
                    ByteBuffer byteBuffer2 = ByteBuffer.allocate((int)Files.size(path));
                    channel1.read(byteBuffer2);
                    byteBuffer2.flip();
                    String readLine = Charset.defaultCharset().decode(byteBuffer2).toString();
                    String[] split = readLine.split("\\s+");
                    String tempResult = "";
                    channel1.close();
                    String[] resultx = split;
                    int var10 = split.length;

                    for(int var11 = 0; var11 < var10; ++var11) {
                        String tokenWithPunctuation = resultx[var11];
                        String token = "";
                        char[] chars = tokenWithPunctuation.toCharArray();
                        char[] var15 = chars;
                        int var16 = chars.length;

                        for(int var17 = 0; var17 < var16; ++var17) {
                            char character = var15[var17];
                            if (character >= 'a' && character <= 'z') {
                                token = token + character;
                            }
                        }

                        String stemmedToken = "";
                        if (stem) {
                            stemmedToken = stopWord.stemString(token);
                            if (stemmedToken.equals("") || stemmedToken.equals((Object)null)) {
                                continue;
                            }
                        }

                        if (!token.equals("") && !token.equals((Object)null) && !stopWord.isStopword(token) && (!stem || !stopWord.isStemmedStopword(token) && !stopWord.isStemmedStopword(stemmedToken))) {
                            if (stem) {
                                tempResult = tempResult + stemmedToken + " ";
                            } else {
                                tempResult = tempResult + token + " ";
                            }
                        }
                    }

                    resultx = null;
                    String result;
                    if (stem) {
                        result = stopWord.removeStemmedStopWords(tempResult);
                    } else {
                        result = stopWord.removeStopWords(tempResult);
                    }

                    String[] tokenizedTerms = result.replaceAll("[\\W&&[^\\s]]", "").split("\\W+");
                    String[] var32 = tokenizedTerms;
                    int var33 = tokenizedTerms.length;

                    for(int var34 = 0; var34 < var33; ++var34) {
                        String term = var32[var34];
                        connectDB.termInsertToTable(conn, term);
                    }
                } catch (Throwable var27) {
                    var4 = var27;
                    throw var27;
                } finally {
                    if (channel1 != null) {
                        if (var4 != null) {
                            try {
                                channel1.close();
                            } catch (Throwable var26) {
                                var4.addSuppressed(var26);
                            }
                        } else {
                            channel1.close();
                        }
                    }

                }
            } catch (Exception var29) {
                System.out.println(var29);
            }

            return true;
        });
        PrintStream var10001 = System.out;
        System.out.getClass();
        var10000.forEach(var10001::println);
    }
}
