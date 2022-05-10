package com.example.app;

import com.vaadin.flow.component.Component;
import com.vaadin.flow.component.Unit;
import com.vaadin.flow.component.button.Button;
import com.vaadin.flow.component.dnd.DropTarget;
import com.vaadin.flow.component.html.Label;
import com.vaadin.flow.component.orderedlayout.HorizontalLayout;
import com.vaadin.flow.component.orderedlayout.VerticalLayout;
import org.springframework.stereotype.Service;

@Service
public class Box extends HorizontalLayout {
    final float BORDER = 10.0f;
    private enum Insert {LEFT, RIGHT, TOP, BOTTOM}
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
        this.setSizeFull();
        settMarginSpacingPadding(this, false, false, false);
        left = makeLayout(Insert.LEFT);
        right = makeLayout(Insert.RIGHT);
        top = makeLayout(Insert.TOP);
        bottom = makeLayout(Insert.BOTTOM);
        container = new HorizontalLayout();
        container.setSizeFull();
        DropTarget<HorizontalLayout> dropContainer = DropTarget.create(container);
        dropContainer.addDropListener(event -> {
            Box newBox = new Box(MyView.createDragAndDropButton());
            container.add(newBox);
        });
        middle = new VerticalLayout(container);
        middle.setSizeFull();
        settMarginSpacingPadding(middle, false, false, false);
        center = new VerticalLayout(top, middle, bottom);
        center.setSizeFull();
        settMarginSpacingPadding(center, false, false, false);
        this.add(left, center, right);
    }

    private VerticalLayout makeLayout(Insert direction) {
        VerticalLayout layout = new VerticalLayout();
        DropTarget<VerticalLayout> dropTarget = DropTarget.create(layout);

        switch (direction) {
            case LEFT:
                layout.setWidth(BORDER, Unit.PIXELS);
                layout.setHeightFull();
                setLabel(layout, "\u2190");
                dropTarget.addDropListener(event -> {
//                    Box targetBox = (Box) event.getComponent().getParent().get();
//                    Button targetButton = (Button) targetBox.get(0);
//                    Button sourceButton = (Button) event.getDragSourceComponent().get();
//                    if (sourceButton.getText().equals(MyView.CONTENT_GENERATOR)) {
//                        MyView.changeButton(targetButton);
//                    } else swapButtons(targetButton, sourceButton);
                });
                return layout;
            case RIGHT:
                layout.setWidth(BORDER, Unit.PIXELS);
                layout.setHeightFull();
                setLabel(layout, "\u2192");
                return layout;
            case TOP:
                layout.setHeight(BORDER, Unit.PIXELS);
                layout.setWidthFull();
                setLabel(layout, "\u2191");
                return layout;
            case BOTTOM:
                layout.setHeight(BORDER, Unit.PIXELS);
                layout.setWidthFull();
                setLabel(layout, "\u2193");
                return layout;
        }
        return null;
    }

    private void swapButtons(Button targetButton, Button sourceButton) {
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

    private void settMarginSpacingPadding(Component layout, boolean margin, boolean spacing, boolean padding) {
        var targetLayout = layout instanceof VerticalLayout
                ? ((VerticalLayout) layout) : (HorizontalLayout) layout;
        targetLayout.setMargin(margin);
        targetLayout.setSpacing(spacing);
        targetLayout.setPadding(padding);
    }
}