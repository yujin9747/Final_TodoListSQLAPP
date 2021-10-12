package com.todo;
  
import java.io.IOException;
import java.util.ArrayList;
import java.util.Scanner;

import com.todo.dao.TodoItem;
import com.todo.dao.TodoList;
import com.todo.menu.Menu;
import com.todo.service.TodoUtil; 
public class TodoMain {
	public static void start() throws IOException {
		Scanner sc = new Scanner(System.in); 
		TodoList l = new TodoList();
		boolean isList = false;
		boolean quit = false;
		String choice = " " ;
		Menu.displaymenu();
		do {
	
			isList = false;
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
				System.out.println("������ ������������ �����մϴ�.") ;
				TodoUtil.listAll(l, "title", 1) ;
				isList = true;
				break;

			case "ls_name_desc":
				System.out.println("������ �������� �����մϴ�.") ;
				TodoUtil.listAll(l, "title", 0) ;
				isList = true;
				break;
				
			case "ls_date":
				System.out.println("�׸��� �Էµ� ������� �����մϴ�.") ;
				TodoUtil.listAll(l, "current_date", 1) ;
				isList = true;
				break;
				
			case "ls_date_desc":
				System.out.println("�׸��� �Էµ� ��������� �����մϴ�.") ;
				TodoUtil.listAll(l, "current_date", 0) ;
				isList = true;
				break;
				
			case "ls_cat":
				System.out.println("ī�װ����� ����մϴ�.") ;
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
				int completed_id = sc.nextInt();
				//TodoUtil.completeItem() ;
				System.out.println("�Ϸ� üũ �Ͽ����ϴ�.") ;
				break ;
				
			case "ls_comp" :
				System.out.println("�Ϸ�� �׸��� ����մϴ�.") ;
				TodoUtil.listAll(l, 1);
				break ;
			default:
				System.out.println("�߸� �Է��ϼ̽��ϴ�! ���� ���ɾ� ������ �ʿ��Ͻø� help�� �Է����ּ���");
				break;
			}
			
			if(isList) TodoUtil.listAll(l);
		} while (!quit);
		sc.close() ;
	}
}