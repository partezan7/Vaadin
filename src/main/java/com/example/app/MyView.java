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
    String CONTENT_GENERATOR = "Content Generator";
    private int contentCounter = 0;
    private int buttenCounter = 0;
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
            System.out.println(String.format("butterCounter = %d, contentCounter = %d", buttenCounter, contentCounter));
            addContent();
            buttenCounter++;
        });
        makeButtonDraggable(contentGeneratorButton);
        verticalLayout.add(contentGeneratorButton);
        add(verticalLayout);
        Board board = new Board();
        board.setSizeFull();
        startBox = new Box();
        add(board);
        board.add(startBox);
    }

    private void addContent() {
        switch (buttenCounter) {
            case 0:
                Box box = new Box(createDragAndDropButton());
                startBox.put(box);
                break;
            case 1:
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
            case 4:
                Box box4 = new Box(createDragAndDropButton());
                startBox.pitAbove(box4);
                break;
        }
    }

    private Button createDragAndDropButton() {
        contentCounter++;
        Button newButton = new Button(String.valueOf(contentCounter));
        newButton.setSizeFull();
        makeButtonDraggable(newButton);
        makeButtonAsDropTarget(newButton);
        return newButton;
    }

    private void makeButtonDraggable(Button button) {
        DragSource<Button> dragSource = DragSource.create(button);
    }

    private void makeButtonAsDropTarget(Button button) {
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

    private void changeButton(Button targetButton) {
        Button newButton = createDragAndDropButton();
        Component targetComponentParent = targetButton.getParent().get();
        var targetLayout = targetComponentParent instanceof VerticalLayout
                ? ((VerticalLayout) targetComponentParent) : (HorizontalLayout) targetComponentParent;
        int indexButton = targetLayout.indexOf(targetButton);
        targetLayout.addComponentAtIndex(indexButton, newButton);
        targetLayout.remove(targetButton);
    }

    private void swapButtons(Button targetButton, Button sourceButton) {
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
