package com.lgs.eden.views.gameslist.news;

import com.lgs.eden.api.news.BasicNewsData;
import com.lgs.eden.application.ApplicationCloseHandler;
import com.lgs.eden.utils.Translate;
import com.lgs.eden.utils.Utility;
import com.lgs.eden.utils.ViewsPath;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.control.Label;

import javafx.scene.web.WebEngine;
import javafx.scene.web.WebView;
import org.commonmark.node.*;
import org.commonmark.parser.Parser;
import org.commonmark.renderer.html.HtmlRenderer;
import org.w3c.dom.Element;
import org.w3c.dom.NodeList;
import org.w3c.dom.events.EventListener;
import org.w3c.dom.events.EventTarget;

import static javafx.concurrent.Worker.State;

/**
 * View for a news
 */
public class News {

    // ------------------------------ STATIC ----------------------------- \\

    public static Parent getScreen(BasicNewsData news) {
        FXMLLoader loader = Utility.loadView(ViewsPath.GAMES_A_NEWS.path);
        Parent parent = Utility.loadViewPane(loader);
        News controller = loader.getController();
        controller.init(news);
        return parent;
    }

    @FXML
    private Label newsTitle;
    @FXML
    private Label newsDate;
    @FXML
    private WebView newsContent;

    private String content;

    // ------------------------------ INSTANCE ----------------------------- \\

    private void init(BasicNewsData news) {
        newsTitle.setText(news.title);
        newsDate.setText(Translate.getDate(news.date));

        Parser parser = Parser.builder().build();
        Node document = parser.parse(
                "\n" +
                "Legendary Games Studio is honoured to present our new application : Tyrn.\n" +
                "\n" +
                "The software is open-source (meaning you can read/edit the source code)\n" +
                "and allow you to create and manage a set of data.\n" +
                "\n" +
                "For instance, in an RPG, you can manage all your data related to \n" +
                "SKILLS, PLAYERS AND MONSTERS, OBJECTS, QUEST etc... et easily\n" +
                "edit any data.\n" +
                "\n" +
                "Take note that the library don't include any assets and only\n" +
                "process you already created content in a single file, sorted\n" +
                "in categories.\n" +
                "You can find some royalties free assets at itch.io, we recommends\n" +
                "[pvgames.itch.io](https://pvgames.itch.io).\n" +
                "\n" +
                "---\n" +
                "\n" +
                "### Fonctionnalités de la V1\n" +
                "\n" +
                "- You can create categories, and add attributes (properties\n" +
                "such as a name for a person). The list of types (text, number, ...)\n" +
                "for an attribute is limited.\n" +
                "- You can pack files (images, musics, atlas, etc...)\n" +
                "- The software is available in english and in french\n" +
                "\n" +
                "\n" +
                "### En phase de tests\n" +
                "\n" +
                "We are currently testing this project, so don't hesitate\n" +
                "to share any bugs/problems you might found at\n" +
                "[tyrn@lgs-games.com](mailto:tyrn@lgs-games.com) and/or\n" +
                "any suggestions/improvement you want us to make.\n" +
                "\n" +
                "We will make a patch in the next weeks.\n" +
                "\n" +
                "### Documentation\n" +
                "\n" +
                "Documentation will be available around the 20th of September at\n" +
                "[https://github.com/lgs-games/tyrn/wiki](https://github.com/lgs-games/tyrn/wiki).\n" +
                "\n" +
                "### Crédits\n" +
                "\n" +
                "* Legendary Games Studio\n" +
                "* Quentin Ramsamy (dev)\n" +
                "* Thibault Meynier (dev)\n" +
                "* Pierre Ribollet (tester)\n" +
                "* FontAwesome (icons)\n" +
                " \n" +
                " "
        );
        HtmlRenderer renderer = HtmlRenderer.builder().build();

        WebEngine engine = this.newsContent.getEngine();
        engine.loadContent(
               this.content =  "<html>" +
                       "<style>" +
                       "body {\n" +
                       "    font-family: Segoe UI,Helvetica,Arial,sans-serif;\n" +
                       "    font-size: 17px;\n" +
                       "\n" +
                       "    background : #1e262c;\n" +
                       "    color:#FFFFFF;\n" +
                       "\n" +
                       "    padding: 0 20px 0 0;\n" +
                       "}"+
                       "a {\n" +
                       "    color: #2aa198;\n" +
                       "}"+
                       "h1, h2, h3, h4, h5, h6 {\n" +
                       "    color: #FFCC33;\n" +
                       "}"+
                       "</style>" +
                       "<body>"
                       +renderer.render(document)
                       +"</body>"
        );

        // disabled right-click
        newsContent.setContextMenuEnabled(false);

        // link listener
        engine.getLoadWorker().stateProperty().addListener(new LinkExternalBrowserListener(engine, content));

        ApplicationCloseHandler.registerLastEngine(engine);
    }

    /**
     * Should open links in user browser.
     */
    private static class LinkExternalBrowserListener implements ChangeListener<State> {
        private final WebEngine engine;
        private final String content;

        private LinkExternalBrowserListener(WebEngine engine, String content) { this.engine = engine;this.content = content; }

        @Override
        public void changed(ObservableValue<? extends State> o, State oldState,
                            State newState) {
            if (newState == State.SUCCEEDED) {

                // note next classes are from org.w3c.dom domain
                EventListener listener = ev -> {
                    ev.preventDefault();
                    String href = ((Element)ev.getTarget()).getAttribute("href");
                    Utility.openInBrowser(href);
                    engine.loadContent(content);
                };

                org.w3c.dom.Document doc = engine.getDocument();
                // add listener to add links
                NodeList links = doc.getElementsByTagName("a");
                for (int i=0; i < links.getLength(); i++) {
                    ((EventTarget)links.item(i))
                            .addEventListener("click", listener, false);
                }
            }
        }
    }
}
