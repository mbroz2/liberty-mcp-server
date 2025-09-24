import {css, LitElement} from 'lit';

export class DemoChat extends LitElement {

    _stripHtml(html)   {
        const div = document.createElement("div");
        div.innerHTML = html;
        return div.textContent || div.innerText || "";
    }
    _reducedHtmlStrip(msg) {
        // Create a new DOMParser to parse the HTML
        const parser = new DOMParser();
        const doc = parser.parseFromString(msg, 'text/html');
        
        // List of allowed tags
        const allowedTags = ['b', 'strong', 'i', 'em', 'u', 'del', 'small', 'big', 'sup', 'sub', 
                            'p', 'h1', 'h2', 'h3', 'h4', 'h5', 'h6', 'br', 'hr', 'ul', 'ol', 'li'];
        
        // Function to process nodes recursively
        function processNode(node) {
            // If this is a text node, keep it
            if (node.nodeType === Node.TEXT_NODE) {
                return node.textContent;
            }
            
            // If this is an element node
            if (node.nodeType === Node.ELEMENT_NODE) {
                // If it's not in our allowed list, just keep its text content
                if (!allowedTags.includes(node.nodeName.toLowerCase())) {
                    return Array.from(node.childNodes).map(processNode).join('');
                }
                
                // If it's an allowed tag, recreate it with its attributes
                const newElement = document.createElement(node.nodeName);
                
                // Process all child nodes
                Array.from(node.childNodes).forEach(child => {
                    const processedContent = processNode(child);
                    if (typeof processedContent === 'string') {
                        newElement.innerHTML += processedContent;
                    } else if (processedContent instanceof Node) {
                        newElement.appendChild(processedContent);
                    }
                });
                
                return newElement.outerHTML;
            }
            
            // For other node types, return empty string
            return '';
        }
        
        // Process the body content
        return Array.from(doc.body.childNodes).map(processNode).join('');
    }

    connectedCallback() {
        const chatBot = document.getElementsByTagName("chat-bot")[0];

        const protocol = (window.location.protocol === 'https:') ? 'wss' : 'ws';
        const socket = new WebSocket(protocol + '://' + window.location.host + '/chatbot');

        const that = this;
        socket.onmessage = function (event) {
            chatBot.hideLastLoading();
            // LLM response
            let lastMessage;
            if (chatBot.messages.length > 0) {
                lastMessage = chatBot.messages[chatBot.messages.length - 1];
            }
            if (lastMessage && lastMessage.sender.name === "Bot"  && ! lastMessage.loading) {
                if (! lastMessage.msg) {
                    lastMessage.msg = "";
                }
                lastMessage.msg += event.data;
                let bubbles = chatBot.shadowRoot.querySelectorAll("chat-bubble");
                let bubble = bubbles.item(bubbles.length - 1);
                if (lastMessage.message) {
                    bubble.innerHTML = that._stripHtml(lastMessage.message) + lastMessage.msg;
                } else {
                    bubble.innerHTML = lastMessage.msg;
                }
                chatBot.body.scrollTo({ top: chatBot.body.scrollHeight, behavior: 'smooth' })
            } else {
                chatBot.sendMessage(null, {
                    message: that._reducedHtmlStrip(event.data),
                    right: false,
                    sender: {
                        name: "Bot"
                    }
                });
            }
        }

        chatBot.addEventListener("sent", function (e) {
            if (e.detail.message.sender.name !== "Bot") {
                // User message
                const msg = that._stripHtml(e.detail.message.message);
                socket.send(msg);
                chatBot.sendMessage("", {
                    right: false,
                    loading: true
                });
            }
        });
    }


}

customElements.define('demo-chat', DemoChat);