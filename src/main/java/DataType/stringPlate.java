//
// Source code recreated from a .class file by IntelliJ IDEA
// (powered by Fernflower decompiler)
//

package DataType;

public class stringPlate {
    private int docNumber = 0;
    private String content = null;
    private int paragraph = 0;
    private int offset = 0;
    private int tf = 0;
    private int df = 0;
    private double doclen = 0.0;


    public stringPlate( String content, int df) {
        this.df = df;
        this.content = content;
    }

    public stringPlate(int docNumber, String content) {
        this.docNumber = docNumber;
        this.content = content;
    }

    public stringPlate(int docNumber, String content, int paragraph) {
        this.docNumber = docNumber;
        this.content = content;
        this.paragraph = paragraph;
    }

    public stringPlate(int docNumber, String content, int tf, double doclen, String forIndex) {
        this.docNumber = docNumber;
        this.content = content;
        this.tf = tf;
        this.doclen = doclen;
    }

    public stringPlate(int docNumber, String content, int offset, double forOffset) {
        this.docNumber = docNumber;
        this.content = content;
        this.offset = offset;
    }

    public stringPlate(String content,int docNumber,  int offset, int paragraph, int df) {
        this.docNumber = docNumber;
        this.content = content;
        this.offset = offset;
        this.paragraph = paragraph;
//        this.tf = tf;
//        this.df = df;
    }

    public int getParagraph() {
        return this.paragraph;
    }

    public void setParagraph(int paragraph) {
        this.paragraph = paragraph;
    }

    public int getOffset() {
        return this.offset;
    }

    public void setOffset(int offset) {
        this.offset = offset;
    }

    public int getDocNumber() {
        return this.docNumber;
    }

    public void setDocNumber(int docNumber) {
        this.docNumber = docNumber;
    }

    public String getContent() {
        return this.content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public int getTf() {
        return tf;
    }

    public void setTf(int tf) {
        this.tf = tf;
    }

    public int getDf() {
        return df;
    }

    public void setDf(int df) {
        this.df = df;
    }
}
