package com.example.user_login.ebook;

public class Ebook_data {

    private String name,pdfurl;

    public Ebook_data() {
    }

    public Ebook_data(String name, String pdfurl) {
        this.name = name;
        this.pdfurl = pdfurl;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getPdfurl() {
        return pdfurl;
    }

    public void setPdfurl(String pdfurl) {
        this.pdfurl = pdfurl;
    }
}
