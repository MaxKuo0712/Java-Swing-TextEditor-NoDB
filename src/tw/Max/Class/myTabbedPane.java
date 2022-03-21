package tw.Max.Class;

import java.awt.*;
import java.awt.event.*;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.util.*;
import javax.swing.*;

import org.w3c.dom.Text;

public class myTabbedPane extends JTabbedPane {
	private int textAreaCount = 0;
	private myTextArea textArea;
	private HashMap<String, String> tabNameMap;
	private LinkedList<JTextPane> tabList;
	
	public myTabbedPane() {
		MyListener myListener = new MyListener();
		tabNameMap = new HashMap<>();
		tabList = new LinkedList<>();
		
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
				addNewTabs();
			}
		}
	}
	
	public void delSheet() {
		if (getTabCount() > 0 && isDeleteSheet() == true) {
			tabNameMap.remove(getTextAreaName());
			tabList.remove(getSelectedIndex());
			remove(getSelectedIndex());
		}
	}
	
	private boolean isDeleteSheet() {
		int isAgain = JOptionPane.showConfirmDialog(null, "確定要刪除該頁籤？", "刪除頁籤", JOptionPane.YES_NO_OPTION);
		System.out.println(isAgain);
		if (isAgain == 0) {
			return true;
		} else {
			return false;
		}
	}
	
	public void addNewTabs() {
		String sheetName = JOptionPane.showInputDialog("請輸入檔案名稱：");
		sheetName = setSheetName(sheetName);
		if (!(sheetName == null)) {
			textArea = new myTextArea();
			textArea.setName(sheetName);
			tabList.add(textArea); // LinkedList 取得text用
			tabNameMap.put(sheetName, ""); // HashMap 確認有沒有重複檔案名稱用
			addTab(sheetName, new JScrollPane(textArea)); // 新增頁籤
		}
	}
	
	
	public void addTabs(String sheetName, byte[] text, File fileRoute) {
		textArea = new myTextArea();
		textArea.setText(new String(text));
		textArea.setName(sheetName);
		tabList.add(textArea); // LinkedList 取得text用
		tabNameMap.put(sheetName, fileRoute.toString()); // HashMap 確認有沒有重複檔案名稱用
		addTab(sheetName, new JScrollPane(textArea)); // 新增頁籤
	}
	
	private String setSheetName(String sheetName) {
		int isNamExists = checkNewName(sheetName);
		
		if (isNamExists == 1) {
			if (sheetName.equals("")) {
				return "untitled";
			} else {
				return sheetName;
			}
		} else if (isNamExists == 2) {
			JOptionPane.showMessageDialog(null, "檔案名稱重複！");
			return null;
		} else if (isNamExists == 0) {
			return null;
		} else {
			return null;
		}
	}
	
	private int checkNewName(String sheetName) {
		if (sheetName == null) {
			return 0;
		} else if (tabNameMap.get(sheetName) == null) {
			return 1;
		} else {
			return 2;
		}
	}
	
	private String getTextAreaText() {
		return tabList.get(getSelectedIndex()).getText();
	}
	
	private String getTextAreaName() {
		return tabList.get(getSelectedIndex()).getName();
	}
	
	private JTextPane getTextArea() {
		return tabList.get(getSelectedIndex());
	}
	
	public int getTabSize() {
		return tabList.size();
	}
	
	private void setFileRoute(String outputName, String file) {
		tabNameMap.replace(outputName, file);
	}
	
	private String getFileRoute(String outputName) {
		return tabNameMap.get(outputName);
	}
	
	
	public void newSave() {
		String outputName = getTextAreaName(); // 取得頁籤名稱
		String outputText = getTextAreaText(); // 取得頁籤內容
		byte[] outputByte = outputText.getBytes(); // 字串轉為byte

		//彈出檔案選擇框
		JFileChooser chooser = new JFileChooser();
		chooser.setDialogTitle("另存新檔");
		chooser.setSelectedFile(new File(outputName)); // 預設檔名是頁籤名稱

		if (chooser.showSaveDialog(null) == JFileChooser.APPROVE_OPTION) {	//假如使用者選擇了儲存
			File file = chooser.getSelectedFile(); // 取得路徑
			setFileRoute(outputName, file.toString()); // 把路徑放入HashMap
			
			try {
				FileOutputStream fos = new FileOutputStream(file.toString().concat(".txt")); // 串流 - 設定存文字檔
				fos.write(outputByte); // 序列化 寫入
				fos.flush();
				fos.close();
				JOptionPane.showMessageDialog(null, "儲存成功");
			} catch (Exception e) {
				System.err.println(e.toString()); // 印出出錯訊息
				e.printStackTrace(); // 印出出錯位置
			}	
		}
	}
	
	public void saveTextArea() {
		String outputName = getTextAreaName(); // 取得頁籤名稱
		String outputText = getTextAreaText(); // 取得頁籤內容
		String fileRoute = getFileRoute(outputName); // 取得該頁籤的路徑
		byte[] outputByte = outputText.getBytes(); // 字串轉為byte
		
		// 如果沒有儲存過，就沒有路徑，那就去找newSave另存新檔
		if (fileRoute == "") {
			newSave();
		} else {
			try {
				FileOutputStream fos = new FileOutputStream(fileRoute.concat(".txt")); // 串流 - 設定存文字檔
				fos.write(outputByte); // 序列化 寫入
				fos.flush();
				fos.close();
				JOptionPane.showMessageDialog(null, "儲存成功");
			} catch (Exception e) {
				System.err.println(e.toString()); // 印出出錯訊息
				e.printStackTrace(); // 印出出錯位置
			}	
		}
	}

	public void load() {
		//彈出檔案選擇框
		JFileChooser chooser = new JFileChooser();
		chooser.setDialogTitle("開啟舊檔");

		if (chooser.showOpenDialog(null) == JFileChooser.APPROVE_OPTION) {	//假如使用者選擇了儲存
			try {
				File file = chooser.getSelectedFile(); // 取得路徑
				String fileName = chooser.getName(file);
				fileName = fileName.substring(0, fileName.lastIndexOf(".")); // 取得去除副檔名的名稱
				
				if (tabNameMap.get(fileName) == null) {
					FileInputStream fin = new FileInputStream(file); // 串流 - 設定存文字檔
					byte[] text = new byte[ (int) file.length()];
					fin.read(text); // 序列化 寫入
					fin.close();
					addTabs(fileName, text, file);
					JOptionPane.showMessageDialog(null, "讀取成功");
				} else {
					JOptionPane.showMessageDialog(null, "檔案已存在於頁籤");
				}
			} catch (Exception e) {
				System.err.println(e.toString()); // 印出出錯訊息
				e.printStackTrace(); // 印出出錯位置
			}	
		}
	}

	public void setTextAreaFont(String item) {
		JTextPane TextArea = getTextArea();
		TextArea.setFont(new Font(item, TextArea.getFont().getStyle(), TextArea.getFont().getSize()));
	}
	
	public void setTextAreaFontSize(String item) {
		JTextPane TextArea = getTextArea();
		TextArea.setFont(new Font(TextArea.getFont().getFontName(), TextArea.getFont().getStyle(), Integer.parseInt(item)));
	}
	
	public void setTextAreaFontColor(String item) {
		JTextPane TextArea = getTextArea();
		if (item == "紅") {
			TextArea.setForeground(Color.red);
		} else if (item == "藍") {
			TextArea.setForeground(Color.blue);
		} else if (item == "黑") {
			TextArea.setForeground(Color.black);
		}
	}
	
}
