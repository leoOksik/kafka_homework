package ru.otus

import akka.NotUsed
import akka.actor.ActorSystem
import akka.stream.{ClosedShape, Graph}
import akka.stream.scaladsl.{Broadcast, Flow, GraphDSL, RunnableGraph, Sink, Source, ZipWith}

object GraphDsl {

  implicit val system: ActorSystem = ActorSystem("fusion")

  val graph: Graph[ClosedShape.type, NotUsed] =
    GraphDSL.create() { implicit builder: GraphDSL.Builder[NotUsed] =>
      import GraphDSL.Implicits._

      val input = builder.add(Source(1 to 5))

      val firstFlow = builder.add(Flow[Int].map(x => x * 10))
      val secondFlow = builder.add(Flow[Int].map(x => x * 2))
      val thirdFlow = builder.add(Flow[Int].map(x => x * 3))
      val broadcast = builder.add(Broadcast[Int](3))
      val zip = builder.add(ZipWith[Int, Int, Int, Int]((a, b, c) => a + b + c))
      val output = builder.add(Sink.foreach(println))

      input ~> broadcast

      broadcast.out(0) ~> firstFlow ~> zip.in0
      broadcast.out(1) ~> secondFlow ~> zip.in1
      broadcast.out(2) ~> thirdFlow ~> zip.in2

      // output -> 15 30 45 60 75
      zip.out ~> output

      ClosedShape
    }

  def main(args: Array[String]): Unit = {
    RunnableGraph.fromGraph(graph).run()
  }
}