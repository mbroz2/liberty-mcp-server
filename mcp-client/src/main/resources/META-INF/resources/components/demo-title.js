import {LitElement, html, css} from 'lit';

export class DemoTitle extends LitElement {

    static styles = css`
      h1 {
        font-family: "IBM Plex Sans", sans-serif;
        font-size: 50px;
        font-style: normal;
        font-variant: normal;
        font-weight: 700;
        line-height: 26.4px;
        color: var(--main-highlight-text-color);
      }

      .title {
        text-align: center;
        padding: 1em;
        background: var(--main-bg-color);
      }
      
      .explanation {
        margin-left: auto;
        margin-right: auto;
        width: 50%;
        text-align: justify;
        font-size: 20px;
      }
      
      .explanation img {
        max-width: 60%;
        display: block;
        float:left;
        margin-right: 2em;
        margin-top: 1em;
      }
    `

    render() {
        return html`
            <div class="title">
                <h1>Liberty MCP Server Demo</h1>
            </div>
            <div class="explanation">
                <p>This sample demonstrates using <strong>Liberty as an MCP Server</strong>, and <strong>Quarkus as the MCP Client</strong>.</p>
                The <em>MCP Server</em> makes available a <code>getForecast</code> tool.<br>
                The <em>MCP Client</em> requests the weather forecast for the location specified in the user's request.
                <br><br>
                <p>Suggested prompts to try out:</p>
                <ul>
                    <li>What's the weather forecast for Maui, Hawaii?</li>
                    <li>Will I need an umbrella this week in Austin, TX?</li>
                    <li>Will it snow in the next 4 days in Toronto, Canada?</li>
                    <li>Who's going to see more rainfall this week, Maui, Hawaii or Seattle, Washington?</li>
                </ul>
            </div>
        `
    }

}

customElements.define('demo-title', DemoTitle);