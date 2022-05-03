import { LitElement, html, css, customElement } from 'lit-element';
import '@vaadin/horizontal-layout/src/vaadin-horizontal-layout.js';
import '@vaadin/vertical-layout/src/vaadin-vertical-layout.js';
import '@vaadin/board/src/vaadin-board.js';

@customElement('my-view')
export class MyView extends LitElement {
  static get styles() {
    return css`
      :host {
          display: block;
          height: 100%;
      }
      `;
  }

  render() {
    return html`
<vaadin-horizontal-layout theme="spacing" id="horizontalLayout" style="width: 100%; height: 100%;">
 <vaadin-vertical-layout theme="spacing" id="verticalLayout" style="width: 100%; height: 100%; flex-shrink: 1; flex-grow: 0;"></vaadin-vertical-layout>
 <vaadin-board id="board" style="width: 100%; flex-grow: 1; flex-shrink: 0;"></vaadin-board>
</vaadin-horizontal-layout>
`;
  }

  // Remove this method to render the contents of this view inside Shadow DOM
  createRenderRoot() {
    return this;
  }
}
