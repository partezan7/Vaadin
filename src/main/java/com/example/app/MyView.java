package com.example.app;

import com.vaadin.flow.component.Component;
import com.vaadin.flow.component.Unit;
import com.vaadin.flow.component.button.Button;
import com.vaadin.flow.component.dnd.DragSource;
import com.vaadin.flow.component.dnd.DropTarget;
import com.vaadin.flow.component.orderedlayout.HorizontalLayout;
import com.vaadin.flow.component.orderedlayout.VerticalLayout;
import com.vaadin.flow.router.Route;

@Route("")
public class MyView extends HorizontalLayout {
    String CONTENT_GENERATOR = "Content Generator";
    int counter = 0;
    private VerticalLayout verticalLayout;
    private VerticalLayout verticalLayout1;
    private VerticalLayout verticalLayout2;
    private HorizontalLayout horizontalLayout;
    private VerticalLayout verticalLayout3;
    private VerticalLayout verticalLayout4;

    public MyView() {
        init();
    }

    private void init() {
        setSizeFull();
        verticalLayout = new VerticalLayout();
        verticalLayout.setHeight(100.0f, Unit.PERCENTAGE);
        verticalLayout.setWidth(150.0f, Unit.PIXELS);
        Button contentGeneratorButton = new Button(CONTENT_GENERATOR);
        contentGeneratorButton.addClickListener(buttonClickEvent -> addContent());
        makeButtonDraggable(contentGeneratorButton);
        verticalLayout.add(contentGeneratorButton);
        add(verticalLayout);
    }

    private void addContent() {
        switch (counter) {
            case 0:
                verticalLayout1 = new VerticalLayout();
                verticalLayout1.add(createDragAndDropButton());
                add(verticalLayout1);
                break;
            case 1:
                verticalLayout2 = new VerticalLayout();
                verticalLayout2.add(createDragAndDropButton());
                add(verticalLayout2);
                break;
            case 2:
                verticalLayout2.add(createDragAndDropButton());
                break;
            case 3:
                verticalLayout3 = new VerticalLayout();
                verticalLayout3.add(createDragAndDropButton());
                add(verticalLayout3);
                break;
            case 4:
                horizontalLayout = new HorizontalLayout();
                horizontalLayout.add(this.getComponentAt(1), this.getComponentAt(2));
                horizontalLayout.setSizeFull();
                verticalLayout4 = new VerticalLayout();
                verticalLayout4.add(createDragAndDropButton());
                verticalLayout4.add(horizontalLayout);
                addComponentAtIndex(1, verticalLayout4);
                break;
            case 5:
                horizontalLayout.addComponentAtIndex(2, horizontalLayout.getComponentAt(1));
                horizontalLayout.addComponentAtIndex(1, createDragAndDropButton());
                break;
        }
    }

    private Button createDragAndDropButton() {
        counter++;
        Button newButton = new Button(String.valueOf(counter));
        newButton.setSizeFull();
        makeButtonDraggable(newButton);
        makeButtonDropTarget(newButton);
        return newButton;
    }

    private void makeButtonDraggable(Button button) {
        DragSource<Button> dragSource = DragSource.create(button);
    }

    private void makeButtonDropTarget(Button button) {
        DropTarget<Button> dropTarget = DropTarget.create(button);
        dropTarget.addDropListener(event -> {
            Button targetButton = event.getComponent();
            Button sourceButton = (Button) event.getDragSourceComponent().get();
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
