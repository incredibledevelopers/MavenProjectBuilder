package com.ipen.project.builder;

import java.awt.Component;
import java.awt.Container;
import java.awt.Dimension;
import java.awt.Insets;
import java.awt.LayoutManager;

public class SingleProjectBuilderLayout implements LayoutManager {

    public SingleProjectBuilderLayout() {
    }

    public void addLayoutComponent(String name, Component comp) {
    }

    public void removeLayoutComponent(Component comp) {
    }

    public Dimension preferredLayoutSize(Container parent) {
        Dimension dim = new Dimension(0, 0);

        Insets insets = parent.getInsets();
        dim.width = 1057 + insets.left + insets.right;
        dim.height = 538 + insets.top + insets.bottom;

        return dim;
    }

    public Dimension minimumLayoutSize(Container parent) {
        Dimension dim = new Dimension(0, 0);
        return dim;
    }

    public void layoutContainer(Container parent) {
        Insets insets = parent.getInsets();

        Component c;
        c = parent.getComponent(0);
        if (c.isVisible()) {c.setBounds(insets.left+40,insets.top+32,168,24);}
        c = parent.getComponent(1);
        if (c.isVisible()) {c.setBounds(insets.left+224,insets.top+32,256,24);}
        c = parent.getComponent(2);
        if (c.isVisible()) {c.setBounds(insets.left+40,insets.top+96,168,24);}
        c = parent.getComponent(3);
        if (c.isVisible()) {c.setBounds(insets.left+448,insets.top+96,32,24);}
        c = parent.getComponent(4);
        if (c.isVisible()) {c.setBounds(insets.left+40,insets.top+160,168,24);}
        c = parent.getComponent(5);
        if (c.isVisible()) {c.setBounds(insets.left+224,insets.top+160,256,24);}
        c = parent.getComponent(6);
        if (c.isVisible()) {c.setBounds(insets.left+224,insets.top+224,72,24);}
        c = parent.getComponent(7);
        if (c.isVisible()) {c.setBounds(insets.left+40,insets.top+128,168,24);}
        c = parent.getComponent(8);
        if (c.isVisible()) {c.setBounds(insets.left+224,insets.top+128,256,24);}
        c = parent.getComponent(9);
        if (c.isVisible()) {c.setBounds(insets.left+40,insets.top+64,168,24);}
        c = parent.getComponent(10);
        if (c.isVisible()) {c.setBounds(insets.left+224,insets.top+64,256,24);}
        c = parent.getComponent(11);
        if (c.isVisible()) {c.setBounds(insets.left+104,insets.top+224,72,24);}
        c = parent.getComponent(12);
        if (c.isVisible()) {c.setBounds(insets.left+48,insets.top+256,232,24);}
        c = parent.getComponent(13);
        if (c.isVisible()) {c.setBounds(insets.left+40,insets.top+296,440,40);}
        c = parent.getComponent(14);
        if (c.isVisible()) {c.setBounds(insets.left+224,insets.top+96,224,24);}
        c = parent.getComponent(15);
        if (c.isVisible()) {c.setBounds(insets.left+40,insets.top+192,168,24);}
        c = parent.getComponent(16);
        if (c.isVisible()) {c.setBounds(insets.left+224,insets.top+192,256,24);}
        c = parent.getComponent(17);
        if (c.isVisible()) {c.setBounds(insets.left+56,insets.top+304,200,24);}
        c = parent.getComponent(18);
        if (c.isVisible()) {c.setBounds(insets.left+40,insets.top+344,120,40);}
        c = parent.getComponent(19);
        if (c.isVisible()) {c.setBounds(insets.left+168,insets.top+344,112,40);}
        c = parent.getComponent(20);
        if (c.isVisible()) {c.setBounds(insets.left+288,insets.top+344,96,40);}
        c = parent.getComponent(21);
        if (c.isVisible()) {c.setBounds(insets.left+392,insets.top+344,112,40);}
    }
}