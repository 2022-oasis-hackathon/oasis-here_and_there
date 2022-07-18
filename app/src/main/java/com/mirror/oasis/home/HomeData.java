package com.mirror.oasis.home;

import java.util.ArrayList;

public class HomeData {
    /*
            location: 전라남도 순천시
        detaillocation: 전라남도 순천시 월등면 계월길 138
        name: 순천향매실마을
        detail: 귀농형(일반)
        date1: 2022.08.01            // 입주가능일
        date2: 2022.06.25~2022.07.24 // 신청기간
        date3: 2022.08.01~2022.09.30 // 운영기간
        info: 지리산의 끝자락 문유산의 정기를 받은 순천향매실마을은 30만평의 매실나무가 조성되어 마을전체가 매실농사를 지으며 전국에서 매실이 가장 많이 나는 마을입니다.
27가구 정도의 귀농귀촌 농가가 마을과 조화를 이루며 활기차게 살아가는 마을입니다.
        product: 매실,복숭아
        representative: 이정x
        phone: 01033044680
        writer: 지자체

     */
    private String key;
    private String location;
    private String detailLocation;
    private String name;
    private String detail; // 세부유형
    private String date1; // 입주가능일
    private String date2; // 신청기간
    private String date3; // 운영기간
    private String info;
    private String product; // 지역주요작목
    private String representative; // 대표자
    private String phone; // 대표자 번호
    private String writer; // 작성자(지자체 or 개인)
    private String firstPhoto;
    private ArrayList<String> photoKeys;

    public HomeData(){}

    public HomeData(String key, String location, String detailLocation, String name, String detail, String date1, String date2, String date3, String info, String product, String representative, String phone, String writer, String firstPhoto, ArrayList<String> photoKeys) {
        this.key = key;
        this.location = location;
        this.detailLocation = detailLocation;
        this.name = name;
        this.detail = detail;
        this.date1 = date1;
        this.date2 = date2;
        this.date3 = date3;
        this.info = info;
        this.product = product;
        this.representative = representative;
        this.phone = phone;
        this.writer = writer;
        this.firstPhoto = firstPhoto;
        this.photoKeys = photoKeys;
    }

    public String getKey() {
        return key;
    }

    public void setKey(String key) {
        this.key = key;
    }

    public String getLocation() {
        return location;
    }

    public void setLocation(String location) {
        this.location = location;
    }

    public String getDetailLocation() {
        return detailLocation;
    }

    public void setDetailLocation(String detailLocation) {
        this.detailLocation = detailLocation;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getDetail() {
        return detail;
    }

    public void setDetail(String detail) {
        this.detail = detail;
    }

    public String getDate1() {
        return date1;
    }

    public void setDate1(String date1) {
        this.date1 = date1;
    }

    public String getDate2() {
        return date2;
    }

    public void setDate2(String date2) {
        this.date2 = date2;
    }

    public String getDate3() {
        return date3;
    }

    public void setDate3(String date3) {
        this.date3 = date3;
    }

    public String getInfo() {
        return info;
    }

    public void setInfo(String info) {
        this.info = info;
    }

    public String getProduct() {
        return product;
    }

    public void setProduct(String product) {
        this.product = product;
    }

    public String getRepresentative() {
        return representative;
    }

    public void setRepresentative(String representative) {
        this.representative = representative;
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public String getWriter() {
        return writer;
    }

    public void setWriter(String writer) {
        this.writer = writer;
    }

    public String getFirstPhoto() {
        return firstPhoto;
    }

    public void setFirstPhoto(String firstPhoto) {
        this.firstPhoto = firstPhoto;
    }

    public ArrayList<String> getPhotoKeys() {
        return photoKeys;
    }

    public void setPhotoKeys(ArrayList<String> photoKeys) {
        this.photoKeys = photoKeys;
    }
}
