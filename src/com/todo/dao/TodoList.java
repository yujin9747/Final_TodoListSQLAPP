package com.todo.dao;
import java.util.*;
import java.sql.Connection; 
import com.todo.service.DbConnection ;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement; 

public class TodoList {
	private List<TodoItem> list;
	Connection conn ;
	public TodoList() {
		this.conn = DbConnection.getConnection() ;
	}
	
	public int addItem(TodoItem t) {
		int count = 0 ; //몇 개의 항목이 영향을 받았는지
        String sql = "insert into list (title, memo, category, current_date, due_date, is_completed, importance, completion_rate)" +
        			"values (?,?,?,?,?,?,?,?) ;" ;
        PreparedStatement pstmt ;
        try {
        	pstmt = conn.prepareStatement(sql) ; 
	        //물음표 채워나감
	        pstmt.setString(1, t.getTitle());
	        pstmt.setString(2, t.getDesc());
	        pstmt.setString(3, t.getCategory());
	        pstmt.setString(4, t.getCurrent_date());
	        pstmt.setString(5, t.getDue_date());
	        pstmt.setInt(6,0);
	        pstmt.setInt(7,t.getImportance());
	        pstmt.setString(8, t.getCompletion_rate());
	        count = pstmt.executeUpdate() ;
	        pstmt.close();
        }catch(Exception e) {
        	e.printStackTrace();
        }
        
		return count ;
	}
	
	public int updateItem(TodoItem t) {
		int count = 0 ; //몇 개의 항목이 영향을 받았는지
        String sql = "update list set title = ?, memo = ? , category = ?, current_date = ?, due_date = ?, is_completed = ?, importance = ?, completion_rate = ? where id = ?;" ;
        PreparedStatement pstmt ;
        try {
        	pstmt = conn.prepareStatement(sql) ; 
	        //물음표 채워나감
	        pstmt.setString(1, t.getTitle());
	        pstmt.setString(2, t.getDesc());
	        pstmt.setString(3, t.getCategory());
	        pstmt.setString(4, t.getCurrent_date());
	        pstmt.setString(5, t.getDue_date());
	        pstmt.setInt(6, t.getComp());
	        pstmt.setInt(7, t.getImportance());
	        pstmt.setString(8, t.getCompletion_rate());
	        pstmt.setInt(9, t.getId());
	        
	        count = pstmt.executeUpdate() ;
	        pstmt.close();
        }catch(Exception e) {
        	e.printStackTrace();
        }
        
		return count ;
	}
	
