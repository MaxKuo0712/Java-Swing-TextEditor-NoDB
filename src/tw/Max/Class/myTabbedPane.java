package tw.Max.Class;

import java.awt.*;
import java.awt.event.*;
import java.util.*;
import javax.swing.*;

public class myTabbedPane extends JTabbedPane {
	private int textAreaCount = 0;
	private myTextArea textArea;
	private HashMap<String, String> sheetNameMap;
	private LinkedList<JTextArea> sheetList;
	
	public myTabbedPane() {
		MyListener myListener = new MyListener();
		sheetNameMap = new HashMap<>();
		sheetList = new LinkedList<>();
		
		// 視窗頁籤
		setComponentOrientation(ComponentOrientation.LEFT_TO_RIGHT);
		setTabLayoutPolicy(JTabbedPane.SCROLL_TAB_LAYOUT);
		addMouseListener(myListener);
	}
	
	// 內部類別 方便存取 MouseAdapter是連接器 可以免除匿名類別要產出一堆不必要的Listener
	private class MyListener extends MouseAdapter {
		@Override
		public void mouseClicked(MouseEvent e) {
			super.mouseClicked(e);
			if (e.getClickCount() == 2) {
				addSheet();
			}
		}
	}
	
	public void delSheet() {
		if (getTabCount() > 0) {
			remove(getSelectedIndex());
		}
	}
	
	public void addSheet() {
		String sheetName = setSheetName();
		if (!sheetName.equals("")) {
			textArea = new myTextArea();
			textArea.setName(sheetName);
			sheetList.add(textArea); // LinkedList 取得text用
			sheetNameMap.put(sheetName, textArea.getText()); // HashMap 確認有沒有重複檔案名稱用
			addTab(sheetName, new JScrollPane(textArea)); // 新增頁籤
		}
	}
	
	private String setSheetName() {
		String sheetName = JOptionPane.showInputDialog("請輸入檔案名稱：");
		if (checkNewName(sheetName)) {
			if (sheetName.equals("")) {
				return "untitled";
			} else {
				return sheetName;
			}
		} else {
			JOptionPane.showMessageDialog(null, "檔案名稱重複！");
			return "";
		}
	}
	
	private boolean checkNewName(String sheetName) {
		if (sheetNameMap.get(sheetName) == null) {
			return true;
		} else {
			return false;
		}
	}
	
	public String getTextArea() {
		return sheetList.get(getSelectedIndex()).getText();
	}
	
	public String getTextAreaName() {
		return sheetList.get(getSelectedIndex()).getName();
	}
	
	public int getTabSize() {
		return sheetList.size();
	}
}
