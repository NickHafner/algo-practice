package graph;

import com.fasterxml.jackson.core.type.TypeReference;
import com.google.common.graph.*;
import common.LoadJson;
import graph.helper.Permission;
import lombok.val;
import org.jgrapht.Graphs;
import org.jgrapht.graph.DefaultDirectedGraph;
import org.jgrapht.graph.DefaultEdge;

import java.util.*;


public class ThirdPartyGraphs {
    public static void main(String[] args) {
        try {
        final List<Permission> permissions = LoadJson.loadResource("NaryTest.json", new TypeReference<List<Permission>>(){});
        val graph = ThirdPartyGraphs.buildGuavaGraph(permissions);
        val apacheGraph = ThirdPartyGraphs.buildApacheGraph(permissions);

        System.out.println(graph.nodes());
        System.out.println(Graphs.successorListOf(apacheGraph, "Overall-Management"));
        System.out.println(Graphs.predecessorListOf(apacheGraph, "Overall-Management"));
        System.out.println(Graphs.neighborListOf(apacheGraph, "Overall-Management"));


        } catch (Exception e) {
            System.out.println(e);
        }
    }
    
    private static DefaultDirectedGraph<String, DefaultEdge> buildApacheGraph(List<Permission> permissions) {
        val distinctNames = permissions.stream().map(Permission::getName).distinct().toList();
        System.out.println(distinctNames.size());
        val graph = new DefaultDirectedGraph<String, DefaultEdge>(DefaultEdge.class);
        distinctNames.forEach(graph::addVertex);
        permissions.stream().filter(permission -> permission.parent != null)
                .forEach(permission ->  {
                    try {
                        graph.addEdge(permission.getParent(), permission.getName());
                    } catch (Exception ignore) {}
                });

        return graph;
    }

    private static ImmutableNetwork<String, String> buildGuavaGraph(List<Permission> permissions) {
        val distinctNames = permissions.stream().map(Permission::getName).distinct().toList();
        System.out.println(distinctNames.size());
        MutableNetwork<String, String> mutGraph = NetworkBuilder.directed()
                .expectedNodeCount(distinctNames.size())
                .nodeOrder(ElementOrder.unordered())
                .build();
        distinctNames.forEach(mutGraph::addNode);
        permissions.stream().filter(permission -> permission.parent != null)
                .forEach(permission ->  {
                    try {
                        mutGraph.addEdge(permission.getParent(), permission.getName(), permission.getParent()+"->"+permission.getName());
                    } catch (Exception ignore) {}
                });
        return ImmutableNetwork.copyOf(mutGraph);
    }
}
