package ylabs.orientdb.session

import com.tinkerpop.blueprints.impls.orient.OrientGraph

final case class ODBGraphSession[+A](override val block: OrientGraph ⇒ A)
    extends AbstractODBSession[A, OrientGraph](block) {

  def run(graph: OrientGraph): A = {
    val result = block(graph)
    close(graph)
    result
  }

  def close(graph: OrientGraph): Unit =
    graph.shutdown()
}

object ODBGraphSession extends ODBSessionInstances[OrientGraph, ODBGraphSession]
