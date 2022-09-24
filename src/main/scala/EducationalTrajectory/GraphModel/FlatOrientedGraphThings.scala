package EducationalTrajectory.GraphModel


class FlatOrientedGraphThings[ T ] {
  case class Graph( nodes: Set[ Node ], edges: Set[ Edge ] )
  
  case class Node( value: T ) {
    def -> ( other: Node ): Edge = Edge(this, other)
  }
  
  case class Edge( source: Node, receiver: Node, weight: Int = 0 )
  
  /**
   * Generating graph with it's main constructor, checking for wrong edges
   */
  def graphSafeCreate(nodes:Set[Node],edges:Set[Edge]):Graph = {
    val incorrectEdge = edges.find(edge => !nodes.contains(edge.source) || !nodes.contains(edge.receiver))
                             .map(edge => (edge, if(!nodes.contains(edge.source)) edge.receiver else edge.source))
    if(incorrectEdge.isEmpty) Graph(nodes, edges)
    else throw new Exception(s"Graph edges includes ${ incorrectEdge.get._1 }, but node ${incorrectEdge.get._2} is missing")
  }
  
  def newNode( value: T ): Node = Node(value)
  
  def newEdge( src: Node, rec: Node ): Edge = Edge(src, rec)
  
  def newNodes( from: Iterable[ T ] ): Set[ Node ] = from.map(Node).toSet
  
  def newEdges( nodes: Iterable[ Node ], edgeExistPred: (Node, Node) => Boolean ): Set[ Edge ] =
    nodes.toSeq.combinations(2).collect {
      case Seq(first, second) if edgeExistPred(first, second) => Edge(first, second)
    }.toSet
  
  def newGraph( fromElementsOf: Iterable[ T ], edgeExistPred: (Node, Node) => Boolean ): Graph = {
    val nodes = newNodes(fromElementsOf)
    val edges = newEdges(nodes, edgeExistPred)
    Graph(nodes, edges)
  }
  
  def graphWith( graph: Graph, nodes: Set[ Node ] = Set.empty, edges: Set[ Edge ] = Set.empty ): Graph =
    graphSafeCreate(graph.nodes ++ nodes, graph.edges ++ edges)
  
  def graphWithout( graph: Graph, nodes: Set[ Node ] = Set.empty, edges: Set[ Edge ] = Set.empty ): Graph =
    graphSafeCreate(graph.nodes -- nodes, graph.edges -- edges)
}