	public int deleteItem(int index) {
		int count = 0 ; //몇 개의 항목이 영향을 받았는지
        String sql = "delete from list where id =?;" ;
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
			ResultSet rs = stmt.executeQuery(sql) ; //list에 있는 id필드의 갯수를 가져와서 rs에 저장. rs에는 한 줄만 저장됨
			rs.next(); //갯수가 저장된 한 줄을 가져옴
			count = rs.getInt("count(id)") ; //그 한 줄에서 count(id) 즉, id의 갯수를 센 int형 자료를 반환받아서 count에저장
			stmt.close();
		}catch(Exception e) {
			e.printStackTrace();
		}
		return count ;
	}
	
	public ArrayList<TodoItem> getList() { //list에 모든 항목을 담아서 리턴해 줘야 함. 
		ArrayList<TodoItem> list = new ArrayList<TodoItem>();
		Statement stmt ;
		try {
			stmt = conn.createStatement() ;
			String sql = "SELECT * FROM list" ;
			ResultSet rs = stmt.executeQuery(sql) ;
			//resultSet에 저장된 것을 한 줄 한 줄 가져오면서, 리스트화 하기
			while(rs.next()) {
				int id = rs.getInt("id") ;
				String title = rs.getString("title") ;
				String desc = rs.getString("memo") ;
				String category = rs.getString("category") ;
				String current_date = rs.getString("current_date") ;
				String due_date = rs.getString("due_date") ;
				int comp = rs.getInt("is_completed") ;
				int importance = rs.getInt("importance") ;
				String completion_rate = rs.getString("completion_rate") ;
				TodoItem t = new TodoItem(title, desc, category, due_date, importance, completion_rate) ;
				t.setId(id) ; //새로 일련번호를 부여하지 않도록. 기존의 데이터에 주어진 일련번호를 setting
				t.setCurrent_date(current_date) ; //현재 시간이 아니라, 그 데이터가 db파일에 추가 되었을 당시의 시간을 setting
				t.setComp(comp);
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
			//resultSet에 저장된 것을 한 줄 한 줄 가져오면서, 리스트화 하기
			while(rs.next()) {
				int id = rs.getInt("id") ;
				String title = rs.getString("title") ;
				String desc = rs.getString("memo") ;
				String category = rs.getString("category") ;
				String current_date = rs.getString("current_date") ;
				String due_date = rs.getString("due_date") ;
				int importance = rs.getInt("importance") ;
				String completion_rate = rs.getString("completion_rate") ;
				TodoItem t = new TodoItem(title, desc, category, due_date, importance, completion_rate) ;
				t.setId(id) ; //새로 일련번호를 부여하지 않도록. 기존의 데이터에 주어진 일련번호를 setting
				t.setCurrent_date(current_date) ; //현재 시간이 아니라, 그 데이터가 db파일에 추가 되었을 당시의 시간을 setting
				list.add(t) ;
			}
			stmt.close();
		}catch(Exception e) {
			e.printStackTrace();
		}
		
		return list ;
	}
	
	public ArrayList<TodoItem> getList(String keyword) { //list에 모든 항목을 담아서 리턴해 줘야 함. 
		ArrayList<TodoItem> list = new ArrayList<TodoItem>();
		PreparedStatement ptmt ;
		keyword = "%" + keyword + "%" ;
		try {
			String sql = "SELECT * FROM list where title like ? OR memo like ?;" ;
			ptmt = conn.prepareStatement(sql) ;
			ptmt.setString(1, keyword);
			ptmt.setString(2, keyword);
			ResultSet rs = ptmt.executeQuery() ;
			while(rs.next()) {
				int id = rs.getInt("id") ;
				String title = rs.getString("title") ;
				String desc = rs.getString("memo") ;
				String category = rs.getString("category") ;
				String current_date = rs.getString("current_date") ;
				String due_date = rs.getString("due_date") ;
				int importance = rs.getInt("importance") ;
				String completion_rate = rs.getString("completion_rate") ;
				TodoItem t = new TodoItem(title, desc, category, due_date, importance, completion_rate) ;
				t.setId(id) ; //새로 일련번호를 부여하지 않도록. 기존의 데이터에 주어진 일련번호를 setting
				t.setCurrent_date(current_date) ; //현재 시간이 아니라, 그 데이터가 db파일에 추가 되었을 당시의 시간을 setting
				list.add(t) ;
			}
			ptmt.close();
		}catch(Exception e) {
			e.printStackTrace();
		}
		
		return list ;
	}

	public ArrayList<TodoItem> getListCategory(String cat) { //list에 모든 항목을 담아서 리턴해 줘야 함. 
		ArrayList<TodoItem> list = new ArrayList<TodoItem>();
		PreparedStatement ptmt ;
		cat = "%" + cat + "%" ;
		try {
			String sql = "SELECT * FROM list where category like ? ;" ;
			ptmt = conn.prepareStatement(sql) ;
			ptmt.setString(1, cat);
			ResultSet rs = ptmt.executeQuery() ;
			while(rs.next()) {
				int id = rs.getInt("id") ;
				String title = rs.getString("title") ;
				String desc = rs.getString("memo") ;
				String category = rs.getString("category") ;
				String current_date = rs.getString("current_date") ;
				String due_date = rs.getString("due_date") ;
				int importance = rs.getInt("importance") ;
				String completion_rate = rs.getString("completion_rate") ;
				TodoItem t = new TodoItem(title, desc, category, due_date, importance, completion_rate) ;
				t.setId(id) ; //새로 일련번호를 부여하지 않도록. 기존의 데이터에 주어진 일련번호를 setting
				t.setCurrent_date(current_date) ; //현재 시간이 아니라, 그 데이터가 db파일에 추가 되었을 당시의 시간을 setting
				list.add(t) ;
			}
			ptmt.close();
		}catch(Exception e) {
			e.printStackTrace();
		}
		
		
		return list ;
	}
	

	public ArrayList<String> getCategories() { //list에 모든 항목을 담아서 리턴해 줘야 함. 
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
	
	public boolean isDuplicate(String title) { 
		for (TodoItem item : getList()) {
			if (title.equals(item.getTitle())) return true;
		}
		return false ;
	}
	
	public ArrayList<TodoItem> getList(int comp) { //list에 완료된 모든 항목을 담아 리턴해야 함
		ArrayList<TodoItem> list = new ArrayList<TodoItem>();
		Statement stmt ;
		try {
			stmt = conn.createStatement() ;
			String sql = "SELECT * FROM list where is_completed = 1" ;
			ResultSet rs = stmt.executeQuery(sql) ;
			//resultSet에 저장된 것을 한 줄 한 줄 가져오면서, 리스트화 하기
			while(rs.next()) {
				int id = rs.getInt("id") ;
				String title = rs.getString("title") ;
				String desc = rs.getString("memo") ;
				String category = rs.getString("category") ;
				String current_date = rs.getString("current_date") ;
				String due_date = rs.getString("due_date") ;
				int importance = rs.getInt("importance") ;
				String completion_rate = rs.getString("completion_rate") ;
				TodoItem t = new TodoItem(title, desc, category, due_date, importance, completion_rate) ;
				t.setId(id) ; //새로 일련번호를 부여하지 않도록. 기존의 데이터에 주어진 일련번호를 setting
				t.setCurrent_date(current_date) ; //현재 시간이 아니라, 그 데이터가 db파일에 추가 되었을 당시의 시간을 setting
				t.setComp(1);
				list.add(t) ;
			}
			stmt.close();
		}catch(Exception e) {
			e.printStackTrace();
		}
		
		return list ;
	}
	
	public int getCount_importance(int importance) { 
		int count=0 ;
		try {
			
			PreparedStatement pstmt ;
			String sql = "SELECT * from list where importance = ? ;" ;
			pstmt = conn.prepareStatement(sql) ;
			pstmt.setInt(1, importance);
			ResultSet rs = pstmt.executeQuery() ;
        
			while(rs.next()) count++ ;
		} catch (SQLException e) {
			e.printStackTrace();
		} 
		return count ;
	}

	public void findFirst(int i) {
		int min = 30000000 ; 
		int min_index = 0 ;
		int sum ;
		for(TodoItem item : getList_importance(i)) {
			String due_date = item.getDue_date() ;
			String []token = due_date.split("/") ;
			int []tokens = {Integer.parseInt(token[0]), Integer.parseInt(token[1]), Integer.parseInt(token[2])} ;
			sum = tokens[0]*10000 + tokens[1]*100 + tokens[2] ;
			if(sum < min) {
				min = sum ;
				min_index = item.getId();
			}
		}
		
		for(TodoItem item : getList()) {
			if(item.getId() == min_index) {
				System.out.println("중요도가 높으면서 마감기한이 가까운 우선순위 1위 항목입니다.") ;
				System.out.println(item.toString()) ;
			}
		}
		
		
	}

	private ArrayList<TodoItem> getList_importance(int i) {
		ArrayList<TodoItem> list = new ArrayList<TodoItem>();
		PreparedStatement pstmt ;
		String sql = "SELECT * from list where importance = ? ;" ;
		try {
			pstmt = conn.prepareStatement(sql) ;
			pstmt.setInt(1, i);
			ResultSet rs = pstmt.executeQuery() ;
			while(rs.next()) {
				int id = rs.getInt("id") ;
				String title = rs.getString("title") ;
				String desc = rs.getString("memo") ;
				String category = rs.getString("category") ;
				String current_date = rs.getString("current_date") ;
				String due_date = rs.getString("due_date") ;
				int comp = rs.getInt("is_completed") ;
				int importance = rs.getInt("importance") ;
				String completion_rate = rs.getString("completion_rate") ;
				TodoItem t = new TodoItem(title, desc, category, due_date, importance, completion_rate) ;
				t.setId(id) ; //새로 일련번호를 부여하지 않도록. 기존의 데이터에 주어진 일련번호를 setting
				t.setCurrent_date(current_date) ; //현재 시간이 아니라, 그 데이터가 db파일에 추가 되었을 당시의 시간을 setting
				t.setComp(comp);
				list.add(t) ;
			}
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		return list;
	}


}
