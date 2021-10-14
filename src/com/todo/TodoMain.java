package com.todo;
  

import java.io.IOException;
import java.util.Scanner;
import com.todo.dao.TodoList;
import com.todo.menu.Menu;
import com.todo.service.TodoUtil; 
public class TodoMain {
	public static void start() throws IOException {
		Scanner sc = new Scanner(System.in); 
		TodoList l = new TodoList();
		boolean quit = false;
		String choice = " " ;
		Menu.displaymenu();
		do {
			Menu.prompt() ;
			choice = sc.next();
			switch (choice) {

			case "add":
				TodoUtil.createItem(l);
				break;
			
			case "del":
				TodoUtil.deleteItem(l);
				break;
				
			case "edit":
				TodoUtil.updateItem(l);
				break;
				
			case "ls":
				TodoUtil.listAll(l);
				break;

			case "ls_name_asc":
				System.out.println("\n제목을 오름차순으로 정렬합니다.") ;
				TodoUtil.listAll(l, "title", 1) ;
				break;

			case "ls_name_desc":
				System.out.println("\n제목을 역순으로 정렬합니다.") ;
				TodoUtil.listAll(l, "title", 0) ;
				break;
				
			case "ls_date":
				System.out.println("\n마감기한이 가까운 순서대로 정렬합니다.") ;
				TodoUtil.listAll(l, "due_date", 1) ;
				break;
				
			case "ls_date_desc":
				System.out.println("\n마감기한기준으로 역순으로 정렬합니다.") ;
				TodoUtil.listAll(l, "due_date", 0) ;
				break;
				
			case "ls_cat":
				System.out.println("\n카테고리를 출력합니다.") ;
				TodoUtil.listCateAll(l) ;
				break;

			case "exit":
				quit = true;
				break;
				
			case "help" :
				Menu.displaymenu();
				break ;
			case "find" :
				String keyword = sc.next();
				TodoUtil.find(l, keyword);
				break ;
				
			case "find_cat" :
				String find_cat_keyword = sc.next();
				TodoUtil.find_cat(l, find_cat_keyword);
				break ;
				
			case "comp" :
				TodoUtil.completeItem(l) ;
				break ;
				
			case "ls_comp" :
				System.out.println("완료된 항목을 출력합니다.") ;
				TodoUtil.listAll(l, 1);
				break ;
			default:
				System.out.println("잘못 입력하셨습니다! 관리 명령어 사용법이 필요하시면 help를 입력해주세요");
				break;
			}
		} while (!quit);
		sc.close() ;
	}
}
