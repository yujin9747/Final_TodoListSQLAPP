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
			System.out.println(list.indexOf(t)+1 + ". [" + t.getCategory() + "] " 
		+ t.getTitle() + " - " + t.getDesc() + " - " + t.getDue_date() + " - " + t.getCurrent_date());
			System.out.println("**�׸� �߰� �Ϸ�**\n");
		}
	}

	public static void deleteItem(TodoList l) {
		
		Scanner sc = new Scanner(System.in);
		System.out.println("\ndel(�׸� ����)�� �����ϼ̽��ϴ�!");
		System.out.print("������ �׸� ��ȣ�� �Է����ּ��� > ");
		int number = sc.nextInt() ;

		int count = 1 ;
		for (TodoItem item : l.getList()) {
			if (count == number) {
				System.out.println(number + ". [" + item.getCategory() + "] " 
			+ item.getTitle() + " - " + item.getDesc() + " - " + item.getDue_date() + " - " + item.getCurrent_date());
				break ;
			}
			count++ ;
		}
		System.out.print("�� �׸��� �����Ͻðڽ��ϱ�? (y/n) > ") ;
		String yesOrno = sc.next() ; 
		if(yesOrno.equals("y")) {
			if(l.deleteItem(number)>0)
				System.out.println("**�׸� ���� �Ϸ�**\n");
		}

	}


	public static void updateItem(TodoList l) {
		
		Scanner sc = new Scanner(System.in);
		System.out.println("\nedit(�׸� ����)�� �����ϼ̽��ϴ�!");
		System.out.print("������ �׸� ��ȣ�� �Է����ּ��� > ");
		int number = sc.nextInt();
		int count = 1 ;
		for (TodoItem item : l.getList()) {
			if (count == number) {
				System.out.println(number + ". [" + item.getCategory() + "] " + item.getTitle() 
				+ " - " + item.getDesc() + " - " + item.getDue_date() + " - " + item.getCurrent_date());
			}	
			count++ ;
		}
		
		System.out.print("���ο� �׸� ī�װ��� �Է����ּ��� > ");
		String new_category = sc.next().trim();
		sc.nextLine() ;
		System.out.print("���ο� �׸� �̸��� �Է����ּ��� > ");
		String new_title = sc.next().trim();
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
	
}