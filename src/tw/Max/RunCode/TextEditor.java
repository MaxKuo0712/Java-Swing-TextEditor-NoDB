package tw.Max.RunCode;

import java.awt.BorderLayout;
import java.awt.ComponentOrientation;
import java.awt.FlowLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.File;
import java.io.FileOutputStream;

import javax.swing.*;
import javax.swing.filechooser.FileNameExtensionFilter;

import tw.Max.Class.myTabbedPane;
import tw.Max.Class.myTextArea;

public class TextEditor extends JFrame {
	private JButton addSheet, save, newSave, read, delSheet;
	private JPanel topPanel;
	private myTabbedPane tabbedPane;
	private myTextArea textArea;
	private String saveType[] = {"txt","java"};
	
	public TextEditor() {
		// 定義視窗
		super("Text Editor");
		setLayout(new BorderLayout());
		
		// 放置上方工具列
		topPanel = new JPanel(new FlowLayout());
		add(topPanel, BorderLayout.NORTH);
		
		// 視窗頁籤
		tabbedPane = new myTabbedPane();
		add(tabbedPane, BorderLayout.CENTER);
		
		// 新增頁籤
		addSheet = new JButton("New");
		topPanel.add(addSheet);	
		
		// 刪除頁籤
		delSheet = new JButton("Delete");
		topPanel.add(delSheet);

		// 儲存
		save = new JButton("Save");
		topPanel.add(save);
		
		// 另存新檔
		newSave = new JButton("Save As");
		topPanel.add(newSave);
		
		// 開啟舊檔
		read = new JButton("Load");
		topPanel.add(read);
		
		// textarea
		textArea = new myTextArea();
		
		setSize(640, 480);
		setVisible(true);
		setDefaultCloseOperation(EXIT_ON_CLOSE);
		
		setListener();
	}
	
	private void setListener() {
		// 新增頁籤
		addSheet.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				addSheet();
			}
		});
		
		// 刪除頁籤
		delSheet.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				delSheet();
			}
		});
	
		// 另存新檔
		newSave.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				// 沒有頁籤的時候，不能執行儲存
				if(tabbedPane.getTabSize() > 0) {
					newSave();
				}
			}
		});
		
		// 存檔
	
	}
	
	private void addSheet() {
		tabbedPane.addSheet();
	}
	
	private void delSheet() {
		tabbedPane.delSheet();
	}
	
	private void newSave() {
//		String OS = System.getProperty("os.name").toLowerCase(); // 設定預設目錄用：可確認作業系統類別 mac, win, Linux ...
//		String userName = System.getProperty("user.name"); // 設定預設目錄用：可確認user name
		String outputName = tabbedPane.getTextAreaName(); // 取得頁籤名稱
		String outputText = tabbedPane.getTextArea(); // 取得頁籤內容
		byte[] outputByte = outputText.getBytes(); // 字串轉為byte
		
		//彈出檔案選擇框
		JFileChooser chooser = new JFileChooser();
		chooser.setDialogTitle("另存新檔");
//		chooser.setCurrentDirectory(new File("/Users/" + userName + "/Desktop/")); // 設定預設目錄為Desktop
		chooser.setSelectedFile(new File(outputName)); // 預設檔名是頁籤名稱
		int option = chooser.showSaveDialog(null);
		
		if(option == JFileChooser.APPROVE_OPTION){	//假如使用者選擇了儲存
			File file = chooser.getSelectedFile(); // 取得路徑
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
	
	public static void main(String[] args) {
		new TextEditor();
	}

}
