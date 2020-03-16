package com.ipen.project.builder;

import java.awt.Component;
import java.awt.Container;
import java.awt.Dimension;
import java.awt.Insets;
import java.awt.LayoutManager;

public class MultiProjectLayout implements LayoutManager {

    public MultiProjectLayout() {
    }

    public void addLayoutComponent(String name, Component comp) {
    }

    public void removeLayoutComponent(Component comp) {
    }

    public Dimension preferredLayoutSize(Container parent) {
        Dimension dim = new Dimension(0, 0);

        Insets insets = parent.getInsets();
        dim.width = 529 + insets.left + insets.right;
        dim.height = 337 + insets.top + insets.bottom;

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
        if (c.isVisible()) {c.setBounds(insets.left+48,insets.top+32,152,24);}
        c = parent.getComponent(1);
        if (c.isVisible()) {c.setBounds(insets.left+208,insets.top+32,160,24);}
        c = parent.getComponent(2);
        if (c.isVisible()) {c.setBounds(insets.left+376,insets.top+32,32,24);}
        c = parent.getComponent(3);
        if (c.isVisible()) {c.setBounds(insets.left+48,insets.top+80,120,32);}
        c = parent.getComponent(4);
        if (c.isVisible()) {c.setBounds(insets.left+176,insets.top+80,112,32);}
        c = parent.getComponent(5);
        if (c.isVisible()) {c.setBounds(insets.left+296,insets.top+80,112,32);}
    }
}