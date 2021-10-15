package com.todo.service;
 
import java.util.*;
import java.io.* ;
import java.sql.Statement;

import com.todo.dao.TodoItem;
import com.todo.dao.TodoList;
 

public class TodoUtil {
	public static void createItem(TodoList list) { 
		
		String title, desc, category, due_date, completion_rate;
		int importance =0;
		Scanner sc = new Scanner(System.in);
		  
		System.out.println("\nadd(항목 추가)를 선택하셨습니다!");
		System.out.print("항목 카테고리를 입력해주세요 > ");
		category = sc.next();
		System.out.print("항목 이름을 입력해주세요 > ");
		title = sc.next();
		if(list.isDuplicate(title)) {
			System.out.println("제목이 중복 됩니다.") ;
			return ;
		}
		sc.nextLine();
		System.out.print("내용을 입력해주세요 > ");
		desc = sc.nextLine();
		
		System.out.print("마감기한을 입력해주세요 > ");
		due_date = sc.nextLine();
		int appropriate = 0;
		while(appropriate == 0) {
			System.out.print("중요도를 입력해주세요(1-5) > ");
			importance = sc.nextInt();
			if(importance >= 1 && importance <=5) appropriate =1 ;
			else System.out.print("잘못 입력했습니다. 1-5사이의 값을 다시 입력하세요.");
		}
		sc.nextLine();
		System.out.print("완료율을 입력해주세요 > ");
		completion_rate = sc.next();
		TodoItem t = new TodoItem(title, desc, category, due_date, importance, completion_rate);
		if(list.addItem(t) > 0 ) {
			System.out.println("**항목 추가 완료**\n");
		}
		
	}

	public static void deleteItem(TodoList l) {
		int number = 0 ;
		int being = 0 ;
		Scanner sc = new Scanner(System.in);
		System.out.println("\ndel(항목 삭제)를 선택하셨습니다!");
		while(being == 0) {
			System.out.print("삭제할 항목 번호를 입력해주세요 > ");
			number = sc.nextInt() ;
			for (TodoItem item : l.getList()) {
				if (item.getId() == number) {
					System.out.println(item.toString()) ;
					being = 1 ;
					break ;
				}
			}
			if(being == 0) System.out.println(number + "번호와 일치하는 항목이 존재하지 않습니다.") ;
		}
		System.out.print("위 항목을 삭제하시겠습니까? (y/n) > ") ;
		String yesOrno = sc.next() ; 
		if(yesOrno.equals("y")) {
			if(l.deleteItem(number)>0)
				System.out.println("**항목 삭제 완료**\n");
		}
	}
	
	// 개인 추가 기눙 : 완료된 항목을 동시에 삭제
	public static void deleteItem(TodoList l, int yesOrno) {
		if(yesOrno == 0) return ;
		System.out.println("\n완료된 모든 항목을 삭제합니다!");
		for(TodoItem item :l.getList()) {
			if(item.getComp() == 1) {
				l.deleteItem(item.getId()) ;
			}
		}
		System.out.println("**완료된 항목 삭제 완료**\n");
	}


	public static void updateItem(TodoList l) {
		
		int number =0;
		Scanner sc = new Scanner(System.in);
		System.out.println("\nedit(항목 수정)를 선택하셨습니다!");
		int being = 0 ;//해당 번호의 아이템이 존재하는지 여부 ( 0 : 존재 안 함. 1 : 존재함 )		
		while(being == 0) {
			System.out.print("수정할 항목 번호를 입력해주세요 > ");
			number = sc.nextInt();
			for (TodoItem item : l.getList()) {
				if (item.getId() == number) {
					System.out.println(item.toString()) ;
					being = 1 ;
					break ;
				}	
			}
			if(being == 0) System.out.println(number + "번호와 일치하는 항목이 존재하지 않습니다.") ;
		}
		System.out.print("새로운 항목 카테고리를 입력해주세요 > ");
		String new_category = sc.next().trim();
		sc.nextLine() ;
		System.out.print("새로운 항목 이름을 입력해주세요 > ");
		String new_title = sc.next().trim();
		if(l.isDuplicate(new_title)) {
			System.out.println("제목이 중복 됩니다.") ;
			return ;
		}
		sc.nextLine() ;
		System.out.print("새 항목의 내용을 입력해주세요 > ");
		String new_description = sc.nextLine().trim();
		System.out.print("새 항목의 마감기한을 입력해주세요 > ");
		String new_due_date = sc.nextLine().trim();
		int appropriate = 0, new_importance = 0 ;
		while(appropriate == 0) {
			System.out.print("새 항목의 중요도를 입력해주세요(1-5) > ");
			new_importance = sc.nextInt();
			if(new_importance >= 1 && new_importance <=5) appropriate =1 ;
			else System.out.print("잘못 입력했습니다. 1-5사이의 값을 다시 입력하세요.");
		}
		sc.nextLine();
		System.out.print("새 항목의 완료율을 입력해주세요 > ");
		String new_completion_rate = sc.nextLine().trim();
		
		TodoItem t = new TodoItem(new_title, new_description, new_category, new_due_date, new_importance, new_completion_rate);
		t.setId(number) ;
		if(l.updateItem(t) > 0) 
			System.out.println("**수정되었습니다**\n");
	}

	

