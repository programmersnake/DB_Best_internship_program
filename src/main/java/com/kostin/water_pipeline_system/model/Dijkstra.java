package com.kostin.water_pipeline_system.model;

import java.util.HashSet;
import java.util.LinkedList;
import java.util.Map;
import java.util.Set;

public class Dijkstra {

    public static int calculateShortestPathFromSource(Graph graph, Node source, Node searchNode) {
        source.setDistance(0);

        int resultFromIntCalculateMinimumDistance=-1;
        Set<Node> settledNodes = new HashSet<>();
        Set<Node> unsettledNodes = new HashSet<>();

        unsettledNodes.add(source);

        while (unsettledNodes.size() != 0) {
            Node currentNode = getLowestDistanceNode(unsettledNodes);
            unsettledNodes.remove(currentNode);
            for (Map.Entry< Node, Integer> adjacencyPair:currentNode.getAdjacentNodes().entrySet()) {
                Node adjacentNode = adjacencyPair.getKey();
                Integer edgeWeight = adjacencyPair.getValue();
                if (!settledNodes.contains(adjacentNode)) {
                    int tempResult = calculateMinimumDistance(adjacentNode, edgeWeight, currentNode, searchNode);
                    if(tempResult!=-1) {
                        if( resultFromIntCalculateMinimumDistance == -1)
                            resultFromIntCalculateMinimumDistance = tempResult;
                        if ( tempResult < resultFromIntCalculateMinimumDistance )
                            resultFromIntCalculateMinimumDistance = tempResult;
                    }
                    unsettledNodes.add(adjacentNode);
                }
            }
            settledNodes.add(currentNode);
        }
        return resultFromIntCalculateMinimumDistance;
    }

    private static Node getLowestDistanceNode(Set< Node > unsettledNodes) {
        Node lowestDistanceNode = null;
        int lowestDistance = Integer.MAX_VALUE;
        for (Node node: unsettledNodes) {
            int nodeDistance = node.getDistance();
            if (nodeDistance < lowestDistance) {
                lowestDistance = nodeDistance;
                lowestDistanceNode = node;
            }
        }
        return lowestDistanceNode;
    }

    private static int calculateMinimumDistance(Node evaluationNode, Integer edgeWeigh, Node sourceNode, Node searchNode) {
        Integer sourceDistance = sourceNode.getDistance();
        if (sourceDistance + edgeWeigh < evaluationNode.getDistance()) {
            evaluationNode.setDistance(sourceDistance + edgeWeigh);
            LinkedList<Node> shortestPath = new LinkedList<>(sourceNode.getShortestPath());
            shortestPath.add(sourceNode);
            evaluationNode.setShortestPath(shortestPath);
            /*System.out.println("evauluation ["+evaluationNode.getName()+"] sourceNode ["+sourceNode.getName()+"] = "+
                    Integer.sum( sourceDistance , edgeWeigh ));
*/
            //if(evaluationNode.equals( searchNode )||sourceNode.equals( searchNode )) {
            if(evaluationNode.equals( searchNode )) {
                //System.out.println("SearchNode was serched. Length = "+Integer.sum( sourceDistance, edgeWeigh));
                return Integer.sum( sourceDistance, edgeWeigh );
            }
        }
        return -1;
    }

}
