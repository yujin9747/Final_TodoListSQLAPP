package com.todo.dao; 
 
import java.text.SimpleDateFormat;
import java.util.Date;
public class TodoItem {
	private int id ;
    private String title;
    private String desc;
    private String current_date;
    private String category ;
    private String due_date ;
    private int comp ;
    private int importance ;
    private String completion_rate ;
 
    public int getComp() {
		return comp;
	}

	public void setComp(int comp) {
		this.comp = comp;
	}

	public TodoItem(String title, String desc, String category, String due_date, int importance, String completion_rate){
        this.title=title;
        this.desc=desc;
        SimpleDateFormat f = new SimpleDateFormat("yyyy/MM/dd kk:mm:ss") ;
        this.current_date=f.format(new Date()) ;
        this.category = category ;
        this.due_date = due_date ;
        this.importance = importance ;
        this.completion_rate = completion_rate ;
    }
    
    public int getImportance() {
		return importance;
	}

	public void setImportance(int importance) {
		this.importance = importance;
	}

	public String getCompletion_rate() {
		return completion_rate;
	}

	public void setCompletion_rate(String completion_rate) {
		this.completion_rate = completion_rate;
	}

	public void setId(int id) {
    	this.id = id ;
    }
    
    public int getId() {
    	return this.id ;
    }
    
    public String getCategory() {
		return category;
	}

	public void setCategory(String category) {
		this.category = category;
	}

	public String getDue_date() {
		return due_date;
	}

	public void setDue_date(String due_date) {
		this.due_date = due_date;
	}

	public String toString() {
		if(comp == 1) return id + ". [" + category + "] " + title + "[v]" + " - " + desc + " - " + due_date + " - " + current_date + "\n";
    	return id + ". [" + category + "] " + title + " - " + desc + " - " + due_date + " - " + current_date + "\n" ;
    }


	public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getDesc() {
        return desc;
    }

    public void setDesc(String desc) {
        this.desc = desc;
    }

    public String getCurrent_date() {
        return current_date;
    }

    public void setCurrent_date(String current_date) {
        this.current_date = current_date;
    }

	public String toStringSimple(int importance) {
		if(importance == 1) return id + ". [" + category + "] " + title + " - 중요도 : * - 완료율 : " + completion_rate + "\n" ;
		else if(importance == 2) return id + ". [" + category + "] " + title + " - 중요도 : ** - 완료율 : " + completion_rate + "\n" ;
		else if(importance == 3) return id + ". [" + category + "] " + title + " - 중요도 : *** - 완료율 : " + completion_rate + "\n" ;
		else if(importance == 4) return id + ". [" + category + "] " + title + " - 중요도 : **** - 완료율 : " + completion_rate + "\n" ;
		else return id + ". [" + category + "] " + title + " - 중요도 : ***** - 완료율 : " + completion_rate + "\n" ;
	}
}
