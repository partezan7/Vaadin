package com.example.app;

import com.vaadin.flow.component.Component;
import com.vaadin.flow.component.Unit;
import com.vaadin.flow.component.html.Label;
import com.vaadin.flow.component.orderedlayout.HorizontalLayout;
import com.vaadin.flow.component.orderedlayout.VerticalLayout;
import org.springframework.stereotype.Service;

@Service
public class Box extends HorizontalLayout {
    final float BORDER = 10.0f;
    private VerticalLayout left;
    private VerticalLayout right;
    private VerticalLayout top;
    private VerticalLayout bottom;
    private VerticalLayout middle;
    private VerticalLayout center;
    private HorizontalLayout container;


    Box() {
        init();
    }


    Box(Component component) {
        init();
        container.add(component);
    }

    public void put(Component boxComponent) {
        container.add(boxComponent);
    }

    public void putLeft(Component boxComponent) {
        container.addComponentAsFirst(boxComponent);
    }

    public void putRight(Component boxComponent) {
        container.add(boxComponent);
    }

    public void putUnder(Component boxComponent) {
        middle.add(boxComponent);
    }

    public void pitAbove(Component boxComponent) {
        middle.addComponentAsFirst(boxComponent);
    }

    public Component get(int index) {
        return middle.getComponentAt(index);
    }

    public void clear() {
        container.removeAll();
    }

    private void init() {
        setSizeFull();
        settMarginSpacingPadding(this, false, false, false);
        left = new VerticalLayout();
        setLabel(left, "\u2190");
        left.setWidth(BORDER, Unit.PIXELS);
        left.setHeightFull();
        right = new VerticalLayout();
        setLabel(right, "\u2192");
        right.setWidth(BORDER, Unit.PIXELS);
        right.setHeightFull();
        top = new VerticalLayout();
        setLabel(top, "\u2191");
        top.setHeight(BORDER, Unit.PIXELS);
        top.setWidthFull();
        bottom = new VerticalLayout();
        setLabel(bottom, "\u2193");
        bottom.setHeight(BORDER, Unit.PIXELS);
        bottom.setWidthFull();
        container = new HorizontalLayout();
        container.setSizeFull();
        middle = new VerticalLayout(container);
        middle.setSizeFull();
        settMarginSpacingPadding(middle, false, false, false);
        center = new VerticalLayout(top, middle, bottom);
        center.setSizeFull();
        settMarginSpacingPadding(center, false, false, false);
        add(left, center, right);

    }

    private void setLabel(VerticalLayout layout, String label) {
        Label text = new Label(label);
        text.setHeight(BORDER, Unit.PIXELS);
        text.setWidth(BORDER, Unit.PIXELS);
        layout.add(text);
        layout.setAlignItems(Alignment.CENTER);
        layout.setJustifyContentMode(JustifyContentMode.CENTER);
        settMarginSpacingPadding(layout, false, false, false);
    }

    private void settMarginSpacingPadding (Component layout, boolean margin, boolean spacing, boolean padding){
        var targetLayout = layout instanceof VerticalLayout
                ? ((VerticalLayout) layout) : (HorizontalLayout) layout;
        targetLayout.setMargin(margin);
        targetLayout.setSpacing(spacing);
        targetLayout.setPadding(padding);
    }
}