	public static void listAll(TodoList l) { //수정 완료
		System.out.println("\n\n<< 전체 목록 , 총 " + l.getCount()+"개 >>");
		for (TodoItem item : l.getList()) {
			System.out.print(item.toString());
		}
	}
	
	public static void listAll_simple(TodoList l) {
		System.out.println("\n\n<< 전체 목록 , 총 " + l.getCount()+"개 >>");
		for (TodoItem item : l.getList()) {
			System.out.print(item.toStringSimple(item.getImportance()));
		}
	}
	
	public static void listAll(TodoList l, int comp) {
		int count = 0 ;
		Scanner sc = new Scanner(System.in) ;
		for (TodoItem item : l.getList(comp)) {
			System.out.print(item.toString());
			count++ ;
		}
		System.out.printf("총 %d개의 항목이 완료 되었습니다.\n", count) ;
		if(count >0) {
			System.out.print("완료된 항목을 모두 삭제하시겠습니까?(1 : 삭제합니다 , 0 : 삭제하지 않습니다) >> ") ;
			int yesOrno = sc.nextInt() ;
			deleteItem(l, yesOrno) ;
		}
	}
	
	public static void listAll(TodoList l, String field, int version) { //수정 완료
		System.out.printf("전체 목록 %d개 \n", l.getCount()) ;
		for (TodoItem item : l.getOrderedList(field, version)) {
			System.out.print(item.toString());
		}
	}


	public static void find(TodoList l, String keyword) {
		int count = 0 ;
		for (TodoItem item : l.getList(keyword)) {
			System.out.print(item.toString());
			count++ ;
		}
		System.out.printf("총 %d개의 항목을 찾았습니다.\n\n", count) ;
	}

	public static void find_cat(TodoList l, String cat) {
		int count = 0 ; //키워드로 찾아진 항목의 갯수 카운팅
		for (TodoItem item : l.getListCategory(cat)) {
			System.out.print(item.toString());
			count++ ;
		}
		System.out.printf("총 %d개의 항목을 찾았습니다.\n\n", count) ;
		
	}
	
	public static void listCateAll(TodoList l) {
		int count = 0 ; //키워드로 찾아진 항목의 갯수 카운팅
		for (String item : l.getCategories()) {
			System.out.print(item + " ") ;
			count++ ;
		}
		System.out.printf("\n총 %d개의 카테고리가 등록되어 있습니다.\n\n", count) ;
	}

	public static void completeItem(TodoList l) {
		System.out.print("항목 완료를 선택하셨습니다! 완료할 항목 번호를 입력해주세요 >> ") ;
		String title = null, desc = null, category = null, due_date = null, current_date = null , completion_rate = null;
		int id =0, importance = 0;
		Scanner sc = new Scanner(System.in) ;
		int number = sc.nextInt();
		int being = 0 ; //해당 번호의 존재 여부
		while(being == 0) {
			for (TodoItem item : l.getList()) {
				if (item.getId() == number) {
					System.out.println(item.toString()) ;
					if(item.getComp() == 1) {
						System.out.println("이미 완료된 항목입니다.") ; 
						return ;
					}
					being = 1 ;
					id = item.getId() ;
					title = item.getTitle();
					desc = item.getDesc() ;
					category = item.getCategory() ;
					due_date = item.getDue_date() ;
					current_date = item.getCurrent_date() ;
					importance = item.getImportance() ;
					completion_rate = item.getCompletion_rate() ;
					break ;
				}
			}
			if(being == 0) {
				System.out.println(number + "번과 항목이 존재하지 않습니다.") ; 
				System.out.println("번호를 다시 입력해주세요. >> ") ;
				number = sc.nextInt() ;
			}
		}
		TodoItem t = new TodoItem(title, desc, category, due_date, importance, completion_rate) ;
		t.setId(id);
		t.setCurrent_date(current_date);
		t.setComp(1);
		if(l.updateItem(t) > 0) 
			System.out.println(number + "번 항목을 완료했습니다.") ;
		
	}
	
	public static void printFirst(TodoList l) {
		if(l.getCount_importance(5) > 0) l.findFirst(5) ;
		else if(l.getCount_importance(4)>0) l.findFirst(4) ;
		else if(l.getCount_importance(3)>0) l.findFirst(3) ;
		else if(l.getCount_importance(2)>0) l.findFirst(2) ;
		else if(l.getCount_importance(1)>0) l.findFirst(1) ;
		else System.out.println("리스트가 비어있습니다.") ;
	}


}