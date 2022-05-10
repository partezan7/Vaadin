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
    static String CONTENT_GENERATOR = "Content Generator";
    static private int contentCounter = 0;
    static private int buttonCounter = 0;
    private VerticalLayout verticalLayout;
    private Box startBox;

    public MyView() {
        init();
    }

    private void init() {
        setSizeFull();
        verticalLayout = new VerticalLayout();
        verticalLayout.setHeight(100.0f, Unit.PERCENTAGE);
        verticalLayout.setWidth(150.0f, Unit.PIXELS);
        Button contentGeneratorButton = new Button(CONTENT_GENERATOR);
        contentGeneratorButton.addClickListener(buttonClickEvent -> {
            addContent();
            buttonCounter++;
        });
        makeButtonDraggable(contentGeneratorButton);
        Button clearButton = new Button("Clear all");
        clearButton.addClickListener(buttonClickEvent -> {
            startBox.clear();
            contentCounter = 0;
            buttonCounter = 0;
        });
        verticalLayout.add(contentGeneratorButton, clearButton);
        add(verticalLayout);
        Board board = new Board();
        board.setSizeFull();
        startBox = new Box();
        add(board);
        board.add(startBox);
    }

    private void addContent() {
        switch (buttonCounter) {
            case 0:
                Box box = new Box(createDragAndDropButton());
                startBox.put(box);
                break;
            case 4:
                Box box1 = new Box(createDragAndDropButton());
                startBox.putLeft(box1);
                break;
            case 2:
                Box box2 = new Box(createDragAndDropButton());
                startBox.putRight(box2);
                break;
            case 3:
                Box box3 = new Box(createDragAndDropButton());
                startBox.putUnder(box3);
                break;
            case 1:
                Box box4 = new Box(createDragAndDropButton());
                startBox.putAbove(box4);
                break;
        }
    }

    static Button createDragAndDropButton() {
        contentCounter++;
        Button newButton = new Button(String.valueOf(contentCounter));
        newButton.setSizeFull();
        makeButtonDraggable(newButton);
//        makeButtonAsDropTarget(newButton);
        return newButton;
    }

    private static void makeButtonDraggable(Button button) {
        DragSource<Button> dragSource = DragSource.create(button);
    }

    private static void makeButtonAsDropTarget(Button button) {
        DropTarget<Button> dropTarget = DropTarget.create(button);
        dropTarget.addDropListener(event -> {
            Button targetButton = event.getComponent();
            Button sourceButton = (Button) event.getDragSourceComponent().get();
            if (targetButton == sourceButton) return;
            if (sourceButton.getText().equals(CONTENT_GENERATOR)) {
                changeButton(targetButton);
            } else swapButtons(targetButton, sourceButton);
        });
    }

    static void changeButton(Button targetButton) {
        Button newButton = createDragAndDropButton();
        Component targetComponentParent = targetButton.getParent().get();
        var targetLayout = targetComponentParent instanceof VerticalLayout
                ? ((VerticalLayout) targetComponentParent) : (HorizontalLayout) targetComponentParent;
        int indexButton = targetLayout.indexOf(targetButton);
        targetLayout.addComponentAtIndex(indexButton, newButton);
        targetLayout.remove(targetButton);
    }

    private static void swapButtons(Button targetButton, Button sourceButton) {
        Component targetComponentParent = targetButton.getParent().get();
        Component sourceComponentParent = sourceButton.getParent().get();
        if (targetComponentParent == sourceComponentParent) {
            var layout = targetComponentParent instanceof VerticalLayout
                    ? ((VerticalLayout) targetComponentParent) : (HorizontalLayout) targetComponentParent;
            int button1Index = layout.indexOf(targetButton);
            int button2Index = layout.indexOf(sourceButton);
            layout.remove(targetButton);
            layout.addComponentAtIndex(button1Index, sourceButton);
            layout.addComponentAtIndex(button2Index, targetButton);
            return;
        }
        var targetLayout = targetComponentParent instanceof VerticalLayout
                ? ((VerticalLayout) targetComponentParent) : (HorizontalLayout) targetComponentParent;
        var sourceLayout = sourceComponentParent instanceof VerticalLayout
                ? ((VerticalLayout) sourceComponentParent) : (HorizontalLayout) sourceComponentParent;
        int targetButtonIndex = targetLayout.indexOf(targetButton);
        int sourceButtonIndex = sourceLayout.indexOf(sourceButton);
        targetLayout.addComponentAtIndex(targetButtonIndex, sourceButton);
        targetLayout.remove(targetButton);
        sourceLayout.addComponentAtIndex(sourceButtonIndex, targetButton);
        sourceLayout.remove(sourceButton);
    }
}
