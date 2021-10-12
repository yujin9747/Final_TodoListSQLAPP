package com.todo.dao;
  
import java.util.*;
import java.sql.Connection; 
import com.todo.service.DbConnection ;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.Statement; 

public class TodoList {
	private List<TodoItem> list;
	Connection conn ;
	public TodoList() {
		this.conn = DbConnection.getConnection() ;
	}
	
	public int addItem(TodoItem t) {
		int count = 0 ; //�� ���� �׸��� ������ �޾Ҵ���
        String sql = "insert into list (title, memo, category, current_date, due_date)" +
        			"values (?,?,?,?,?) ;" ;
        PreparedStatement pstmt ;
        try {
        	pstmt = conn.prepareStatement(sql) ; 
	        //����ǥ ä������
	        pstmt.setString(1, t.getTitle());
	        pstmt.setString(2, t.getDesc());
	        pstmt.setString(3, t.getCategory());
	        pstmt.setString(4, t.getCurrent_date());
	        pstmt.setString(5, t.getDue_date());
	        count = pstmt.executeUpdate() ;
	        pstmt.close();
        }catch(Exception e) {
        	e.printStackTrace();
        }
        
		return count ;
	}
	
	public int updateItem(TodoItem t) {
		int count = 0 ; //�� ���� �׸��� ������ �޾Ҵ���
        String sql = "update list set title = ?, memo = ? , category = ?, current_date = ? due_date = ? where = ?;" ;
        PreparedStatement pstmt ;
        try {
        	pstmt = conn.prepareStatement(sql) ; 
	        //����ǥ ä������
	        pstmt.setString(1, t.getTitle());
	        pstmt.setString(2, t.getDesc());
	        pstmt.setString(3, t.getCategory());
	        pstmt.setString(4, t.getCurrent_date());
	        pstmt.setString(5, t.getDue_date());
	        pstmt.setInt(6, t.getId());
	        count = pstmt.executeUpdate() ;
	        pstmt.close();
        }catch(Exception e) {
        	e.printStackTrace();
        }
        
		return count ;
	}
	
	public int deleteItem(int index) {
		int count = 0 ; //�� ���� �׸��� ������ �޾Ҵ���
        String sql = "delete from list where = ?;" ;
        PreparedStatement pstmt ;
        try {
        	pstmt = conn.prepareStatement(sql) ; 
	        pstmt.setInt(1, index);
	        count = pstmt.executeUpdate() ;
	        pstmt.close();
        }catch(Exception e) {
        	e.printStackTrace();
        }
        
		return count ;
	}
	
	public int getCount() {
		Statement stmt ;
		int count = 0 ;
		try {
			stmt = conn.createStatement();
			String sql = "SELECT count(id) from list ;" ;
			ResultSet rs = stmt.executeQuery(sql) ; //list�� �ִ� id�ʵ��� ������ �����ͼ� rs�� ����. rs���� �� �ٸ� �����
			rs.next(); //������ ����� �� ���� ������
			count = rs.getInt("count(id)") ; //�� �� �ٿ��� count(id) ��, id�� ������ �� int�� �ڷḦ ��ȯ�޾Ƽ� count������
			stmt.close();
		}catch(Exception e) {
			e.printStackTrace();
		}
		return count ;
	}
	
	public ArrayList<TodoItem> getList() { //list�� ��� �׸��� ��Ƽ� ������ ��� ��. 
		ArrayList<TodoItem> list = new ArrayList<TodoItem>();
		Statement stmt ;
		try {
			stmt = conn.createStatement() ;
			String sql = "SELECT * FROM list" ;
			ResultSet rs = stmt.executeQuery(sql) ;
			//resultSet�� ����� ���� �� �� �� �� �������鼭, ����Ʈȭ �ϱ�
			while(rs.next()) {
				int id = rs.getInt("id") ;
				String title = rs.getString("title") ;
				String desc = rs.getString("memo") ;
				String category = rs.getString("category") ;
				String current_date = rs.getString("current_date") ;
				String due_date = rs.getString("due_date") ;
				TodoItem t = new TodoItem(title, desc, category, due_date) ;
				t.setId(id) ; //���� �Ϸù�ȣ�� �ο����� �ʵ���. ������ �����Ϳ� �־��� �Ϸù�ȣ�� setting
				t.setCurrent_date(current_date) ; //���� �ð��� �ƴ϶�, �� �����Ͱ� db���Ͽ� �߰� �Ǿ��� ����� �ð��� setting
				list.add(t) ;
			}
			stmt.close();
		}catch(Exception e) {
			e.printStackTrace();
		}
		
		return list ;
	}
	
	public ArrayList<TodoItem> getOrderedList(String field, int version) {
		ArrayList<TodoItem> list = new ArrayList<TodoItem>();
		Statement stmt ;
		try {
			stmt = conn.createStatement() ;
			String sql = "SELECT * FROM list ORDER BY " + field + ";";
			if(version == 0) sql = "SELECT * FROM list ORDER BY " + field + " DESC ;" ;
			ResultSet rs = stmt.executeQuery(sql) ;
			//resultSet�� ����� ���� �� �� �� �� �������鼭, ����Ʈȭ �ϱ�
			while(rs.next()) {
				int id = rs.getInt("id") ;
				String title = rs.getString("title") ;
				String desc = rs.getString("memo") ;
				String category = rs.getString("category") ;
				String current_date = rs.getString("current_date") ;
				String due_date = rs.getString("due_date") ;
				TodoItem t = new TodoItem(title, desc, category, due_date) ;
				t.setId(id) ; //���� �Ϸù�ȣ�� �ο����� �ʵ���. ������ �����Ϳ� �־��� �Ϸù�ȣ�� setting
				t.setCurrent_date(current_date) ; //���� �ð��� �ƴ϶�, �� �����Ͱ� db���Ͽ� �߰� �Ǿ��� ����� �ð��� setting
				list.add(t) ;
			}
			stmt.close();
		}catch(Exception e) {
			e.printStackTrace();
		}
		
		return list ;
	}
	
