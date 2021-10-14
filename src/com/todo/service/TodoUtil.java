package com.todo.service;
 
import java.util.*;
import java.io.* ;
import java.sql.Statement;

import com.todo.dao.TodoItem;
import com.todo.dao.TodoList;
 
public class TodoUtil {
	public static void createItem(TodoList list) { 
		
		String title, desc, category, due_date;
		Scanner sc = new Scanner(System.in);
		  
		System.out.println("\nadd(�׸� �߰�)�� �����ϼ̽��ϴ�!");
		System.out.print("�׸� ī�װ��� �Է����ּ��� > ");
		category = sc.next();
		System.out.print("�׸� �̸��� �Է����ּ��� > ");
		title = sc.next();
		if(list.isDuplicate(title)) {
			System.out.println("������ �ߺ� �˴ϴ�.") ;
			return ;
		}
		sc.nextLine();
		System.out.print("������ �Է����ּ��� > ");
		desc = sc.nextLine();
		
		System.out.print("���������� �Է����ּ��� > ");
		due_date = sc.nextLine();
		TodoItem t = new TodoItem(title, desc, category, due_date);
		if(list.addItem(t) > 0 ) {
			System.out.println("**�׸� �߰� �Ϸ�**\n");
		}
	}

	public static void deleteItem(TodoList l) {
		int number = 0 ;
		int being = 0 ;
		Scanner sc = new Scanner(System.in);
		System.out.println("\ndel(�׸� ����)�� �����ϼ̽��ϴ�!");
		while(being == 0) {
			System.out.print("������ �׸� ��ȣ�� �Է����ּ��� > ");
			number = sc.nextInt() ;
			for (TodoItem item : l.getList()) {
				if (item.getId() == number) {
					System.out.println(item.toString()) ;
					being = 1 ;
					break ;
				}
			}
			if(being == 0) System.out.println(number + "��ȣ�� ��ġ�ϴ� �׸��� �������� �ʽ��ϴ�.") ;
		}
		System.out.print("�� �׸��� �����Ͻðڽ��ϱ�? (y/n) > ") ;
		String yesOrno = sc.next() ; 
		if(yesOrno.equals("y")) {
			if(l.deleteItem(number)>0)
				System.out.println("**�׸� ���� �Ϸ�**\n");
		}
	}


	public static void updateItem(TodoList l) {
		
		int number =0;
		Scanner sc = new Scanner(System.in);
		System.out.println("\nedit(�׸� ����)�� �����ϼ̽��ϴ�!");
		int being = 0 ;//�ش� ��ȣ�� �������� �����ϴ��� ���� ( 0 : ���� �� ��. 1 : ������ )		
		while(being == 0) {
			System.out.print("������ �׸� ��ȣ�� �Է����ּ��� > ");
			number = sc.nextInt();
			for (TodoItem item : l.getList()) {
				if (item.getId() == number) {
					System.out.println(item.toString()) ;
					being = 1 ;
					break ;
				}	
			}
			if(being == 0) System.out.println(number + "��ȣ�� ��ġ�ϴ� �׸��� �������� �ʽ��ϴ�.") ;
		}
		System.out.print("���ο� �׸� ī�װ��� �Է����ּ��� > ");
		String new_category = sc.next().trim();
		sc.nextLine() ;
		System.out.print("���ο� �׸� �̸��� �Է����ּ��� > ");
		String new_title = sc.next().trim();
		if(l.isDuplicate(new_title)) {
			System.out.println("������ �ߺ� �˴ϴ�.") ;
			return ;
		}
		sc.nextLine() ;
		System.out.print("�� �׸��� ������ �Է����ּ��� > ");
		String new_description = sc.nextLine().trim();
		System.out.print("�� �׸��� ���������� �Է����ּ��� > ");
		String new_due_date = sc.nextLine().trim();
		
		TodoItem t = new TodoItem(new_title, new_description, new_category, new_due_date);
		t.setId(number) ;
		if(l.updateItem(t) > 0) 
			System.out.println("**�����Ǿ����ϴ�**\n");
	}

	

	public static void listAll(TodoList l) { //���� �Ϸ�
		System.out.println("\n\n<< ��ü ��� , �� " + l.getCount()+"�� >>");
		for (TodoItem item : l.getList()) {
			System.out.print(item.toString());
		}
	}
	
	public static void listAll(TodoList l, int comp) {
		int count = 0 ;
		for (TodoItem item : l.getList(comp)) {
			System.out.print(item.toString());
			count++ ;
		}
		System.out.printf("�� %d���� �׸��� �Ϸ� �Ǿ����ϴ�.\n", count) ;
	}
	
	public static void listAll(TodoList l, String field, int version) { //���� �Ϸ�
		//����Ʈ �����ϴ� �Լ� ȣ�� �ؼ� ���ĵ� ����Ʈ �ޱ�
		//���ĵ� ����Ʈ �ϳ��ϳ� ����ϱ�
		System.out.printf("��ü ��� %d�� \n", l.getCount()) ;
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
		System.out.printf("�� %d���� �׸��� ã�ҽ��ϴ�.\n\n", count) ;
	}

	public static void find_cat(TodoList l, String cat) {
		int count = 0 ; //Ű����� ã���� �׸��� ���� ī����
		for (TodoItem item : l.getListCategory(cat)) {
			System.out.print(item.toString());
			count++ ;
		}
		System.out.printf("�� %d���� �׸��� ã�ҽ��ϴ�.\n\n", count) ;
		
	}
	
	public static void listCateAll(TodoList l) {
		int count = 0 ; //Ű����� ã���� �׸��� ���� ī����
		for (String item : l.getCategories()) {
			System.out.print(item + " ") ;
			count++ ;
		}
		System.out.printf("\n�� %d���� ī�װ��� ��ϵǾ� �ֽ��ϴ�.\n\n", count) ;
	}

	public static void completeItem(TodoList l) {
		System.out.print("�׸� �ϷḦ �����ϼ̽��ϴ�! �Ϸ��� �׸� ��ȣ�� �Է����ּ��� >> ") ;
		String title = null, desc = null, category = null, due_date = null, current_date = null ;
		int id =0;
		Scanner sc = new Scanner(System.in) ;
		int number = sc.nextInt();
		int being = 0 ; //�ش� ��ȣ�� ���� ����
		while(being == 0) {
			for (TodoItem item : l.getList()) {
				if (item.getId() == number) {
					System.out.println(item.toString()) ;
					if(item.getComp() == 1) {
						System.out.println("�̹� �Ϸ�� �׸��Դϴ�.") ; 
						return ;
					}
					being = 1 ;
					id = item.getId() ;
					title = item.getTitle();
					desc = item.getDesc() ;
					category = item.getCategory() ;
					due_date = item.getDue_date() ;
					current_date = item.getCurrent_date() ;
					break ;
				}
			}
			if(being == 0) {
				System.out.println(number + "���� �׸��� �������� �ʽ��ϴ�.") ; 
				System.out.println("��ȣ�� �ٽ� �Է����ּ���. >> ") ;
				number = sc.nextInt() ;
			}
		}
		TodoItem t = new TodoItem(title, desc, category, due_date) ;
		t.setId(id);
		t.setCurrent_date(current_date);
		t.setComp(1);
		if(l.updateItem(t) > 0) 
			System.out.println(number + "�� �׸��� �Ϸ��߽��ϴ�.") ;
		
	}
	
}