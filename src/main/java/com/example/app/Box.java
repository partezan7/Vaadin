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
    private VerticalLayout center;
    private VerticalLayout right;

    Box() {
        init();
    }


    Box(Component component) {
        init();
        center.addComponentAtIndex(1, component);
    }
     public void put(Component boxComponent) {
         System.out.println(center.getComponentCount());
        if (center.getComponentCount() == 3){
            clear();

            center.addComponentAtIndex(1, boxComponent);


            for (int i = 0; i < center.getComponentCount(); i++) {
                System.out.println(center.getComponentAt(i));
            }
        }

    }
    public Component get (){
        if (center.getComponentCount() == 3){
            return center.getComponentAt(1);
        }
        return null;
    }

    public void clear(){
        center.remove(center.getComponentAt(1));
    }

    private void init() {
        setSizeFull();
        setMargin(false);
        setSpacing(false);
        setPadding(false);
        left = new VerticalLayout();
        setLabel(left, "\u2190");
        left.setWidth(BORDER, Unit.PIXELS);
        left.setHeightFull();
        right = new VerticalLayout();
        setLabel(right, "\u2192");
        right.setWidth(BORDER, Unit.PIXELS);
        right.setHeightFull();
        VerticalLayout top = new VerticalLayout();
        setLabel(top, "\u2191");
        top.setHeight(BORDER, Unit.PIXELS);
        top.setWidthFull();
        VerticalLayout bottom = new VerticalLayout();
        setLabel(bottom, "\u2193");
        bottom.setHeight(BORDER, Unit.PIXELS);
        bottom.setWidthFull();
        center = new VerticalLayout(top, bottom);
        center.setSizeFull();
        add(left, center, right);

    }

    private void setLabel(VerticalLayout layout, String label) {
        Label text = new Label(label);
        layout.add(text);
        layout.setAlignItems(Alignment.CENTER);
        layout.setJustifyContentMode(JustifyContentMode.CENTER);
            layout.setMargin(false);
            layout.setSpacing(false);
            layout.setPadding(false);
    }
}