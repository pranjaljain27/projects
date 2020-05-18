package com.example.otpverify.Model;

public class Detail {
    String Addr;
    String BusiName;
    String Cato;
    String CityName;
    String Emails;
    String Loclty;
    String whoOwn;
    String StateName;

    public Detail()
    {

    }
    public Detail( String whoOwn,String busiName,String emails,String addr,String loclty, String cityName, String stateName, String cato) {
        Addr = addr;
        BusiName = busiName;
        Cato = cato;
        CityName = cityName;
        Emails = emails;
        Loclty = loclty;
        this.whoOwn = whoOwn;
        StateName = stateName;
    }

    public String getAddr() {
        return Addr;
    }

    public void setAddr(String addr) {
        Addr = addr;
    }

    public String getBusiName() {
        return BusiName;
    }

    public void setBusiName(String busiName) {
        BusiName = busiName;
    }

    public String getCato() {
        return Cato;
    }

    public void setCato(String cato) {
        Cato = cato;
    }

    public String getCityName() {
        return CityName;
    }

    public void setCityName(String cityName) {
        CityName = cityName;
    }

    public String getEmails() {
        return Emails;
    }

    public void setEmails(String emails) {
        Emails = emails;
    }

    public String getLoclty() {
        return Loclty;
    }

    public void setLoclty(String loclty) {
        Loclty = loclty;
    }

    public String getWhoOwn() {
        return whoOwn;
    }

    public void setWhoOwn(String whoOwn) {
        this.whoOwn = whoOwn;
    }

    public String getStateName() {
        return StateName;
    }

    public void setStateName(String stateName) {
        StateName = stateName;
    }
}
