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
import org.commonmark.Extension;
import org.commonmark.ext.gfm.tables.TablesExtension;
import org.commonmark.node.Node;
import org.commonmark.parser.Parser;
import org.commonmark.renderer.html.HtmlRenderer;
import org.w3c.dom.Element;
import org.w3c.dom.NodeList;
import org.w3c.dom.events.EventListener;
import org.w3c.dom.events.EventTarget;

import java.util.Collections;
import java.util.List;

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

    // ------------------------------ INSTANCE ----------------------------- \\

    private void init(BasicNewsData news) {
        newsTitle.setText(news.title);
        newsDate.setText(Translate.getDate(news.date));
        List<Extension> extensions = Collections.singletonList(TablesExtension.create());
        Parser parser = Parser.builder()
                .extensions(extensions)
                .build();
        Node document = parser.parse(Utility.getFileAsString(news.link));

        HtmlRenderer renderer = HtmlRenderer.builder()
                .extensions(extensions)
                .build();

        WebEngine engine = this.newsContent.getEngine();
        engine.loadContent("<html lang='fr'>" +
                "<head>" +
                "<meta http-equiv=\"Content-Type\" content=\"text/html; charset=utf-8\" />" +
                "<meta charset='UTF-8'>" +
                "<style>" +
                "body {\n" +
                "    font-family: Segoe UI,Helvetica,Arial,sans-serif;\n" +
                "    font-size: 17px;\n" +
                "\n" +
                "    background : #1e262c;\n" +
                "    color:#FFFFFF;\n" +
                "\n" +
                "    padding: 0 20px 0 0;\n" +
                "}" +
                "a {\n" +
                "    color: #2aa198;\n" +
                "}" +
                "h1, h2, h3, h4, h5, h6 {\n" +
                "    color: #FFCC33;\n" +
                "}" +
                "</style>" +
                "</head>"+
                "<body>"
                + renderer.render(document)
                + "</body>"
                + "</html>");

        // disabled right-click
        newsContent.setContextMenuEnabled(false);

        // link listener
        engine.getLoadWorker().stateProperty().addListener(new LinkExternalBrowserListener(engine));

        ApplicationCloseHandler.registerLastEngine(engine);
    }

    // ------------------------------ LISTENERS ----------------------------- \\

    /**
     * Should open links in user browser.
     */
    private record LinkExternalBrowserListener(WebEngine engine) implements ChangeListener<State> {

        @Override
        public void changed(ObservableValue<? extends State> o, State oldState,
                            State newState) {
            if (newState == State.SUCCEEDED) {

                // note next classes are from org.w3c.dom domain
                EventListener listener = ev -> {
                    ev.preventDefault();
                    String href = ((Element) ev.getTarget()).getAttribute("href");
                    Utility.openInBrowser(href);
                };

                org.w3c.dom.Document doc = engine.getDocument();
                // add listener to add links
                NodeList links = doc.getElementsByTagName("a");
                for (int i = 0; i < links.getLength(); i++) {
                    ((EventTarget) links.item(i))
                            .addEventListener("click", listener, false);
                }
            }
        }
    }

    // ------------------------------ TEST ----------------------------- \\

    @SuppressWarnings({"unused", "SpellCheckingInspection"})
    private static final String MARKDOWN_TEST = """
            Legendary Games Studio is honoured to present our new application : Tyrn.

            The software is open-source (meaning you can read/edit the source code)
            and allow you to create and manage a set of data.

            For instance, in an RPG, you can manage all your data related to
            SKILLS, PLAYERS AND MONSTERS, OBJECTS, QUEST etc... et easily
            edit any data.

            Take note that the library don't include any assets and only
            process you already created content in a single file, sorted
            in categories.
            You can find some royalties free assets at itch.io, we recommends
            [pvgames.itch.io](https://pvgames.itch.io).

            ---

            ### Fonctionnalités de la V1

            - You can create categories, and add attributes (properties
              such as a name for a person). The list of types (text, number, ...)
              for an attribute is limited.
            - You can pack files (images, musics, atlas, etc...)
            - The software is available in english and in french


            ### En phase de tests

            We are currently testing this project, so don't hesitate
            to share any bugs/problems you might found at
            [tyrn@lgs-games.com](mailto:tyrn@lgs-games.com) and/or
            any suggestions/improvement you want us to make.

            We will make a patch in the next weeks.

            ### Documentation

            Documentation will be available around the 20th of September at
            [https://github.com/lgs-games/tyrn/wiki](https://github.com/lgs-games/tyrn/wiki).

            | Colonne | Colonne |
            | ------ | ------ |
            | tab[0][0] | tab[0][1] |
            | tab[1][0] | tab[1][1] |
            | tab[2][0] | tab[2][1] |
            | tab[3][0] | tab[3][1] |
            | tab[4][0] | tab[4][1] |
            | tab[5][0] | tab[5][1] |

            ### Crédits

            * Legendary Games Studio
            * Quentin Ramsamy (dev)
            * Thibault Meynier (dev)
            * Pierre Ribollet (tester)
            * FontAwesome (icons)""";
}
