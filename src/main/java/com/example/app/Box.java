package com.example.app;

import com.vaadin.flow.component.Component;
import com.vaadin.flow.component.Unit;
import com.vaadin.flow.component.html.Label;
import com.vaadin.flow.component.orderedlayout.HorizontalLayout;
import com.vaadin.flow.component.orderedlayout.VerticalLayout;
import org.springframework.stereotype.Service;

@Service
public class Box extends HorizontalLayout {
    final float BORDER = 50.0f;
    private VerticalLayout left;
    private VerticalLayout center;
    private VerticalLayout right;

    Box() {
        init();
    }


    Box(Component component) {
        init();
        center.addComponentAtIndex(1, component);
    }

    private void init() {
        setSizeFull();
        setMargin(false);
        setSpacing(false);
        setPadding(false);
//            setMargin(true);
//            setSpacing(true);
//            setPadding(true);
        left = new VerticalLayout();
        setBoard(left, "\u2190");
        left.setWidth(BORDER, Unit.PIXELS);
        left.setHeightFull();
        right = new VerticalLayout();
        setBoard(right, "\u2192");
        right.setWidth(BORDER, Unit.PIXELS);
        right.setHeightFull();
        VerticalLayout top = new VerticalLayout();
        setBoard(top, "\u2191");
        top.setHeight(BORDER, Unit.PIXELS);
        top.setWidthFull();
        VerticalLayout bottom = new VerticalLayout();
        setBoard(bottom, "\u2193");
        bottom.setHeight(BORDER, Unit.PIXELS);
        bottom.setWidthFull();
        center = new VerticalLayout(top, bottom);
        center.setSizeFull();
        add(left, center, right);

    }

    private void setBoard(VerticalLayout layout, String label) {
        Label text = new Label(label);
        layout.add(text);
        layout.setAlignItems(Alignment.CENTER);
        layout.setJustifyContentMode(JustifyContentMode.CENTER);
//            left.setMargin(false);
//            left.setSpacing(false);
//            left.setPadding(false);
//            setMargin(true);
//            setSpacing(true);
//            setPadding(true);
    }
}