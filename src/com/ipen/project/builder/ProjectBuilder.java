package com.ipen.project.builder;

import java.awt.Image;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;

import javax.imageio.ImageIO;
import javax.swing.ImageIcon;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JMenu;
import javax.swing.JMenuBar;
import javax.swing.JMenuItem;

@SuppressWarnings("serial")
public class ProjectBuilder extends JFrame implements ActionListener {
	JFrame f;
	JMenuBar mb;
	JMenu file;
	JMenuItem singleProject;
	JMenuItem multiProject;

	public ProjectBuilder() {
		f = new JFrame();

		BufferedImage img = null;
		try {
			img = ImageIO.read(new File("resources//image.jpg"));
		} catch (IOException e) {
			e.printStackTrace();
		}
		Image dimg = img.getScaledInstance(550, 550, Image.SCALE_SMOOTH);
		ImageIcon imageIcon = new ImageIcon(dimg);
		f.setContentPane(new JLabel(imageIcon));

		mb = new JMenuBar();
		file = new JMenu("File");

		singleProject = new JMenuItem("Single Project Builder");
		singleProject.addActionListener(this);

		multiProject = new JMenuItem("Multi Project Builder");
		multiProject.addActionListener(this);
		file.add(singleProject);
		file.add(multiProject);

		mb.add(file);
		f.setJMenuBar(mb);
		f.setLayout(null);
		f.setSize(550, 450);
		f.setResizable(false);
		f.setVisible(true);
	}

	public static void main(String[] args) {
		new ProjectBuilder();
	}

	@Override
	public void actionPerformed(ActionEvent e) {
		if (e.getSource() == singleProject) {
			f.setDefaultCloseOperation(JFrame.HIDE_ON_CLOSE);
			f.setVisible(false);
			MultiProjectBuilder.getInstance().setVisible(false);
			SingleProjectBuilder.getInstance().setVisible(true);
		} else if (e.getSource() == multiProject) {
			f.setDefaultCloseOperation(JFrame.HIDE_ON_CLOSE);
			f.setVisible(false);
			SingleProjectBuilder.getInstance().setVisible(false);
			MultiProjectBuilder.getInstance().setVisible(true);
		}
	}

}