	public ArrayList<TodoItem> getList(String keyword) { //list�� ��� �׸��� ��Ƽ� ������ ��� ��. 
		ArrayList<TodoItem> list = new ArrayList<TodoItem>();
		PreparedStatement ptmt ;
		keyword = "%" + keyword + "%" ;
		try {
			String sql = "SELECT * FROM list where title like ? OR memo like ?;" ;
			ptmt = conn.prepareStatement(sql) ;
			ptmt.setString(1, keyword);
			ptmt.setString(2, keyword);
			ResultSet rs = ptmt.executeQuery(sql) ;
			while(rs.next()) {
				int id = rs.getInt("id") ;
				String title = rs.getString("title") ;
				String desc = rs.getString("memo") ;
				String category = rs.getString("category") ;
				String current_date = rs.getString("current_date") ;
				String due_date = rs.getString("due_date") ;
				TodoItem t = new TodoItem(title, desc, category, due_date) ;
				t.setId(id) ; //���� �Ϸù�ȣ�� �ο����� �ʵ���. ������ �����Ϳ� �־��� �Ϸù�ȣ�� setting
				t.setCurrent_date(current_date) ; //���� �ð��� �ƴ϶�, �� �����Ͱ� db���Ͽ� �߰� �Ǿ��� ����� �ð��� setting
				list.add(t) ;
			}
			ptmt.close();
		}catch(Exception e) {
			e.printStackTrace();
		}
		
		return list ;
	}

	public ArrayList<TodoItem> getListCategory(String cat) { //list�� ��� �׸��� ��Ƽ� ������ ��� ��. 
		ArrayList<TodoItem> list = new ArrayList<TodoItem>();
		PreparedStatement ptmt ;
		cat = "%" + cat + "%" ;
		try {
			String sql = "SELECT * FROM list where category like ? ;" ;
			ptmt = conn.prepareStatement(sql) ;
			ptmt.setString(1, cat);
			ResultSet rs = ptmt.executeQuery(sql) ;
			while(rs.next()) {
				int id = rs.getInt("id") ;
				String title = rs.getString("title") ;
				String desc = rs.getString("memo") ;
				String category = rs.getString("category") ;
				String current_date = rs.getString("current_date") ;
				String due_date = rs.getString("due_date") ;
				TodoItem t = new TodoItem(title, desc, category, due_date) ;
				t.setId(id) ; //���� �Ϸù�ȣ�� �ο����� �ʵ���. ������ �����Ϳ� �־��� �Ϸù�ȣ�� setting
				t.setCurrent_date(current_date) ; //���� �ð��� �ƴ϶�, �� �����Ͱ� db���Ͽ� �߰� �Ǿ��� ����� �ð��� setting
				list.add(t) ;
			}
			ptmt.close();
		}catch(Exception e) {
			e.printStackTrace();
		}
		
		return list ;
	}
	
	
	public ArrayList<String> getCategories() { //list�� ��� �׸��� ��Ƽ� ������ ��� ��. 
		ArrayList<String> list = new ArrayList<String>();
		Statement stmt ;
		try {
			stmt = conn.createStatement() ;
			String sql = "SELECT DISTINCT category FROM list ;" ;
			ResultSet rs = stmt.executeQuery(sql) ;
			while(rs.next()) {
				String category = rs.getString("category") ;
				list.add(category) ;
			}
			stmt.close();
		}catch(Exception e) {
			e.printStackTrace();
		}
		
		return list ;
	}
	
	public int indexOf(TodoItem t) {
		return list.indexOf(t);
	}
	
	public boolean isDuplicate(String title) { 
		for (TodoItem item : list) {
			if (title.equals(item.getTitle())) return true;
		}
		return false ;
	}
	
	public ArrayList<TodoItem> getList(int comp) { //list�� �Ϸ�� ��� �׸��� ��� �����ؾ� ��
		ArrayList<TodoItem> list = new ArrayList<TodoItem>();
		Statement stmt ;
		try {
			stmt = conn.createStatement() ;
			String sql = "SELECT * FROM list where is_completed = 1" ;
			ResultSet rs = stmt.executeQuery(sql) ;
			//resultSet�� ����� ���� �� �� �� �� �������鼭, ����Ʈȭ �ϱ�
			while(rs.next()) {
				int id = rs.getInt("id") ;
				String title = rs.getString("title") ;
				String desc = rs.getString("memo") ;
				String category = rs.getString("category") ;
				String current_date = rs.getString("current_date") ;
				String due_date = rs.getString("due_date") ;
				TodoItem t = new TodoItem(title, desc, category, due_date) ;
				t.setId(id) ; //���� �Ϸù�ȣ�� �ο����� �ʵ���. ������ �����Ϳ� �־��� �Ϸù�ȣ�� setting
				t.setCurrent_date(current_date) ; //���� �ð��� �ƴ϶�, �� �����Ͱ� db���Ͽ� �߰� �Ǿ��� ����� �ð��� setting
				list.add(t) ;
			}
			stmt.close();
		}catch(Exception e) {
			e.printStackTrace();
		}
		
		return list ;
	}
	


}
