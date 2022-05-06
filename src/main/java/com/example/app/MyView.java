package com.example.app;

import com.vaadin.flow.component.Unit;
import com.vaadin.flow.component.board.Board;
import com.vaadin.flow.component.board.Row;
import com.vaadin.flow.component.button.Button;
import com.vaadin.flow.component.dnd.DragSource;
import com.vaadin.flow.component.dnd.DropTarget;
import com.vaadin.flow.component.orderedlayout.HorizontalLayout;
import com.vaadin.flow.component.orderedlayout.VerticalLayout;
import com.vaadin.flow.router.Route;

@Route("")
public class MyView extends HorizontalLayout {
    int counter = 0;
    private Button button;
    private VerticalLayout verticalLayout;
    private Board board;

    public MyView() {
        init();
    }

    private void init() {
        setSizeFull();

        verticalLayout = new VerticalLayout();
        verticalLayout.setHeight(100.0f, Unit.PERCENTAGE);
        verticalLayout.setWidth(10.0f, Unit.PERCENTAGE);

        button = createButton();
        verticalLayout.add(button);

        board = new Board();
        board.setSizeFull();

        add(verticalLayout, board);
        dropButton(board);
    }

    private void dragButton(Button button) {
        DragSource<Button> dragSource = DragSource.create(button);
        dragSource.addDragEndListener(event -> {
        });
    }

    private void dropButton(Board board) {
        DropTarget<Board> dropTarget = DropTarget.create(board);
        dropTarget.addDropListener(event -> {
            verticalLayout.add(createButton());
            Row row0 = new Row();
            Button button = (Button) event.getDragSourceComponent().get();
            button.setSizeFull();
            row0.add(button);
            board.add(row0);
        });
    }

    private Button createButton() {
        counter++;
        Button newButton = new Button(String.valueOf(counter),
                buttonClickEvent -> button.setHeight(30, Unit.PERCENTAGE));
        dragButton(newButton);
        return newButton;
    }
}
