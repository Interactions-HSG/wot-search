package com.hsg.interactions.hypermas.crawler.search;

import com.hsg.interactions.hypermas.crawler.core.EventBusRegistry;
import fr.inria.corese.core.Graph;
import fr.inria.corese.core.load.Load;
import fr.inria.corese.core.load.LoadException;

import fr.inria.corese.core.print.ResultFormat;
import fr.inria.corese.core.print.TripleFormat;
import fr.inria.corese.core.query.QueryProcess;
import fr.inria.corese.kgram.core.Mappings;
import fr.inria.corese.sparql.exceptions.EngineException;
import io.vertx.core.AbstractVerticle;
import io.vertx.core.Vertx;
import io.vertx.core.eventbus.EventBus;
import io.vertx.core.eventbus.Message;

public class SearchEngineVerticle extends AbstractVerticle {
    private Vertx vertx;
    private Graph coreseGraph;
    private Load coreseLoad;
    private QueryProcess exec;

//    public SearchEngine() {
//        vertx = Vertx.currentContext().owner();
//        coreseGraph = Graph.create();
//        coreseLoad = Load.create(coreseGraph);
//        exec = QueryProcess.create(coreseGraph);
//
//        EventBus eventBus = vertx.eventBus();
//        eventBus.consumer(EventBusRegistry.SEARCH_ENGINE_DATA_ADDRESS, this::handleDataReload);
//    }

    @Override
    public void start() {
        vertx = Vertx.currentContext().owner();
        coreseGraph = Graph.create();
        coreseLoad = Load.create(coreseGraph);
        exec = QueryProcess.create(coreseGraph);

        EventBus eventBus = vertx.eventBus();
        eventBus.consumer(EventBusRegistry.SEARCH_ENGINE_DATA_ADDRESS, this::handleDataReload);
        eventBus.consumer(EventBusRegistry.SEARCH_ENGINE_QUERY, this::handleSearchQuery);
    }

    public void handleSearchQuery(Message<String> message) {
        try {
            String query = message.body();

            Mappings map = exec.query(query);
            if (!query.toLowerCase().contains("construct")) {
                // we can only return plain sparql results, no nice turtle
                ResultFormat f = ResultFormat.create(map);
                message.reply(f.toString());
            } else {
                Graph g = exec.getGraph(map);
                TripleFormat tf = TripleFormat.create(g);
                message.reply(tf.toString());
            }

        } catch (EngineException e) {
            e.printStackTrace();
            message.fail(500, e.getMessage());
        }
    }

    private void handleDataReload(Message<String> message) {
        String filePath = message.body();
        try {
            coreseGraph.empty();
            coreseLoad.parse(filePath);
            message.reply("ok");
        } catch (LoadException e) {
            e.printStackTrace();
            message.fail(500, e.getMessage());
        }
    }
}
