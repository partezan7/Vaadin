package com.example.app;

import com.vaadin.flow.component.Tag;
import com.vaadin.flow.component.board.Board;
import com.vaadin.flow.component.board.Row;
import com.vaadin.flow.component.button.Button;
import com.vaadin.flow.component.dependency.JsModule;
import com.vaadin.flow.component.dnd.DragSource;
import com.vaadin.flow.component.dnd.DropTarget;
import com.vaadin.flow.component.html.Div;
import com.vaadin.flow.component.littemplate.LitTemplate;
import com.vaadin.flow.component.orderedlayout.HorizontalLayout;
import com.vaadin.flow.component.orderedlayout.VerticalLayout;
import com.vaadin.flow.component.template.Id;
import com.vaadin.flow.router.Route;

/**
 * A Designer generated component for the my-view template.
 * <p>
 * Designer will add and remove fields with @Id mappings but
 * does not overwrite or otherwise change this file.
 */
@Tag("my-view")
@JsModule("./my-view.ts")
@Route("")
public class MyView extends LitTemplate {
    int counter = 0;
    private Button button;
    @Id("verticalLayout")
    private VerticalLayout verticalLayout;
    @Id("horizontalLayout")
    private HorizontalLayout horizontalLayout;
    @Id("board")
    private Board board;

    /**
     * Creates a new MyView.
     */
    public MyView() {
        button = createButton();
        verticalLayout.add(button);
    }

    private void dragButton(Button button) {
        DragSource<Button> dragSource = DragSource.create(button);
        dropButton(board);
        dragSource.addDragEndListener(event -> {
            verticalLayout.removeAll();
            verticalLayout.add(createButton());
        });
    }

//    private void dropButton(Board board) {
//        DropTarget<Board> dropTarget = DropTarget.create(board);
//        dropTarget.addDropListener(event -> {
//            Row row0 = new Row();
//            Div div0 = new Div();
//            Div div1 = new Div();
//            Div div2 = new Div();
//            Div div3 = new Div();
//            Button button = (Button) event.getDragSourceComponent().get();
//            button.setSizeFull();
//
//            div0.add(button);
//            row0.add(div0, div1, div2, div3);
//            board.add(row0);
//        });
//    }

    private void dropButton(Board board) {
        DropTarget<Board> dropTarget = DropTarget.create(board);
        dropTarget.addDropListener(event -> {
            if (counter == 1) {
                Row row0 = new Row();
                row0.setSizeFull();
                Div div0 = new Div();
                Button button = (Button) event.getDragSourceComponent().get();
                button.setSizeFull();

                div0.add(button);
                row0.add(div0);
                board.add(row0);
            }
        });
    }

    private Button createButton() {
        counter++;
        Button newButton = new Button(String.valueOf(counter));
        dragButton(newButton);
        return newButton;
    }
}
