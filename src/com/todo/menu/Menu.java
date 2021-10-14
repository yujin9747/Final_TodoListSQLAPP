package com.todo.menu;
public class Menu {   
    public static void displaymenu()
    {  
    	
        System.out.println();
        System.out.println("TodoList 관리 명령어 사용법");
        System.out.println("add - 항목 추가하기");
        System.out.println("del - 항목 삭제하기");
        System.out.println("edit - 항목 수정하기");
        System.out.println("comp - 항목 완료 체크하기");
        System.out.println("ls - 전체 목록 보기");
        System.out.println("ls_name_asc - 목록을 이름순으로 정렬하기(오름차순)");
        System.out.println("ls_name_desc - 목록을 이름순으로 정렬하기(내림차순)");
        System.out.println("ls_date - 마감기한 순서대로 정렬하기");
        System.out.println("ls_date_desc - 마감기한 역순으로 정렬하기");
        System.out.println("ls_cat - 카테고리 정렬하기");
        System.out.println("ls_comp - 완료된 항목 정렬하기");
        System.out.println("find (키워드 입력) - 키워드로 검색하기");
        System.out.println("find_cat (키워드 입력) - 키워드로 카테고리 검색하기");
        System.out.println("exit - 종료");
    }
    public static void prompt() {
    	System.out.print("원하시는 메뉴를 입력해주세요 > (도움말 띄우기 : help)") ;
    }
}
