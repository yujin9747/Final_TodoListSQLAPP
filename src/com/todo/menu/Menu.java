package com.todo.menu;
public class Menu {   
    public static void displaymenu()
    {  
    	
        System.out.println();
        System.out.println("TodoList ���� ��ɾ� ����");
        System.out.println("add - �׸� �߰��ϱ�");
        System.out.println("del - �׸� �����ϱ�");
        System.out.println("edit - �׸� �����ϱ�");
        System.out.println("comp - �׸� �Ϸ� üũ�ϱ�");
        System.out.println("ls - ��ü ��� ����");
        System.out.println("ls_name_asc - ����� �̸������� �����ϱ�(��������)");
        System.out.println("ls_name_desc - ����� �̸������� �����ϱ�(��������)");
        System.out.println("ls_date - �������� ������� �����ϱ�");
        System.out.println("ls_date_desc - �������� �������� �����ϱ�");
        System.out.println("ls_cat - ī�װ� �����ϱ�");
        System.out.println("ls_comp - �Ϸ�� �׸� �����ϱ�");
        System.out.println("find (Ű���� �Է�) - Ű����� �˻��ϱ�");
        System.out.println("find_cat (Ű���� �Է�) - Ű����� ī�װ� �˻��ϱ�");
        System.out.println("exit - ����");
    }
    public static void prompt() {
    	System.out.print("���Ͻô� �޴��� �Է����ּ��� > (���� ���� : help)") ;
    }
}
