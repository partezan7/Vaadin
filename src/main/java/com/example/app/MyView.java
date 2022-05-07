package com.example.app;

import com.vaadin.flow.component.Component;
import com.vaadin.flow.component.Unit;
import com.vaadin.flow.component.board.Board;
import com.vaadin.flow.component.button.Button;
import com.vaadin.flow.component.dnd.DragSource;
import com.vaadin.flow.component.dnd.DropTarget;
import com.vaadin.flow.component.orderedlayout.HorizontalLayout;
import com.vaadin.flow.component.orderedlayout.VerticalLayout;
import com.vaadin.flow.router.Route;

@Route("")
public class MyView extends HorizontalLayout {
    int counter = 0;
    private VerticalLayout verticalLayout;
    private VerticalLayout verticalLayout1;
    private VerticalLayout verticalLayout2;
    private HorizontalLayout horizontalLayout;
    private VerticalLayout verticalLayout3;
    private VerticalLayout verticalLayout4;
    private Board board;

    public MyView() {
        init();
    }

    private void init() {
        setSizeFull();
        verticalLayout = new VerticalLayout();
        verticalLayout.setHeight(100.0f, Unit.PERCENTAGE);
        verticalLayout.setWidth(150.0f, Unit.PIXELS);
        Button buttonGenerator = new Button("Content Generator");
        dragButton(buttonGenerator);
        verticalLayout.add(buttonGenerator);
        buttonGenerator.addClickListener(buttonClickEvent -> addContent());
        add(verticalLayout);
    }

    private void addContent() {
        switch (counter) {
            case 0:
                verticalLayout1 = new VerticalLayout();
                verticalLayout1.add(createButton());
                add(verticalLayout1);
                break;
            case 1:
                verticalLayout2 = new VerticalLayout();
                verticalLayout2.add(createButton());
                add(verticalLayout2);
                break;
            case 2:
                verticalLayout2.add(createButton());
                break;
            case 3:
                verticalLayout3 = new VerticalLayout();
                verticalLayout3.add(createButton());
                add(verticalLayout3);
                break;
            case 4:
                horizontalLayout = new HorizontalLayout();
                horizontalLayout.add(this.getComponentAt(1), this.getComponentAt(2));
                horizontalLayout.setSizeFull();
                verticalLayout4 = new VerticalLayout();
                verticalLayout4.add(createButton());
                verticalLayout4.add(horizontalLayout);
                addComponentAtIndex(1, verticalLayout4);
                break;
            case 5:
                horizontalLayout.addComponentAtIndex(2, horizontalLayout.getComponentAt(1));
                horizontalLayout.addComponentAtIndex(1, createButton());
                break;
        }
    }

    private void dragButton(Button button) {
        DragSource<Button> dragSource = DragSource.create(button);
        dragSource.addDragEndListener(event -> {
        });
    }

    private void dropButton(Button button) {
        DropTarget<Button> dropTarget = DropTarget.create(button);
        dropTarget.addDropListener(event -> {
            Button newButton = createButton();
            Button oldButton = event.getComponent();
            replaceButton(oldButton, newButton);
        });
    }

    private void replaceButton(Button oldButton, Button newButton) {
        Component component = oldButton.getParent().get();
        if (component.getClass().equals(VerticalLayout.class)) {
            VerticalLayout verticalLayout = (VerticalLayout) component;
            int indexButton = verticalLayout.indexOf(oldButton);
            verticalLayout.remove(oldButton);
            verticalLayout.addComponentAtIndex(indexButton, newButton);
        } else {
            if (component.getClass().equals(HorizontalLayout.class)) {
                HorizontalLayout horizontalLayout = (HorizontalLayout) component;
                int indexButton = horizontalLayout.indexOf(oldButton);
                horizontalLayout.remove(oldButton);
                horizontalLayout.addComponentAtIndex(indexButton, newButton);
            }
        }
    }

    private Button createButton() {
        counter++;
        Button newButton = new Button(String.valueOf(counter));
        dropButton(newButton);
        newButton.setSizeFull();
        return newButton;
    }
}
