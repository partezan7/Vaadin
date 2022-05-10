package com.example.app;

import com.vaadin.flow.component.Component;
import com.vaadin.flow.component.Unit;
import com.vaadin.flow.component.dnd.DropTarget;
import com.vaadin.flow.component.html.Label;
import com.vaadin.flow.component.orderedlayout.HorizontalLayout;
import com.vaadin.flow.component.orderedlayout.VerticalLayout;
import org.springframework.stereotype.Service;

@Service
public class Box extends HorizontalLayout {
    final float BORDER = 20.0f;

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

    public void putAbove(Component boxComponent) {
        middle.addComponentAsFirst(boxComponent);
    }

    public void clear(){
        middle.removeAll();
        middle.add(makeContainer());
    }

    private void init() {
        this.setSizeFull();
        settMarginSpacingPadding(this, false, false, false);
        left = makeLayout(Insert.LEFT);
        right = makeLayout(Insert.RIGHT);
        center = makeCenter();
        this.add(left, center, right);
    }

    private VerticalLayout makeCenter() {
        top = makeLayout(Insert.TOP);
        bottom = makeLayout(Insert.BOTTOM);
        container = makeContainer();
        middle = new VerticalLayout(container);
        middle.setSizeFull();
        settMarginSpacingPadding(middle, false, false, false);
        center = new VerticalLayout(top, middle, bottom);
        settMarginSpacingPadding(center, false, false, false);
        return center;
    }

    private HorizontalLayout makeContainer() {
        container = new HorizontalLayout();
        container.setSizeFull();
        DropTarget<HorizontalLayout> dropContainer = DropTarget.create(container);
        dropContainer.addDropListener(event -> {
            Box newBox = new Box(MyView.createDragAndDropButton());
            container.add(newBox);
        });
        return container;
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
                    Component targetComponent = event.getComponent().getParent().get();
                    if (targetComponent instanceof Box) {
                        Box targetBox = (Box) targetComponent;
                        targetBox.putLeft(MyView.createDragAndDropButton());
                    }
                });
                return layout;
            case RIGHT:
                layout.setWidth(BORDER, Unit.PIXELS);
                layout.setHeightFull();
                setLabel(layout, "\u2192");
                dropTarget.addDropListener(event -> {
                    Component targetComponent = event.getComponent().getParent().get();
                    if (targetComponent instanceof Box) {
                        Box targetBox = (Box) targetComponent;
                        targetBox.putRight(MyView.createDragAndDropButton());
                    }
                });
                return layout;
            case TOP:
                layout.setHeight(BORDER, Unit.PIXELS);
                layout.setWidthFull();
                setLabel(layout, "\u2191");
                dropTarget.addDropListener(event -> {
                    Component targetComponent = event.getComponent().getParent().get();
                    Box targetBox;
                    if (targetComponent instanceof Box) {
                        targetBox = (Box) targetComponent;
                    } else {
                        targetBox = (Box) targetComponent.getParent().get();
                    }
                    targetBox.putAbove(MyView.createDragAndDropButton());
                });
                return layout;
            case BOTTOM:
                layout.setHeight(BORDER, Unit.PIXELS);
                layout.setWidthFull();
                setLabel(layout, "\u2193");
                dropTarget.addDropListener(event -> {
                    Component targetComponent = event.getComponent().getParent().get();
                    Box targetBox;
                    if (targetComponent instanceof Box) {
                        targetBox = (Box) targetComponent;
                    } else {
                        targetBox = (Box) targetComponent.getParent().get();
                    }
                    targetBox.putUnder(MyView.createDragAndDropButton());
                });
                return layout;
        }
        return null;
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